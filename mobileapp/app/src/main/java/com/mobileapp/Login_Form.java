package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Form extends AppCompatActivity {

    EditText UserName,Password;
    CheckBox check;
    Button login,back;

    SQLiteDatabase databse;


    //SharedPreferences shared;
    public static String mysharedName="shared";
    public static  String userName="usernameKey";
    public static  String userPassword="userpasswordKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        back=findViewById(R.id.back_home);
        check=(CheckBox)findViewById(R.id.checkbox);
        setContentView(R.layout.activity_log);

        UserName=(EditText)findViewById(R.id.username);
        Password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.loginButton);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Form.this, com.example.mypro.MainActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInformation();
            }
        });
    }
    public void checkInformation()
    {
        Toast.makeText(this,"information is being proccessed",Toast.LENGTH_LONG).show();
        String un=UserName.getText().toString();
        int up=Integer.parseInt(Password.getText().toString());
        Cursor var= readData();
        if(var.getCount()==0)
        {
            displayERROR();
        }
        else
        {
            while(var.moveToNext())
            {
                if((un.equals(var.getString(2)))&&(up==var.getInt(3)))
                {
                    //shared perferences to save data
                    saveinShared(un,Password.getText().toString());




                    Intent intent=new Intent(getApplicationContext(), com.example.mypro.UserPage.class);
                    startActivity(intent);
                    // Toast.makeText(this,"login successfully",Toast.LENGTH_LONG).show();
                    break;
                }

            }

        }
        // shared=getSharedPreferences()
    }
    public Cursor readData()
    {
        com.example.mypro.HelperClass openHelper = new com.example.mypro.HelperClass(this);
        databse = openHelper.getWritableDatabase();
        String query="select *from "+ModelClass.modelInnerClass.tablename;
        Cursor result=databse.rawQuery(query,null);
        return result;
    }
    public void displayERROR()
    {
        Toast.makeText(this,"NO DATA IN DATABASE",Toast.LENGTH_LONG).show();
    }

    //sharedperferences method
    public void  saveinShared(String un,String userPas) {
        SharedPreferences sharedPreferences=getSharedPreferences(mysharedName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userName, un);
        editor.putString(userPassword, userPas);
        editor.commit();
        Toast.makeText(this,"your data stored in shared perferences",Toast.LENGTH_LONG).show();
    }

}

