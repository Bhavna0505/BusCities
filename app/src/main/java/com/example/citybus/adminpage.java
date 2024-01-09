package com.example.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminpage extends AppCompatActivity {
    Button a,b,c,d,e,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        getSupportActionBar().setTitle("Services");
        a = findViewById(R.id.adminbus);
        b = findViewById(R.id.adminsmart);
        c = findViewById(R.id.adminadd);
        d = findViewById(R.id.adminfeed);
        e = findViewById(R.id.adminadddriver);
        f = findViewById(R.id.adminlogout);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminpage.this,adminbusfunc.class));
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminpage.this,adminsmart.class));
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminpage.this,addadmin.class));
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminpage.this,adminfeedback.class));
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminpage.this,adddriver.class));
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminpage.this,FrontPage.class));
                finish();
            }
        });



    }
}
