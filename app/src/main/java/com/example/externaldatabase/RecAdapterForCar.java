package com.example.externaldatabase;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapterForCar extends RecyclerView.Adapter<RecAdapterForCar.RecViewHolder> {

private  ArrayList<Car> cars;
private  OnRecViewItemLisiner lisiner;
    public RecAdapterForCar(ArrayList<Car> cars , OnRecViewItemLisiner lisiner) {
        this.cars = cars;
        this.lisiner = lisiner;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout,null,false);
               RecViewHolder recViewHolder = new RecViewHolder(v);

        return recViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        Car car = cars.get(position);
       holder.tv_model.setText(car.getModel());
       holder.tv_color.setText(car.getColor());
       holder.tv_dpl.setText(car.getDpl()+"");
      if(car.getImage()!=null && !car.getImage().isEmpty())
           if(car.getImage()!=null&&!car.getImage().equals(""))
       holder.iv_image.setImageURI(Uri.parse(car.getImage()));
       holder.id = car.getId();

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class  RecViewHolder extends RecyclerView.ViewHolder
    {
       TextView tv_model , tv_color , tv_dpl; ImageView iv_image;
       int id;
        public RecViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_model = itemView.findViewById(R.id.customcar_tv_model);
            tv_color = itemView.findViewById(R.id.customcar_tv_color);
            tv_dpl = itemView.findViewById(R.id.customcar_tv_dpl);
            iv_image = itemView.findViewById(R.id.customCar_im);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //int id = (int)iv_image.getTag();
                    lisiner.onItemClick(id);
                }
            });

        }
    }
}
