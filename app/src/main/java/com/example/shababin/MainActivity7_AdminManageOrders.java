package com.example.shababin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity7_AdminManageOrders extends AppCompatActivity {
    ArrayList<Order> availableOrder;
    ArrayList<String> availableOrderUserNames;
    ListView itemsListView;
    public ArrayAdapter myAdapter2;
    private ProgressDialog createNewDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity7_admin_manage_orders);
        itemsListView=findViewById(R.id.listViewOrders);
        availableOrder=new ArrayList<Order>();
        availableOrderUserNames=new ArrayList<String>();
        FirebaseApp.initializeApp(getApplicationContext());
        updateScreenData();
    }
    private void updateScreenData() {
        availableOrderUserNames.clear();
        availableOrder.clear();
        createNewDialog = new ProgressDialog(MainActivity7_AdminManageOrders.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(MainActivity7_AdminManageOrders.this);
        FirebaseDatabase.getInstance().getReference().child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    Order fetchedItem=child.getValue(Order.class);

                    availableOrderUserNames.add(fetchedItem.orderOwnerName);
                    availableOrder.add(fetchedItem);


                }
                myAdapter2=new ArrayAdapter
                        (getApplicationContext(),R.layout.items,R.id.ItemsNameField,availableOrderUserNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        //TextView tv1=view.findViewById(R.id.ItemsNameField);

                        TextView tv2=view.findViewById(R.id.ItemsBrandField);
                        tv2.setText(availableOrder.get(position).orderOwnerPhone);
                        TextView tv3=view.findViewById(R.id.ItemsCategoryField);
                        tv3.setText(availableOrder.get(position).totalPrice+"");
                        TextView tv4=view.findViewById(R.id.ItemsPriceField);
                        tv4.setText(availableOrder.get(position).paymentStatus);





                        return view;
                    }
                };
                itemsListView.setAdapter(myAdapter2);
                createNewDialog.dismiss();
                myAdapter2.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void BackToAdminMain(View view) {

        Intent go =new Intent(getApplicationContext(),MainActivity4_AdminMainPage.class);
        startActivity(go);
    }
}