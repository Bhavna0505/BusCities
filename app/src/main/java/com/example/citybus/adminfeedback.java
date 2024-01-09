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

public class adminfeedback extends AppCompatActivity {

    RecyclerView RV;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter<feedbackrecord, adminfeedback.feedViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminfeedback);
        getSupportActionBar().setTitle("Feedback and Complaints");

        db = FirebaseFirestore.getInstance();
        RV = findViewById(R.id.adminfeedRV);


        //Query
        Query qr = db.collection("Feedback").orderBy("NAME");
        //Recycler Options
        FirestoreRecyclerOptions<feedbackrecord> options = new FirestoreRecyclerOptions.Builder<feedbackrecord>()
                .setQuery(qr,feedbackrecord.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<feedbackrecord, adminfeedback.feedViewHolder>(options)
        {
            @NonNull
            @Override
            public adminfeedback.feedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed,parent,false);
                return new adminfeedback.feedViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull adminfeedback.feedViewHolder feedViewHolder, int i, @NonNull feedbackrecord feed)
            {

                feedViewHolder.NAME.setText("Name : "+feed.getNAME());
                feedViewHolder.EMAIL_ID.setText("Email-ID : "+feed.getEMAIL_ID());
                feedViewHolder.MOBILE.setText("Contact Number : +91"+feed.getMOBILE());
                feedViewHolder.ISSUE_TYPE.setText("Issue Type : "+feed.getISSUE_TYPE());
                feedViewHolder.FEEDBACK.setText("Feedback : "+feed.getFEEDBACK());

            }
        };
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setAdapter(adapter);

    }

    class feedViewHolder extends RecyclerView.ViewHolder
    {
        private TextView NAME;
        private TextView EMAIL_ID;
        private TextView MOBILE;
        private TextView ISSUE_TYPE;
        private TextView FEEDBACK;
        //Contructor
        public feedViewHolder(@NonNull View itemView)
        {
            super(itemView);

            NAME = itemView.findViewById(R.id.adminnamefeed);
            EMAIL_ID = itemView.findViewById(R.id.adminemailfeed);
            MOBILE = itemView.findViewById(R.id.adminmobfeed);
            ISSUE_TYPE = itemView.findViewById(R.id.adminissuefeed);
            FEEDBACK = itemView.findViewById(R.id.adminfeedbackfeed);
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
