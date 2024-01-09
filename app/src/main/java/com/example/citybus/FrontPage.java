package com.example.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontPage extends AppCompatActivity
{
    Button user,admin,driver;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        getSupportActionBar().setTitle("City Bus");
        user = findViewById(R.id.userbutton);
        admin = findViewById(R.id.adminbutton);
        driver = findViewById(R.id.driverbutton);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrontPage.this,MainActivity.class));
            }
        });
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrontPage.this,DriverLogin.class));
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrontPage.this,adminlogin.class));
            }
        });

    }
}
