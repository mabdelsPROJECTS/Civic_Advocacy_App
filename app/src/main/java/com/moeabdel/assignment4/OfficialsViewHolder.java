package com.moeabdel.assignment4;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moeabdel.assignment4.databinding.ActivityOfficialBinding;

public class OfficialsViewHolder extends RecyclerView.ViewHolder {

     TextView officialsTitle;
     TextView officialsName;
     ImageView officialsPic;


    public OfficialsViewHolder(@NonNull View itemview){
        super(itemview);
        officialsTitle = itemview.findViewById(R.id.recyclerViewTitle);
        officialsName = itemview.findViewById(R.id.recyclerViewName);
        officialsPic = itemview.findViewById(R.id.recyclerViewOfficalsImage);

    }
}
