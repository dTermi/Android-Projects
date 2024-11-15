package com.example.be_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.DescriptorProtos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements Serializable {

    private FloatingActionButton fab_Done;
    private EditText et_SignIn_UserName, et_SignIn_Mail, et_SignIn_Address, et_SignIn_MobileNumber, et_SignIn_Password, et_SignIn_ReEnterPassword;
    private FirebaseFirestore SignIn_Page_fbfs = FirebaseFirestore.getInstance();
    private FirebaseAuth SignIn_Page_fbAuth = FirebaseAuth.getInstance();

    private Map<String, Object> UserDetails = new HashMap<>();

    String U_Name, Mail, Address, Mobile, Password, RePassword, value = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        et_SignIn_UserName = findViewById(R.id.et_SignIn_UserName);
        et_SignIn_Mail = findViewById(R.id.et_SignIn_Email);
        et_SignIn_Address = findViewById(R.id.et_SignIn_Address);
        et_SignIn_MobileNumber = findViewById(R.id.et_SignIn_MobileNumber);
        et_SignIn_Password = findViewById(R.id.et_SignIn_SetPassword);
        et_SignIn_ReEnterPassword = findViewById(R.id.et_SignIn_ReenterPassword);

        fab_Done = findViewById(R.id.fab_SignIn_Save);

        Toast.makeText(SignInActivity.this, R.string.SignInPageeFABToast, Toast.LENGTH_LONG).show();

        TextWatcher SignIn_Page_TextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(et_SignIn_UserName.getText().toString().trim().isEmpty()) && !(et_SignIn_Mail.getText().toString().trim().isEmpty()) && !(et_SignIn_Password.getText().toString().trim().isEmpty()) && !(et_SignIn_ReEnterPassword.getText().toString().trim().isEmpty()) && (et_SignIn_Password.getText().toString().trim().equals(et_SignIn_ReEnterPassword.getText().toString().trim()))) {
                    fab_Done.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        et_SignIn_UserName.addTextChangedListener(SignIn_Page_TextWatcher);
        et_SignIn_Mail.addTextChangedListener(SignIn_Page_TextWatcher);
        et_SignIn_Password.addTextChangedListener(SignIn_Page_TextWatcher);
        et_SignIn_ReEnterPassword.addTextChangedListener(SignIn_Page_TextWatcher);

        fab_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                U_Name = et_SignIn_UserName.getText().toString().trim();
                Mail = et_SignIn_Mail.getText().toString().trim();
                Address = et_SignIn_Address.getText().toString().trim();
                Mobile = et_SignIn_MobileNumber.getText().toString().trim();
                Password = et_SignIn_Password.getText().toString().trim();
                RePassword = et_SignIn_ReEnterPassword.getText().toString().trim();

                UserDetails.put("Name", U_Name);
                UserDetails.put("Mail ID", Mail);
                UserDetails.put("Address", Address);
                UserDetails.put("Mobile Number", Mobile);

//======================================================== SAVE USERNAME IN LOCAL MEMORY IN A FILE WITH FILENAME = MAIL-ID ENTERED BY USER FOR LATER USE ========================================================
                try(FileOutputStream Read_UserName = openFileOutput(Mail, Context.MODE_PRIVATE))
                {
                    Read_UserName.write(U_Name.getBytes());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
//======================================================== SAVE USERNAME IN LOCAL MEMORY FOR LATER USE END ========================================================

//======================================================== SAVE Mail ID IN LOCAL MEMORY IN A FILE WITH FILENAME = MAIL-ID FOR LATER USE ========================================================

                try(FileOutputStream Read_MailID = openFileOutput("Mail Id", Context.MODE_PRIVATE))
                {
                    Read_MailID.write(Mail.getBytes());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

//======================================================== SAVE Mail ID IN LOCAL MEMORY IN A FILE WITH FILENAME = MAIL-ID FOR LATER USE END ========================================================
                DocumentReference SignIn_Page_User_Details_DocRef = SignIn_Page_fbfs.collection(U_Name).document("User Details");
//                SignInActivityStoreData SIASD = new SignInActivityStoreData(U_Name, Mail, Address, Mobile);


                if (!(et_SignIn_UserName.getText().toString().trim().isEmpty() && et_SignIn_Mail.getText().toString().trim().isEmpty() && et_SignIn_Password.getText().toString().trim().isEmpty() && et_SignIn_ReEnterPassword.getText().toString().trim().isEmpty())) {
                    SignIn_Page_fbAuth.createUserWithEmailAndPassword(Mail, Password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!(task.isSuccessful())) {
                                FirebaseAuthException FAE = (FirebaseAuthException) task.getException();
                                Toast.makeText(getApplicationContext(), "Un-successful" + FAE.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                if (Password.equals(RePassword) && !U_Name.equals("") && !Mail.equals("") && !Password.equals("") && !RePassword.equals("")) {
                                    SignIn_Page_User_Details_DocRef.set(UserDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
//                                            Toast.makeText(getApplicationContext(), "Information Updated", Toast.LENGTH_SHORT).show();

                                            Intent To_Login_Page = new Intent(SignInActivity.this, MainActivity.class);
                                            startActivity(To_Login_Page);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Information Updation Unsuccessful", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    et_SignIn_UserName.setText("");
                                    et_SignIn_Mail.setText("");
                                    et_SignIn_Address.setText("");
                                    et_SignIn_MobileNumber.setText("");
                                    et_SignIn_Password.setText("");
                                    et_SignIn_ReEnterPassword.setText("");
                                } else {
                                    Toast.makeText(SignInActivity.this, "Check and Re-Enter the Passwords...", Toast.LENGTH_SHORT).show();
                                    et_SignIn_Password.setText("");
                                    et_SignIn_Password.requestFocus();
                                    et_SignIn_ReEnterPassword.setText("");
                                    et_SignIn_ReEnterPassword.requestFocus();
                                }
                            }
                        }
                    });
                }
//======================================================== SAVE "VALUE" IN LOCAL MEMORY FOR LATER USE ========================================================
//                try(FileOutputStream Read_Value = openFileOutput("Value", Context.MODE_PRIVATE))
//                {
//                    Read_Value.write(value.getBytes());                         // When accessed in MainActivity, after logging in, if value = 1: user directed to Invoice Item Selection activity, else if value != 1: User directed to Shop Details Activity
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//======================================================== SAVE "VALUE" IN LOCAL MEMORY FOR LATER USE END ========================================================

            }
        });
    }
}