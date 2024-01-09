package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class booking_history extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView RV;
    FirestoreRecyclerAdapter<history, historyViewHolder> adp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        getSupportActionBar().setTitle("Booked Ticket History");
        db = FirebaseFirestore.getInstance();
        RV = findViewById(R.id.historyRV);
        //Query
        Query qr = db.collection("BookedTickets").whereEqualTo("EMAIL",PassAttribute.Email).orderBy("DATE");
        //Recycler Options
        FirestoreRecyclerOptions<history> opt = new FirestoreRecyclerOptions.Builder<history>()
                .setQuery(qr,history.class)
                .build();
        adp = new FirestoreRecyclerAdapter<history, historyViewHolder>(opt)
        {
            @NonNull
            @Override
            public historyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historylist,parent,false);
                return new historyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull historyViewHolder historyViewHolder, int i, @NonNull history history)
            {
                historyViewHolder.PAYMENT_STATUS.setText("Payment Status : "+history.getPAYMENT_STATUS());
                historyViewHolder.TOTAL_FARE.setText("Fare : "+history.getTOTAL_FARE());
                historyViewHolder.SEAT_NUMBER.setText("Seat Number : "+history.getSEAT_NUMBER());
                historyViewHolder.DESTINATION.setText("Destination : "+history.getDESTINATION());
                historyViewHolder.SOURCE.setText("Source : "+history.getSOURCE());
                historyViewHolder.BUS_NUMBER.setText(history.getBUS_NUMBER());
                historyViewHolder.TICKET_ID.setText("Ticket ID : "+history.getTICKET_ID());
                historyViewHolder.PASSENGER_AGE.setText("Passenger Age : "+history.getPASSENGER_AGE());
                historyViewHolder.PASSENGER_NAME.setText("Passenger Name : "+history.getPASSENGER_NAME());
                historyViewHolder.DATE.setText("Date of Booking : "+history.getDATE());
                historyViewHolder.TIME.setText("Time : "+history.getTIME());
                historyViewHolder.EMAIL.setText("Email : "+history.getEMAIL());
                historyViewHolder.VIA.setText("Via : "+history.getVIA());
                historyViewHolder.QUOTA.setText("QUOTA : "+history.getQUOTA());
                historyViewHolder.SEAT_PREFERENCE.setText("Seat Type : "+history.getSEAT_PREFERENCE());

            }

        };
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setAdapter(adp);

    }

    private class historyViewHolder extends  RecyclerView.ViewHolder
    {
        private TextView TICKET_ID;
        private TextView PASSENGER_NAME;
        private TextView PASSENGER_AGE;
        private TextView BUS_NUMBER;
        private TextView SOURCE;
        private TextView DESTINATION;
        private TextView VIA;
        private TextView DATE;
        private TextView TIME;
        private TextView SEAT_NUMBER;
        private TextView TOTAL_FARE;
        private TextView PAYMENT_STATUS;
        private TextView EMAIL;
        private TextView QUOTA;
        private TextView SEAT_PREFERENCE;



        public historyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            TICKET_ID = itemView.findViewById(R.id.historyorderid);
            PASSENGER_NAME = itemView.findViewById(R.id.historyname);
            PASSENGER_AGE = itemView.findViewById(R.id.historyage);
            BUS_NUMBER = itemView.findViewById(R.id.historybusno);
            SOURCE = itemView.findViewById(R.id.historyfrom);
            DESTINATION = itemView.findViewById(R.id.historyto);
            VIA = itemView.findViewById(R.id.historyvia);
            DATE = itemView.findViewById(R.id.historydate);
            TIME = itemView.findViewById(R.id.historytime);
            SEAT_NUMBER = itemView.findViewById(R.id.historyseat);
            TOTAL_FARE = itemView.findViewById(R.id.historyfare);
            PAYMENT_STATUS = itemView.findViewById(R.id.historypayment);
            EMAIL = itemView.findViewById(R.id.historyemail);
            QUOTA = itemView.findViewById(R.id.historyquota);
            SEAT_PREFERENCE = itemView.findViewById(R.id.historyseattype);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adp.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adp.stopListening();
    }
}
