package com.example.be_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText et_U_Name, et_Password;
    private Button btn_Login;
    private TextView tv_SignIn;

    // Initialize FirebaseFirestore
    private FirebaseFirestore loginFbfs;

    // Initialize FirebaseAuth
    private FirebaseAuth loginFbAuth;

    private FirebaseAuth.AuthStateListener loginFbAuthState;

    private static final String TAG = "CHECKING";
    String Email, password, LoginStatus = "", UserName = "";
    int charReadUserName, charReadValue;

    private FileInputStream Get_UserName_From_Local_Memory, Get_Value_From_Local_Memory;

    Map<String, Object> Garment_List = new HashMap<>();
    Map<String, Object> Service_List = new HashMap<>();
    String[] Garments = new String[]{"Full Shirt", "Half Shirt", "Full T-Shirt", "Half T-Shirt", "Sleeve-less T-Shirt", "Jeans", "Trousers", "Shorts", "Track Pant"};
    String[] Services_Offered = new String[]{"Wash", "Press", "Wash & Press", "Dry Cleaning"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_U_Name = findViewById(R.id.et_email);
        et_Password = findViewById(R.id.et_password);

        btn_Login = findViewById(R.id.btn_login);

        tv_SignIn = findViewById(R.id.tv_SignIn);

        // Get Instance of FirebaseFirestore
        // Create a socket with the database
        loginFbfs = FirebaseFirestore.getInstance();

        // Get Instance of FirebaseAuth
        loginFbAuth = FirebaseAuth.getInstance();

        for (int u = 0; u < Garments.length; u++) {
            Garment_List.put(Garments[u], Garments[u]);            // Populate the Garment HashMap
        }

        for (int p = 0; p < Services_Offered.length; p++) {
            Service_List.put(Services_Offered[p], Services_Offered[p]);
        }

//======================================================== ACCESS "VALUE" FROM LOCAL MEMORY ========================================================
        try {
            Get_Value_From_Local_Memory = openFileInput("Login Status");

            while ((charReadValue = Get_Value_From_Local_Memory.read()) != -1) {
                LoginStatus = LoginStatus + (char) charReadValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//======================================================== ACCESS "VALUE" FROM LOCAL MEMORY END ========================================================


        loginFbAuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser SignIn_page_fb_user = firebaseAuth.getCurrentUser();
                if (SignIn_page_fb_user != null && LoginStatus.equals("0")) {
                    Toast.makeText(MainActivity.this, "In AuthListener", Toast.LENGTH_SHORT).show();
                    openShopDetailsPage();
                } else {
                    Toast.makeText(MainActivity.this, "Unsuccessful...", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_U_Name.getText().toString().trim().equals("")) {
                    et_U_Name.setError("Enter Email-ID");
                    et_U_Name.requestFocus();
                }

                if (et_Password.getText().toString().trim().equals("")) {
                    et_Password.setError("Enter Password");
                    et_Password.requestFocus();
                }

                if (!(et_U_Name.getText().toString().equals("") && et_Password.getText().toString().equals(""))) {
                    Email = et_U_Name.getText().toString();
                    password = et_Password.getText().toString();

//======================================================== ACCESS USERNAME FROM LOCAL MEMORY WHERE FILENAME = MAIL-ID ENTERED BY THE USER ========================================================
                    try {
                        Get_UserName_From_Local_Memory = openFileInput(Email);
                        String UN = "";
                        while ((charReadUserName = Get_UserName_From_Local_Memory.read()) != -1) {
                            UN = UN + (char) charReadUserName;
                        }
                        UserName = UN;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//======================================================== ACCESS USERNAME FROM LOCAL MEMORY END ========================================================

//======================================================== SAVE USERNAME IN LOCAL MEMORY IN A FILE WITH FILENAME = "UserName" FOR LATER USE ========================================================
                    try(FileOutputStream Read_UserName = openFileOutput("UserName", Context.MODE_PRIVATE))
                    {
                        Read_UserName.write(UserName.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
//======================================================== SAVE USERNAME IN LOCAL MEMORY FOR LATER USE END ========================================================

//======================================================== ADD A LIST OF GARMENTS FOR THE USER TO CHOOSE FROM ========================================================
                    loginFbfs.collection(UserName).document("Garment List").set(Garment_List).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Try again...", Toast.LENGTH_SHORT).show();
                        }
                    });
//======================================================== ADD A LIST OF ALL SERVICES FOR THE USER TO CHOOSE FROM ========================================================

                    loginFbfs.collection(UserName).document("Services List").set(Service_List).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Try again...", Toast.LENGTH_SHORT).show();
                        }
                    });

                    loginFbAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login Error...", Toast.LENGTH_SHORT).show();
                                et_U_Name.setText("");
                                et_U_Name.requestFocus();
                                et_Password.setText("");
                                et_Password.requestFocus();
                            } else {
//                                Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                if (LoginStatus.equals("Logged-Out")) {                       // Check if the value of "VALUE" = Logged-Out. If yes, open Invoice Item Selection Activity
                                    Intent To_InvoiceItemSelection_Activity = new Intent(MainActivity.this, SelectInvoiceItemsActivity.class);
                                    startActivity(To_InvoiceItemSelection_Activity);
                                }
                                else{
                                    openShopDetailsPage();
                                }
                                LoginStatus = "Logged-In";
//======================================================== SAVE "VALUE" IN LOCAL MEMORY FOR LATER USE ========================================================
                                try (FileOutputStream Read_Value = openFileOutput("Login Status", Context.MODE_PRIVATE)) {
                                    Read_Value.write(LoginStatus.getBytes());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//======================================================== SAVE "VALUE" IN LOCAL MEMORY FOR LATER USE END ========================================================
                            }
                        }
                    });
                }
            }
        });
    }

    public void openSignInPage(View view) {
        Intent toSignInActivity = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(toSignInActivity);
    }

    public void openShopDetailsPage() {
        Intent toShopDetailsActivity = new Intent(MainActivity.this, Activity_Shop_Details.class);
        toShopDetailsActivity.putExtra("Garment List", Garments);
        toShopDetailsActivity.putExtra("GarCnt", Garments.length);
        toShopDetailsActivity.putExtra("Service List", Services_Offered);
        toShopDetailsActivity.putExtra("SerCnt", Services_Offered.length);
        startActivity(toShopDetailsActivity);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}