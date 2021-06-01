package com.example.externaldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class AppDatabase extends SQLiteOpenHelper {
    public  static final   int DB_VEWRSION=1;
    public static final String DB_NAME="Cars_DB";
    public  static  final String Car_CLN_id = "id";
    public static final String Car_table_NAME="car";
    public static final String Car_CLN_Modle="modle";
    public static final String Car_CLN_Color="color";
    public static final String Car_CLN_DPL= "distanceperleter" ;
    public AppDatabase(Context context)
    {
        super(context,DB_NAME,null,DB_VEWRSION);
    }
    /*@Override
    public void onCreate(SQLiteDatabase db) {
        //يتم استدعاها عند انشاء الداتا بيز مره واحده فقط
        db.execSQL("create table "+Car_table_NAME+" ("+Car_CLN_id+" INTEGER  primary key autoincrement , "+Car_CLN_Modle+" TEXT ,  "+Car_CLN_Color+" TEXT , "+Car_CLN_DPL+" REAL  )");



    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //يتم استدعاها عند تحديث الاصدار
        db.execSQL("drop table if exists "+Car_table_NAME);
        onCreate(db);

    }

    public  boolean insertCar(Car car, Context c)
    {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(Car_CLN_Modle,car.getModel());
        values.put(Car_CLN_Color,car.getColor());
        values.put(Car_CLN_DPL,car.getDpl());
        long result =  sd.insert(Car_table_NAME,null,values);
        return  result!=-1;

    }


    public  boolean updateCar(Car car)
    {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(Car_CLN_Modle,car.getModel());
        values.put(Car_CLN_Color,car.getColor());
        values.put(Car_CLN_DPL,car.getDpl());
        String args[] = {String.valueOf(car.getId())};
        int result = sd.update(Car_table_NAME,values,"Id = ?",args);
        return  result>0;

    }


    public  long getCarsCount()
    {
        SQLiteDatabase sd = getReadableDatabase();

        return DatabaseUtils.queryNumEntries(sd,Car_table_NAME);


    }


    public  boolean deleteCar(Car car)
    {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(Car_CLN_Modle,car.getModel());
        values.put(Car_CLN_Color,car.getColor());
        values.put(Car_CLN_DPL,car.getDpl());
        String args[] = {String.valueOf(car.getModel())};
        int result = sd.delete(Car_table_NAME,"modle=?",args);
        return  result!=0;

    }

 public ArrayList<Car> getAllCars()
 {
     SQLiteDatabase sd = getReadableDatabase();
     ArrayList<Car> cars = new ArrayList<>();
     Cursor cursor= sd.rawQuery("select * from "+Car_table_NAME,null);

     while (cursor.moveToNext())
     {
         int id = cursor.getInt(cursor.getColumnIndex(Car_CLN_id));
         String model = cursor.getString(cursor.getColumnIndex(Car_CLN_Modle));
         String color = cursor.getString(cursor.getColumnIndex(Car_CLN_Color));
         double dpl = cursor.getDouble(cursor.getColumnIndex(Car_CLN_DPL));
        // Car car = new Car(id,model,color,dpl);
         //cars.add(car);

     }
     cursor.close();
     return cars;

 }




    public ArrayList<Car> getCars(String ModleSerach)
    {
        SQLiteDatabase sd = getReadableDatabase();
        ArrayList<Car> cars = new ArrayList<>();
        String args [] = {"%"+ModleSerach};
        Cursor cursor= sd.rawQuery("select * from "+Car_table_NAME+ " where "+Car_CLN_Modle +" like?",args);

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex(Car_CLN_id));
            String model = cursor.getString(cursor.getColumnIndex(Car_CLN_Modle));
            String color = cursor.getString(cursor.getColumnIndex(Car_CLN_Color));
            double dpl = cursor.getDouble(cursor.getColumnIndex(Car_CLN_DPL));
           // Car car = new Car(id,model,color,dpl);
           // cars.add(car);

        }
        cursor.close();
        return cars;

    }
}