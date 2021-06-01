package com.example.externaldatabase;

public class Car {
    private  int id;
    private  String model ,
                    color,
                    image,
                    description;
    private float dpl;


    public Car(int id, String model, String color, String image, String description, float dpl) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.image = image;
        this.description = description;
        this.dpl = dpl;
    }


    public Car( String model, String color, String image, String description, float dpl) {
        this.model = model;
        this.color = color;
        this.image = image;
        this.description = description;
        this.dpl = dpl;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDpl() {
        return dpl;
    }

    public void setDpl(float dpl) {
        this.dpl = dpl;
    }
}
