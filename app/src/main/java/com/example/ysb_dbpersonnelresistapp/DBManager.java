package com.example.ysb_dbpersonnelresistapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context) {
        super(context,"PersonnelDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table Personnel(" +
                "name text,"+
                "studentid text," +
                "grade text,"+
                "phoneno text,"+
                "hobby text,"+
                "area text,"+
                "photo text)" );

    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db,int i,int i1){

    }


}//class

