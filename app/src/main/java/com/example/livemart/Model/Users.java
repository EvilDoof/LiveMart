package com.example.livemart.Model;

public class Users {
    private String name, email, phone, password, image, address;

    public Users()
    {

    }

    public Users(String name, String email, String phone, String password) { //, String image, String address
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) { this.email = email; }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
