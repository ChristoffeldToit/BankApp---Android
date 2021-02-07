package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class transferFunds extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    /* Define the UI elements */
    private Button btnTransfer;
    private EditText transferText;
    private TextView txtCurrentBal;
    private TextView txtSavings;
    private Spinner spinToTransfer;

    //Variables that we will use to work with user input
    Integer txtTransfer;
    //Variables that we will use to work with user input
    Integer currentBal, currentSavings;

    DatabaseHelper myDb;//Instance of the database helper class we will use to reference database objects


    Cursor res = myDb.getUserDetails(1, 2, 3);//Calls method getUserDetails in our DatabaseHelper class in order to use information through Cursor res


    public transferFunds()
    {
        //Needed to make List<transfer> work
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_funds);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Actionbar containing back arrow.Code indicating parent activity found in AndroidManifest.xml

        /* Bind the XML views to Java Code Elements*/
        Spinner spin = findViewById(R.id.transferSpinner);
        btnTransfer = findViewById(R.id.btnTransferBetween);
        transferText = findViewById(R.id.etTransferAmount);
        txtCurrentBal = findViewById(R.id.txtCurrentBalance);
        txtSavings = findViewById(R.id.txtSavingsBalance);
        spinToTransfer = findViewById(R.id.transferSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(   //Fills spinner with array(item_class) created in strings.xml
                this, R.array.items_class, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);               //Connect spinner and adapter
        spin.setOnItemSelectedListener(this);   //Attach itemListener to spinner

        /*Method is placed in onCreate in order to initiate method when this activity is started*/
        transferFunction();
        displayAvailableAmounts();
    }

    //Method that will display the users current balance and savings balance once the activity is started
    public void displayAvailableAmounts()
    {
        /*Cursor res will be used with our getUserDetails method to fetch specific information */
        txtCurrentBal.setText("Current Account Balance: R" + res.getString(7));//Using setText we can display the information stored in column 7 to the user. Current account balance.
        txtSavings.setText("Savings Account Balance: R" + res.getString(8));//Using setText we can display the information stored in column 8 to the user. Current savings.
    }

    //This method will be used to transfer money from one account to another
    public void transferFunction()
    {
        //Attaching a setOnClickListener allows us to bind functionality to the button 'btnTransfer'
        btnTransfer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //This will parse the value entered by the user to an int and store it in txtTransfer so that we can work with and pass the users input where we need it.
                txtTransfer = Integer.parseInt(transferText.getText().toString());

                //Using Cursor res we will fetch the information stored in the 7th column of table user and store it in currentBal. Information fetched is current account balance.
                currentBal = res.getInt(7);
                //Using Cursor res we will fetch the information stored in the 8th column of table user and store it in currentSavings. Information fetched is savings account balance.
                currentSavings = res.getInt(8);

                if (txtTransfer.equals(""))//Checks if user entered an amount
                {
                    Toast.makeText(transferFunds.this,//Prompts the user to enter an mount in order to continue
                            "Please specify an amount to transfer",
                            Toast.LENGTH_LONG).show();
                } else if (spinToTransfer.equals("Current to Savings") & txtTransfer < currentBal)//Checks what the spinner is set to out of its two options and ensures that the entered amount is less than what the client has in their current account.
                {//If the amount entered is not less than what the client has there is not enough money in the account to facilitate this transaction
                    boolean isUpdated = myDb.updateBalance(-txtTransfer, txtTransfer);//This will update two values. The users entered amount into the savings balance account and then deduct that entered amount from the current balance account
                    //Database insert
                    if (isUpdated == true)
                    {//isUpdated is a Boolean so true means a successful write operation to our database
                        Toast.makeText(transferFunds.this,
                                "Transfer to savings account completed successfully, " +
                                        "Updated savings amount R: " + res.getInt(8),//Informing the user that the amount was transferred as well as displaying the updated savings amount in their account
                                Toast.LENGTH_LONG).show();
                    } else
                        {//isUpdated is a Boolean so false means an unsuccessful write operation to our database
                        Toast.makeText(transferFunds.this,//Toast message informing user that the write could not be completed
                                "Transfer could not be completed, there might not be enough funds in your current account.",
                                Toast.LENGTH_LONG).show();
                    }
                } else if (spinToTransfer.equals("Savings to Current") & txtTransfer < currentSavings)//Checks what the spinner is set to out of its two options and ensures that the entered amount is less than what the client has in their savings account.
                {//If the amount entered is not less than what the client has in their savings account there is not enough money in the account to facilitate this transaction
                    boolean isUpdated = myDb.updateBalance(txtTransfer, -txtTransfer);//This will update two values. The users entered amount into the current balance account and then deduct that entered amount from the savings account
                    if (isUpdated == true)
                    {
                        Toast.makeText(transferFunds.this,
                                "Transfer to current account completed successfully. " +
                                        "Updated current amount R: " + res.getInt(7),//Informing the user that the amount was transferred as well as displaying the updated current balance in their account
                                Toast.LENGTH_LONG).show();

                        Toast.makeText(transferFunds.this,
                                "Transfer to current account completed successfully. " +
                                        "Updated current amount R: " + res.getInt(7),//Informing the user that the amount was transferred as well as displaying the updated current balance in their account
                                Toast.LENGTH_LONG).show();


                    } else
                        {//isUpdated is a Boolean so false means an unsuccessful write operation to our database
                        Toast.makeText(transferFunds.this,
                                "Transfer could not be completed, there might not be enough funds in your current account.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String toast = parent.getItemAtPosition(position).toString(); //Fetches selected value and places it in 'toast'
        Toast.makeText(parent.getContext(), toast, Toast.LENGTH_LONG).show();//Display 'toast' after client selected spinner
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //On nothing select do nothing
    }
}