package com.example.todoappred;

import android.widget.EditText;

public class ToDo {

    private int id;
    private String item;
    private String deadline;

    public ToDo(int newId, String newItem, String newDeadline){
        setID(newId);
        setItem(newItem);
        setDeadline(newDeadline);
    }



    private void setID(int newId) {
        id = newId;
    }

    private void setItem(String newItem) {
        item = newItem;
    }

    private void setDeadline(String newDeadline) {
        deadline = newDeadline;
    }

    public String getItem() {
        return item;
    }

    public String toString(){
        return id + "; " + item + "; " + deadline;
    }

    public int getId() {
        return id;
    }

    public String getDeadline() {
        return deadline;
    }

}
