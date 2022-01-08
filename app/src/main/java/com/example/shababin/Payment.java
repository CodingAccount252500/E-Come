package com.example.shababin;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Payment {

    public String cardNumber;
    public String ccv2;
    public String expire;
    public  double balance;
    public Payment() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Payment(String cardNumber, String ccv2,String expire,double balance) {
        this.cardNumber = cardNumber;
        this.ccv2 = ccv2;
        this.expire=expire;
        this.balance=balance;


    }

}
