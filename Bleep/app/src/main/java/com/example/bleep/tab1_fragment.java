package com.example.bleep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class tab1_fragment extends Fragment
{

    EditText et_bp_sys_full , et_bp_dia_full ;
    TextView tv_var_sys , tv_var_dia , tv_sys , tv_dia;
    Button btn_enter;
    private FirebaseFirestore tab1_fbfs = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab1_fragment , container , false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        et_bp_sys_full = getActivity().findViewById(R.id.et_bp_level1);
        et_bp_dia_full = getActivity().findViewById(R.id.et_bp_level2);
        tv_sys = getActivity().findViewById(R.id.tvtv_bp_sistolic);
        tv_dia = getActivity().findViewById(R.id.tvtv_bp_diastolic);
        tv_var_sys = getActivity().findViewById(R.id.tv_bp_sys_change);
        tv_var_dia = getActivity().findViewById(R.id.tv_bp_dia_change);
        btn_enter = getActivity().findViewById(R.id.btn_enter);

        btn_enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Integer s = Integer.valueOf(et_bp_sys_full.getText().toString());
                Integer d = Integer.valueOf(et_bp_dia_full.getText().toString());

                tv_sys.setText(Integer.toString(s));
                tv_dia.setText(Integer.toString(d));

                Integer vs = s - 120;
                Integer vd = d - 80;

               /* Calendar tab1_date = Calendar.getInstance();
                SimpleDateFormat tab1_df = new SimpleDateFormat("yyyy/mm/dd");
                String tab1_date_str = tab1_df.format(tab1_date.getTime());

               SimpleDateFormat tab1_sdf = new SimpleDateFormat("yyyy/mm/dd");
               String tab1_date_str = tab1_sdf.format(new Date());*/

                Calendar tab1_date = Calendar.getInstance();
                int day = tab1_date.get(Calendar.DAY_OF_MONTH);
                int month = tab1_date.get(Calendar.MONTH);
                int year = tab1_date.get(Calendar.YEAR);
                String tab1_date_str = day + "/" + (month + 1) + "/" + year;

                String fs_var_sis = Integer.toString(vs);
                String fs_var_dia = Integer.toString(vd);
                String fs_sis = Integer.toString(s);
                String fs_dia = Integer.toString(d);

                CollectionReference tab1_cr = tab1_fbfs.collection("Blood Pressure Details");
                tab1_blood_pressure_details tbpd = new tab1_blood_pressure_details(fs_sis , fs_dia , fs_var_sis , fs_var_dia , tab1_date_str);

                tab1_cr.add(tbpd).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(getContext() , "Success!!" , Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getContext() , "Unsuccessful..." , Toast.LENGTH_SHORT).show();
                    }
                });

                if(s < 120 || s > 120 || s ==120)
                {
                    tv_var_sys.setText(Integer.toString(vs));
                }

                if(d < 80 || d > 80 || d == 80)
                {
                    tv_var_dia.setText(Integer.toString(vd));
                }
            }
        });
    }
}
