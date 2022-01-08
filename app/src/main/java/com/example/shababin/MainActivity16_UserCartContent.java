package com.example.shababin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivity16_UserCartContent extends AppCompatActivity {
    public ListView orderListView;
    public ArrayAdapter checkedOutListAdapter;
    TextView totalPrice;
    public ArrayList<String> namesofSelectedItems;
    public static double calculateTotalPrice;

    public ArrayList<Integer>calculateQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity16_user_cart_content);
        FirebaseApp.initializeApp(getApplicationContext());
        orderListView=findViewById(R.id.orederDetailsListView2);
        totalPrice=findViewById(R.id.total);
        //get The Orders
        // 1- store the items name in special array
        namesofSelectedItems=new ArrayList<String>();
        calculateQuantity=new ArrayList<Integer>();
        calculateTotalPrice=0;

        for(String i : MainActivity15_ItemInfo.cartOfItems.keySet()){
            namesofSelectedItems.add(i);
            calculateQuantity.add(MainActivity15_ItemInfo.cartOfItems.get(i));
            calculateTotalPrice+=MainActivity15_ItemInfo.cartOfItems.get(i)*MainActivity15_ItemInfo.pricing.get(i);
        }

        checkedOutListAdapter=new ArrayAdapter
                (getApplicationContext(),R.layout.cartcontent,R.id.itemsNameListLabel,namesofSelectedItems){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getView(position, convertView, parent);
                TextView quantity=view.findViewById(R.id.itemsquantityListLabel8585);
                quantity.setText(calculateQuantity.get(position)+"");
                return view;
            }
        };
        totalPrice.setText("Total Price = "+calculateTotalPrice);
        orderListView.setAdapter(checkedOutListAdapter);
    }

    public void GoToCompleteOrder(View view) {
        Intent moveToCategory=new Intent(getApplicationContext(),MainActivity17_UserSendOrder.class);
        startActivity(moveToCategory);
    }
}