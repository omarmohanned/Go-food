package com.example.gofood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder> {
    Context context;
    ArrayList<user> list;
    private DatabaseReference databaseReference1,reference;
    double lattitude,longitude;
    private FirebaseAuth firebaseAuth, firebaseAuth1;
    private FirebaseUser firebaseUser;

    public myadapter() {
    }

    public myadapter(Context context, ArrayList<user> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
       return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.MyViewHolder holder, int position) {

        user user=list.get(position);
        holder.firstname.setText(user.getResname());
        holder.lastname.setText(user.getPhone());
        holder.age.setText(user.getPlace());

        List<String> foods= Arrays.asList("zinger","fahita","chicken","Matrix","shwerma");
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        ArrayAdapter adapter=new ArrayAdapter(context, android.R.layout.simple_spinner_item,foods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.item.setAdapter(adapter);
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String items= holder.item.getSelectedItem().toString();
                databaseReference1= FirebaseDatabase.getInstance().getReference();
                Toast.makeText(context,items,Toast.LENGTH_LONG).show();
                databaseReference1.child("allres").child(user.getResname()).child(holder.item.getSelectedItem().toString())
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       holder.zinger.setText( dataSnapshot.getValue(String.class));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        holder.order.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to add this order");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    long time= System.currentTimeMillis();
                    String longAsString = "" + time;
                    reference.child(firebaseUser.getUid()).child("orders").child("restaurantname").setValue(user.getResname());
                    reference.child(firebaseUser.getUid()).child("orders").child("phone").setValue(user.getPhone());
                    reference.child(firebaseUser.getUid()).child("orders").child("place").setValue(user.getPlace());
                    reference.child(firebaseUser.getUid()).child("orders").child("food").setValue(holder.item.getSelectedItem().toString());
                    reference.child(firebaseUser.getUid()).child("orders").child("comments").setValue(holder.comments.getText().toString());
                    reference.child(firebaseUser.getUid()).child("orders").child("lat").setValue(user.getLat());
                    reference.child(firebaseUser.getUid()).child("orders").child("lon").setValue(user.getLon());
                    reference.child(firebaseUser.getUid()).child("orders").child("phone_num").setValue(holder.phone_number.getText().toString());
                     /////////////////////////////////////////////
                    reference.child("orders").child(longAsString).child("restaurantname").setValue(user.getResname());
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
          Button order,check;
          Spinner item;
          EditText comments,phone_number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstname=itemView.findViewById(R.id.tvfirstname);
            lastname=itemView.findViewById(R.id.tvlastname);
            age=itemView.findViewById(R.id.tvage);
            order=itemView.findViewById(R.id.order);
            check=itemView.findViewById(R.id.check);
            item=itemView.findViewById(R.id.item);
            zinger=itemView.findViewById(R.id.zinger);
            phone_number=itemView.findViewById(R.id.phone_number);
            comments=itemView.findViewById(R.id.comments);

        }
    }
}
