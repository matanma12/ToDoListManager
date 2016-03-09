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

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mTodos;

    public MyAdapter(Context context, ArrayList<String> tasks) {
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
        TextView item = (TextView) convertView.findViewById(R.id.taskItem);
        item.setText(mTodos.get(position));
        if(position % 2 == 0) {
            item.setTextColor(Color.RED);
        } else {
            item.setTextColor(Color.BLUE);
        }
        return convertView;
    }
}
