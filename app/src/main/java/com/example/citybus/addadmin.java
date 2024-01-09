package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addadmin extends AppCompatActivity {
    EditText id;
    Button add;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addadmin);
        getSupportActionBar().setTitle("Add Admin");
        id = findViewById(R.id.adminid);
        add = findViewById(R.id.Addemail);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = id.getText().toString();
                if(email.isEmpty())
                {
                    id.setError("Please Enter the E-mail ID");
                    id.requestFocus();
                }
                else if(!(email.isEmpty()))
                {
                    db = FirebaseFirestore.getInstance();
                    Map<String, Object> data = new HashMap<>();
                    data.put("ID", email);
                    db.collection("Admin")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(addadmin.this,"Admin Added Successfully",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(addadmin.this,adminpage.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(addadmin.this,"Failed to add admin",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(addadmin.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
