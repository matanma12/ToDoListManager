package todolist.huji.ac.il.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TodoListManagerActivity extends AppCompatActivity {

    private ArrayList<String> mTodoList = new ArrayList<>();
    private MyAdapter mAdapter;
    private ListView mTodoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        mTodoListView = (ListView) findViewById(R.id.lstTodoItems);
        mAdapter = new MyAdapter(this, mTodoList);
        mTodoListView.setAdapter(mAdapter);
        mTodoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//delete an audio
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           final int pos, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
                dialogBuilder.setTitle(mTodoList.get(pos));
                dialogBuilder.setPositiveButton("Delete Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTodoList.remove(pos);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText newTask = (EditText) findViewById(R.id.edtNewItem);
        String task = newTask.getText().toString();
        newTask.setText("");
        mTodoList.add(task);
        mAdapter.notifyDataSetChanged();
        return true;
    }
}