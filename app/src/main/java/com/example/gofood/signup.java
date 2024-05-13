package com.example.gofood;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class signup extends AppCompatActivity {
    public boolean valdite_num = true;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private EditText user_name, email, pass, phone_number, code;
    private Button connect,test1, confirm;




    private String  phone;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }

    }
    private void updateUI() {
        Toast.makeText(getApplicationContext(), "you are logged in", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext().getApplicationContext(), signin.class));
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();


        user_name = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        connect = findViewById(R.id.connect);
        phone_number = findViewById(R.id.phone_number);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                    Snackbar.make(view, "please fill all the information ", Snackbar.LENGTH_LONG).show();
                    if (user_name.getText().toString().isEmpty()) {
                        user_name.setHint("Enter your name ");
                    } else if (email.getText().toString().isEmpty()) {
                        email.setHint("Enter your Email ");

                    } else if (pass.getText().toString().isEmpty()) {
                        pass.setHint("Enter your Password ");

                    } else if (pass.getText().toString().length() < 9) {
                        Snackbar.make(view, "password is too short should be 9 characters", Snackbar.LENGTH_LONG).show();
                    } else if (phone_number.getText().toString().isEmpty()) {
                        phone_number.setHint("Enter your Phone number ");
                    }

                } else if (pass.getText().toString().contains("@") || pass.getText().toString().contains("#") || pass.getText().toString().contains("$")) {
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        phone = phone_number.getText().toString();
                                        phone = phone.substring(1);
                                        phone = "+962" + phone;

                                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(getApplicationContext(), "sign up successfully(on complete)", Toast.LENGTH_LONG).show();
                                        reference.child(firebaseUser.getUid()).child("username").setValue(user_name.getText().toString());
                                        reference.child(firebaseUser.getUid()).child("email").setValue(email.getText().toString());
                                        reference.child(firebaseUser.getUid()).child("phone").setValue(phone);
                                        reference.child(firebaseUser.getUid()).child("choplace").setValue("Customer");
                                        reference.child(firebaseUser.getUid()).child("fav_orders_uid").setValue(0);
                                        reference.child(firebaseUser.getUid()).child("boolan_fav").setValue(true);


                                    } else {
                                        String problem = task.getException().getMessage();
                                        Toast.makeText(getApplicationContext(), "                  failed to sign up\n" + problem, Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                } else if (!pass.getText().toString().contains("@") || !pass.getText().toString().contains("#") || !pass.getText().toString().contains("$")) {
                    Snackbar.make(view, "password should contain  @ or # or $", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }}


