package com.example.gofood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder> {
    Context context;
    ArrayList<user> list;
    private DatabaseReference reference;
    double lattitude,longitude;
    private FirebaseAuth firebaseAuth, firebaseAuth1;
    private FirebaseUser firebaseUser;


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
        firebaseAuth1 = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        user user=list.get(position);
        holder.firstname.setText(user.getResname());
        holder.lastname.setText(user.getPhone());
        holder.age.setText(user.getPlace());
        holder.order.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {



        }
});



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

TextView firstname,lastname,age;
Button order;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstname=itemView.findViewById(R.id.tvfirstname);
            lastname=itemView.findViewById(R.id.tvlastname);
            age=itemView.findViewById(R.id.tvage);
            order=itemView.findViewById(R.id.order);


        }
    }
}
