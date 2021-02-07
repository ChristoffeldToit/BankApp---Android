package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;

public class registration_activity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = //Custom password pattern that we will use to compare our clients passwords with.
            Pattern.compile(("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{5,}" +               //at least 5 characters
                    "$"));

    private static final Pattern mobile_number_verification = //Custom mobile number pattern that we will use to compare our clients passwords with.
            Pattern.compile(("^" +
                    "(?=.[0-9])" +         //Specify digits only
                    ".{10,}" +             //at least 10 characters
                    "$"));


    DatabaseHelper myDb;//Instance of the database helper class we will use to reference database objects

    /* Define the UI elements */
    private EditText clientFirstName;
    private EditText clientLastName;
    private EditText clientMail;
    private EditText clientPassword;
    private EditText clientNumber;
    private TextView eLogin;
    private Button btnAccountReg;
    private Button btnMaleRadio;
    private Button btnFemaleReg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /* Bind the XML views to Java Code Elements */
        clientFirstName = findViewById(R.id.etName);
        clientLastName = findViewById(R.id.etLastName);
        clientMail = findViewById(R.id.etEmail);
        clientPassword = findViewById(R.id.etPassword);
        clientNumber = findViewById(R.id.etMobile);
        eLogin = findViewById(R.id.txtLogin);
        btnAccountReg = findViewById(R.id.btnAccount);
        btnMaleRadio = findViewById(R.id.btnMale);
        btnFemaleReg = findViewById(R.id.btnFemale);

        clientPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//Hides the password wile being entered.

        myDb = new DatabaseHelper(this); //Calls constructor in DBHelper class that created the DB and table

        //TextView link that redirects user to Login Activity
        eLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                /* Using intents the user will be redirected to another page */
                Intent move = new Intent(registration_activity.this, loginActivity.class);
                startActivity(move);
            }
        });

        //Method that will validate our clients input
        validateClientInformation();
    }

    private void validateClientInformation()
    {
        eLogin.setOnClickListener(//Attaching a listener to our button. This allows us to add functionality when the button is pressed
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        /*Assigning variables to the user inputs allowing us to work with the users provided information */
                        String eFirstName = clientFirstName.getText().toString();
                        String eLastName = clientLastName.getText().toString();
                        String eNumber = clientNumber.getText().toString();
                        String ePassword = clientPassword.getText().toString();
                        String eMail = clientMail.getText().toString();

                        String ID = "";
                        Integer current = 0;
                        Integer saving = 0;

                        Cursor res = myDb.getEmailFromDB(); //Cursor 'res' will fetch specific information from our database through the getEmailFromDB method created in DatabaseHelper

                        if (eFirstName.equals("") || eLastName.equals(""))//If nothing is entered we will prompt the user with a Toast to enter necessary information
                        { //Checks the edit text field of first name and last name to see if the fields have been entered
                            Toast.makeText(registration_activity.this,//Prompting our user to enter required information
                                    "Please enter both your first name and last name",
                                    Toast.LENGTH_LONG).show();
                        } else if (eNumber.equals("") || !mobile_number_verification.matcher(eNumber).matches())//If nothing is entered we will prompt the user with a Toast to enter necessary information
                        {//Number entered will be compared to determine if it is less than 10 or contains any letters. If it doesn't match this the user will receive a toast
                            Toast.makeText(registration_activity.this,//Prompting our user to enter required information and follow our email format
                                    "Please enter your mobile number and remember to use the correct format",
                                    Toast.LENGTH_LONG).show();
                        } else if (ePassword.equals("") || !PASSWORD_PATTERN.matcher(ePassword).matches())//If nothing is entered or in the incorrect format we will prompt the user with a Toast to enter necessary information
                        {
                            Toast.makeText(registration_activity.this,//Prompting our user to enter required information
                                    "Password must be entered and in the correct format",
                                    Toast.LENGTH_LONG).show();
                            //Because we use androids pre-defined EMAIL_ADDRESS matcher we did not have to create one ourselves.
                        } else if (eMail.equals("") || !Patterns.EMAIL_ADDRESS.matcher(eMail).matches())//If nothing is entered we will prompt the user with a Toast to enter necessary information
                        { //Using androids built in EMAIL_ADDRESS pattern to match our client emails. If it does not match the minimum complexity they will receive a toast message
                            Toast.makeText(registration_activity.this,//Prompting our user to enter required information
                                    "Email must be entered and in the correct format",
                                    Toast.LENGTH_LONG).show();
                        } else if(res.getString(4).equals(eMail)) //Cursor res will fetch client email information through our 'getEmailFromDb' method and compare it to the clients provided email. If this email address exists the user will receive a toast
                        {
                            Toast.makeText(registration_activity.this,
                                    "Email already exists",//Informing the user that the email they entered is already stored in our database
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                            {
                            boolean isInserted = myDb.addUser
                                    ( //Uses the myDb instance we initiated in the on create method to call the addUser function in our database Helper class and inserts the data into the respective fields
                                            //This will add all necessary client information to our database using the addUser method in the databaseHelper class
                                    ID,
                                    eFirstName,
                                    eLastName,
                                    eNumber,
                                    ePassword,
                                    eMail,
                                    current,
                                    saving
                            );

                            if (isInserted == true)
                            { //isInserted is a boolean so true indicates a successful write operation
                                Toast.makeText(registration_activity.this,//Notify the user wit a toast that they will be redirected to the login page
                                        "Data inserted successfully you will now be redirected to the login page", Toast.LENGTH_LONG).show();
                                //Using intent we redirect the user to the login page where they can now use their information to login
                                startActivity(new Intent(
                                        registration_activity.this,
                                        loginActivity.class));
                            } else
                                {//If isInserted returns false it was not a successful write operation
                                Toast.makeText(registration_activity.this, //Informing the user that the data could not be inserted into our database using the addUser method
                                        "Data could not insert successfully", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
        }
}