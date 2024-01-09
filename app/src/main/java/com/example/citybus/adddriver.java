package com.example.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class adddriver extends AppCompatActivity {

    FirebaseFirestore db;
    EditText email;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddriver);
        getSupportActionBar().setTitle("Add Driver");
        email = findViewById(R.id.adddrivercontent);
        add = findViewById(R.id.adddriverbutton);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("EMAIL", email.getText().toString());
                db.collection("AddDrivers").add(data);
                Toast.makeText(adddriver.this,"Account added as Driver",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(adddriver.this,adminpage.class));
                finish();
            }
        });



    }
}
