package com.example.citybus;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.mtp.MtpObjectInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class feedback extends AppCompatActivity {
    Spinner SP;
    EditText name,mobno,feed;
    Button sendfeed;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] issuetype = {"Technical Issue","Rash Driving Issue","Offline Ticket Issue","Refund Issue","Other Issue"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setTitle("Feedback & Complaints");
        SP = findViewById(R.id.feedspinner);
        name = findViewById(R.id.feedname);
        name.setText(PassAttribute.Name);
        mobno = findViewById(R.id.feedmob);
        feed = findViewById(R.id.feedback);
        sendfeed = findViewById(R.id.feedbuttonsubmit);

        ArrayAdapter<String> issue = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, issuetype);
        issue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP.setAdapter(issue);
        //To generate unique ID
        String PassedEmail = PassAttribute.Email;
        PassedEmail = PassedEmail.replaceAll("\\.","dot");
        //String uniqueID = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy, EEE 'at' HH:mm:ss z");
        String currentDateandTime = date.format(new Date());
        final String uniqueID = " "+PassedEmail+" "+currentDateandTime;
        final String ID = PassedEmail;

        sendfeed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String NameofUser = name.getText().toString();
                String MobnoofUser = mobno.getText().toString();
                String FeedofUser = feed.getText().toString();
                String IssueofUser = SP.getSelectedItem().toString();
                if(NameofUser.isEmpty())
                {
                    name.setError("Please Enter Name");
                    name.requestFocus();
                    sendfeed.setEnabled(false);
                }
                else if(MobnoofUser.isEmpty())
                {
                    mobno.setError("Please Enter Mobile number");
                    mobno.requestFocus();
                    sendfeed.setEnabled(false);
                }
                else if(FeedofUser.isEmpty())
                {
                    feed.setError("Please give Feedback");
                    feed.requestFocus();
                    sendfeed.setEnabled(false);
                }
                else if(IssueofUser.isEmpty())
                {
                    Toast.makeText(feedback.this,"Please select the issue type", Toast.LENGTH_SHORT).show();
                    SP.requestFocus();
                    sendfeed.setEnabled(false);
                }
                else if (!(NameofUser.isEmpty() && MobnoofUser.isEmpty() && FeedofUser.isEmpty() && IssueofUser.isEmpty()))
                {
                    sendfeed.setEnabled(true);
                    db = FirebaseFirestore.getInstance();
                    Map<String, Object> data = new HashMap<>();
                    data.put("NAME", NameofUser);
                    data.put("MOBILE",MobnoofUser);
                    data.put("ISSUE_TYPE",IssueofUser);
                    data.put("FEEDBACK",FeedofUser);
                    data.put("EMAIL_ID",ID);
                    db.collection("Feedback").document(uniqueID).set(data);



                    Toast.makeText(feedback.this,"Feedback Successfully Submitted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(feedback.this,Services.class));
                    finish();
                }
                else
                {
                    Toast.makeText(feedback.this,"Error Occurred !!! Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
