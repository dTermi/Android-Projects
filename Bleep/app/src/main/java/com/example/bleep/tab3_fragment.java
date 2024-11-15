package com.example.bleep;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PrivateKey;
import java.util.Calendar;
import java.util.Date;

public class tab3_fragment extends Fragment
{
    private EditText et_fasting , et_after_fasting;
    private TextView tv_fast_var , tv_after_fast_var;
    Integer i_ip_fast , i_ip_after_fast ,i_var_fast , i_var_after_fast;
    Button btn_tab3_enter;
    FirebaseFirestore tab3_fbfs = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab3_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        et_fasting = getActivity().findViewById(R.id.et_sugar_fasting);
        et_after_fasting = getActivity().findViewById(R.id.et_sugar_after_fasting);

        tv_fast_var = getActivity().findViewById(R.id.tv_var_fasting);
        tv_after_fast_var = getActivity().findViewById(R.id.tv_var_after_fasting);

        btn_tab3_enter = getActivity().findViewById(R.id.btn_tab3_enter);

        btn_tab3_enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                et_fasting.getText().toString();
                et_after_fasting.getText().toString();

                i_ip_fast = Integer.valueOf(et_fasting.getText().toString());
                i_ip_after_fast = Integer.valueOf(et_after_fasting.getText().toString());

                i_var_fast = i_ip_fast - 100;
                i_var_after_fast = i_ip_after_fast - 140;

                String str_fast = Integer.toString(i_var_fast);
                String str_after_fast = Integer.toString(i_ip_after_fast);

                if(i_ip_fast < 100 || i_ip_fast > 100 || i_ip_fast == 100)
                {
                    tv_fast_var.setText(Integer.toString(i_var_fast));
                }

                if(i_ip_after_fast < 140 || i_ip_after_fast > 140 || i_ip_after_fast == 140)
                {
                    tv_after_fast_var.setText(Integer.toString(i_var_after_fast ));
                }

                CollectionReference tab3_cr = tab3_fbfs.collection("Sugar Level Information");
                tab3_sugar_information_fbfs tsif= new tab3_sugar_information_fbfs(et_fasting.getText().toString() , et_after_fasting.getText().toString() , str_fast , str_after_fast);

                tab3_cr.add(tsif).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(getContext() , "success" , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getContext() , "Un-successful" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}