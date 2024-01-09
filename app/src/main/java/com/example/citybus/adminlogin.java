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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class adminlogin extends AppCompatActivity {

    EditText email,pass;
    Button loginadmin;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        getSupportActionBar().setTitle("Admin Login");
        fireAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.adminemail);
        pass = findViewById(R.id.adminpass);
        loginadmin = findViewById(R.id.adminloginbutton);
        loginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String em = email.getText().toString();
                final String ps = pass.getText().toString();
                db = FirebaseFirestore.getInstance();
                db.collection("Admin")
                        .whereEqualTo("ID",em)
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
                                            Toast.makeText(adminlogin.this, "Please fill the fields",Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(adminlogin.this, "You are not Registered as Admin",Toast.LENGTH_SHORT).show();
                                        }

                                        else if (!(em.isEmpty() && ps.isEmpty()))
                                        {
                                            fireAuth.signInWithEmailAndPassword(em,ps).addOnCompleteListener(adminlogin.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(!task.isSuccessful())
                                                    {
                                                        Toast.makeText(adminlogin.this, "Login Unsuccessfull, Please try again",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        startActivity(new Intent(adminlogin.this,adminpage.class));
                                                        Toast.makeText(adminlogin.this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                                else
                                {
                                    Toast.makeText(adminlogin.this,"Error Occurred !!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
