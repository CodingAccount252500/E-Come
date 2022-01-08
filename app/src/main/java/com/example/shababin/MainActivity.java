package com.example.shababin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public CircleImageView logo;
    public TextView appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo=(CircleImageView)findViewById(R.id.logoImage);
        appName=(TextView)findViewById(R.id.applicationName);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        // Make Animation on Screen Content
        SetAnimation();
        //Activation Screen
        ApplayThread();

    }
    private void SetAnimation(){
        logo.setY(-4200);
        appName.setX(-3500);
        logo.animate().translationYBy(4200).alpha(1).setDuration(3500);
        appName.animate().translationXBy(3500).alpha(1).rotation(360).setDuration(3500);
    }
    private void ApplayThread(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity13_UserCategoryList.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}