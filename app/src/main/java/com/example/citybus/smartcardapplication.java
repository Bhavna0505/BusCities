package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class smartcardapplication extends AppCompatActivity {
    TextView name,dob,aadhar,addr,orderid,payment,status,postal,contact,card;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartcardapplication);
        getSupportActionBar().setTitle("Application Details");
        name = findViewById(R.id.appliname);
        dob = findViewById(R.id.appliDOB);
        aadhar = findViewById(R.id.appliaadhar);
        addr = findViewById(R.id.appliaddress);
        orderid = findViewById(R.id.appliorderid);
        status = findViewById(R.id.applistatus);
        payment = findViewById(R.id.applipayment);
        postal = findViewById(R.id.applipostal);
        contact = findViewById(R.id.applicontact);
        card = findViewById(R.id.applicardno);


        String uniqueID = PassAttribute.Email;
        uniqueID = uniqueID.replaceAll("\\.","dot");

        db = FirebaseFirestore.getInstance();

        db.collection("SmartCardApplications")
                .document(uniqueID)
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
                                name.setText("Name : "+document.getString("NAME"));
                                dob.setText("DOB : "+document.getString("DOB"));
                                aadhar.setText("AADHAR No : "+document.getString("AADHAR"));
                                addr.setText("Address : "+document.getString("ADDRESS"));
                                orderid.setText("Order ID : "+document.getString("ORDER_ID"));
                                status.setText("Application Status : "+document.getString("APPLICATION_STATUS"));
                                payment.setText("Payment Status : "+document.getString("PAYMENT_STATUS"));
                                postal.setText("Postal Status : "+document.getString("POSTAL_STATUS"));
                                contact.setText("Contact No : "+document.getString("CONTACT_NO"));
                                card.setText("Card No : "+document.getString("CARD_NO"));
                            }
                            else
                            {
                                Toast.makeText(smartcardapplication.this, "Record not found",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(smartcardapplication.this, "Error getting document",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(smartcardapplication.this,Services.class));
    }
}
