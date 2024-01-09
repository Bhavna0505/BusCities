package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class adminsmart extends AppCompatActivity {

    RecyclerView RV;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter<smartapplicationsRV, smartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsmart);
        getSupportActionBar().setTitle("Smart Card Applications");

        db = FirebaseFirestore.getInstance();
        RV = findViewById(R.id.smartRV);


        //Query
        Query qr = db.collection("SmartCardApplications").orderBy("APPLICATION_STATUS").orderBy("POSTAL_STATUS", Query.Direction.DESCENDING);
        //Recycler Options
        FirestoreRecyclerOptions<smartapplicationsRV> options = new FirestoreRecyclerOptions.Builder<smartapplicationsRV>()
                .setQuery(qr,smartapplicationsRV.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<smartapplicationsRV, smartViewHolder>(options)
        {
            @NonNull
            @Override
            public adminsmart.smartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application,parent,false);
                return new adminsmart.smartViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull smartViewHolder smartViewHolder, int i, @NonNull smartapplicationsRV smartapplicationsRV)
            {
                smartViewHolder.NAME.setText("Name : "+smartapplicationsRV.getNAME());
                smartViewHolder.DOB.setText("Date of Birth : "+smartapplicationsRV.getDOB());
                smartViewHolder.AADHAR.setText("AADHAR : "+smartapplicationsRV.getAADHAR());
                smartViewHolder.ADDRESS.setText("Address : "+smartapplicationsRV.getADDRESS());
                smartViewHolder.CONTACT_NO.setText("Contact Number : +91"+smartapplicationsRV.getCONTACT_NO());
                smartViewHolder.APPLICATION_STATUS.setText("Application Status : "+smartapplicationsRV.getAPPLICATION_STATUS());
                smartViewHolder.PAYMENT_STATUS.setText("Payment Status : "+smartapplicationsRV.getPAYMENT_STATUS());
                smartViewHolder.POSTAL_STATUS.setText("Postal Status : "+smartapplicationsRV.getPOSTAL_STATUS());
                smartViewHolder.CARD_NO.setText("Card Number : "+smartapplicationsRV.getCARD_NO());
                smartViewHolder.ORDER_ID.setText("Order ID : "+smartapplicationsRV.getORDER_ID());
            }
        };
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setAdapter(adapter);

    }

    class smartViewHolder extends RecyclerView.ViewHolder
    {
        private TextView NAME;
        private TextView DOB;
        private TextView AADHAR;
        private TextView ADDRESS;
        private TextView CONTACT_NO;
        private TextView APPLICATION_STATUS;
        private TextView PAYMENT_STATUS;
        private TextView POSTAL_STATUS;
        private TextView CARD_NO;
        private TextView ORDER_ID;
        //Contructor
        public smartViewHolder(@NonNull View itemView)
        {
            super(itemView);

            NAME = itemView.findViewById(R.id.adminsmartname);
            DOB = itemView.findViewById(R.id.adminsmartdateofbirth);
            AADHAR = itemView.findViewById(R.id.adminsmartaadhar);
            ADDRESS = itemView.findViewById(R.id.adminsmartaddress);
            CONTACT_NO = itemView.findViewById(R.id.adminsmartmobile);
            APPLICATION_STATUS = itemView.findViewById(R.id.adminsmartapplistatus);
            PAYMENT_STATUS = itemView.findViewById(R.id.adminsmartpaymentstatus);
            POSTAL_STATUS = itemView.findViewById(R.id.adminsmartpostalstatus);
            CARD_NO = itemView.findViewById(R.id.adminsmartcardno);
            ORDER_ID = itemView.findViewById(R.id.adminsmartorderid);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adminsmartattributes.APPLICATION_STATUS = APPLICATION_STATUS.getText().toString();
                    adminsmartattributes.POSTAL_STATUS = POSTAL_STATUS.getText().toString();
                    adminsmartattributes.ORDER_ID = ORDER_ID.getText().toString();
                    adminsmartattributes.CARD_NO = CARD_NO.getText().toString();
                    startActivity(new Intent(adminsmart.this,adminsmartchange.class));
                    finish();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
