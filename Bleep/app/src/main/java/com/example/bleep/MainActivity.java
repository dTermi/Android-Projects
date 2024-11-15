package com.example.bleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    EditText et_userName , et_password;
    Button log_in;
    TextView tv_sign_in;
    CheckBox cb_keep_logged_in;
    boolean isCecked = false;
    String username , password;
    private FirebaseFirestore fbfs;
    FirebaseAuth ma_fb_auth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_in = findViewById(R.id.btn_signIn);
        cb_keep_logged_in = findViewById(R.id.cb_keep_logged_in);
        et_userName = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        fbfs = FirebaseFirestore.getInstance();                   //Create a socl=ket with the database

        authStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser fb_usr = firebaseAuth.getCurrentUser();
                if(fb_usr != null)
                {
                    Toast.makeText(getApplicationContext() , "in authListener", Toast.LENGTH_SHORT).show();
                    openTabbedActivity();
                }
                else
                {
                    Toast.makeText(getApplicationContext() , "Un-successful", Toast.LENGTH_SHORT).show();
                }
            }
        };
                //When LOG IN button is clicked...
        log_in.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(et_userName.getText().toString().isEmpty())
                {
                    et_userName.setError("Enter Email-ID");
                    et_userName.requestFocus();
                }
                else if(et_password.getText().toString().isEmpty())
                {
                    et_password.setError("Enter Password");
                    et_password.requestFocus();
                }
                else if(!(et_userName.getText().toString().isEmpty() && et_password.getText().toString().isEmpty() ) )
                {
                    username = et_userName.getText().toString();
                    password = et_password.getText().toString();

                    ma_fb_auth.signInWithEmailAndPassword(username , password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext() , "login error", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                openTabbedActivity();
                            }
                        }
                    });
                    /*Intent i = new Intent(MainActivity.this, tabbed.class);
                    startActivity(i);*/
                }



            }
        });
    }

    public void openTabbedActivity()
    {
        cb_keep_logged_in.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isChecked", isChecked);
                editor.commit();

                SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
                isChecked = settings1.getBoolean("isChecked", false);

                if (isChecked)
                {
                    Intent i = new Intent(MainActivity.this, tabbed.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });

        Intent intent = new Intent(this , tabbed.class);
        startActivity(intent);
    }

    public void perform_action(View v)
    {
        tv_sign_in = findViewById(R.id.tv_not_signed_in);

        Intent intent_usr_info = new Intent(this , User_Information.class);
        startActivity(intent_usr_info);
    }
}
