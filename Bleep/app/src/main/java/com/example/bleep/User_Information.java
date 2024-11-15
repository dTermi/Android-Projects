package com.example.bleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_Information extends AppCompatActivity
{

    Button btn_main_screen;
    private EditText et_name , et_gender , et_dob , et_age , et_blood_grp , et_mail , et_password , et_re_enter_password;
    String name , gender , dob , age , blood_grp , mail , pw , re_pw;
    private FirebaseFirestore fbfs_usr_info;
    FirebaseAuth usr_inf_fb_auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__information);

//      Connecting .java variables with .xml IDs

        et_name = findViewById(R.id.et_usr_info_name);
        et_gender = findViewById(R.id.et_usr_info_gender);
        et_dob = findViewById(R.id.et_usr_info_dob);
        et_age = findViewById(R.id.et_usr_info_age);
        et_blood_grp = findViewById(R.id.et_usr_info_blood_grp);
        et_mail = findViewById(R.id.et_usr_info_mail);
        et_password = findViewById(R.id.et_usr_info_pasword);
        et_re_enter_password = findViewById(R.id.et_usr_info_reenter_password);

        btn_main_screen = findViewById(R.id.btn_usr_info_submit);

        fbfs_usr_info = FirebaseFirestore.getInstance();

        btn_main_screen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //        Get info from EditTexts
                name = et_name.getText().toString().trim();
                gender = et_gender.getText().toString().trim();
                dob = et_dob.getText().toString().trim();
                age = et_age.getText().toString().trim();
                blood_grp = et_blood_grp.getText().toString().trim();
                mail = et_mail.getText().toString().trim();
                pw = et_password.getText().toString().trim();
                re_pw = et_re_enter_password.getText().toString().trim();

                final CollectionReference usr_info_cr = fbfs_usr_info.collection("User_Information");
                final CollectionReference login_cr = fbfs_usr_info.collection("Login_Info");

                final User_Information_fbfs usr_info = new User_Information_fbfs(name , gender , dob ,age ,blood_grp , mail);
                final Login_Info login_info = new Login_Info(mail , pw);

                if(et_mail.getText().toString().isEmpty())
                {
                    et_mail.setError("Enter Username");
                }

                if(et_password.getText().toString().isEmpty())
                {
                    et_password.setError("Enter Password");
                }

                if(et_re_enter_password.getText().toString().isEmpty())
                {
                    et_re_enter_password.setError("Enter Password");
                }

                if(!(et_mail.getText().toString().isEmpty() && et_password.getText().toString().isEmpty() && et_re_enter_password.getText().toString().isEmpty()))
                {
                    usr_inf_fb_auth.createUserWithEmailAndPassword(mail , pw).addOnCompleteListener(User_Information.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(!(task.isSuccessful()))
                            {
                                FirebaseAuthException fex = (FirebaseAuthException) task.getException();
                                Toast.makeText(getApplicationContext() , "Un-successful"+fex.getMessage() , Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                            {
                                if(et_password.getText().toString().equals(et_re_enter_password.getText().toString()))
//                if(pw == re_pw)
                                {
                                    usr_info_cr.add(usr_info).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                    {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference)
                                        {
                                            Toast.makeText(getApplicationContext() , "Information Updated" , Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            Toast.makeText(getApplicationContext() , "Information Updation Unsuccessful" , Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    login_cr.add(login_info).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                    {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference)
                                        {
                                            Toast.makeText(getApplicationContext() , "Information Updated" , Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            Toast.makeText(getApplicationContext() , "Information Updation Unsuccessful" , Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    et_name.setText("");
                                    et_gender.setText("");
                                    et_dob.setText("");
                                    et_age.setText("");
                                    et_blood_grp.setText("");
                                    et_mail.setText("");
                                    et_password.setText("");
                                    et_re_enter_password.setText("");
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Check and Re-Enter the Passwords...", Toast.LENGTH_SHORT).show();
                                    et_password.setText("");
                                    et_re_enter_password.setText("");
                                }

                                openTabbedActivity();
                            }
                        }
                    });
                }
            }
        });
    }

    public void openTabbedActivity()
    {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }
}
