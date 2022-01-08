package com.example.shababin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivity10_AdminAddItem2 extends AppCompatActivity {

    BootstrapEditText priceField;
    ArrayList<String>colors=new ArrayList<String>();
    ArrayList<String>sizing=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity10_admin_add_item2);
        FirebaseApp.initializeApp(getApplicationContext());
    }


}