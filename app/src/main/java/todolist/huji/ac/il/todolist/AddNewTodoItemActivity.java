package todolist.huji.ac.il.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewTodoItemActivity extends Activity {
    private EditText title;
    private DatePicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);
        title = (EditText)findViewById(R.id.edtNewItem);
        picker = (DatePicker)findViewById(R.id.datePicker);
    }


    public void onOkClick(View v){

        String task = title.getText().toString();

        int year = picker.getYear();
        int month = picker.getMonth();
        int day = picker.getDayOfMonth();
        month+=2;//adjust to reality
        String date = year + "/" + month + "/" + day;
        java.util.Date utilDate = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.dateFormat));
            utilDate = formatter.parse(date);
        } catch (ParseException e) {
            title.setHint(R.string.dateErrorNotice);
        }
        Intent back = new Intent(this,TodoListManagerActivity.class);
        back.putExtra(getString(R.string.title), task);
        back.putExtra(getString(R.string.dueDate), utilDate);
        if (getParent() == null) {
            setResult(Activity.RESULT_OK, back);
        } else {
            getParent().setResult(Activity.RESULT_OK, back);
        }
        finish();

    }

    public void onCancelClick(View v){
        Intent back = new Intent(this,TodoListManagerActivity.class);
        startActivity(back);
    }

}
