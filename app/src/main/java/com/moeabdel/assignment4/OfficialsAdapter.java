package com.moeabdel.assignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfficialsAdapter extends RecyclerView.Adapter<OfficialsViewHolder> {



    private ArrayList<Officials> officialsArrayList;
    private MainActivity mainActivity;

    private Picasso picasso;

     private static final String TAG = "OfficialsAdapter";
    public OfficialsAdapter(ArrayList<Officials> officialsArrayList, MainActivity mainActivity){
        this.officialsArrayList = officialsArrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public OfficialsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.officials_recyclerview, parent,false);
        itemview.setOnClickListener(mainActivity);
        picasso = Picasso.get();
        //picasso.setIndicatorsEnabled(true);
        //picasso.setLoggingEnabled(true);
        return new OfficialsViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialsViewHolder holder, int position) {
        Officials officials = officialsArrayList.get(position);
        String title = officials.getOfficialsTitle();
        String name = officials.getOfficialsName();
        String party = officials.getOfficialsParty();
        String photo = officials.getOfficialsPhotoUrl();
        if(!photo.isEmpty()){
            picasso.load(photo).error(R.drawable.brokenimage).into(holder.officialsPic);
        }

        else {
            picasso.load(R.drawable.missing).into(holder.officialsPic);
       }

        holder.officialsTitle.setText(title);
        holder.officialsName.setText(name + " (" + party + ")" );


    }

    @Override
    public int getItemCount() {
        return officialsArrayList.size();
    }
}


