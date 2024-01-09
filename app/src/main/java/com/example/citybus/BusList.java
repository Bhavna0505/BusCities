package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BusList extends AppCompatActivity
{
    RecyclerView RV;
    TextView Txt;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter<Buses, BusesViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        getSupportActionBar().setTitle("Ticket Booking");
        String from = ticketbookvalues.fromloc;
        String to = ticketbookvalues.toloc;

        RV = findViewById(R.id.buslist);

        //Query
        Query qr = db.collection("BusDetails").whereEqualTo("FROM", from).whereEqualTo("TO",to).orderBy("TIME");
        //Recycler Options
        FirestoreRecyclerOptions<Buses> options = new FirestoreRecyclerOptions.Builder<Buses>()
                .setQuery(qr,Buses.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Buses, BusesViewHolder>(options)
        {
            @NonNull
            @Override
            public BusesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buslist,parent,false);
                return new BusesViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BusesViewHolder busesViewHolder, int i, @NonNull Buses buses)
            {
                busesViewHolder.FROMTO.setText(buses.getFROM()+" ---> "+buses.getTO());
                busesViewHolder.NO.setText(buses.getNO());

                if(instantbooking.ck1.isChecked())
                    busesViewHolder.SEAT.setText("Seats Available (Handicapped Quota) : "+buses.getHANDI());
                else if(instantbooking.ck2.isChecked())
                    busesViewHolder.SEAT.setText("Seats Available (Senior Citizen Quota) : "+buses.getSENIOR());
                else
                    busesViewHolder.SEAT.setText("Seats Available : "+buses.getSEAT());

                busesViewHolder.TIME.setText("Time : "+buses.getTIME());
                busesViewHolder.Via.setText("Via : "+buses.getVia());
                busesViewHolder.price.setText("Price : "+buses.getPrice());
            }
        };
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setAdapter(adapter);


    }
    class BusesViewHolder extends RecyclerView.ViewHolder
    {
        private TextView NO;
        private TextView FROMTO;
        private TextView SEAT;
        private TextView TIME;
        private TextView Via;
        private TextView price;
        //Contructor
        public BusesViewHolder(@NonNull View itemView)
        {
            super(itemView);

            NO = itemView.findViewById(R.id.listBusNo);
            FROMTO = itemView.findViewById(R.id.listBusfromto);
            Via = itemView.findViewById(R.id.listBusvia);
            SEAT = itemView.findViewById(R.id.listBusseat);
            TIME = itemView.findViewById(R.id.listBustime);
            price = itemView.findViewById(R.id.listBusprice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketbookvalues.tme = TIME.getText().toString().substring(7);
                    ticketbookvalues.busno = NO.getText().toString();
                    ticketbookvalues.via = Via.getText().toString().substring(6);
                    ticketbookvalues.price = Integer.parseInt(price.getText().toString().substring(8));

                    if(instantbooking.ck1.isChecked())
                        ticketbookvalues.seatavl = Integer.parseInt(SEAT.getText().toString().substring(38));
                    else if(instantbooking.ck2.isChecked())
                        ticketbookvalues.seatavl = Integer.parseInt(SEAT.getText().toString().substring(41));
                    else
                        ticketbookvalues.seatavl = Integer.parseInt(SEAT.getText().toString().substring(18));

                    if(ticketbookvalues.seatavl == 0)
                    {
                        Toast.makeText(BusList.this, "Sorry No Seats Available !!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(BusList.this,"You Selected "+ticketbookvalues.busno, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BusList.this, TravellerDetails.class));
                    }
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
