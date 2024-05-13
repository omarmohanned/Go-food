package com.example.gofood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class myadapterorders_taken extends RecyclerView.Adapter<myadapterorders_taken.MyViewHolder> {
    Context context;
    ArrayList<orderss_taken> list;
    private DatabaseReference databaseReference1,reference;
    double lattitude,longitude;
    private FirebaseAuth firebaseAuth, firebaseAuth1;
    private FirebaseUser firebaseUser;


    public myadapterorders_taken(Context context, ArrayList<orderss_taken> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myadapterorders_taken.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.itemorders_taken,parent,false);
       return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterorders_taken.MyViewHolder holder, int position) {

        orderss_taken user=list.get(position);
        holder.firstname.setText(user.getRestaurantname());
        holder.lastname.setText(user.getPhone());
        holder.age.setText(user.getPlace());
        holder.comments.setText(user.getComments());
        holder.phone_number.setText(user.getPhone_num());
        holder.zinger.setText(user.getFood());

        List<String> foods= Arrays.asList("zinger","fahita","chicken","Matrix");
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;

        holder.location.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to accept this order and deliver");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    long time= System.currentTimeMillis();
                    String longAsString = "" + time;
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("restaurantname").setValue(user.getRestaurantname());
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("phone").setValue(user.getPhone());
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("place").setValue(user.getPlace());
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("food").setValue(holder.item.getSelectedItem().toString());
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("comments").setValue(holder.comments.getText().toString());
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("lat").setValue(user.getLat());
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("lon").setValue(user.getLon());
                    reference.child(firebaseUser.getUid()).child("taken_orders").child("phone_num").setValue(holder.phone_number.getText().toString());
                     /////////////////////////////////////////////
                    reference.child("orders").child(longAsString).child("restaurantname").setValue(user.getRestaurantname());
                    reference.child("orders").child(longAsString).child("phone").setValue(user.getPhone());
                    reference.child("orders").child(longAsString).child("place").setValue(user.getPlace());
                    reference.child("orders").child(longAsString).child("food").setValue(holder.item.getSelectedItem().toString());
                    reference.child("orders").child(longAsString).child("comments").setValue(holder.comments.getText().toString());
                    reference.child("orders").child(longAsString).child("lat").setValue(user.getLat());
                    reference.child("orders").child(longAsString).child("lon").setValue(user.getLon());
                    reference.child("orders").child(longAsString).child("phone_num").setValue(holder.phone_number.getText().toString());

                }
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

builder.show();

        }
});



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

TextView firstname,lastname,age,zinger;
Button location;
Spinner item;
EditText comments,phone_number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstname=itemView.findViewById(R.id.tvfirstname);
            lastname=itemView.findViewById(R.id.tvlastname);
            age=itemView.findViewById(R.id.tvage);
            location=itemView.findViewById(R.id.location);

            item=itemView.findViewById(R.id.item);
            zinger=itemView.findViewById(R.id.zinger);
            phone_number=itemView.findViewById(R.id.phone_number);
            comments=itemView.findViewById(R.id.comments);

        }
    }
}
