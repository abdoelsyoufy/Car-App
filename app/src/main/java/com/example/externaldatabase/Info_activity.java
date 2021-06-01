package com.example.externaldatabase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public class Info_activity extends AppCompatActivity {
    public static final int RESULT_CODE_FOR_ADD = 2;
    public static final int RESULT_CODE_FOR_EDIT = 3;
    private TextInputEditText ed_model,ed_description,ed_color,ed_dpl;
    private ImageView imageView;
    private Toolbar toolbar;
    private AccessDatabase accessDatabase;
    public  static int CarId = -1;
    private final int PICK_IMAGE_REQ_CODE=1;
    private  Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        ed_model = findViewById(R.id.info_ed_Model);
        ed_color = findViewById(R.id.info_ed_color);
        ed_description = findViewById(R.id.info_ed_descrpition);
        ed_dpl = findViewById(R.id.info_ed_DPL);
        imageView = findViewById(R.id.infoactivity_carimage);
        toolbar = findViewById(R.id.infoactivity_toolbar);
        setSupportActionBar(toolbar);
        accessDatabase = accessDatabase.getInstance(this);
        Intent intent = getIntent();
        CarId = intent.getIntExtra(MainActivity.CAR_ID, -1);
        if (CarId == -1) {
            //insert car
            enableFieled();
            clearFieled();

        } else {
            // update car
            disableFieled();
            accessDatabase.open();
            Car car = accessDatabase.getCar(CarId);
            accessDatabase.close();
            if (car != null) {
                fillFieledCar(car);
            }

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentgetimage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentgetimage,PICK_IMAGE_REQ_CODE);
            }
        });



    }

public void fillFieledCar(Car car)
{
    ed_model.setText(car.getModel());
    ed_color.setText(car.getColor());
    ed_dpl.setText(car.getDpl() + "");
    ed_description.setText(car.getDescription());
    if(car.getImage()!=null&&!car.getImage().equals(""))
    imageView.setImageURI(Uri.parse(car.getImage()));
}


    public void disableFieled()
    {
        ed_color.setEnabled(false);
        ed_description.setEnabled(false);
        ed_dpl.setEnabled(false);
        ed_model.setEnabled(false);
        imageView.setEnabled(false);

    }

    public void enableFieled()
    {
        ed_color.setEnabled(true);
        ed_description.setEnabled(true);
        ed_dpl.setEnabled(true);
        ed_model.setEnabled(true);
        imageView.setEnabled(true);

    }

    public void clearFieled()
    {
        ed_color.setText("");
        ed_description.setText("");
        ed_dpl.setText("");
        ed_model.setText("");
        imageView.setImageURI(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.infoactivity_menu,menu);
        MenuItem save = menu.findItem(R.id.infoactivity_menu_save);
        MenuItem edit = menu.findItem(R.id.infoactivity_menu_edit);
        MenuItem delete = menu.findItem(R.id.infoactivity_menu_delete);
        if(CarId == -1)
        {
            //insert car
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);

        }

        else
        {
            //update car
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String model , color , descrip , image="";
        float dpl;
        switch (item.getItemId())
        {

            case R.id.infoactivity_menu_save:
                model = ed_model.getText().toString();
                color = ed_color.getText().toString();
                descrip = ed_description.getText().toString();
                dpl = Float.parseFloat(ed_dpl.getText().toString());

                accessDatabase.open();
                boolean result;

                if(CarId == -1) {
                    if(imageUri!=null&&!imageUri.equals(""))
                        image = imageUri.toString();
                    Car car = new Car(CarId,model,color,image,descrip,dpl);
                    result = accessDatabase.insertCar(car);
                    if(result)
                    Toast.makeText(this, "car added successfully", Toast.LENGTH_LONG).show();
                    setResult(RESULT_CODE_FOR_ADD,null);
                }
                else
                {
                   if(imageUri!=null&&!imageUri.equals(""))
                        image = imageUri.toString();
                    Car car = new Car(CarId,model,color,image,descrip,dpl);
                    result = accessDatabase.updateCar(car);
                    if(result)
                    Toast.makeText(this, "car updated successfully", Toast.LENGTH_LONG).show();
                    setResult(RESULT_CODE_FOR_EDIT,null);
                }
                accessDatabase.close();
                 finish();
                return true;
            case R.id.infoactivity_menu_edit:
                enableFieled();
                MenuItem save = toolbar.getMenu().findItem(R.id.infoactivity_menu_save);
                MenuItem edit =  toolbar.getMenu().findItem(R.id.infoactivity_menu_edit);
                MenuItem delete =  toolbar.getMenu().findItem(R.id.infoactivity_menu_delete);
                save.setVisible(true);
                edit.setVisible(false);
                delete.setVisible(false);
                return true;
            case R.id.infoactivity_menu_delete:
                accessDatabase.open();
               boolean res = accessDatabase.deleteCar(CarId);
               accessDatabase.close();
               if(res) {
                   Toast.makeText(this, "car deleted successfully", Toast.LENGTH_SHORT).show();
                   setResult(RESULT_CODE_FOR_EDIT,null);
                   finish();
               }
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQ_CODE&&resultCode==RESULT_OK)
        {
            if(data!=null)
            {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }

    }
}