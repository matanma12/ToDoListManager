package todolist.huji.ac.il.todolist;

/**
 * Created by Matan on 3/9/2016.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Task> mTodos;

    public MyAdapter(Context context, ArrayList<Task> tasks) {
        mContext = context;
        mTodos = tasks;
    }

    @Override
    public int getCount() {
        return mTodos.size();
    }

    @Override
    public Object getItem(int position) {
        return mTodos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.task,parent,false);
        TextView title = (TextView) convertView.findViewById(R.id.txtTodoTitle);
        title.setText((mTodos.get(position)).title);
        TextView date = (TextView) convertView.findViewById(R.id.txtTodoDueDate);
        if((mTodos.get(position)).date == null) {
            date.setText(R.string.dateIsNull);
        }
        else {
            Calendar cal = Calendar.getInstance();
            cal.setTime((mTodos.get(position)).date);
            int taskYear = cal.get(Calendar.YEAR);
            int taskMonth = cal.get(Calendar.MONTH);
            int taskDay = cal.get(Calendar.DAY_OF_MONTH);
            Date toDay = new Date();
            cal.setTime(toDay);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            date.setText(mTodos.get(position).dateToString());
            if (taskYear == year&& taskMonth == (month+1) && taskDay == day) {
                title.setTextColor(Color.RED);
                date.setTextColor(Color.RED);
            } else {
                title.setTextColor(Color.BLUE);
                date.setTextColor(Color.BLUE);
            }
        }
        return convertView;
    }
}
