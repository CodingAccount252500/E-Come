package com.example.shababin;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Item {

    public String categoryName;
    public String brandName;
    public String itemImage;
    public String itemName;
    public String itemDescription;
    public double itemPrice;
    public int itemQuantityInStock;


    public Item() {

    }

    public Item(String categoryName, String brandName, String itemImage, String itemName, String itemDescription,  double itemPrice,int qtn) {
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemDescription = itemDescription;

        this.itemPrice = itemPrice;
        this.itemQuantityInStock=qtn;
    }
}
