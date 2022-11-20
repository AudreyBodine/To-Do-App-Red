package com.example.todoappred;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "toDoDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TODO = "do";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String DEADLINE = "deadline";


    public DatabaseManager(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // build a SQL create statement
        String sqlCreate = "create table " + TABLE_TODO + "( " + ID;
        sqlCreate += " integer primary key autoincrement, " + ITEM;
        sqlCreate += " text, " + DEADLINE + " character )" ;


        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL( "drop table if exists " + TABLE_TODO );
        onCreate( db );
    }

    public void insert (ToDo td){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_TODO;
        sqlInsert += " values( null, '" + td.getItem( );
        sqlInsert += "', '" + td.getDeadline( ) + "' )";


        db.execSQL( sqlInsert );
        db.close( );
    }

    public ArrayList<ToDo> selectAll() {

        String sqlQuery = "select * from " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<ToDo> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            ToDo currentToDo
                    = new ToDo(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));

            tasks.add(currentToDo);
        }
        db.close();
        return tasks;
    }


    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlDelete = "delete from " + TABLE_TODO;
        sqlDelete += " where " + ID + " = " + id;

        db.execSQL( sqlDelete );
        db.close( );
    }
}
