package com.example.gofood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

public class signin extends AppCompatActivity {
private Button signup;
    private FirebaseAuth firebaseAuth, firebaseAuth1;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private EditText email1;
    private EditText pass1;
    private Button connect1;
    private TextView terms;
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }

    }

    private void updateUI() {
        Toast.makeText(getApplicationContext(), "you are logged in", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        finish();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        signup=findViewById(R.id.signup);
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        email1 = findViewById(R.id.email);
        pass1 = findViewById(R.id.pass);
        connect1 =findViewById(R.id.signin);
        terms = findViewById(R.id.terms);
        firebaseAuth1 = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.example.gofood.signup.class));
            }
        });
        connect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (email1.getText().toString().isEmpty() || pass1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "fail to log in", Toast.LENGTH_LONG).show();
                } else {

                    firebaseAuth.signInWithEmailAndPassword(email1.getText().toString(), pass1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity2.class));


                            } else {
                                String problem = task.getException().getMessage().substring(30);
                                Toast.makeText(getApplicationContext(), "failed to sign in\n" + problem, Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }


            }
        });




    }
}