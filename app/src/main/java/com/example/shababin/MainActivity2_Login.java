package com.example.shababin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2_Login extends AppCompatActivity {
    public BootstrapEditText emailField,passwordField;
    public static User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_login);
        FirebaseApp.initializeApp(getApplicationContext());
        DefineMirrorObjectForEditText();
    }
    public  void DefineMirrorObjectForEditText(){
        emailField=findViewById(R.id.loginEmailTextField);
        passwordField=findViewById(R.id.loginPasswordTextField);

    }
    public void LoginToApplicaion(View view) {
        //check if all required data is exist
        ProgressDialog loadingDialog;
        loadingDialog = new ProgressDialog(MainActivity2_Login.this);
        loadingDialog.setMessage("Please Wait ... ");
        loadingDialog.show();
        if(emailField.getText().toString().equals("") || passwordField.getText().toString().equals("")){
            loadingDialog.dismiss();
            Toast.makeText(MainActivity2_Login.this, "All Fields Are Required ", Toast.LENGTH_LONG).show();
        }else{
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");

            final Query gameQuery = ref;
            gameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        //Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot user : snapshot.getChildren()) {
                            User fetchedUsers = user.getValue(User.class);
                            if (fetchedUsers.email.equals(emailField.getText().toString())&&fetchedUsers.password.equals(passwordField.getText().toString())) {
                                //user.getKey()
                                if(fetchedUsers.roleType.equals("Admin")){
                                    currentUser=fetchedUsers;
                                    loadingDialog.dismiss();
                                    Intent moveToAdminScreen=new Intent(getApplicationContext(),MainActivity4_AdminMainPage.class);
                                    startActivity(moveToAdminScreen);

                                }else if (fetchedUsers.roleType.equals("User")){
                                    currentUser=fetchedUsers;
                                    loadingDialog.dismiss();
                                    Intent moveToAdminScreen=new Intent(getApplicationContext(),MainActivity14_UserItemList.class);
                                    startActivity(moveToAdminScreen);
                                }else{
                                    currentUser=fetchedUsers;
                                    loadingDialog.dismiss();
                                    //Delivery
                                    /*Intent moveToAdminScreen=new Intent(getApplicationContext(),MainActivity11_DeliveryAssignedOrder.class);
                                    startActivity(moveToAdminScreen);*/
                                }
                            }else if(fetchedUsers.email.equals(emailField.getText().toString())&&!(fetchedUsers.password.equals(passwordField.getText().toString()))){
                                loadingDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Login Failed Check Your Information", Toast.LENGTH_SHORT).show();
                            }else{
                                loadingDialog.dismiss();
                                //Toast.makeText(getApplicationContext(), "Login Failed Try Again Please", Toast.LENGTH_SHORT).show();
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

    public void MoveToCreateAccount(View view) {
        Intent moveToCreateAccountScreen=new Intent(MainActivity2_Login.this,MainActivity3_CreateAccount.class);
        startActivity(moveToCreateAccountScreen);
    }
}