package com.example.robmansions;

public class testiClass {

    private String Image,Message, Username;
    testiClass(){}

    public testiClass(String image, String message, String username) {
        Image = image;
        Message = message;
        Username = username;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
