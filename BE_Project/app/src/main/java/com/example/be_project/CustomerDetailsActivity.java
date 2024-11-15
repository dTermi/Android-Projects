package com.example.be_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class CustomerDetailsActivity extends AppCompatActivity {

    private EditText et_CustomerDetailsActivity_Name, et_CustomerDetailsActivity_Mobile, et_CustomerDetailsActivity_Address, et_CustomerDetailsActivity_Mail;
    private FloatingActionButton fab_CustomerDetailsActivity_Done;
    private CheckBox cb_CustomerDetailsActivity_AllDone;

    private String UserName = "", Name, MobileNumber, Address, Mail;

    private Map<String, Object> MapCustomerDetails = new HashMap<>();

    private int charRead;

    private FileInputStream Get_UserName_From_Local_Memory;

    private FirebaseFirestore fbfs_CustomerDetails_Activity = FirebaseFirestore.getInstance();

    @Override
    public void onBackPressed() {
        Toast.makeText(CustomerDetailsActivity.this, "Cannot go to the previous Screen", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        et_CustomerDetailsActivity_Name = findViewById(R.id.et_CustomerDetailsActivity_Name);
        et_CustomerDetailsActivity_Mobile = findViewById(R.id.et_CustomerDetailsActivity_Mobile);
        et_CustomerDetailsActivity_Address = findViewById(R.id.et_CustomerDetailsActivity_Address);
        et_CustomerDetailsActivity_Mail = findViewById(R.id.et_CustomerDetailsActivity_Mail);

        fab_CustomerDetailsActivity_Done = findViewById(R.id.fab_CustomerDetails_Done);

        cb_CustomerDetailsActivity_AllDone = findViewById(R.id.cb_ShopDetails_AllDone);

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


        fab_CustomerDetailsActivity_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = et_CustomerDetailsActivity_Name.getText().toString().trim();
                Address = et_CustomerDetailsActivity_Address.getText().toString().trim();
                MobileNumber = et_CustomerDetailsActivity_Mobile.getText().toString().trim();
                Mail = et_CustomerDetailsActivity_Mail.getText().toString().trim();

                MapCustomerDetails.put("Customer Name", Name);
                MapCustomerDetails.put("Customer Address", Address);
                MapCustomerDetails.put("Customer Mobile Number", MobileNumber);
                MapCustomerDetails.put("Customer Mail ID", Mail);

                DocumentReference CustomerDetails_DocRef = fbfs_CustomerDetails_Activity.collection(UserName).document("All Customers").collection("Details").document(Name);
//                CustomerDetailsStoreData CDSD = new CustomerDetailsStoreData(Name, MobileNumber, Address, Mail);

                if (Name.isEmpty() || MobileNumber.isEmpty() || Address.isEmpty() || Mail.isEmpty()) {
                    Toast.makeText(CustomerDetailsActivity.this, "Cannot store empty data", Toast.LENGTH_SHORT).show();
                } else if (!Name.isEmpty() && !MobileNumber.isEmpty() && !Address.isEmpty() && !Mail.isEmpty()) {
                    CustomerDetails_DocRef.set(MapCustomerDetails);
                    et_CustomerDetailsActivity_Name.requestFocus();

                    if (cb_CustomerDetailsActivity_AllDone.isChecked())                       // If condition is true, go to Invoice Item Selection Activity
                    {
                        Intent To_InvoiceItemSelection_Activity = new Intent(CustomerDetailsActivity.this, SelectInvoiceItemsActivity.class);
                        startActivity(To_InvoiceItemSelection_Activity);
                    }
                }

                et_CustomerDetailsActivity_Name.setText("");
                et_CustomerDetailsActivity_Mobile.setText("");
                et_CustomerDetailsActivity_Address.setText("");
                et_CustomerDetailsActivity_Mail.setText("");
            }
        });

    }
}