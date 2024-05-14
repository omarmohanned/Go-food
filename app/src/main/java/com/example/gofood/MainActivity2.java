package com.example.gofood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gofood.databinding.ActivityMain2Binding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth firebaseAuth;
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setCheckable(true);
                int itemid=menuItem.getItemId();
                if (itemid==R.id.nav_home1){
                    Toast.makeText(getApplicationContext(), "Main menu", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), home.class));
                } else if (itemid==R.id.nav_gallery) {
                    startActivity(new Intent(getApplicationContext(), taken_orders.class));
                }else if (itemid==R.id.IRBID) {
                    startActivity(new Intent(getApplicationContext(), driver.class));
                }else if (itemid==R.id.aqaba) {
                    startActivity(new Intent(getApplicationContext(), check_res.class));
                }else if (itemid==R.id.tutorial) {
                    startActivity(new Intent(getApplicationContext(), tutorial.class));
                }else if (itemid==R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), settings.class));
                }else if (itemid==R.id.sign_out) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), signin.class));
                    finish();
                }


                return false;
            }
        });
    }





    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}