package com.example.shababin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity6_AdminMangeItem extends AppCompatActivity {
    ArrayList<Item> availableItems;
    ArrayList<String> availableItemsNames;

    ListView itemsListView;
    public ArrayAdapter myAdapter;
    private ProgressDialog createNewDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity6_admin_mange_item);
        FirebaseApp.initializeApp(getApplicationContext());
        itemsListView=findViewById(R.id.listViewDisplayItem);
        availableItems=new ArrayList<Item>();
        availableItemsNames=new ArrayList<String>();

        updateScreenData();
    }

    public void AddItem(View view) {
        Intent go=new Intent(getApplicationContext(),MainActivity9_AdminAddItem.class);
        startActivity(go);
    }
   private void updateScreenData() {
        availableItemsNames.clear();
        availableItems.clear();
        createNewDialog = new ProgressDialog(MainActivity6_AdminMangeItem.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(MainActivity6_AdminMangeItem.this);
        FirebaseDatabase.getInstance().getReference().child("Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    Item fetchedItem=child.getValue(Item.class);

                        availableItemsNames.add(fetchedItem.itemName);
                        availableItems.add(fetchedItem);


                }
                myAdapter=new ArrayAdapter
                        (getApplicationContext(),R.layout.items,R.id.ItemsNameField,availableItemsNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView brand=view.findViewById(R.id.ItemsBrandField);
                        brand.setText(availableItems.get(position).brandName);
                        TextView category=view.findViewById(R.id.ItemsCategoryField);
                        category.setText(availableItems.get(position).categoryName+"");
                        CircleImageView imageView=view.findViewById(R.id.itemImage);
                        Glide.
                                with(getApplicationContext()).load(availableItems
                                .get(position).itemImage).into(imageView);

                        return view;
                    }
                };
                /*itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        clikedItem=availableItems.get(i);
                        Toast.makeText(getApplicationContext(), clikedItem.categoryName+"", Toast.LENGTH_SHORT).show();
                        Intent goInfo=new Intent(getApplicationContext(),MainActivity15_ItemInfo.class);
                        startActivity(goInfo);


                    }
                });*/
                itemsListView.setAdapter(myAdapter);
                createNewDialog.dismiss();
                myAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}