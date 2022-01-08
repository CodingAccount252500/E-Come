package com.example.shababin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity9_AdminAddItem extends AppCompatActivity {
    private Uri imageUri;
    public CircleImageView itemImage;
    public BootstrapButton uploadImageChooise;
    public BootstrapEditText itemName, category, brand, description,quantity,priceField;
    public  static  Item staticFutreItem;
    public void  DefineAllScreenObject() {

        itemImage = findViewById(R.id.restaurantprofile_image);
        uploadImageChooise=findViewById(R.id.uploadProfileImage);
        itemName =findViewById(R.id.newUserNameField);
        category =findViewById(R.id.newUserPhoneField);
        brand =findViewById(R.id.newUserEmailField);
        description =findViewById(R.id.newUserPasswordField);
        priceField =findViewById(R.id.price);
        quantity=findViewById(R.id.quan);
        staticFutreItem=new Item();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity9_admin_add_item);
        DefineAllScreenObject();
        FirebaseApp.initializeApp(getApplicationContext());
        uploadImageChooise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageFromGallery=new Intent();
                getImageFromGallery.setType("image/*");
                getImageFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(getImageFromGallery,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null ){
            try {
                imageUri=data.getData();
                itemImage.setImageURI(imageUri);

            }catch (Exception exception){

            }
        }else {
            Toast.makeText(this, "Smothing Worng !", Toast.LENGTH_SHORT).show();
        }

    }
    public String getFileExtention(Uri muri){
        ContentResolver mContentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(muri));
    }


    public void continueAddItem(View view) {
        ProgressDialog createNewAdminDialog;
        createNewAdminDialog = new ProgressDialog(MainActivity9_AdminAddItem.this);
        createNewAdminDialog.setMessage("Please Wait ... ");
        createNewAdminDialog.show();
        FirebaseStorage mfirebaseStorage=FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = mfirebaseStorage.getReference();

        StorageReference fileUploadingReference = storageRef.child(System.currentTimeMillis()+"."+getFileExtention(imageUri));
        fileUploadingReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileUploadingReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        //After Image Is getting Then Create object then Upload data to firebase

                        staticFutreItem.itemImage=uri.toString();
                        staticFutreItem.itemName=itemName.getText().toString();
                        staticFutreItem.categoryName=category.getText().toString();
                        staticFutreItem.brandName=brand.getText().toString();
                        staticFutreItem.itemQuantityInStock=Integer.parseInt(quantity.getText().toString());
                        staticFutreItem.itemPrice=Double.parseDouble(priceField.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Items").push().setValue(staticFutreItem)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        createNewAdminDialog.dismiss();
                                        finish();
                                    }
                                });
                        Toast.makeText(MainActivity9_AdminAddItem.this, "Created Successfully Operation Success ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity9_AdminAddItem.this, "Upload Operation Failed ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}