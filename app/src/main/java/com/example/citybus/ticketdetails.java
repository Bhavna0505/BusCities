package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ticketdetails extends AppCompatActivity {
    TextView no,frm,to,tm,seat,name,age,via,order,fare,seattype,quota;
    Button returnhome;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketdetails);
        getSupportActionBar().setTitle("Ticket Details");
        order = findViewById(R.id.ticketorderID);
        no = findViewById(R.id.ticketbusno);
        frm = findViewById(R.id.ticketbusfrom);
        to = findViewById(R.id.ticketbusto);
        via = findViewById(R.id.ticketbusvia);
        tm = findViewById(R.id.ticketbustime);
        fare = findViewById(R.id.ticketfare);
        seat = findViewById(R.id.ticketseatno);
        name = findViewById(R.id.ticketname);
        age = findViewById(R.id.ticketage);
        returnhome = findViewById(R.id.buttonreturn);
        seattype = findViewById(R.id.seattype);
        quota = findViewById(R.id.quota);

        order.setText("Order ID : "+ticketbookvalues.orderID);
        no.setText(ticketbookvalues.busno);
        frm.setText("From : "+ticketbookvalues.fromloc);
        to.setText("To :"+ticketbookvalues.toloc);
        via.setText("Via : "+ticketbookvalues.via);
        tm.setText("Time : "+ticketbookvalues.tme+" hrs");
        fare.setText("Total Fare : "+ticketbookvalues.price+" INR");
        seat.setText("Seat Number : "+ticketbookvalues.seatno);
        name.setText("Name : "+ticketbookvalues.travellername);
        age.setText("Age : "+ticketbookvalues.age);
        seattype.setText("Seat Type : "+ticketbookvalues.seatType);
        if(instantbooking.ck1.isChecked())
            quota.setText("Quota : Physically Handicapped");
        else if(instantbooking.ck2.isChecked())
            quota.setText("Quota : Senior Citizen");
        else
            quota.setText("Quota : GENERAL");



        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = date.format(new Date());


        db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("TICKET_ID", ticketbookvalues.orderID);
        data.put("PASSENGER_NAME", ticketbookvalues.travellername);
        data.put("PASSENGER_AGE", ticketbookvalues.age);
        data.put("BUS_NUMBER", ticketbookvalues.busno);
        data.put("SOURCE", ticketbookvalues.fromloc);
        data.put("DESTINATION", ticketbookvalues.toloc);
        data.put("VIA", ticketbookvalues.via);
        data.put("DATE", currentDateandTime);
        data.put("TIME", ticketbookvalues.tme);
        data.put("SEAT_NUMBER", ticketbookvalues.seatno);
        data.put("TOTAL_FARE", ticketbookvalues.price);
        data.put("PAYMENT_STATUS","PAID");
        data.put("EMAIL",PassAttribute.Email);

        if(instantbooking.ck1.isChecked())
            data.put("QUOTA","PHYSICALLY HANDICAPPED");
        else if(instantbooking.ck2.isChecked())
            data.put("QUOTA","SENIOR CITIZEN");
        else
            data.put("QUOTA","GENERAL");
        data.put("SEAT_PREFERENCE",ticketbookvalues.seatType);



        db.collection("BookedTickets").document(ticketbookvalues.orderID).set(data);

        db.collection("BusDetails")
                .whereEqualTo("NO",ticketbookvalues.busno)
                .whereEqualTo("TIME",ticketbookvalues.tme)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                if(instantbooking.ck1.isChecked())
                                    db.collection("BusDetails").document(document.getId()+"").update("HANDI",ticketbookvalues.seatavl-1);
                                else if(instantbooking.ck2.isChecked())
                                    db.collection("BusDetails").document(document.getId()+"").update("SENIOR",ticketbookvalues.seatavl-1);
                                else
                                    db.collection("BusDetails").document(document.getId()+"").update("SEAT",ticketbookvalues.seatavl-1);


                                Toast.makeText(ticketdetails.this,"Available Seats Updated",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(ticketdetails.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        returnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ticketdetails.this,"Redirecting to homepage",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ticketdetails.this,Services.class));
                finish();
            }
        });




    }
}
