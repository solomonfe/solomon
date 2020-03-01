package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import java.util.ArrayList;

public class RegisterPage extends AppCompatActivity {
    Button save, logout;
    RecyclerView recycle;
    EditText detail;
    LinearLayoutManager linearLayoutManager;
    com.example.mobileapp.AdapterClass adapter;

    public List<Users_Form> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detail = (EditText) findViewById(R.id.detail);
        setContentView(R.layout.activity_page);
        logout = (Button) findViewById(R.id.logoutButton);
        recycle = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        userList = new ArrayList<>();
        //  adapter=new AdapterClass(userList,this);

        recycle.setLayoutManager(linearLayoutManager);
//         recycle.setAdapter(adapter);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplication(), Login_Form.class);
                startActivity(i);
            }
        });

        //calling readData() method;
        Cursor curser = readData();
        if (curser.getCount() == 0)
            Toast.makeText(this, "no data retrived from database to recycler view", Toast.LENGTH_LONG).show();
        else {
            if (curser.moveToFirst()) {
                do {
                    userList.add(new Users_Form(
                            curser.getString(0),
                            curser.getString(1)
                    ));

                } while (curser.moveToNext());

            }
        }
        //callin adapter class
        adapter = new com.example.mobileapp.AdapterClass(userList, this);
        recycle.setAdapter(adapter);

    }

    //method to retrive from table
    public Cursor readData() {
        HelperClass openHelper = new HelperClass(this);
        SQLiteDatabase databse = openHelper.getWritableDatabase();
        String query = "select *from " + ModelClass.modelInnerClass.tablename;
        Cursor result = databse.rawQuery(query, null);
        return result;
    }

    //accepting clicked postion and item
    public void displayDetail(View view) {
        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
        i.putExtra("first name=", new Users_Form().getFname());
        i.putExtra("last name=", new Users_Form().getLname());
        startActivity(i);
    }




}