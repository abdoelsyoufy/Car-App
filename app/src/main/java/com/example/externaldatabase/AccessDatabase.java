package com.example.externaldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AccessDatabase {
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private static AccessDatabase instance;

    private  AccessDatabase (Context context)
    {
        this.sqLiteOpenHelper = new mydatabase(context);
    }

    public  static  AccessDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance = new AccessDatabase(context);
        }
        return instance;
    }


    public  void  open()
    {
        this.sqLiteDatabase = this.sqLiteOpenHelper.getWritableDatabase();
    }
    public  void  close()
    {
if(this.sqLiteDatabase !=null)
{
    this.sqLiteDatabase.close();
}

    }

    public  boolean insertCar(Car car)
    {

        ContentValues values =  new ContentValues();
        values.put(mydatabase.CAR_CLN_MODEL,car.getModel());
        values.put(mydatabase.Car_CLN_Color,car.getColor());
        values.put(mydatabase.Car_CLN_DPL,car.getDpl());
        values.put(mydatabase.Car_CLN_Description,car.getDescription());
        values.put(mydatabase.Car_CLN_image,car.getImage());

        long result =  sqLiteDatabase.insert(mydatabase.Car_table_NAME,null,values);
        return  result!=-1;

    }

    public  boolean updateCar(Car car)
    {
        ContentValues values =  new ContentValues();
        values.put(mydatabase.CAR_CLN_MODEL,car.getModel());
        values.put(mydatabase.Car_CLN_Color,car.getColor());
        values.put(mydatabase.Car_CLN_DPL,car.getDpl());
        values.put(mydatabase.Car_CLN_Description,car.getDescription());
        values.put(mydatabase.Car_CLN_image,car.getImage());
        String args[] = {String.valueOf(car.getId())};
        int result = sqLiteDatabase.update(mydatabase.Car_table_NAME,values,"Id = ?",args);
        return  result!=0;

    }


    public  long getCarsCount()
    {


        return DatabaseUtils.queryNumEntries(sqLiteDatabase,mydatabase.Car_table_NAME);


    }


    public  boolean deleteCar(int carid)
    {


        String args[] = {String.valueOf(carid)};
        int result = sqLiteDatabase.delete(mydatabase.Car_table_NAME,"Id=?",args);
        return  result!=0;

    }

    public ArrayList<Car> getAllCars()
    {

        ArrayList<Car> cars = new ArrayList<>();
        Cursor cursor= sqLiteDatabase.rawQuery("select * from "+mydatabase.Car_table_NAME,null);

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex(mydatabase.Car_CLN_id));
            String model = cursor.getString(cursor.getColumnIndex(mydatabase.CAR_CLN_MODEL));
            String color = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_Color));
            String image = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_image));
            String description = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_Description));
            float dpl = cursor.getFloat(cursor.getColumnIndex(mydatabase.Car_CLN_DPL));
            Car car = new Car(id,model,color,image,description,dpl);
            cars.add(car);

        }
        cursor.close();
        return cars;

    }




    public ArrayList<Car> getCars(String ModleSerach)
    {
        ArrayList<Car> cars = new ArrayList<>();
        String args [] = {"%"+ModleSerach+"%"};
        Cursor cursor= sqLiteDatabase.rawQuery("select * from "+mydatabase.Car_table_NAME+ " where "+mydatabase.CAR_CLN_MODEL +" like?",args);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex(mydatabase.Car_CLN_id));
            String model = cursor.getString(cursor.getColumnIndex(mydatabase.CAR_CLN_MODEL));
            String color = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_Color));
            String image = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_image));
            String description = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_Description));
            float dpl = cursor.getFloat(cursor.getColumnIndex(mydatabase.Car_CLN_DPL));
            Car car = new Car(id,model,color,image,description,dpl);
            cars.add(car);

        }
        cursor.close();
        return cars;

    }


    public Car  getCar(int carid)
    {


        String args [] = {String.valueOf(carid)};
        Cursor cursor= sqLiteDatabase.rawQuery("select * from "+mydatabase.Car_table_NAME+ " where "+mydatabase.Car_CLN_id +" =?",args);
if(cursor!=null && cursor.moveToNext()) {
    int id = cursor.getInt(cursor.getColumnIndex(mydatabase.Car_CLN_id));
    String model = cursor.getString(cursor.getColumnIndex(mydatabase.CAR_CLN_MODEL));
    String color = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_Color));
    String image = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_image));
    String description = cursor.getString(cursor.getColumnIndex(mydatabase.Car_CLN_Description));
    float dpl = cursor.getFloat(cursor.getColumnIndex(mydatabase.Car_CLN_DPL));
    Car car = new Car(id, model, color, image, description, dpl);
    cursor.close();
    return car;
}
return null;
    }
}
