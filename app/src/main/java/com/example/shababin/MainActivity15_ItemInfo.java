package com.example.shababin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;

import java.util.HashMap;

public class MainActivity15_ItemInfo extends AppCompatActivity {
    public static HashMap<String,Integer> cartOfItems;
    public static HashMap<String,Double> pricing;
    ImageView imageView;
    TextView  name,price,Brand,description;
    BootstrapEditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity15_item_info);
        cartOfItems=new HashMap<String,Integer> ();
        pricing=new HashMap<String,Double> ();
        FirebaseApp.initializeApp(getApplicationContext());
        imageView=findViewById(R.id.itemInfoImage);
        name=findViewById(R.id.itemInfoName);

        Brand=findViewById(R.id.itemInfoBrand);
        description=findViewById(R.id.itemInfoDescription);
        price=findViewById(R.id.itemInfoPrice);
        notes=findViewById(R.id.itemNotes);

        //MainActivity14_UserItemList.clikedItem2

        Glide.with(getApplicationContext()).load(MainActivity14_UserItemList.clikedItem2.itemImage).into(imageView);
        name.setText("Producat Name  : "+MainActivity14_UserItemList.clikedItem2.itemName);
        Brand.setText("Brand Name  :"+MainActivity14_UserItemList.clikedItem2.brandName);
        description.setText(" Description  :"+MainActivity14_UserItemList.clikedItem2.itemDescription);
        price.setText("Producat Price :"+MainActivity14_UserItemList.clikedItem2.itemPrice);


    }
    private void addItemIntoHashMapCart(String selectedItems,double selectedItemprice) {
        if (cartOfItems.containsKey(selectedItems)){
            //if the item Already exists
            //just i want to update the quantity
            cartOfItems.put(selectedItems, cartOfItems.get(selectedItems) + 1);

        }else {
            cartOfItems.put(selectedItems,1);
            pricing.put(selectedItems,selectedItemprice);
        }
    }

    public void SaveItemInCart(View view) {

        addItemIntoHashMapCart(MainActivity14_UserItemList.clikedItem2.itemName,MainActivity14_UserItemList.clikedItem2.itemPrice);
        Toast.makeText(getApplicationContext(), "Saved Item", Toast.LENGTH_SHORT).show();
    }

    public void backToList(View view) {
        Intent mm=new Intent(getApplicationContext(),MainActivity14_UserItemList.class);
        startActivity(mm);
    }
}