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

public class MainActivity14_UserItemList extends AppCompatActivity {
    ArrayList<Item> availableItems2;
    ArrayList<String> availableItemsNames2;
    public  static Item clikedItem2;
    ListView itemsListView2;
    public ArrayAdapter myAdapter2;
    private ProgressDialog createNewDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity14_user_item_list);
        itemsListView2=findViewById(R.id.myItems);
        availableItems2=new ArrayList<Item>();
        availableItemsNames2=new ArrayList<String>();
        clikedItem2=new Item();
        FirebaseApp.initializeApp(getApplicationContext());
        updateScreenData();

    }
    private void updateScreenData() {
        availableItemsNames2.clear();
        availableItems2.clear();
        createNewDialog2 = new ProgressDialog(MainActivity14_UserItemList.this);
        createNewDialog2.setMessage("Please Wait ... ");
        createNewDialog2.show();
        FirebaseApp.initializeApp(MainActivity14_UserItemList.this);
        FirebaseDatabase.getInstance().getReference().child("Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {

                    Item fetchedItem=child.getValue(Item.class);
                    if(fetchedItem.categoryName.equalsIgnoreCase(MainActivity13_UserCategoryList.selectedCategory)) {
                        availableItemsNames2.add(fetchedItem.itemName);
                        availableItems2.add(fetchedItem);
                    }

                }
                myAdapter2=new ArrayAdapter
                        (getApplicationContext(),R.layout.items,R.id.ItemsNameField,availableItemsNames2){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView brand=view.findViewById(R.id.ItemsBrandField);
                        brand.setText(availableItems2.get(position).brandName);
                        TextView category=view.findViewById(R.id.ItemsCategoryField);
                        category.setText(availableItems2.get(position).categoryName+"");
                        CircleImageView imageView=view.findViewById(R.id.itemImage);
                        Glide.
                                with(getApplicationContext()).load(availableItems2
                                .get(position).itemImage).into(imageView);

                        return view;
                    }
                };
                itemsListView2.setAdapter(myAdapter2);
                itemsListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        clikedItem2=availableItems2.get(i);
                        Toast.makeText(getApplicationContext(), clikedItem2.categoryName+"", Toast.LENGTH_SHORT).show();
                        Intent goInfo=new Intent(getApplicationContext(),MainActivity15_ItemInfo.class);
                        startActivity(goInfo);


                    }
                });

                createNewDialog2.dismiss();
                myAdapter2.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void CheckOut(View view) {
        if(MainActivity15_ItemInfo.cartOfItems.size()>0){
            Intent moveToCartItem = new Intent(getApplicationContext(), MainActivity16_UserCartContent.class);
            startActivity(moveToCartItem);
        }else{
            Toast.makeText(getApplicationContext(), "No Items Yet", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToList2(View view) {
        Intent moveToCategory=new Intent(getApplicationContext(),MainActivity13_UserCategoryList.class);
        startActivity(moveToCategory);
    }
}