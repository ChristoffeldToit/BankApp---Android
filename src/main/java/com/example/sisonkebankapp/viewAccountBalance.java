package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class viewAccountBalance extends AppCompatActivity
{
    DatabaseHelper myDb;//Instance of the database helper class we will use to reference database objects

    /* Define the UI elements */
    private TextView txtNameHolder;
    private TextView txtSurnameHolder;
    private TextView txtCurrentBal;
    private TextView txtSavingsAcc;

    Cursor res = myDb.getUserDetails();//Calls method getUserDetails using and storing information through Cursor res

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_balance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Actionbar containing back arrow.Code indicating parent activity found in AndroidManifest.xml

        /* Bind the XML views to Java Code Elements*/
        txtNameHolder = findViewById(R.id.txtHolderName);
        txtSurnameHolder = findViewById(R.id.txtHolderSurname);
        txtCurrentBal = findViewById(R.id.txtCurrentBalance);
        txtSavingsAcc = findViewById(R.id.txtAccountBalance);

        myDb = new DatabaseHelper(this); //Calls constructor in DBHelper class that created the DB and table

        /*Method is placed in onCreate in order to initiate method when this activity is started*/
        displayUserDetails();
    }

    public void displayUserDetails()//Method that will fetch and display user information when the activity is started
    {
        /*Cursor res will fetch user information through method 'getUserDetails'*/
        /*Using res.getString we can indicate the column of information we want to retrieve and display through our widget */
        /*Using setText we can display the retrieved information. This will set the text to what we have retrieved from the database respectively */
        txtNameHolder.setText("Account Holder Name: "+ res.getString(2));
        txtSurnameHolder.setText("Account Holder Surname: " + res.getString(3));
        txtCurrentBal.setText("Current Account Balance: R" + res.getString(7));
        txtSavingsAcc.setText("Savings Account Balance: R" + res.getString(8));
    }
}