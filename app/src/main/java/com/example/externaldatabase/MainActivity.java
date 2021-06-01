package com.example.externaldatabase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQ_CODE =1 ;
    private FloatingActionButton floatingActionButton ;
       private Toolbar toolbar;
       private RecyclerView recyclerView;
       private RecAdapterForCar recAdapterForCar;
       private AccessDatabase accessDatabase;
       public final static int REQ_CODE_SAVE  = 1;
       public final static int REQ_CODE_EDITE  = 2;
       public final static String CAR_ID  = "carId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    String permissions [] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                 ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQ_CODE);
                }

                floatingActionButton  = findViewById(R.id.floatingactionbtn_addcar);
                toolbar = findViewById(R.id.mainactivity_toolbar);
                recyclerView = findViewById(R.id.mainactivity_recyclerview);
               setSupportActionBar(toolbar);
               accessDatabase = accessDatabase.getInstance(this);
                accessDatabase.open();
               ArrayList<Car> carsArrayList = accessDatabase.getAllCars();
                 accessDatabase.close();
               recAdapterForCar = new RecAdapterForCar(carsArrayList, new OnRecViewItemLisiner() {
                   @Override
                   public void onItemClick(int carId) {
                       Intent intent = new Intent(MainActivity.this,Info_activity.class);
                   intent.putExtra(CAR_ID,carId);
                   startActivityForResult(intent,REQ_CODE_EDITE);


                   }
               });


                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recAdapterForCar);

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,Info_activity.class);
                        startActivityForResult(intent,REQ_CODE_SAVE);
                    }
                });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater menuInflater =getMenuInflater();
       menuInflater.inflate(R.menu.mainactivity_menu,menu);
       SearchView searchView =(SearchView) menu.findItem(R.id.mainactivity_menu_search).getActionView();
        // فنكشن تعمل علي وضع ايقون داخل السيرش
       searchView.setSubmitButtonEnabled(true);

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               accessDatabase.open();
               ArrayList<Car> cars = accessDatabase.getCars(query);
               accessDatabase.close();
               recAdapterForCar.setCars(cars);
               recAdapterForCar.notifyDataSetChanged();
               return true;
           }


           @Override
           public boolean onQueryTextChange(String newText) {
               accessDatabase.open();
               ArrayList<Car> cars = accessDatabase.getCars(newText);
               accessDatabase.close();
               recAdapterForCar.setCars(cars);
               recAdapterForCar.notifyDataSetChanged();
               return false;
           }
       });

       searchView.setOnCloseListener(new SearchView.OnCloseListener() {
           @Override
           public boolean onClose() {
               accessDatabase.open();
               ArrayList<Car> cars = accessDatabase.getAllCars();
               accessDatabase.close();
               recAdapterForCar.setCars(cars);
               recAdapterForCar.notifyDataSetChanged();
               return true;
           }
       });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE_SAVE || requestCode == REQ_CODE_EDITE)
        {
            accessDatabase.open();
            ArrayList<Car> carsArrayList = accessDatabase.getAllCars();
            accessDatabase.close();
            recAdapterForCar.setCars(carsArrayList);
            // to referch the data
            recAdapterForCar.notifyDataSetChanged();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ_CODE:
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }


    }


}
