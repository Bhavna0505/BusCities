package com.example.citybus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addbus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addbus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText no,from,to,via,fare,time;
    Button add;
    FirebaseFirestore db;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addbus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addbus.
     */
    // TODO: Rename and change types and number of parameters
    public static addbus newInstance(String param1, String param2) {
        addbus fragment = new addbus();
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
        View rootView = inflater.inflate(R.layout.fragment_addbus, container, false);
        no = rootView.findViewById(R.id.addbusno);
        from = rootView.findViewById(R.id.bussource);
        to = rootView.findViewById(R.id.busdestination);
        via = rootView.findViewById(R.id.busvia);
        fare = rootView.findViewById(R.id.busprice);
        time = rootView.findViewById(R.id.bustime);

        add = rootView.findViewById(R.id.addbusbutton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(via.getText().toString().isEmpty() && from.getText().toString().isEmpty() && to.getText().toString().isEmpty()
                && fare.getText().toString().isEmpty() && time.getText().toString().isEmpty() && no.getText().toString().isEmpty())
        {
            Toast.makeText(getContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
        }
        else if(no.getText().toString().isEmpty())
        {
            no.setError("Please Enter Bus Number");
            no.requestFocus();

        }
        else if(from.getText().toString().isEmpty())
        {
            from.setError("Please Enter Source Station");
            from.requestFocus();
        }
        else if(to.getText().toString().isEmpty())
        {
            to.setError("Please Enter Destination Station");
            to.requestFocus();
        }
        else if(via.getText().toString().isEmpty())
        {
            via.setError("Please Enter Via Station");
            via.requestFocus();
        }
        else if(fare.getText().toString().isEmpty())
        {
            fare.setError("Please Enter Fare");
            fare.requestFocus();
        }
        else if(time.getText().toString().isEmpty())
        {
            time.setError("Please Enter Departure Time");
            time.requestFocus();
        }
        else if(!(via.getText().toString().isEmpty() && from.getText().toString().isEmpty() && to.getText().toString().isEmpty() && fare.getText().toString().isEmpty() && time.getText().toString().isEmpty() && no.getText().toString().isEmpty()))
        {


                    db = FirebaseFirestore.getInstance();
                    Map<String, Object> data = new HashMap<>();
                    data.put("FROM", from.getText().toString());
                    data.put("TO",to.getText().toString());
                    data.put("SEAT",4);
                    data.put("HANDI",1);
                    data.put("SENIOR",1);
                    data.put("TIME",time.getText().toString());
                    data.put("Via",via.getText().toString());
                    data.put("price",Integer.parseInt(fare.getText().toString()));
                    data.put("NO",no.getText().toString());
                    db.collection("BusDetails").add(data);
                    Toast.makeText(getContext(),"Bus Details Added",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getContext(),"Error Occurred !!!",Toast.LENGTH_SHORT).show();
        }
            }
        });


        return rootView;
    }
}
