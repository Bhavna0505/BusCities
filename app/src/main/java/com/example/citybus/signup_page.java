package com.example.citybus;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class signup_page extends AppCompatActivity
{
    //Declaring the elements
    EditText emailID, password, repassword, Full_name;
    Button btsignup;
    RadioGroup gendergroup;
    RadioButton genderbutton;
    TextView returnToLogin;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        getSupportActionBar().setTitle("Sign Up");
        fireAuth = FirebaseAuth.getInstance();//Firebase Authentication
        //Fetching value of the the elements
        emailID = findViewById(R.id.signupemail);
        password = findViewById(R.id.signuppass);
        repassword = findViewById(R.id.signuprepass);
        Full_name = findViewById(R.id.signupname);
        returnToLogin = findViewById(R.id.returnlogin);
        gendergroup = findViewById(R.id.radiogender);
        btsignup = findViewById(R.id.buttonsignup);

        btsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String name = Full_name.getText().toString();
                final String email = emailID.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String  pass = password.getText().toString();
                String repass = repassword.getText().toString();
                int selectedID = gendergroup.getCheckedRadioButtonId();
                if(name.isEmpty() && email.isEmpty() && pass.isEmpty() && repass.isEmpty() && selectedID == -1)
                {
                    //If all the fields are left blank
                    Toast.makeText(signup_page.this, "Please fill the fields",Toast.LENGTH_SHORT).show();
                }
                else if (name.isEmpty())
                {
                    //If name is left blank
                    Full_name.setError("Please enter Full name");
                    Full_name.requestFocus();
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
                else if (repass.isEmpty())
                {
                    //If confirm password is left blank
                    repassword.setError("Please enter confirmation password");
                    repassword.requestFocus();
                }
                else if (selectedID == -1)
                {
                    //If gender is not selected
                    Toast.makeText(signup_page.this, "Please select gender",Toast.LENGTH_SHORT).show();
                    gendergroup.requestFocus();
                }
                else if (!(pass.equals(repass)))
                {
                    //If password does not matches with confirm password
                    repassword.setError("Password and Confirm Password does not match");
                    repassword.requestFocus();
                }
                else if (!(email.trim().matches(emailPattern)))
                {
                    //To check whether the email is correct
                    Toast.makeText(signup_page.this, "Please enter a valid email address",Toast.LENGTH_SHORT).show();
                }
                else if (!(name.isEmpty() && email.isEmpty() && pass.isEmpty() && repass.isEmpty() && selectedID == -1))
                {
                    String uniqueID = email;
                    uniqueID = uniqueID.replaceAll("\\.","dot");
                    genderbutton = gendergroup.findViewById(selectedID);
                    final String gen = genderbutton.getText().toString();
                    PassAttribute.Email=email;
                    PassAttribute.Name=name;
                    PassAttribute.Gender=gen;
                    final String finalUniqueID = uniqueID;
                    fireAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(signup_page.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                //If sign up is unsuccessfull and task is incomplete
                                Toast.makeText(signup_page.this, "Signup Unsuccessfull, Please try again",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                db = FirebaseFirestore.getInstance();
                                Map<String, Object> data = new HashMap<>();
                                data.put("EMAILID", email);
                                data.put("NAME",name);
                                data.put("GENDER",gen);
                                db.collection("SignUpUsers").document(finalUniqueID).set(data);
                                //Transfer of control to login page
                                startActivity(new Intent(signup_page.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
                }
                else
                {
                    //To display short time messages
                    Toast.makeText(signup_page.this, "Error Ocurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
        returnToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Used for change for page
                Intent i = new Intent(signup_page.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
