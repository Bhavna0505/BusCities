package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DriverLogin extends AppCompatActivity {
    EditText pass,email;
    Button lg,rg;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_driver_login);
        getSupportActionBar().setTitle("Driver Login");
        pass = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        lg = findViewById(R.id.login);
        rg = findViewById(R.id.register);
        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverLogin.this,driver_signup.class));
                finish();
            }
        });

        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String em = email.getText().toString();
                final String ps = pass.getText().toString();
                db = FirebaseFirestore.getInstance();
                db.collection("AddDrivers")
                        .whereEqualTo("EMAIL",em)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task)
                            {
                                if (task.isSuccessful()) {
                                    for (final QueryDocumentSnapshot document : task.getResult())
                                    {
                                        if(em.isEmpty() && ps.isEmpty())
                                        {
                                            //If all the fields are left blank
                                            Toast.makeText(DriverLogin.this, "Please fill the fields",Toast.LENGTH_SHORT).show();
                                        }
                                        else if (em.isEmpty())
                                        {
                                            email.setError("Please Enter Email");
                                            email.requestFocus();
                                        }
                                        else if(ps.isEmpty())
                                        {
                                            pass.setError("Please Enter Password");
                                            pass.requestFocus();
                                        }
                                        else if(!(document.exists()))
                                        {
                                            Toast.makeText(DriverLogin.this, "You are not Registered as Driver\nPlease send the request to admin from feedback section in User Login",Toast.LENGTH_SHORT).show();
                                        }

                                        else if (!(em.isEmpty() && ps.isEmpty()))
                                        {
                                            fireAuth.signInWithEmailAndPassword(em,ps).addOnCompleteListener(DriverLogin.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(!task.isSuccessful())
                                                    {
                                                        Toast.makeText(DriverLogin.this, "Login Unsuccessfull, Please try again",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        String useremail=em;
                                                        useremail = useremail.replaceAll("\\.","dot");
                                                        db = FirebaseFirestore.getInstance();
                                                        db.collection("SignUpDrivers")
                                                                .document(useremail)
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                                                    {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            DocumentSnapshot document = task.getResult();
                                                                            if (document.exists())
                                                                            {
                                                                                BusnoDriver.driverbusno = document.getString("BUS_NO");
                                                                                BusnoDriver.driveremail = document.getString("EMAILID");
                                                                            }
                                                                            else
                                                                            {
                                                                                Toast.makeText(DriverLogin.this, "Record not found",Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                        else
                                                                        {
                                                                            Toast.makeText(DriverLogin.this, "Error getting document",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                        //Change of page
                                                        startActivity(new Intent(DriverLogin.this, driverpage.class));
                                                        finish();
                                                    }
                                                }
                                            });
                                        }
                                        else
                                        {
                                            Toast.makeText(DriverLogin.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                else
                                {
                                    Toast.makeText(DriverLogin.this,"You are not Registered as Driver\nPlease send the request to admin from feedback section in User Login",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });













































            }
        });




    }
}
