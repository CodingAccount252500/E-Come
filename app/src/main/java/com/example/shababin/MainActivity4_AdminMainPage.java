package com.example.shababin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;

public class MainActivity4_AdminMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4_admin_main_page);
        FirebaseApp.initializeApp(getApplicationContext());
    }

    public void Orders(View view) {
        Intent go=new Intent(getApplicationContext(),MainActivity7_AdminManageOrders.class);
        startActivity(go);
    }

    public void Logout(View view) {
        Intent go=new Intent(getApplicationContext(),MainActivity2_Login.class);
        startActivity(go);
    }

    public void Items(View view) {
        Intent go=new Intent(getApplicationContext(),MainActivity6_AdminMangeItem.class);
        startActivity(go);
    }
}