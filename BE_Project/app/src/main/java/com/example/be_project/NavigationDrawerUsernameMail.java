package com.example.be_project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NavigationDrawerUsernameMail extends AppCompatActivity {

    private TextView tv_UserName, tv_MailID;
    private ImageView iv_UserImage;
    private String UserName = "", MailId = "";
    private FileInputStream GetUsernameFromLocalMemory, GetMailFromLocalMemory;
    private int charReadUsername, charReadMail;
    private FirebaseFirestore fbfs_NavigationDrawerUNMail = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_for_invoice_items_nav_header);

        tv_UserName = findViewById(R.id.tv_InvoiceItemsSelection_UserName);
        tv_MailID = findViewById(R.id.tv_InvoiceItemsSelection_MailId);
        iv_UserImage = findViewById(R.id.iv_InvoiceItemsSelection_UserImage);

        try {
            GetUsernameFromLocalMemory = openFileInput("UserName");

            while ((charReadUsername = GetUsernameFromLocalMemory.read()) != -1) {
                UserName = UserName + (char) charReadUsername;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            GetMailFromLocalMemory = openFileInput("Mail Id");
//
//            while ((charReadMail = GetMailFromLocalMemory.read()) != -1) {
//                MailId = MailId + (char) charReadMail;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        DocumentReference NavigationDrawerGetMailID = fbfs_NavigationDrawerUNMail.collection(UserName).document("User_Details");
        NavigationDrawerGetMailID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    MailId =documentSnapshot.getString("Mail ID");
                }
            }
        });

        tv_UserName.setText(UserName);
        tv_MailID.setText(MailId);
    }
}

