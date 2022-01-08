package com.example.shababin;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String phone;
    public String password;
    public String roleType;
    public String profileImage;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String phone,String password,String roleType,String image) {
        this.username = username;
        this.email = email;
        this.phone=phone;
        this.password=password;
        this.roleType=roleType;
        this.profileImage=image;
    }

}
