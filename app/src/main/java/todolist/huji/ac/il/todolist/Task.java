package todolist.huji.ac.il.todolist;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task {
    public String title;
    public Date date;


    public Task(String title, Date date){
        this.title = title;
        this.date = date;

    }

    public String dateToString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int mon = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return "" + mon + "/" + day + "/" + year;
    }


}
