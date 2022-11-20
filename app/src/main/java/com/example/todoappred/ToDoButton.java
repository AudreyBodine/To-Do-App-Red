package com.example.todoappred;

import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;

import java.util.Date;

public class ToDoButton extends AppCompatButton {
    private ToDo td;

    public ToDoButton(Context context, ToDo newTask ) {
        super( context );
        td = newTask;
    }

    public String getDeadline( ) {
        return td.getDeadline( );
    }
}

