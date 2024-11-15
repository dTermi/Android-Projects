package com.example.be_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileOutputStream;

public class SelectInvoiceItemsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton btn_InvoiceItemsSelection_OpenNavigationDrawer;
    private DrawerLayout dl_InvoiceItemsSelection;
    private TextView tv_NavigationDrawer_UserName, tv_NavigationDrawer_MailId;
    private ImageView iv_NavigationDrawer_UserPic;
    private NavigationView nv_SelectInvoiceItems_NavigationView;

    private FirebaseAuth fb_Auth_SelectInvoiceItems = FirebaseAuth.getInstance();
    String value = "Logged-Out", checked = "checked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_invoice_items);

        btn_InvoiceItemsSelection_OpenNavigationDrawer = findViewById(R.id.btn_InvoiceItemsSelection_OpenNavigationDrawer);
        dl_InvoiceItemsSelection = findViewById(R.id.dl_InvoiceItemsSelection);
        nv_SelectInvoiceItems_NavigationView = findViewById(R.id.nv_InvoiceItemsSelection_NavigationView);

        nv_SelectInvoiceItems_NavigationView.setNavigationItemSelectedListener(this);

        btn_InvoiceItemsSelection_OpenNavigationDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_InvoiceItemsSelection.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(dl_InvoiceItemsSelection.isDrawerOpen(GravityCompat.START))
        {
            dl_InvoiceItemsSelection.closeDrawer(GravityCompat.START);
        }
        else
        {
            fb_Auth_SelectInvoiceItems.signOut();
            finishAffinity();
            finish();
        }
    }

//  Invoked when any of the Navigation Drawer Item Selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.DrawerMenu_ListCustomers:
                break;

            case R.id.DrawerMenu_GarmentDetails:
//                Intent To_GarmentDetails_Activity = new Intent(SelectInvoiceItemsActivity.this, GarmentServicePriceDetailsActivity.class);
////======================================================== SAVE "From_SelectInvoiceItems_Activity" as "checked" IN LOCAL MEMORY FOR LATER USE ========================================================
//                try(FileOutputStream Read_Value = openFileOutput("From_SelectInvoiceItems_Activity", Context.MODE_PRIVATE))
//                {
//                    Read_Value.write(checked.getBytes());                         // When accessed in MainActivity, after logging in, user directed to this activity.
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
////======================================================== SAVE "From_SelectInvoiceItems_Activity" as "checked" IN LOCAL MEMORY FOR LATER USE END ========================================================
//                startActivity(To_GarmentDetails_Activity);
                if(dl_InvoiceItemsSelection.isDrawerOpen(GravityCompat.START))
                {
                    dl_InvoiceItemsSelection.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.DrawerMenu_ShopDetails:
//                Intent To_ShopDetails_Activity = new Intent(SelectInvoiceItemsActivity.this, Activity_Shop_Details.class);
////======================================================== SAVE "From_SelectInvoiceItems_Activity" as "checked" IN LOCAL MEMORY FOR LATER USE ========================================================
//                try(FileOutputStream Read_Value = openFileOutput("From_SelectInvoiceItems_Activity", Context.MODE_PRIVATE))
//                {
//                    Read_Value.write(checked.getBytes());                         // When accessed in MainActivity, after logging in, user directed to this activity.
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
////======================================================== SAVE "From_SelectInvoiceItems_Activity" as "checked" IN LOCAL MEMORY FOR LATER USE END ========================================================
//                startActivity(To_ShopDetails_Activity);
                if(dl_InvoiceItemsSelection.isDrawerOpen(GravityCompat.START))
                {
                    dl_InvoiceItemsSelection.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.DrawerMenu_AppDetails:
                Intent To_Help_Activity = new Intent(SelectInvoiceItemsActivity.this, HelpActivity.class);
                startActivity(To_Help_Activity);
                if(dl_InvoiceItemsSelection.isDrawerOpen(GravityCompat.START))
                {
                    dl_InvoiceItemsSelection.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.DrawerMenu_AddCustomer:
                Intent To_CustomerDetails_Activity = new Intent(SelectInvoiceItemsActivity.this, CustomerDetailsActivity.class);
                startActivity(To_CustomerDetails_Activity);
                if(dl_InvoiceItemsSelection.isDrawerOpen(GravityCompat.START))
                {
                    dl_InvoiceItemsSelection.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.DrawerMenu_Logout:
                fb_Auth_SelectInvoiceItems.signOut();

//======================================================== SAVE "VALUE" IN LOCAL MEMORY FOR LATER USE ========================================================
                try(FileOutputStream Read_Value = openFileOutput("Login Status", Context.MODE_PRIVATE))
                {
                    Read_Value.write(value.getBytes());                         // When accessed in MainActivity, after logging in, user directed to this activity.
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
//======================================================== SAVE "VALUE" IN LOCAL MEMORY FOR LATER USE END ========================================================

                Intent To_Login_Activity = new Intent(SelectInvoiceItemsActivity.this, MainActivity.class);
                startActivity(To_Login_Activity);
                break;
        }
        return true;
    }
}