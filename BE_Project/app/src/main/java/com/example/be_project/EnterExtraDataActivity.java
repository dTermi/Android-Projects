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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnterExtraDataActivity extends AppCompatActivity {

    private EditText et_EnterExtraData_Garment, et_EnterExtraData_Service, et_EnterExtraData_Price;
    private CheckBox cb_EnterExtraData_Done;
    private FloatingActionButton fab_EnterExtraData_Done;
    private FirebaseFirestore fbfs_EnterExtraData = FirebaseFirestore.getInstance();
    private FileInputStream Get_UserName_From_Local_Memory;
    boolean garmentDoesNotExist = false, serviceDoesNotExist = false;

    String GarmentName, Service, Price, UserName = "", GarmentList[], ServiceList[];
    int charRead, GarCnt, SerCnt, g=0, s=0;
    ArrayList<String> AL_Garment = new ArrayList<>();
    ArrayList<String> AL_Service = new ArrayList<>();

    private Map<String, Object> MapGL = new HashMap<>();
    private Map<String, Object> MapSL = new HashMap<>();
    private Map<String, Object> MapPrice = new HashMap<>();

    @Override
    public void onBackPressed() {
        Toast.makeText(EnterExtraDataActivity.this, "Cannot go to the previous Screen", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_extra_data);

        et_EnterExtraData_Garment = findViewById(R.id.et_EnterExtraData_GarmentName);
        et_EnterExtraData_Service = findViewById(R.id.et_EnterExtraData_Service);
        et_EnterExtraData_Price = findViewById(R.id.et_EnterExtraData_Price);

        cb_EnterExtraData_Done = findViewById(R.id.cb_EnterExtraData_Done);

        fab_EnterExtraData_Done = findViewById(R.id.fab_EnterExtraData_Done);

//======================================================== ACCESS USERNAME FROM LOCAL MEMORY ========================================================
        try
        {
            Get_UserName_From_Local_Memory = openFileInput("UserName");

            while ((charRead = Get_UserName_From_Local_Memory.read()) != -1)
            {
                UserName = UserName +Character.toString((char)charRead);
            }
        }
        catch (Exception e)
        {
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

       fab_EnterExtraData_Done.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               GarmentName = et_EnterExtraData_Garment.getText().toString().trim();
               Service = et_EnterExtraData_Service.getText().toString().trim();
               Price = et_EnterExtraData_Price.getText().toString().trim();

               MapPrice.put("Price", Price);

               for(int i=0; i<AL_Garment.size(); i++)
               {
                   String GName = AL_Garment.get(i).trim().toLowerCase();
                   if(GName.equals(GarmentName.trim().toLowerCase()))
                   {
                       garmentDoesNotExist = false;
                   }
                   else if(!(GName.equals(GarmentName.trim().toLowerCase())))
                   {
                       garmentDoesNotExist = true;
                   }
               }

//               for(int i=0; i<GarmentList.length; i++)                                                                                // Check if entered GarmentName is already in the ArrrayList. If not, add it
//               {
//
//                   if(GarmentList[i].trim().toLowerCase().equals(GarmentName.trim().toLowerCase()))
//                   {
//                       garmentDoesNotExist = false;
//                   }
//                   else
//                   {
//                       garmentDoesNotExist = true;
//                   }
//               }

               if(garmentDoesNotExist == true)
               {
                   AL_Garment.add(GarmentName);
               }



               for(int i=0; i<AL_Service.size(); i++)
               {
                   String SName = AL_Service.get(i).trim().toLowerCase();
                   if(SName.equals(Service.trim().toLowerCase()))
                   {
                       serviceDoesNotExist = false;
                   }
                   else if(!(SName.equals(Service.trim().toLowerCase())))
                   {
                       serviceDoesNotExist = true;
                   }
               }

               if(serviceDoesNotExist == true)
               {
                   AL_Service.add(Service);
               }

//               for(int i=0; i<AL_Garment.size(); i++)
//               {
//                   if(!(string.contains(GarmentName)))
//                   {
//                       AL_Garment.add(GarmentName);
//                   }
//               }

//               for(String string: AL_Service)                                                       // Check if entered ServiceName is already in the ArrrayList. If not, add it
//               {
//                   if(!(string.contains(Service)))
//                   {
//                       AL_Service.add(Service);
//                   }
//               }

               DocumentReference EnterExtraData_DocRef = fbfs_EnterExtraData.collection(UserName).document("All Garments").collection(GarmentName).document(Service);
//               EnterExtraDataStoreData EEDSD = new EnterExtraDataStoreData(Price);

               if(!GarmentName.isEmpty() && !Service.isEmpty() && !Price.isEmpty())
               {
                   EnterExtraData_DocRef.set(MapPrice);
                   et_EnterExtraData_Garment.requestFocus();

                   if(cb_EnterExtraData_Done.isChecked())                           // If CheckBox is checked
                   {
                       Intent To_SelectInvoiceItems = new Intent(EnterExtraDataActivity.this, SelectInvoiceItemsActivity.class);
                       To_SelectInvoiceItems.putExtra("Garment List", GarmentList);
                       To_SelectInvoiceItems.putExtra("GarCnt", GarmentList.length);
                       To_SelectInvoiceItems.putExtra("Service List", ServiceList);
                       To_SelectInvoiceItems.putExtra("SerCnt", ServiceList.length);
                       startActivity(To_SelectInvoiceItems);
                   }
               }

               et_EnterExtraData_Garment.setText("");
               et_EnterExtraData_Service.setText("");
               et_EnterExtraData_Price.setText("");

               for(int i=0; i<AL_Garment.size(); i++)
               {
                   MapGL.put(AL_Garment.get(i), AL_Garment.get(i));
               }
               for(int i=0; i<AL_Service.size(); i++)
               {
                   MapSL.put(AL_Service.get(i), AL_Service.get(i));
               }

               fbfs_EnterExtraData.collection(UserName).document("Garment List").set(MapGL);
               fbfs_EnterExtraData.collection(UserName).document("Services List").set(MapSL);
           }
       });
    }
}