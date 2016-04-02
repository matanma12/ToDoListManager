package todolist.huji.ac.il.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Date;

public class TodoListManagerActivity extends AppCompatActivity {

    private ArrayList<Task> mTodoList = new ArrayList<>();
    private MyAdapter mAdapter;
    private ListView mTodoListView;
    private Firebase myFirebaseRef;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://todolistm.firebaseio.com/");
        myFirebaseRef.child("message1").setValue("MatanMaman");
        db = new DBHandler(getApplicationContext());

        mTodoList = db.getAllTasks();
        mTodoListView = (ListView) findViewById(R.id.lstTodoItems);
        mAdapter = new MyAdapter(this, mTodoList);
        mTodoListView.setAdapter(mAdapter);
        mTodoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//delete an audio
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           final int pos, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
                dialogBuilder.setTitle(mTodoList.get(pos).title);
                dialogBuilder.setNegativeButton(R.string.onDelete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteTask(mTodoList.get(pos));
                        mTodoList.remove(pos);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                if (mTodoList.get(pos).title.startsWith(getString(R.string.onCall))) {
                    dialogBuilder.setPositiveButton(mTodoList.get(pos).title, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent dial = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:" + mTodoList.get(pos).title));
                            startActivity(dial);
                        }
                    });
                }
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
        Intent Add = new Intent(this, AddNewTodoItemActivity.class);
        startActivityForResult(Add, 0);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title = data.getStringExtra(getString(R.string.title));
        Date utilDate = (Date)data.getSerializableExtra(getString(R.string.dueDate));
        if(utilDate != null && title != null && !title.isEmpty()){
            Task newTask = new Task(title,utilDate);
            db.insertTask(newTask);
            mTodoList.add(newTask);
            mAdapter.notifyDataSetChanged();
        }

    }
}