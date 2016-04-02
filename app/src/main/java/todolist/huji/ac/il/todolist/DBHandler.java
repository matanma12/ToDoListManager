package todolist.huji.ac.il.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "todo_db";

    private SQLiteDatabase db;

    public static String MAIN_TABLE_NAME = "todo";
    public static String KEY_TODO_STR= "todoStr";
    public static String KEY_DATE= "todoDate";

    private static final String CREATE_TABLE_MAIN = "CREATE TABLE "
            + MAIN_TABLE_NAME + "(" + KEY_TODO_STR + " TEXT," + KEY_DATE
            + " TEXT)";



    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
         db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertTask(Task task)
    {
        ContentValues newTask = new ContentValues();
        newTask.put(KEY_TODO_STR, task.title);
        newTask.put(KEY_DATE, task.dateToString());
        Long ret = db.insertOrThrow(MAIN_TABLE_NAME, null, newTask);
        Log.i("todo","insertTask returned with: " + ret);
    }

    public void deleteTask(Task task) {
        int ret = db.delete(MAIN_TABLE_NAME, KEY_TODO_STR + " = '" + task.title+"'", null);
        Log.i("todo","deleteTask returned with: " + ret);

    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();

        String selectAllTasks = "SELECT * FROM " + MAIN_TABLE_NAME;
        Cursor crs = db.rawQuery(selectAllTasks, null);
        if(crs.getCount() == 0)
        {
            Log.i("todo", "getAllTasks: No stories in db.");
            crs.close();
            return allTasks;
        }
        crs.moveToFirst();
        for(int i = 0; i <  crs.getCount(); i++) {
            String title = crs.getString(0);
            String dateStr = crs.getString(1);
            Date date = parseDate(dateStr);
            Task newTask = new Task(title, date);
            allTasks.add(newTask);
            crs.moveToNext();
        }
        crs.close();
        return allTasks;
    }

    private Date parseDate(String datestr) {
        String[] dateSplitted = datestr.split("/");
        String dayStr = dateSplitted[1];
        String monthStr = dateSplitted[0];
        String yearStr =  dateSplitted[2];

        int day = Integer.parseInt(dayStr);
        int month = Integer.parseInt(monthStr);
        int year = Integer.parseInt(yearStr);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
