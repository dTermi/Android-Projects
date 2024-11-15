package com.example.be_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Shop_Details extends AppCompatActivity {

    private static final String TAG = "CHECKING";
    private EditText et_ShopDetails_name, et_ShopDetails_address, et_ShopDetails_timings, et_ShopDetails_contact;
    private FloatingActionButton fab_ShopDetails_done;

    private FirebaseFirestore Shop_Details_fbfs = FirebaseFirestore.getInstance();

    String ShopName, ShopAddress, ShopTimings, ShopContact, UserName = "", GarmentList[], ServiceList[];
    int charReadUn, charReadCh, GarCnt, SerCnt;

    private FileInputStream Get_UserName_From_Local_Memory;

    private Map<String, Object> MapShopData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__shop__details);

        et_ShopDetails_name = findViewById(R.id.et_ShopDetails_ShopName);
        et_ShopDetails_address = findViewById(R.id.et_ShopDetails_Address);
        et_ShopDetails_timings = findViewById(R.id.et_ShopDetails_Timings);
        et_ShopDetails_contact = findViewById(R.id.et_ShopDetails_ContactNumber);

        fab_ShopDetails_done = findViewById(R.id.fab_ShopDetails_Done);

        Toast.makeText(Activity_Shop_Details.this, R.string.ShopDetailsPageFABToast, Toast.LENGTH_LONG).show();

//======================================================== ACCESS USERNAME FROM LOCAL MEMORY ========================================================
        try {
            Get_UserName_From_Local_Memory = openFileInput("UserName");

            while ((charReadUn = Get_UserName_From_Local_Memory.read()) != -1) {
                UserName = UserName + Character.toString((char) charReadUn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//======================================================== ACCESS USERNAME FROM LOCAL MEMORY END ========================================================

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

//      Receive the ArrayLists Garment and Service sent from the LogIn Activity
        Bundle Extras = getIntent().getExtras();
        GarCnt = Extras.getInt("GarCnt");
        SerCnt = Extras.getInt("SerCnt");

        GarmentList = new String[GarCnt];
        ServiceList = new String[SerCnt];

        GarmentList = Extras.getStringArray("Garment List");
        ServiceList = Extras.getStringArray("Service List");

        TextWatcher Shop_Details_TextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                fab_ShopDetails_done.hide();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(et_ShopDetails_name.getText().toString().trim().isEmpty()) && !(et_ShopDetails_address.getText().toString().trim().isEmpty()) && !(et_ShopDetails_timings.getText().toString().trim().isEmpty()) && !(et_ShopDetails_contact.getText().toString().trim().isEmpty())) {
                    fab_ShopDetails_done.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        et_ShopDetails_name.addTextChangedListener(Shop_Details_TextWatcher);
        et_ShopDetails_address.addTextChangedListener(Shop_Details_TextWatcher);
        et_ShopDetails_timings.addTextChangedListener(Shop_Details_TextWatcher);
        et_ShopDetails_contact.addTextChangedListener(Shop_Details_TextWatcher);

        fab_ShopDetails_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShopName = et_ShopDetails_name.getText().toString().trim();
                ShopAddress = et_ShopDetails_address.getText().toString().trim();
                ShopTimings = et_ShopDetails_timings.getText().toString().trim();
                ShopContact = et_ShopDetails_contact.getText().toString().trim();

                MapShopData.put("Shop Name", ShopName);
                MapShopData.put("Shop Address", ShopAddress);
                MapShopData.put("Shop Timings", ShopTimings);
                MapShopData.put("Shop Contact Number", ShopContact);

                DocumentReference Shop_Details_Page_DocRef = Shop_Details_fbfs.collection(UserName).document("Shop Details");

//                ShopDetailsActivityStoreData SDASD = new ShopDetailsActivityStoreData(ShopName, ShopAddress, ShopTimings, ShopContact);

                if (!ShopName.isEmpty() && !ShopAddress.isEmpty() && !ShopTimings.isEmpty() && !ShopContact.isEmpty()) {
                    Shop_Details_Page_DocRef.set(MapShopData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            if (checked.equals("checked")) {                               // If the value in "checked" is empty, that means the user wasn't redirected from the SelectInvoiceItems Activity
//                                checked = "";
////======================================================== SAVE "From_SelectInvoiceItems_Activity" as "checked" IN LOCAL MEMORY FOR LATER USE ========================================================
//                                try (FileOutputStream Read_Value = openFileOutput("From_SelectInvoiceItems_Activity", Context.MODE_PRIVATE)) {
//                                    Read_Value.write(checked.getBytes());                         // When accessed in MainActivity, after logging in, user directed to this activity.
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
////======================================================== SAVE "From_SelectInvoiceItems_Activity" as "checked" IN LOCAL MEMORY FOR LATER USE END ========================================================
//
//                                Intent To_SelectInvoiceItem_Activity = new Intent(Activity_Shop_Details.this, SelectInvoiceItemsActivity.class);
//                                startActivity(To_SelectInvoiceItem_Activity);
//                            }

                            Intent To_GarmentServicePrice_Page = new Intent(Activity_Shop_Details.this, GarmentServicePriceDetailsActivity.class);
                            To_GarmentServicePrice_Page.putExtra("Garment List", GarmentList);
                            To_GarmentServicePrice_Page.putExtra("GarCnt", GarmentList.length);
                            To_GarmentServicePrice_Page.putExtra("Service List", ServiceList);
                            To_GarmentServicePrice_Page.putExtra("SerCnt", ServiceList.length);
                            startActivity(To_GarmentServicePrice_Page);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Information Updation Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Activity_Shop_Details.this, "Cannot go to the previous Screen", Toast.LENGTH_SHORT).show();
    }
}