package com.example.citybus;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Services extends AppCompatActivity {
    TextView LOGOUT,hello;
    Button busst,tickbook,bookhis,smart,feed;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener fireBaseListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        getSupportActionBar().setTitle("Services");
        db = FirebaseFirestore.getInstance();
        LOGOUT = findViewById(R.id.logout);
        LOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Services.this,FrontPage.class));
                finish();
            }
        });
        hello = findViewById(R.id.hello);
        String hellostring = "Hello "+PassAttribute.Name+" !!!";
        hello.setText(hellostring);
        busst = findViewById(R.id.buttonbusstatus);
        tickbook = findViewById(R.id.buttonticketbook);
        bookhis = findViewById(R.id.buttontickethistory);
        smart = findViewById(R.id.buttonsmartcard);
        feed = findViewById(R.id.buttonfeedback);
        busst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, MapsActivity.class));
            }
        });
        tickbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, instantbooking.class));
            }
        });
        bookhis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, booking_history.class));
            }
        });
        smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uniqueID = PassAttribute.Email;
                uniqueID = uniqueID.replaceAll("\\.","dot");
                db.collection("SmartCardApplications")
                        .document(uniqueID)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task)
                            {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        startActivity(new Intent(Services.this,smartcardapplication.class));
                                    }
                                    else
                                    {
                                        startActivity(new Intent(Services.this, smartcard.class));
                                    }
                                }
                            }
                        });

            }
        });
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, feedback.class));
            }
        });

    }
}
