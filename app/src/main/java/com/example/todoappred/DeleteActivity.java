package com.example.todoappred;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoappred.DatabaseManager;
import com.google.android.material.internal.NavigationMenuItemView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DeleteActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private Object Date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);

        try {
            updateView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateView() throws ParseException {
        ArrayList<ToDo> tasks = dbManager.selectAll();
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup group = new RadioGroup(this);

        for (ToDo td : tasks) {
            RadioButton rb = new RadioButton(this);
            rb.setId(td.getId());
            rb.setText(td.toString());
            group.addView(rb);

           /* Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
           // Date deadline = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

            String deadlineAsString = dateFormat.format(R.id.deadlineET);

            calendar = Calendar.getInstance();
            Date today = calendar.getTime();

            String todayAsString = dateFormat.format(today);

            if(deadlineAsString.equals(todayAsString))
            {System.out.println(true);
                rb.setTextColor(Color.BLACK);}
            else if(deadlineAsString != (todayAsString))
            {System.out.println(false);
                rb.setTextColor(Color.RED);}



            /*SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String valid_until = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());

            Date strDate = null;
            try {
                strDate = sdf.parse(valid_until);
            } catch (ParseException e) {
                e.printStackTrace();
            }

           if (new Date().after(strDate)) {
                 rb.setTextColor(Color.RED);
            }
            else{rb.setTextColor(Color.BLACK);}*/
            /*{
                // current date
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String string1 = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                TextView date = new TextView(this);
                date.setText(string1);
                Date currentDate = new Date();
                try{
                    currentDate = sdf.parse(string1);
                }catch(ParseException e){
                    e.printStackTrace();
                }

                // due date
                Date dueDate = new Date();
                try{
                    dueDate = sdf.parse(ToDo.getDueDate()); // create a second date object
                }catch(ParseException e){
                    e.printStackTrace();
                }

                // compare the dates
                int diff = currentDate.compareTo(dueDate);
                if (diff > 0) {rb.setTextColor(Color.RED);} // past due = red
                else{rb.setTextColor(Color.BLACK);} // not past due = black

            }*/



        }


        // set up event handling
        RadioButtonHandler rbh = new RadioButtonHandler( );
        group.setOnCheckedChangeListener(rbh);

        // create a back button
        Button backButton = new Button( this );
        backButton.setText("Back");

        backButton.setOnClickListener( new View.OnClickListener( ) {
            public void onClick(View v) {
                DeleteActivity.this.finish();
            }
        });

        scrollView.addView(group);
        layout.addView( scrollView );

        // add back button at bottom
        RelativeLayout.LayoutParams params
                = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT );
        params.addRule( RelativeLayout.ALIGN_PARENT_BOTTOM );
        params.addRule( RelativeLayout.CENTER_HORIZONTAL );
        params.setMargins( 0, 0, 0, 50 );
        layout.addView( backButton, params );

        setContentView( layout );
    }

   /* private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String string1 = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        TextView date = new TextView(this);
        date.setText(string1);

        Date currentDate = new Date();
        try{
            currentDate = sdf.parse(string1);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return getCurrentDate().toString();
    }*/



    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            // delete item from database
            dbManager.deleteById( checkedId );
            Toast.makeText( DeleteActivity.this, "ToDo deleted",
                    Toast.LENGTH_SHORT ).show( );

            // update screen
            try {
                updateView( );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}