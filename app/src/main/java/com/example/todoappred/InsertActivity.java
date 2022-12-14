package com.example.todoappred;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InsertActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    Date deadline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_insert);
    }

    public void insert(View view) throws ParseException {
        // Retrieve to-do list item
        Log.w("InsertActivity", "Insert Button Pushed");
        EditText itemET = findViewById(R.id.list_itemET);
        EditText deadlineET = findViewById(R.id.deadlineET);
        String item = itemET.getText().toString();
        String deadline = deadlineET.getText().toString();

        // Insert into database
        //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       // deadline = sdf.parse(deadlineString);
        ToDo td = new ToDo(0, item, deadline);
        dbManager.insert(td);
        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();

        // Clear data
        itemET.setText("");
        deadlineET.setText("");
    }

    public void goBack(View view) {
        this.finish();

    }
}


