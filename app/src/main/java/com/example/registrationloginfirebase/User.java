package com.example.registrationloginfirebase;

public class User {

     String name;
     String phone;
     String user_id;


    public User(String name, String phone, String user_id) {
        this.name = name;
        this.phone = phone;
        this.user_id = user_id;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
