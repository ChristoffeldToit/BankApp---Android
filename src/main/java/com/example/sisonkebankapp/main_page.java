package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class main_page extends AppCompatActivity {

    private Button btnTransfer;
    private Button btnViewBal;
    private Button btnLogoutUser;
    private TextView txtNameDisplay;

    DatabaseHelper myDb;//Instance of the database helper class we will use to reference database objects
    Cursor res = myDb.getUserDetails();//Calls method getUserDetails using and storing information through Cursor res

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        /* Bind the XML views to Java Code Elements*/
        btnTransfer = findViewById(R.id.btnTransferBetween);
        btnViewBal = findViewById(R.id.btnViewBalance);
        btnLogoutUser = findViewById(R.id.btnLogout);
        txtNameDisplay = findViewById(R.id.txtClientName);

        /*Methods is placed in onCreate in order to initiate methods when this activity is started*/
        transferButton();
        ViewBalanceButton();
        displayName();
        logoutButton();

    }

    public void displayName()
    {
        //Cursor res will fetch the users name using the getUserDetails method created and stored in our DatabaseHelper class in column 2 in the users table and using 'setText' we can display it in out text widget
        //This method is called in the onCreate so it will be initialised and executed as soon as the user enters this activity
        txtNameDisplay.setText("Welcome: "+ res.getString(2));
    }


    public void transferButton()
    {
        btnTransfer.setOnClickListener(new View.OnClickListener()
        { //Attach setOnClickListener to our Transfer button
            @Override
            public void onClick(View v) {
                Intent move = new Intent(main_page.this, transferFunds.class); //Using intents user is redirected from main_page to the transferFunds activity
                startActivity(move);
            }
        });
    }

    public void ViewBalanceButton()
    {
        btnViewBal.setOnClickListener(new View.OnClickListener() { //Attach setOnClickListener view balance button
            @Override
            public void onClick(View v) {
                Intent move = new Intent(main_page.this, viewAccountBalance.class); //Using intents user is redirected from main_page to the viewAccountBalance activity
                startActivity(move);
            }
        });
    }

    public void logoutButton()
    {
        btnLogoutUser.setOnClickListener(new View.OnClickListener() { //Attach setOnClickListener view balance button
            @Override
            public void onClick(View v) {
                Intent move = new Intent(main_page.this, loginActivity.class); //Using intents user is redirected from main_page to the login page activity
                startActivity(move);
            }
        });
    }

}