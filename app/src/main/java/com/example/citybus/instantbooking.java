package com.example.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class instantbooking extends AppCompatActivity {

    Spinner FROM,TO;
    Button search;
    static CheckBox ck1,ck2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instantbooking);
        getSupportActionBar().setTitle("Ticket Booking");
        String[] FROMLOCATIONS= {"FROM","Majestic","Yeswanthpur TTMC","Shivajinagar","Peenya 2nd Stage","Jalahalli Cross"};
        String[] TOLOCATIONS = {"TO","Majestic","Yeswanthpur TTMC","Shivajinagar","Peenya 2nd Stage","Jalahalli Cross"};
        FROM = findViewById(R.id.bookingfrom);
        TO = findViewById(R.id.bookingto);
        search = findViewById(R.id.bookingbuttonsearch);
        ck1 = findViewById(R.id.handicap);
        ck2 = findViewById(R.id.citizen);

        ArrayAdapter<String> from = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, FROMLOCATIONS);
        from.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FROM.setAdapter(from);

        ArrayAdapter<String> to = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TOLOCATIONS);
        to.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TO.setAdapter(to);

        search.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ticketbookvalues.fromloc =  FROM.getSelectedItem().toString();
                ticketbookvalues.toloc = TO.getSelectedItem().toString();
                if(ticketbookvalues.fromloc.equals("FROM"))
                {
                    Toast.makeText(instantbooking.this,"Please select the FROM station",Toast.LENGTH_SHORT).show();
                    FROM.requestFocus();
                }
                else if(ticketbookvalues.toloc.equals("TO"))
                {
                    Toast.makeText(instantbooking.this,"Please select the TO station",Toast.LENGTH_SHORT).show();
                    TO.requestFocus();
                }/*
                else if(ck1.isChecked())
                {
                    Toast.makeText(instantbooking.this,"You are looking in Physically Handicapped Quota",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(instantbooking.this,BusListHandi.class));
                }
                else if(ck2.isChecked())
                {
                    Toast.makeText(instantbooking.this,"You are looking in Senior Citizen Quota",Toast.LENGTH_SHORT).show();
                }*/
                else if(!(ticketbookvalues.fromloc.isEmpty() && ticketbookvalues.toloc.isEmpty()))
                {
                    startActivity(new Intent(instantbooking.this,BusList.class));
                }
                else
                {
                    Toast.makeText(instantbooking.this,"Error Occurred !!! Please Try Again...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
