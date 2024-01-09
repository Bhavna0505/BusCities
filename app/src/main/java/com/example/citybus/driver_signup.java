package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class driver_signup extends AppCompatActivity {
    Button su,re;
    EditText pass,email;
    Spinner SP;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);
        getSupportActionBar().setTitle("Sign Up Driver");
        fireAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.registeremail);
        pass = findViewById(R.id.registerpass);
        SP = findViewById(R.id.busno);
        su = findViewById(R.id.signup);
        re = findViewById(R.id.loginre);

        final String[] busno = {"Please select Bus No","252-in","252-out","252A-in","252A-out","273C-in","273C-out"};
        ArrayAdapter<String> bus = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, busno);
        bus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP.setAdapter(bus);

        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String em = email.getText().toString();
                String ps = pass.getText().toString();
                final String bus = SP.getSelectedItem().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(em.isEmpty() && ps.isEmpty() && bus.isEmpty())
                {
                    //If all the fields are left blank
                    Toast.makeText(driver_signup.this, "Please fill the fields",Toast.LENGTH_SHORT).show();
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
                else if (bus.isEmpty())
                {
                    Toast.makeText(driver_signup.this,"Please select Bus Number",Toast.LENGTH_SHORT).show();
                    SP.requestFocus();
                }

                else if (!(em.trim().matches(emailPattern)))
                {
                    Toast.makeText(driver_signup.this, "Please enter a valid email address",Toast.LENGTH_SHORT).show();
                }
                else if (!(em.isEmpty() && ps.isEmpty() && bus.isEmpty()))
                {
                    String uniqueID = em;
                    uniqueID = uniqueID.replaceAll("\\.","dot");
                    BusnoDriver.driverbusno = bus;
                    final String finalUniqueID = uniqueID;
                    fireAuth.createUserWithEmailAndPassword(em,ps).addOnCompleteListener(driver_signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                //If sign up is unsuccessfull and task is incomplete
                                Toast.makeText(driver_signup.this, "Signup Unsuccessfull, Please try again",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                db = FirebaseFirestore.getInstance();
                                Map<String, Object> data = new HashMap<>();
                                data.put("EMAILID", em);
                                data.put("BUS_NO",bus);
                                db.collection("SignUpDrivers").document(finalUniqueID).set(data);
                                //Transfer of control to login page
                                startActivity(new Intent(driver_signup.this, DriverLogin.class));
                                finish();
                            }
                        }
                    });
                }
                else
                {
                    //To display short time messages
                    Toast.makeText(driver_signup.this, "Error Ocurred",Toast.LENGTH_SHORT).show();
                }

            }
        });


    re.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(driver_signup.this,DriverLogin.class));
            finish();
        }
    });
    }
}
