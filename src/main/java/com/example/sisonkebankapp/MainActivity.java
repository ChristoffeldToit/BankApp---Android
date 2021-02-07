package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    private ListView listView;//Initialises our listView where we can attach the Sisonke header
    private int digit[] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};//Creates an array of 18 Integer digits called 'digit'
    private ListAdapter listAdapter;//Creates our list adapter, this will all be used for the projects header

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Bind the XML views to Java Code Elements*/
        listView = findViewById(R.id.listView);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        //Add Header View
        View headerView = inflater.inflate(R.layout.header_view, null, false);
        listView.addHeaderView(headerView);//Add view to list view as header view

        listAdapter = new ListAdapter(this,digit);
        listView.setAdapter(listAdapter);

        Intent move = new Intent(MainActivity.this, loginActivity.class); //Using intents user is redirected from main_page to the transferFunds activity
        startActivity(move);
    }
}