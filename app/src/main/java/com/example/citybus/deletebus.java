package com.example.citybus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link deletebus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class deletebus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText no,time;
    Button deletebus;
    FirebaseFirestore db;

    public deletebus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment deletebus.
     */
    // TODO: Rename and change types and number of parameters
    public static deletebus newInstance(String param1, String param2) {
        deletebus fragment = new deletebus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_deletebus, container, false);
        no = rootView.findViewById(R.id.deletebusno);
        time = rootView.findViewById(R.id.deletebustime);
        deletebus = rootView.findViewById(R.id.deletebusbutton);




        deletebus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(no.getText().toString().isEmpty() && time.getText().toString().isEmpty())
        {
            Toast.makeText(getContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
        }
        else if(no.getText().toString().isEmpty())
        {
            no.setError("Please Enter Bus Number");
            no.requestFocus();
        }
        else if(time.getText().toString().isEmpty())
        {
            time.setError("Please Enter Time of Departure");
            time.requestFocus();
        }
        else if(!(no.getText().toString().isEmpty() && time.getText().toString().isEmpty()))
        {
                    db = FirebaseFirestore.getInstance();
                    db.collection("BusDetails")
                            .whereEqualTo("NO",no.getText().toString())
                            .whereEqualTo("TIME",time.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult())
                                        {
                                            db.collection("BusDetails").document(document.getId()+"").delete();
                                            Toast.makeText(getContext(),"Bus Record Deleted Succesfully",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
        }
        else
        {
            Toast.makeText(getContext(), "Error Occurred !!!", Toast.LENGTH_SHORT).show();
        }
            }
        });





        return rootView;
    }
}
