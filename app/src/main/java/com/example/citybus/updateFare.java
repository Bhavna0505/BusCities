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
 * Use the {@link updateFare#newInstance} factory method to
 * create an instance of this fragment.
 */
public class updateFare extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button bt;
    EditText bus,fare;
    FirebaseFirestore db;

    public updateFare() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment updateFare.
     */
    // TODO: Rename and change types and number of parameters
    public static updateFare newInstance(String param1, String param2) {
        updateFare fragment = new updateFare();
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
        View rootView = inflater.inflate(R.layout.fragment_update_fare, container, false);
        bt = rootView.findViewById(R.id.updatefarebutton);
        bus = rootView.findViewById(R.id.busnumberupdate);
        fare = rootView.findViewById(R.id.updatefareedittext);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String busno = bus.getText().toString();
                final String updatedfare = fare.getText().toString();

                if(busno.isEmpty() && updatedfare.isEmpty())
                {
                    Toast.makeText(getContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else if(busno.isEmpty())
                {
                    bus.setError("Enter Bus No");
                    bus.requestFocus();
                }
                else if(updatedfare.isEmpty())
                {
                    fare.setError("Enter Updated fare");
                    fare.requestFocus();
                }
                else if(!(busno.isEmpty() && updatedfare.isEmpty()))
                {
                    db = FirebaseFirestore.getInstance();
                    db.collection("BusDetails")
                            .whereEqualTo("NO",busno)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult())
                                        {
                                            db.collection("BusDetails").document(document.getId()+"").update("price",Integer.parseInt(updatedfare));
                                            Toast.makeText(getContext(),"Fare Updated",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(),"Error Occured",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return rootView;
    }
}
