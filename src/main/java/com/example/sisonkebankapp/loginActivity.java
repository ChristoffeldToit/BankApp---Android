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

public class loginActivity extends AppCompatActivity
{
    /* Define the UI elements */
    private EditText uEmail;
    private TextView txtRegister;
    private EditText ePassword;
    private Button eLogin;
    private Button eRedirect;
    private String clientPassword;
    private String clientEmail;

    DatabaseHelper myDb;//Instance of the database helper class we will use to reference database objects

    String userEmail = "";
    String userPassword = "";

    /* Class to hold credentials */
    static class Credentials
    {
        String name11 = "Admin";
        String password11 = "123456";
    }

    boolean isValid = false;

    private static final Pattern password_verification = //Password validation that will be used to ensure clients password matches our criteria
            Pattern.compile(("^" +
                    "(?=.[0-9])" +        //specify digits
                    "(?=.[a-zA-Z])" +     //any letters
                    ".{5,}" +             //at least 5 characters
                    "$"));

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Bind the XML views to Java Code Elements*/
        uEmail = findViewById(R.id.etEmail);
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//Hides the password wile being entered.

        myDb = new DatabaseHelper(this); //Calls constructor in DBHelper class that created the DB and table

        /*Method is placed in onCreate in order to initiate method when this activity is started*/
        userLogin();

        DatabaseHelper myDb = null;

        /* Describe the logic when the login button is clicked */
        final DatabaseHelper finalPeopleDB = myDb;

        myDb = new DatabaseHelper(this);

        //Attach a setOnClickListener to our textView so that user can click on a textView and be redirected to another page. This only attaches the textView to a listener and calls the method that will handle redirection.
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectUser(v);
            }
        });
    }

        private final void userLogin()
        {
            eLogin.setOnClickListener(new View.OnClickListener() //Attach a setOnClickListener to button 'eLogin' allowing us to add functionality to the button when it is pressed
            {
                @Override
                public void onClick(View view)
                {
                    /* Obtain user inputs and assigns them to variables that we can work with */
                    userEmail = uEmail.getText().toString();
                    userPassword = ePassword.getText().toString();

                    Cursor res1 = myDb.compareEmail(clientEmail); //Cursor 'res' will fetch specific email information from our database method compareEmail

                    Cursor res2 = myDb.comparePassword(clientPassword);//Cursor 'res2' will fetch specific password information from our database method comparePassword

                    if (userEmail.equals("") || userPassword.equals(""))//If nothing is entered we will prompt the user with a Toast to enter necessary information
                    {
                        Toast.makeText(loginActivity.this,//Toast message informing the user that there is still information needed in order to continue
                                "Please enter both your email and password before pressing login",
                                Toast.LENGTH_LONG).show();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) //Email validation
                    {
                        Toast.makeText(loginActivity.this,//Toast message informing the user that the email entered does not match our minimum complexity
                                "Please enter a valid email address",
                                Toast.LENGTH_LONG).show();
                    } else if (!password_verification.matcher((CharSequence) ePassword).matches()) //Verify password using a password matcher function
                    {
                        Toast.makeText(loginActivity.this,//Toast message informing the user that the password does not meet our required complexity
                                "Password must be at least 5 characters",
                                Toast.LENGTH_LONG).show();
                    } else if (res1.equals(userEmail) && res2.equals(userPassword) )
                    {
                        Toast.makeText(loginActivity.this,//Toast message informing the user that the information entered is correct
                                "Information entered is correct", //If the email and password matches those fetched by res1 & res2 the email and password is in our database and the user can continue to the main_page
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent( //Information is correct and user will be redirected to the main_page indicating a successful login
                                loginActivity.this,// Using intents the user will be redirected to another page
                                main_page.class));
                    }
                }
            });
        }

    private void redirectUser(View view)
    { // Method that redirects user to Registration page using intents
        startActivity(new Intent(
                loginActivity.this,
                registration_activity.class));
    }

    /* Validate the credentials */
    private boolean validate(String userName, String userPassword)
    {
        /* Get the object of Credentials class */
        Credentials credentials = new Credentials();

        /* Check the credentials */
        if(userName.equals(credentials.name11) && userPassword.equals(credentials.password11))
        {
            return true;
        }

        return false;
    }

}