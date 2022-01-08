package com.example.shababin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity18_UserPayForOrder extends AppCompatActivity {
    BootstrapEditText card,cvv,exp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity18_user_pay_for_order);
        FirebaseApp.initializeApp(getApplicationContext());
        card=findViewById(R.id.f1);
        cvv=findViewById(R.id.f2);
        exp=findViewById(R.id.f3);



    }

    public void PayAndSendOrder(View view) {
        ProgressDialog loadingDialog;
        loadingDialog = new ProgressDialog(MainActivity18_UserPayForOrder.this);
        loadingDialog.setMessage("Please Wait ... ");
        loadingDialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Payment");
        final Query gameQuery = ref;
        gameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Payment fetchedRecorde = item.getValue(Payment.class);
                        if (fetchedRecorde.cardNumber.equals(card.getText().toString())&& fetchedRecorde.ccv2.equals(cvv.getText().toString())
                               && fetchedRecorde.expire.equals(exp.getText().toString())
                                && fetchedRecorde.balance >= MainActivity16_UserCartContent.calculateTotalPrice) {
                            MainActivity17_UserSendOrder.o.paymentStatus="Complete";
                            FirebaseDatabase.getInstance().getReference().child("Orders").push().setValue(MainActivity17_UserSendOrder.o)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            loadingDialog.dismiss();
                                            Intent go =new Intent(getApplicationContext(),MainActivity13_UserCategoryList.class);
                                            startActivity(go);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Failed Operation", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            loadingDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}