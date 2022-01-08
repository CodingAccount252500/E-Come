package com.example.shababin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity17_UserSendOrder extends AppCompatActivity {
    String payWay="";
    public static Order o;
    public RadioButton pType1,pType2;
    BootstrapEditText addressOrder,noteOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity17_user_send_order);
        pType1=findViewById(R.id.RadioButtonChooies1);
        pType2=findViewById(R.id.RadioButtonChooies2);
        addressOrder=findViewById(R.id.addressField);
        noteOrder=findViewById(R.id.noteField);
        FirebaseApp.initializeApp(getApplicationContext());
         o = new Order();
    }

    public void ChooicePayment(View view) {
        if(pType1.isChecked()){
            payWay="Direct";
        }else{
            payWay="Online";
        }
    }


    public void StoreOrder(View view) {
        ProgressDialog createNewAdminDialog;
        createNewAdminDialog = new ProgressDialog(MainActivity17_UserSendOrder.this);
        createNewAdminDialog.setMessage("Please Wait ... ");
        createNewAdminDialog.show();

        o.cartItemsSelectedByUser=MainActivity15_ItemInfo.cartOfItems;
        o.orderComment=noteOrder.getText().toString();
        o.address=addressOrder.getText().toString();
        o.paymentStatus="UnComplete";
        o.paymentWay=payWay;
        o.totalPrice=MainActivity16_UserCartContent.calculateTotalPrice;
        o.orderOwnerName=MainActivity2_Login.currentUser.username;
        o.orderOwnerPhone=MainActivity2_Login.currentUser.phone;

        if(o.paymentWay=="Direct"){

            FirebaseDatabase.getInstance().getReference().child("Orders").push().setValue(o)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            createNewAdminDialog.dismiss();
                            Intent go =new Intent(getApplicationContext(),MainActivity13_UserCategoryList.class);
                            startActivity(go);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    createNewAdminDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed Operation", Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            createNewAdminDialog.dismiss();
            Intent go =new Intent(getApplicationContext(),MainActivity18_UserPayForOrder.class);
            startActivity(go);
        }

    }
}