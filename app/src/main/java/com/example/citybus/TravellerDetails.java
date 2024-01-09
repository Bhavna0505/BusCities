package com.example.citybus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TravellerDetails extends AppCompatActivity implements PaymentResultListener
{
    EditText name,age;
    Button pay;
    String TAG ="Payment Failed";
    RadioGroup seatgroup;
    RadioButton seatpreference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveller_details);
        Checkout.preload(getApplicationContext());
        name = findViewById(R.id.travellername);
        age = findViewById(R.id.travellerage);
        seatgroup = findViewById(R.id.radioseat);
        pay = findViewById(R.id.travellersubmitbutton);
        pay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String nm = name.getText().toString();
               String ag = age.getText().toString();
               if(nm.isEmpty())
               {
                   name.setError("Please Enter Name");
                   name.requestFocus();
               }
               else if(ag.isEmpty())
               {
                   age.setError("Please Enter Age");
                   age.requestFocus();
               }
               else if(Integer.parseInt(ag)>150 || Integer.parseInt(ag)<5)
               {
                   age.setError("Please Enter Valid Age between 5 and 100");
               }
               else if(instantbooking.ck2.isChecked() && Integer.parseInt(ag)<60)
               {
                   age.setError("You are not a Senior Citizen");
               }
               else if(!(nm.isEmpty() && ag.isEmpty() && (Integer.parseInt(ag)>100 || Integer.parseInt(ag)<5)))
               {
                    ticketbookvalues.travellername = nm;
                    ticketbookvalues.age = ag;

                   int selectedID = seatgroup.getCheckedRadioButtonId();
                   if(selectedID!=-1)
                   {
                       seatpreference = seatgroup.findViewById(selectedID);
                       String seattype = seatpreference.getText().toString();
                       ticketbookvalues.seatType = seattype;
                   }
                   else
                   {
                       ticketbookvalues.seatType = "NOT SELECTED";
                   }


                   if(ticketbookvalues.seatType.equals("Window Seat")) {
                       ticketbookvalues.price = ticketbookvalues.price + 2;

                       if(instantbooking.ck1.isChecked()) {
                           ticketbookvalues.seatno = 12;
                       }
                       else if(instantbooking.ck2.isChecked()) {
                           ticketbookvalues.seatno = 13;
                       }
                       else if(ticketbookvalues.seatavl == 4)
                           ticketbookvalues.seatno = 20;
                       else if(ticketbookvalues.seatavl == 3)
                           ticketbookvalues.seatno = 21;
                       else if(ticketbookvalues.seatavl == 2)
                           ticketbookvalues.seatno = 24;
                       else
                           ticketbookvalues.seatno = 25;

                   }
                   else if(ticketbookvalues.seatType.equals("Non-Window Seat")) {

                       if(instantbooking.ck1.isChecked()) {
                           ticketbookvalues.seatno = 11;
                       }
                       else if(instantbooking.ck2.isChecked()) {
                           ticketbookvalues.seatno = 14;
                       }
                       else if(ticketbookvalues.seatavl == 4)
                           ticketbookvalues.seatno = 19;
                       else if(ticketbookvalues.seatavl == 3)
                           ticketbookvalues.seatno = 22;
                       else if(ticketbookvalues.seatavl == 2)
                           ticketbookvalues.seatno = 23;
                       else
                           ticketbookvalues.seatno = 26;
                   }
                   else
                   {
                       if(instantbooking.ck1.isChecked()) {
                           ticketbookvalues.seatno = 12;
                           ticketbookvalues.seatType="Window Seat";
                       }
                       else if(instantbooking.ck2.isChecked()) {
                           ticketbookvalues.seatno = 13;
                           ticketbookvalues.seatType="Window Seat";
                       }
                       else if(ticketbookvalues.seatavl == 4) {
                           ticketbookvalues.seatno = 15;
                           ticketbookvalues.seatType="Non-Window Seat";
                       }
                       else if(ticketbookvalues.seatavl == 3) {
                           ticketbookvalues.seatno = 16;
                           ticketbookvalues.seatType="Window Seat";
                       }
                       else if(ticketbookvalues.seatavl == 2) {
                           ticketbookvalues.seatno = 17;
                           ticketbookvalues.seatType="Window Seat";
                       }
                       else {
                           ticketbookvalues.seatno = 18;
                           ticketbookvalues.seatType="Non-Window Seat";
                       }
                   }


                    ticketbookvalues.orderID=ticketbookvalues.busno+ticketbookvalues.age+"";
                    SimpleDateFormat date = new SimpleDateFormat("ddMMyyyyHHmmss");
                    ticketbookvalues.orderID = ticketbookvalues.orderID.concat(date.format(new Date()));
                    startPayment();

               }
               else
               {
                   Toast.makeText(TravellerDetails.this,"Error Occurred !!! Please Try Again",Toast.LENGTH_SHORT).show();
               }
           }
       });

    }

    public void startPayment()
    {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_jQtY6ghfuOhTDd");
        final Activity activity = this;
        try
        {
            JSONObject options = new JSONObject();
            options.put("name", "City Bus");
            options.put("description", "Instant Ticket Booking");
           // options.put("order_id", ticketbookvalues.orderID);
            options.put("currency", "INR");
            options.put("amount", (100*ticketbookvalues.price)+"");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TravellerDetails.this,ticketdetails.class));
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TravellerDetails.this,Services.class));
        finish();

    }
}
