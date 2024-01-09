package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class adminsmartchange extends AppCompatActivity {
    Spinner SP1,SP2;
    TextView order,card;
    FirebaseFirestore db;
    Button up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsmartchange);
        getSupportActionBar().setTitle("Update Status");

        SP1 = findViewById(R.id.updateappsta);
        SP2 = findViewById(R.id.updatepossta);
        order = findViewById(R.id.updateorder);
        card = findViewById(R.id.updatecard);
        up = findViewById(R.id.updatebuttonadmin);


        String[] APPLISTATUS= {"APPLIED","APPROVED"};
        String[] POSTALSTATUS = {"TO BE UPDATED","DISPATCHED","DELIVERED"};

        ArrayAdapter<String> status = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, APPLISTATUS);
        status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP1.setAdapter(status);

        ArrayAdapter<String> posstat = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, POSTALSTATUS);
        posstat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP2.setAdapter(posstat);


        order.setText(adminsmartattributes.ORDER_ID);
        card.setText(adminsmartattributes.CARD_NO);





        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String statusselected = SP1.getSelectedItem().toString();
                final String postselected = SP2.getSelectedItem().toString();
                db = FirebaseFirestore.getInstance();
                db.collection("SmartCardApplications")
                        .whereEqualTo("ORDER_ID",adminsmartattributes.ORDER_ID.substring(11))
                        .whereEqualTo("CARD_NO",adminsmartattributes.CARD_NO.substring(14))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task)
                            {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult())
                                    {
                                        db.collection("SmartCardApplications").document(document.getId()+"").update("APPLICATION_STATUS",statusselected);
                                        db.collection("SmartCardApplications").document(document.getId()+"").update("POSTAL_STATUS",postselected);
                                        Toast.makeText(adminsmartchange.this,"Status Updated",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(adminsmartchange.this,adminpage.class));
                                        finish();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(adminsmartchange.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}
