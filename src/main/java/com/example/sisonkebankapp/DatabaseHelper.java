package com.example.sisonkebankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "people.db";//Assign our database name and String value to hold it
    public static final String TABLE_NAME = "users";//Assign our table name and String value to hold it
    public static final String COL1 = "ID";//Create and name our eight columns that will be used to store our users data
    public static final String COL2 = "FIRST NAME";
    public static final String COL3 = "LAST NAME";
    public static final String COL4 = "EMAIL";
    public static final String COL5 = "PASSWORD";
    public static final String COL6 = "MOBILE";
    public static final String COL7 = "CURRENT";
    public static final String COL8 = "Savings";

    String clientPassword;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);//When this constructor is called 'DATABASE_NAME' db will be created
    }


    @Override
    public void onCreate(SQLiteDatabase db) {//Creates our table and assigns column names and data types
        String createTable = " CREATE TABLE " + TABLE_NAME + " (" + COL1 + "INTEGER PRIMARY KEY AUTOINCREMENT,"  + COL2 + "TEXT," + COL3 + "TEXT," + COL4 + "TEXT," + COL5 + "TEXT," + COL6 + "INTEGER" + COL7 + "INTEGER" + COL8 + "INTEGER" + ")";
        db.execSQL(createTable); //db. is a built in sqlLite variable that will execute the argument within execSQL().

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(String.format("DROP IF TABLE EXISTS" + TABLE_NAME); --- Initial statement
        db.execSQL(String.format("DROP IF TABLE EXISTS", TABLE_NAME));
        onCreate(db);
    }

    public boolean updateBalance(Integer current, Integer savings)//Creates a method that can be passed two values in order to update the two financial columns we have created
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL7, current);//This will update column 7, current
        contentValues.put(COL8, savings);//This will update column 8, savings

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) //If result = -1 data was not inserted
        {
            return false;
            //Message to show unsuccessful login
            /*Toast.makeText(DatabaseHelper.this,
                    "Please enter a valid email address",
                    Toast.LENGTH_LONG).show();*/
        } else {
            return true;
        }
    }

    /*Method that will insert values into table users.
        This method uses a boolean so we will be returning a true or false to indicate a successful or unsuccessful write operation to the database
        If the write is successful these columns listed below will be added to */
    public boolean addUser(String ID, String email, String password,
                           String lastName, String firstName, String mobile,
                           Integer current, Integer savings)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        current = 5000; //When the users account information gets added they get two accounts, current and savings
        savings = 10000;
        contentValues.put(COL1, ID); //Indicates the column name and the column value type that will be updated
        contentValues.put(COL2, firstName);
        contentValues.put(COL3, lastName);
        contentValues.put(COL4, email);
        contentValues.put(COL5, password);
        contentValues.put(COL6, mobile);
        contentValues.put(COL7, current);
        contentValues.put(COL8, savings);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) //If result = -1 data was not inserted
        {
            return false;
            //Message to show unsuccessful login
            /*Toast.makeText(DatabaseHelper.this,
                    "Please enter a valid email address",
                    Toast.LENGTH_LONG).show();*/
        } else {
            return true;
        }
    }

    public Cursor getEmailFromDB()//Method that will be used to fetch stored emails in order to display or compare them as needed
    {
        SQLiteDatabase db = this.getWritableDatabase(); //This is where the DB and table will be created
        Cursor res = db.rawQuery("select 4 from TABLE_NAME ", null); //This will select everything from the email column (column 4) to be compared with clients input
        return res;
    }

    public Cursor getUserDetails()//Method that will fetch specific information stored in different columns of our table users. This can then be used by different activities to display or compare information.
    {
        SQLiteDatabase db = this.getWritableDatabase(); //This is where the DB and table will be created
        Cursor res = db.rawQuery("select 2, 3, 7, 8 from TABLE_NAME ", null);//This will retrieve values from column 2,3,7,8 (firstName, lastName, Current and Savings) to be displayed or compared to as needed by different activities.
        Cursor res1 = db.rawQuery("select  2 from TABLE_NAME NAME WHERE ID = 5", null);
        Cursor res2 = db.query()
        return res;

    }

    public Cursor compareEmail(String clientEmail)//Method that will be used to compare clients input to emails stored in our database
    {
        SQLiteDatabase db = this.getWritableDatabase(); //This is where the DB and table will be created
        Cursor cursor = db.query("TABLE_NAME", null,
                " EMAIL = ?", new String[]{clientEmail}, null, null, null);
        return cursor;
    }

    public Cursor comparePassword(String clientPassword)//Method that will be used to compare clients input to passwords stored in our database
    {
        SQLiteDatabase db = this.getWritableDatabase(); //This is where the DB and table will be created
        Cursor cursor = db.query("TABLE_NAME", null,
                " PASSWORD = ?", new String[]{clientPassword}, null, null, null);
        return cursor;
    }
}
