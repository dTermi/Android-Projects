package com.example.be_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GarmentServicePriceDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner Spnr_GarmentServicePrice_Garment, Spnr_GarmentServicePrice_Service;
    private EditText et_GarmentServicePrice_price;
    private FloatingActionButton fab_GarmentServicePrice_Expand, fab_GarmentServicePrice_Done, fab_GarmentServicePrice_AdditionalData;
    private Button btn_save;
    private TextView tv_Done, tv_AddData;
    private FirebaseFirestore fbfs_GarmentServicePrice = FirebaseFirestore.getInstance();

    Animation fab_open, fab_close, fab_rotateClockwise, fab_rotateAntiClockwise;

    private int charRead, GarCnt, SerCnt, charReadCh;
    private boolean isOpened = false;
    private static final String TAG = "DocSnippets";
    private String UserName = "", GarmentSpnrSelectedItem = "", ServiceSpnrSelectedItem = "", price, GarmentList[], ServiceList[];
    private ArrayList<String> AL_Garment = new ArrayList<>();
    private ArrayList<String> AL_Service = new ArrayList<>();
    private FileInputStream Get_UserName_From_Local_Memory, Get_Checked_From_Local_Memory;
    private Map<String, Object> MapPrice = new HashMap<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(GarmentServicePriceDetailsActivity.this, "Cannot go to the previous Screen", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garment_service_price_details);

        Spnr_GarmentServicePrice_Garment = findViewById(R.id.GarmentServicePriceActivity_Spnr_Garment);
        Spnr_GarmentServicePrice_Garment.setOnItemSelectedListener(this);
        Spnr_GarmentServicePrice_Service = findViewById(R.id.GarmentServicePriceActivity_Spnr_Service);
        Spnr_GarmentServicePrice_Service.setOnItemSelectedListener(this);
        ;

        et_GarmentServicePrice_price = findViewById(R.id.GarmentServicePriceActivity_et_Price);

        btn_save = findViewById(R.id.GarmentServicePriceActivity_btn_SaveCurrentData);

        fab_GarmentServicePrice_Expand = findViewById(R.id.fab_GarmentsServicePrice_Expand);
        fab_GarmentServicePrice_Done = findViewById(R.id.fab_GarmentsServicePrice_Done);
        fab_GarmentServicePrice_AdditionalData = findViewById(R.id.fab_GarmentsServicePrice_AdditionalData);

        tv_Done = findViewById(R.id.tv_GarmentsServicePrice_Done);
        tv_AddData = findViewById(R.id.tv_GarmentsServicePrice_AdditionalData);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
//        fab_rotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
//        fab_rotateAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

//======================================================== ACCESS "From_SelectInvoiceItems_Activity" as "checked" FROM LOCAL MEMORY ========================================================
//        try {
//            Get_Checked_From_Local_Memory = openFileInput("From_SelectInvoiceItems_Activity");
//
//            while ((charReadCh = Get_Checked_From_Local_Memory.read()) != -1) {
//                checked = checked + Character.toString((char) charReadCh);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//======================================================== ACCESS "From_SelectInvoiceItems_Activity" as "checked" FROM LOCAL MEMORY ========================================================

//======================================================== ACCESS USERNAME FROM LOCAL MEMORY ========================================================
        try {
            Get_UserName_From_Local_Memory = openFileInput("UserName");

            while ((charRead = Get_UserName_From_Local_Memory.read()) != -1) {
                UserName = UserName + Character.toString((char) charRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//======================================================== ACCESS USERNAME FROM LOCAL MEMORY END ========================================================

//      Receive the ArrayLists Garment and Service sent from the LogIn Activity
        Bundle Extras = getIntent().getExtras();
        GarCnt = Extras.getInt("GarCnt");
        SerCnt = Extras.getInt("SerCnt");

        GarmentList = new String[GarCnt];
        ServiceList = new String[SerCnt];

        GarmentList = Extras.getStringArray("Garment List");        // Save all the Garments in the GarmentList Array
        ServiceList = Extras.getStringArray("Service List");        // Save all the Services in the ServiceList Array

        for (int t = 0; t < GarmentList.length; t++) {
            AL_Garment.add(GarmentList[t]);
        }

        for (int r = 0; r < ServiceList.length; r++) {
            AL_Service.add(ServiceList[r]);
        }

        ArrayAdapter<String> Spnr_Garment_Add_Data = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, AL_Garment);
        Spnr_Garment_Add_Data.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Spnr_GarmentServicePrice_Garment.setAdapter(Spnr_Garment_Add_Data);

        ArrayAdapter<String> Spnr_Service_Add_Data = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, AL_Service);
        Spnr_Service_Add_Data.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Spnr_GarmentServicePrice_Service.setAdapter(Spnr_Service_Add_Data);


//======================================================== ENTER GARMENT, SERVICE AND PRICE IN DATABASE ========================================================
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference GarmentServicePrice_DocRef = fbfs_GarmentServicePrice.collection(UserName).document("All Garments").collection(GarmentSpnrSelectedItem).document(ServiceSpnrSelectedItem);

                price = et_GarmentServicePrice_price.getText().toString().trim();

                MapPrice.put("Price", price);

//                GarmentServicePriceStoreData GSPSD = new GarmentServicePriceStoreData(price);

                if (!GarmentSpnrSelectedItem.isEmpty() && !ServiceSpnrSelectedItem.isEmpty() && !price.isEmpty()) {
                    GarmentServicePrice_DocRef.set(MapPrice);
                } else if (GarmentSpnrSelectedItem.isEmpty() || ServiceSpnrSelectedItem.isEmpty() || price.isEmpty()) {
                    Toast.makeText(GarmentServicePriceDetailsActivity.this, "Please Enter the required Fields", Toast.LENGTH_SHORT).show();
                }
                et_GarmentServicePrice_price.setText("");
            }
        });
//======================================================== ENTER GARMENT, SERVICE AND PRICE IN DATABASE END ========================================================

        fab_GarmentServicePrice_Expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpened) {
                    fab_GarmentServicePrice_Done.startAnimation(fab_close);
                    fab_GarmentServicePrice_AdditionalData.setAnimation(fab_close);
                    tv_Done.setVisibility(View.INVISIBLE);
                    tv_AddData.setVisibility(View.INVISIBLE);
                    tv_Done.setAnimation(fab_close);
                    tv_AddData.setAnimation(fab_close);

//                    fab_GarmentServicePrice_Expand.startAnimation(fab_rotateAntiClockwise);
                    fab_GarmentServicePrice_Done.setClickable(false);
                    fab_GarmentServicePrice_AdditionalData.setClickable(false);

                    isOpened = false;
                } else {
                    fab_GarmentServicePrice_Done.startAnimation(fab_open);
                    fab_GarmentServicePrice_AdditionalData.setAnimation(fab_open);
                    tv_Done.setVisibility(View.VISIBLE);
                    tv_AddData.setVisibility(View.VISIBLE);
                    tv_Done.setAnimation(fab_open);
                    tv_AddData.setAnimation(fab_open);

//                    fab_GarmentServicePrice_Expand.startAnimation(fab_rotateClockwise);
                    fab_GarmentServicePrice_Done.setClickable(true);
                    fab_GarmentServicePrice_AdditionalData.setClickable(true);

                    isOpened = true;
                }
            }
        });

        fab_GarmentServicePrice_AdditionalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent To_EnterExtraData_Activity = new Intent(GarmentServicePriceDetailsActivity.this, EnterExtraDataActivity.class);
                To_EnterExtraData_Activity.putExtra("Garment List", GarmentList);
                To_EnterExtraData_Activity.putExtra("GarCnt", GarmentList.length);
                To_EnterExtraData_Activity.putExtra("Service List", ServiceList);
                To_EnterExtraData_Activity.putExtra("SerCnt", ServiceList.length);
                startActivity(To_EnterExtraData_Activity);
            }
        });

        fab_GarmentServicePrice_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent To_SelectInvoiceItems_Activity = new Intent(GarmentServicePriceDetailsActivity.this, SelectInvoiceItemsActivity.class);
                To_SelectInvoiceItems_Activity.putExtra("Garment List", GarmentList);
                To_SelectInvoiceItems_Activity.putExtra("GarCnt", GarmentList.length);
                To_SelectInvoiceItems_Activity.putExtra("Service List", ServiceList);
                To_SelectInvoiceItems_Activity.putExtra("SerCnt", ServiceList.length);
                startActivity(To_SelectInvoiceItems_Activity);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.GarmentServicePriceActivity_Spnr_Garment:
                GarmentSpnrSelectedItem = parent.getSelectedItem().toString().trim();
                break;

            case R.id.GarmentServicePriceActivity_Spnr_Service:
                ServiceSpnrSelectedItem = parent.getSelectedItem().toString().trim();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}