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

public class tab2_fragment extends Fragment
{
    private Button btn_calc;

    private TextView tv1 , tv2 , tv3 , tv4 , tv5 , tv6 , tv7 , tv8 , tv9 , tv10 , tv11 , tv12 , tv_total , tv_ideal_total;
    private TextView tot_tv1 , tot_tv2 , tot_tv3 ,tot_tv4 , tot_tv5 , tot_tv6 , tot_tv7 , tot_tv8 , tot_tv9 , tot_tv10 , tot_tv11 , tot_tv12;
    private EditText et1 , et2 , et3 , et4 , et5 , et6 , et7 , et8 , et9 , et10 , et11 , et12;
    FirebaseFirestore tab2_fbfs = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab2_fragment , container , false);

        return view;
    }

   /* @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        tot_tv1 = getActivity().findViewById(R.id.tv_table_layout_row1_tot);
        tot_tv2 = getActivity().findViewById(R.id.tv_table_layout_row2_tot);
        tot_tv3 = getActivity().findViewById(R.id.tv_table_layout_row3_tot);
        tot_tv4 = getActivity().findViewById(R.id.tv_table_layout_row4_tot);
        tot_tv5 = getActivity().findViewById(R.id.tv_table_layout_row5_tot);
        tot_tv6 = getActivity().findViewById(R.id.tv_table_layout_row6_tot);
        tot_tv7 = getActivity().findViewById(R.id.tv_table_layout_row7_tot);
        tot_tv8 = getActivity().findViewById(R.id.tv_table_layout_row8_tot);
        tot_tv9 = getActivity().findViewById(R.id.tv_table_layout_row9_tot);
        tot_tv10 = getActivity().findViewById(R.id.tv_table_layout_row10_tot);
        tot_tv11 = getActivity().findViewById(R.id.tv_table_layout_row11_tot);
        tot_tv12 = getActivity().findViewById(R.id.tv_table_layout_row12_tot);
        tv_total = getActivity().findViewById(R.id.tv_cal_tra_total_value);

        btn_calc = getActivity().findViewById(R.id.btn_cal_tra_total_calc);

        //       Reference to Calories Column
        tv1 = getActivity().findViewById(R.id.tv_table_layout_row1_cal);
        tv2 = getActivity().findViewById(R.id.tv_table_layout_row2_cal);
        tv3 = getActivity().findViewById(R.id.tv_table_layout_row3_cal);
        tv4 = getActivity().findViewById(R.id.tv_table_layout_row4_cal);
        tv5 = getActivity().findViewById(R.id.tv_table_layout_row5_cal);
        tv6 = getActivity().findViewById(R.id.tv_table_layout_row6_cal);
        tv7 = getActivity().findViewById(R.id.tv_table_layout_row7_cal);
        tv8 = getActivity().findViewById(R.id.tv_table_layout_row8_cal);
        tv9 = getActivity().findViewById(R.id.tv_table_layout_row9_cal);
        tv10 = getActivity().findViewById(R.id.tv_table_layout_row10_cal);
        tv11 = getActivity().findViewById(R.id.tv_table_layout_row11_cal);
        tv12 = getActivity().findViewById(R.id.tv_table_layout_row12_cal);

        //       Reference to CONSUMED EditText Column
        et1 = getActivity().findViewById(R.id.et_table_layout_row1_con);
        et2 = getActivity().findViewById(R.id.et_table_layout_row2_con);
        et3 = getActivity().findViewById(R.id.et_table_layout_row3_con);
        et4 = getActivity().findViewById(R.id.et_table_layout_row4_con);
        et5 = getActivity().findViewById(R.id.et_table_layout_row5_con);
        et6 = getActivity().findViewById(R.id.et_table_layout_row6_con);
        et7 = getActivity().findViewById(R.id.et_table_layout_row7_con);
        et8 = getActivity().findViewById(R.id.et_table_layout_row8_con);
        et9 = getActivity().findViewById(R.id.et_table_layout_row9_con);
        et10 = getActivity().findViewById(R.id.et_table_layout_row10_con);
        et11 = getActivity().findViewById(R.id.et_table_layout_row11_con);
        et12 = getActivity().findViewById(R.id.et_table_layout_row12_con);

        btn_calc.setOnClickListener(new View.OnClickListener()
        {
            String e1 = et1.getText().toString();
            String e2 = et2.getText().toString();
            String e3 = et3.getText().toString();
            String e4 = et4.getText().toString();
            String e5 = et5.getText().toString();
            String e6 = et6.getText().toString();
            String e7 = et7.getText().toString();
            String e8 = et8.getText().toString();
            String e9 = et9.getText().toString();
            String e10 = et10.getText().toString();
            String e11 = et11.getText().toString();
            String e12 = et12.getText().toString();


            String gt_cal1 = tv1.getText().toString();
            String gt_cal2 = tv2.getText().toString();
            String gt_cal3 = tv3.getText().toString();
            String gt_cal4 = tv4.getText().toString();
            String gt_cal5 = tv5.getText().toString();
            String gt_cal6 = tv6.getText().toString();
            String gt_cal7 = tv7.getText().toString();
            String gt_cal8 = tv8.getText().toString();
            String gt_cal9 = tv9.getText().toString();
            String gt_cal10 = tv10.getText().toString();
            String gt_cal11 = tv11.getText().toString();
            String gt_cal12 = tv12.getText().toString();


            @Override
            public void onClick(View v)
            {
                Integer i1 = Integer.valueOf(e1);
                Integer i2 = Integer.valueOf(e2);
                Integer i3 = Integer.valueOf(e3);
                Integer i4 = Integer.valueOf(e4);
                Integer i5 = Integer.valueOf(e5);
                Integer i6 = Integer.valueOf(e6);
                Integer i7 = Integer.valueOf(e7);
                Integer i8 = Integer.valueOf(e8);
                Integer i9 = Integer.valueOf(e9);
                Integer i10 = Integer.valueOf(e10);
                Integer i11 = Integer.valueOf(e11);
                Integer i12 = Integer.valueOf(e12);

                Integer it1 = Integer.valueOf(gt_cal1);
                Integer it2 = Integer.valueOf(gt_cal2);
                Integer it3 = Integer.valueOf(gt_cal3);
                Integer it4 = Integer.valueOf(gt_cal4);
                Integer it5 = Integer.valueOf(gt_cal5);
                Integer it6 = Integer.valueOf(gt_cal6);
                Integer it7 = Integer.valueOf(gt_cal7);
                Integer it8 = Integer.valueOf(gt_cal8);
                Integer it9 = Integer.valueOf(gt_cal9);
                Integer it10 = Integer.valueOf(gt_cal10);
                Integer it11 = Integer.valueOf(gt_cal11);
                Integer it12 = Integer.valueOf(gt_cal12);

                Integer r1 = i1 * it1;
                Integer r2 = i2 * it2;
                Integer r3 = i3 * it3;
                Integer r4 = i4 * it4;
                Integer r5 = i5 * it5;
                Integer r6 = i6 * it6;
                Integer r7 = i7 * it7;
                Integer r8 = i8 * it8;
                Integer r9 = i9 * it9;
                Integer r10 = i10 * it10;
                Integer r11 = i11 * it11;
                Integer r12 = i12 * it12;

                Integer tot = r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9 + r10 + r11 + r12;


                tot_tv1.setText(r1.toString());
                tot_tv2.setText(r2.toString());
                tot_tv3.setText(r3.toString());
                tot_tv4.setText(r4.toString());
                tot_tv5.setText(r5.toString());
                tot_tv6.setText(r6.toString());
                tot_tv7.setText(r7.toString());
                tot_tv8.setText(r8.toString());
                tot_tv9.setText(r9.toString());
                tot_tv10.setText(r10.toString());
                tot_tv11.setText(r11.toString());
                tot_tv12.setText(r12.toString());
                tv_total.setText(tot);
            }
        });
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

//       Reference to TOTAL Column
        tot_tv1 = getActivity().findViewById(R.id.tv_table_layout_row1_tot);
        tot_tv2 = getActivity().findViewById(R.id.tv_table_layout_row2_tot);
        tot_tv3 = getActivity().findViewById(R.id.tv_table_layout_row3_tot);
        tot_tv4 = getActivity().findViewById(R.id.tv_table_layout_row4_tot);
        tot_tv5 = getActivity().findViewById(R.id.tv_table_layout_row5_tot);
        tot_tv6 = getActivity().findViewById(R.id.tv_table_layout_row6_tot);
        tot_tv7 = getActivity().findViewById(R.id.tv_table_layout_row7_tot);
        tot_tv8 = getActivity().findViewById(R.id.tv_table_layout_row8_tot);
        tot_tv9 = getActivity().findViewById(R.id.tv_table_layout_row9_tot);
        tot_tv10 = getActivity().findViewById(R.id.tv_table_layout_row10_tot);
        tot_tv11 = getActivity().findViewById(R.id.tv_table_layout_row11_tot);
        tot_tv12 = getActivity().findViewById(R.id.tv_table_layout_row12_tot);
        tv_total = getActivity().findViewById(R.id.tv_cal_tra_total_value);
        tv_ideal_total = getActivity().findViewById(R.id.tv_cal_tra_ideal_total_value);

        btn_calc = getActivity().findViewById(R.id.btn_cal_tra_total_calc);

        //       Reference to Calories Column
        tv1 = getActivity().findViewById(R.id.tv_table_layout_row1_cal);
        tv2 = getActivity().findViewById(R.id.tv_table_layout_row2_cal);
        tv3 = getActivity().findViewById(R.id.tv_table_layout_row3_cal);
        tv4 = getActivity().findViewById(R.id.tv_table_layout_row4_cal);
        tv5 = getActivity().findViewById(R.id.tv_table_layout_row5_cal);
        tv6 = getActivity().findViewById(R.id.tv_table_layout_row6_cal);
        tv7 = getActivity().findViewById(R.id.tv_table_layout_row7_cal);
        tv8 = getActivity().findViewById(R.id.tv_table_layout_row8_cal);
        tv9 = getActivity().findViewById(R.id.tv_table_layout_row9_cal);
        tv10 = getActivity().findViewById(R.id.tv_table_layout_row10_cal);
        tv11 = getActivity().findViewById(R.id.tv_table_layout_row11_cal);
        tv12 = getActivity().findViewById(R.id.tv_table_layout_row12_cal);

        //       Reference to CONSUMED EditText Column
        et1 = getActivity().findViewById(R.id.et_table_layout_row1_con);
        et2 = getActivity().findViewById(R.id.et_table_layout_row2_con);
        et3 = getActivity().findViewById(R.id.et_table_layout_row3_con);
        et4 = getActivity().findViewById(R.id.et_table_layout_row4_con);
        et5 = getActivity().findViewById(R.id.et_table_layout_row5_con);
        et6 = getActivity().findViewById(R.id.et_table_layout_row6_con);
        et7 = getActivity().findViewById(R.id.et_table_layout_row7_con);
        et8 = getActivity().findViewById(R.id.et_table_layout_row8_con);
        et9 = getActivity().findViewById(R.id.et_table_layout_row9_con);
        et10 = getActivity().findViewById(R.id.et_table_layout_row10_con);
        et11 = getActivity().findViewById(R.id.et_table_layout_row11_con);
        et12 = getActivity().findViewById(R.id.et_table_layout_row12_con);

        btn_calc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Integer i1 = Integer.valueOf(et1.getText().toString());
                Integer i2 = Integer.valueOf(et2.getText().toString());
                Integer i3 = Integer.valueOf(et3.getText().toString());
                Integer i4 = Integer.valueOf(et4.getText().toString());
                Integer i5 = Integer.valueOf(et5.getText().toString());
                Integer i6 = Integer.valueOf(et6.getText().toString());
                Integer i7 = Integer.valueOf(et7.getText().toString());
                Integer i8 = Integer.valueOf(et8.getText().toString());
                Integer i9 = Integer.valueOf(et9.getText().toString());
                Integer i10 = Integer.valueOf(et10.getText().toString());
                Integer i11 = Integer.valueOf(et11.getText().toString());
                Integer i12 = Integer.valueOf(et12.getText().toString());

                Integer it1 = Integer.valueOf(tv1.getText().toString());
                Integer it2 = Integer.valueOf(tv2.getText().toString());
                Integer it3 = Integer.valueOf(tv3.getText().toString());
                Integer it4 = Integer.valueOf(tv4.getText().toString());
                Integer it5 = Integer.valueOf(tv5.getText().toString());
                Integer it6 = Integer.valueOf(tv6.getText().toString());
                Integer it7 = Integer.valueOf(tv7.getText().toString());
                Integer it8 = Integer.valueOf(tv8.getText().toString());
                Integer it9 = Integer.valueOf(tv9.getText().toString());
                Integer it10 = Integer.valueOf(tv10.getText().toString());
                Integer it11 = Integer.valueOf(tv11.getText().toString());
                Integer it12 = Integer.valueOf(tv12.getText().toString());

                /*Calendar tab2_date = Calendar.getInstance();
                SimpleDateFormat tab2_df = new SimpleDateFormat("yyyy/mm/dd");
                String tab2_date_str = tab2_df.format(tab2_date.getTime());

                SimpleDateFormat tab2_sdf = new SimpleDateFormat("yyyy/mm/dd");
                String tab2_date_str = tab2_sdf.format(new Date());*/

                Calendar tab2_date = Calendar.getInstance();
                int day = tab2_date.get(Calendar.DAY_OF_MONTH);
                int month = tab2_date.get(Calendar.MONTH);
                int year = tab2_date.get(Calendar.YEAR);
                String tab2_date_str = day + "/" + (month + 1) + "/" + year;

                Integer r1 = i1 * it1;
                Integer r2 = i2 * it2;
                Integer r3 = i3 * it3;
                Integer r4 = i4 * it4;
                Integer r5 = i5 * it5;
                Integer r6 = i6 * it6;
                Integer r7 = i7 * it7;
                Integer r8 = i8 * it8;
                Integer r9 = i9 * it9;
                Integer r10 = i10 * it10;
                Integer r11 = i11 * it11;
                Integer r12 = i12 * it12;

                Integer tot = r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9 + r10 + r11 + r12;

                String str_ideal_tot_cal = tv_ideal_total.getText().toString();
                String con_tot = Integer.toString(tot);

                CollectionReference tab2_cr = tab2_fbfs.collection("Calorie Count");
                tab2_calorie_tracker_fbfs tctf = new tab2_calorie_tracker_fbfs(con_tot , str_ideal_tot_cal , tab2_date_str);

                tab2_cr.add(tctf).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
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

                tot_tv1.setText(Integer.toString(r1));
                tot_tv2.setText(Integer.toString(r2));
                tot_tv3.setText(Integer.toString(r3));
                tot_tv4.setText(Integer.toString(r4));
                tot_tv5.setText(Integer.toString(r5));
                tot_tv6.setText(Integer.toString(r6));
                tot_tv7.setText(Integer.toString(r7));
                tot_tv8.setText(Integer.toString(r8));
                tot_tv9.setText(Integer.toString(r9));
                tot_tv10.setText(Integer.toString(r10));
                tot_tv11.setText(Integer.toString(r11));
                tot_tv12.setText(Integer.toString(r12));

                tv_total.setText(Integer.toString(tot));
            }
        });
    }
}
