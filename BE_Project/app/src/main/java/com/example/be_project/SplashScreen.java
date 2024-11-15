package com.example.be_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth fb_Auth;
    FirebaseUser fb_User_SplashScreen_GetCurrent_User;
    private FileInputStream Get_Value_From_Local_Memory;

    int charReadValue;

    String LoginStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fb_Auth = FirebaseAuth.getInstance();

//======================================================== ACCESS "Login Status" FROM LOCAL MEMORY ========================================================
        try {
            Get_Value_From_Local_Memory = openFileInput("Login Status");

            while ((charReadValue = Get_Value_From_Local_Memory.read()) != -1) {
                LoginStatus = LoginStatus + (char) charReadValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//======================================================== ACCESS "VALUE" FROM LOCAL MEMORY END ========================================================

        Thread SplashScreen = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        SplashScreen.start();
    }

    @Override
    protected void onStart() {
        super.onStart();

        fb_User_SplashScreen_GetCurrent_User = fb_Auth.getCurrentUser();

        if (LoginStatus.equals("Logged-In"))                            // User has logged in previously and not logged out yet ----- value = 0
        {
            Intent To_SelectInvoiceItems = new Intent(SplashScreen.this, SelectInvoiceItemsActivity.class);
            startActivity(To_SelectInvoiceItems);
        } else if (LoginStatus.equals("Logged-Out")) {                     // There is no no current user and user has logged out ----- need to login again
            Intent To_Login_Page = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(To_Login_Page);
        } else {
            Intent To_Login_Page = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(To_Login_Page);
        }
    }
}