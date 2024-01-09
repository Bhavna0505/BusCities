package com.example.citybus;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
{
    EditText emailID, password;
    Button btlogin;
    TextView goToSignup;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthState;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("User Login");
        fireAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpass);
        goToSignup = findViewById(R.id.buttonregister);
        btlogin = findViewById(R.id.buttonlogin);
        /*mAuthState = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser FBU = fireAuth.getCurrentUser();
                if(FBU!=null)
                {
                    String userEmail=FBU.getEmail();
                    userEmail = userEmail.replaceAll("\\.","dot");
                    db = FirebaseFirestore.getInstance();
                    db.collection("SignUpUsers")
                            .document(userEmail)
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
                                        PassAttribute.Email = document.getString("EMAILID");
                                        PassAttribute.Name = document.getString("NAME");
                                        PassAttribute.Gender = document.getString("GENDER");
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Record not found",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Error getting document",Toast.LENGTH_SHORT).show();
                                }
                            }
                    });
                    Toast.makeText(MainActivity.this, "You have been logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Services.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login to continue",Toast.LENGTH_SHORT).show();
                }
            }
        };*/
        Toast.makeText(MainActivity.this, "Please Login to continue",Toast.LENGTH_SHORT).show();
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailID.getText().toString();
                String  pass = password.getText().toString();
                if(email.isEmpty() && pass.isEmpty())
                {
                    //If password and email both are left blank
                    Toast.makeText(MainActivity.this, "Please fill the fields",Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty())
                {
                    //If email is left blank
                    emailID.setError("Please enter email ID");
                    emailID.requestFocus();
                }
                else if (pass.isEmpty())
                {
                    //If password is left blank
                    password.setError("Please enter password");
                    password.requestFocus();
                }

                else if (!(email.isEmpty() && pass.isEmpty()))
                {
                    fireAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Login Unsuccessfull, Please try again",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String useremail=email;
                                useremail = useremail.replaceAll("\\.","dot");
                                db = FirebaseFirestore.getInstance();

                                db.collection("SignUpUsers")
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
                                                        PassAttribute.Email = document.getString("EMAILID");
                                                        PassAttribute.Name = document.getString("NAME");
                                                        PassAttribute.Gender = document.getString("GENDER");
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(MainActivity.this, "Record not found",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                else
                                                {
                                                    Toast.makeText(MainActivity.this, "Error getting document",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                //Change of page
                                startActivity(new Intent(MainActivity.this, Services.class));
                                finish();
                            }
                        }
                    });
                }
                else
                {
                    //To display short time messages
                    Toast.makeText(MainActivity.this, "Error Ocurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,signup_page.class));
                finish();
            }
        });
    }/*
    @Override
    protected void onStart()
    {
        super.onStart();
        fireAuth.addAuthStateListener(mAuthState);
    }*/
}