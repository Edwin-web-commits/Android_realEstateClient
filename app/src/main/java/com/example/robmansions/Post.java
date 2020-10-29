package com.example.robmansions;

public class Post {


    private String ViewType;
    private String Location;
    private String Image;
    private String Price;

    Post(){};

    public Post(String viewType, String location, String image, String price) {
        ViewType = viewType;
        Location = location;
        Image = image;
        Price=price;
    }

    public String getViewType() {
        return ViewType;
    }

    public void setViewType(String viewType) {
        ViewType = viewType;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
