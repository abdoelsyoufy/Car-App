package com.example.externaldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class mydatabase  extends SQLiteAssetHelper {

    public  static final   int DB_VERSION=2;
    public static final String DB_NAME="cars.db";
    public  static  final String Car_CLN_id = "Id";
    public static final String Car_table_NAME="car";
    public static final String CAR_CLN_MODEL ="Model";
    public static final String Car_CLN_Color="color";
    public static final String Car_CLN_DPL= "distanceperleter" ;
    public static final String Car_CLN_Description= "Descriptrion" ;
    public static final String Car_CLN_image= "image" ;


    public  mydatabase (Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }


//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table "+Car_table_NAME+" ("+Car_CLN_id+" INTEGER  primary key autoincrement , "+CAR_CLN_MODEL+" TEXT ,  "+Car_CLN_Description+" TEXT ,  "+Car_CLN_image+" TEXT , "+Car_CLN_DPL+" REAL ,"+Car_CLN_Color+" TEXT  )");
//
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
