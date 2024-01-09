package com.example.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class driverpage extends AppCompatActivity {
    Button mp,bt,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverpage);
        getSupportActionBar().setTitle("Services");
        mp = findViewById(R.id.maps);
        bt = findViewById(R.id.ticket);
        logout = findViewById(R.id.logoutdriver);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(driverpage.this,drivertickets.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(driverpage.this,FrontPage.class));
                finish();
            }
        });
        mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(driverpage.this,DriverMap.class));
            }
        });

    }
}
