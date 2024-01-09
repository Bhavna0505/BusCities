package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class smartcard extends AppCompatActivity implements PaymentResultListener
{
    EditText Name,dob,addr,aadhar,mob;
    Button Apply;
    CheckBox CK;
    FirebaseFirestore db;
    String TAG ="Payment Failed";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartcard);
        getSupportActionBar().setTitle("Smart Card");
        Checkout.preload(getApplicationContext());
        Name = findViewById(R.id.smartname);
        dob = findViewById(R.id.smartdob);
        addr = findViewById(R.id.smartaddr);
        aadhar = findViewById(R.id.smartaadhar);
        mob = findViewById(R.id.smartmob);
        Apply = findViewById(R.id.smartbutton);
        CK = findViewById(R.id.smartcheckBox);
        Name.setText(PassAttribute.Name);
        Name.setEnabled(false);
        Name.setFocusable(false);

        db = FirebaseFirestore.getInstance();

        String uniqueID = PassAttribute.Email;
        /*uniqueID = uniqueID.replaceAll("\\.","dot");
        db.collection("SmartCardApplications")
                .document(uniqueID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                startActivity(new Intent(smartcard.this,smartcardapplication.class));
                                finish();
                            }
                        }
                    }
                });
*/

        final String finalUniqueID = uniqueID;
        Apply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String NAME = Name.getText().toString();
                String DOB = dob.getText().toString();
                String ADDRESS = addr.getText().toString();
                String AADHAR = aadhar.getText().toString();
                String MOBILE = mob.getText().toString();
                if(DOB.isEmpty())
                {
                    dob.setError("Please enter Date of Birth");
                    dob.requestFocus();
                }
                else if(ADDRESS.isEmpty())
                {
                    addr.setError("Please enter address");
                    addr.requestFocus();
                }
                else if(AADHAR.isEmpty())
                {
                    aadhar.setError("Please enter AADHAR Number");
                    aadhar.requestFocus();
                }
                else if(MOBILE.isEmpty())
                {
                    mob.setError("Please enter Mobile Number");
                    mob.requestFocus();
                }
                else if(!(CK.isChecked()))
                {
                    CK.setError("Please Check this box");
                    CK.requestFocus();
                }
                else if(AADHAR.length() != 12)
                {
                    aadhar.setError("Please enter 12 digit Aadhar Number");
                    aadhar.requestFocus();
                }
                else if(!(NAME.isEmpty() && DOB.isEmpty() && ADDRESS.isEmpty() && AADHAR.isEmpty() && MOBILE.isEmpty()))
                {
                    String CardNo;
                    CardNo = AADHAR.substring(AADHAR.length() - 4);
                    CardNo = CardNo.concat(" ");
                    SimpleDateFormat Year = new SimpleDateFormat("yyyy");
                    CardNo = CardNo.concat(Year.format(new Date()));
                    CardNo = CardNo.concat(" ");
                    SimpleDateFormat date = new SimpleDateFormat("ddMM");
                    CardNo = CardNo.concat(date.format(new Date()));
                    CardNo = CardNo.concat(" ");
                    SimpleDateFormat time = new SimpleDateFormat("HHmm");
                    CardNo = CardNo.concat(time.format(new Date()));

                    String OrderID;
                    OrderID = MOBILE.substring(MOBILE.length()-4);
                    SimpleDateFormat dttm = new SimpleDateFormat("ddMMyyyyHHmmss");
                    OrderID = OrderID.concat(dttm.format(new Date()));
                    OrderID = OrderID.concat(AADHAR.substring(AADHAR.length() - 4));


                    SmartCardAttributes.Name = NAME;
                    SmartCardAttributes.DOB = DOB;
                    SmartCardAttributes.Addr = ADDRESS;
                    SmartCardAttributes.Aadhar = AADHAR;
                    SmartCardAttributes.Contact = MOBILE;
                    SmartCardAttributes.CardNo = CardNo;
                    SmartCardAttributes.UniqueID = finalUniqueID;
                    SmartCardAttributes.Status = "APPLIED";
                    SmartCardAttributes.PaymentStatus = "PAID";
                    SmartCardAttributes.PostalStatus = "To be Updated";
                    SmartCardAttributes.OrderID = OrderID;



                    startPayment();




                }
                else
                {
                    Toast.makeText(smartcard.this,"Error Occured !!! Please Try again",Toast.LENGTH_SHORT).show();

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
            options.put("description", "Smart Card Application");
            // options.put("order_id", ticketbookvalues.orderID);
            options.put("currency", "INR");
            options.put("amount", "75000");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Map<String, Object> data = new HashMap<>();
        data.put("NAME", SmartCardAttributes.Name);
        data.put("DOB", SmartCardAttributes.DOB);
        data.put("AADHAR", SmartCardAttributes.Aadhar);
        data.put("ADDRESS", SmartCardAttributes.Addr);
        data.put("CONTACT_NO", SmartCardAttributes.Contact);
        data.put("APPLICATION_STATUS", SmartCardAttributes.Status);
        data.put("PAYMENT_STATUS", SmartCardAttributes.PaymentStatus);
        data.put("POSTAL_STATUS", SmartCardAttributes.PostalStatus);
        data.put("CARD_NO", SmartCardAttributes.CardNo);
        data.put("ORDER_ID", SmartCardAttributes.OrderID);
        db.collection("SmartCardApplications").document(SmartCardAttributes.UniqueID).set(data);
        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(smartcard.this,smartcardapplication.class));
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Last Transaction Payment failed",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(smartcard.this,Services.class));
        finish();

    }
}
