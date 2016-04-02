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

    public void insertTodo(Task todo)
    {
        ContentValues newTodo = new ContentValues();
        newTodo.put(KEY_TODO_STR,todo.title);
        newTodo.put(KEY_DATE,todo.dateToString());
        Long ret = db.insertOrThrow(MAIN_TABLE_NAME, null, newTodo);
        Log.i("todo","insertTodo returned with: " + ret);
    }

    public void deleteTodo(Task todo) {
/*        String sqlComandDeleteTodo = "DELETE FROM " + MAIN_TABLE_NAME + " WHERE " + KEY_TODO_STR + "=" + todo.getTodo();
        db.execSQL(sqlComandDeleteTodo);*/
        int ret = db.delete(MAIN_TABLE_NAME, KEY_TODO_STR + " = '" + todo.title+"'", null);
        Log.i("todo","deleteTodo returned with: " + ret);

    }

    public ArrayList<Task> getAllTodos() {
        ArrayList<Task> allTodos = new ArrayList<>();

        String selectAllTodos = "SELECT * FROM " + MAIN_TABLE_NAME;
        Cursor crs = db.rawQuery(selectAllTodos, null);
        if(crs.getCount() == 0)
        {
            Log.i("todo", "getAllTodos: No stories in db.");
            crs.close();
            return allTodos;
        }
        crs.moveToFirst();
        for(int i = 0; i <  crs.getCount(); i++) {
            String todoStr = crs.getString(0);
            String datestr = crs.getString(1);
            Date date = parseDate(datestr);
            Task newTodo = new Task(todoStr, date);
            allTodos.add(newTodo);
            crs.moveToNext();
        }
        crs.close();
        return allTodos;
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
