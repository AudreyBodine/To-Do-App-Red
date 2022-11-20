package com.example.todoappred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ScrollView scrollView = null;
    private int buttonWidth;
    private DatabaseManager dbManager;
    Date dueDate;
    Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = findViewById( R.id.toolbar );
       // setSupportActionBar( toolbar );
        dbManager = new DatabaseManager(this);
        scrollView = findViewById(R.id.scrollView);
        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        buttonWidth = size.x / 2;
        updateView();
    }

    protected void onResume( ) {
        super.onResume( );
        updateView( );
    }

    public void updateView( ) {
        ArrayList<ToDo> tasks = dbManager.selectAll( );
        if( tasks.size( ) > 0 ) {
            // remove subviews inside scrollView if necessary
            scrollView.removeAllViewsInLayout();

            // set up the grid layout
            GridLayout grid = new GridLayout(this);
            grid.setRowCount((tasks.size() + 1) / 2);
            grid.setColumnCount(2);

            // create array of buttons, 2 per row
            ToDoButton[] buttons = new ToDoButton[tasks.size()];
            //ButtonHandler bh = new ButtonHandler( );

            // fill the grid
            int i = 0;
            for (ToDo td : tasks) {
                // create the button
                buttons[i] = new ToDoButton(this, td);
                buttons[i].setText(td.getItem()
                        + "\n" + td.getDeadline());

                // set up event handling
                //buttons[i].setOnClickListener( bh );

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                // current date
                String todayDateString = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                TextView date = new TextView(this);
                date.setText(todayDateString);
                currentDate = new Date();
                try {
                    currentDate = sdf.parse(todayDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // due date
                dueDate = new Date();
                try {
                    dueDate = sdf.parse(td.getDeadline()); // create a second date object
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // compare the dates
                int diff = currentDate.compareTo(dueDate);
                if (diff > 0) {buttons[i].setTextColor(Color.RED);} // past due = red
                else{buttons[i].setTextColor(Color.BLACK);} // not past due = black

                // add the button to grid
                grid.addView(buttons[i], buttonWidth,
                        GridLayout.LayoutParams.WRAP_CONTENT);
                i++;
            }
            scrollView.addView(grid);

           /* for (ToDo td : tasks) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                // current date
                String todayDateString = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                TextView date = new TextView(this);
                date.setText(todayDateString);
                currentDate = new Date();
                try {
                    currentDate = sdf.parse(todayDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // due date
                dueDate = new Date();
                try {
                    dueDate = sdf.parse(// TODO: 11/19/22  .getDeadline()); // create a second date object
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // compare the dates
                int diff = currentDate.compareTo(dueDate);
                if (diff > 0) {.setTextColor(Color.RED);} // past due = red
                else{.setTextColor(Color.BLACK);} // not past due = black
            }*/
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Log.w("MainActivity", "Add Selected");
                Intent insertIntent = new Intent(this, InsertActivity.class);
                this.startActivity(insertIntent);
                return true;
            case R.id.action_delete:
                Log.w("MainActivity", "Delete Selected");
                Intent deleteIntent = new Intent(this, DeleteActivity.class);
                this.startActivity(deleteIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}