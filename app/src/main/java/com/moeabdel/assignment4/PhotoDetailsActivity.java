package com.moeabdel.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moeabdel.assignment4.databinding.ActivityPhotoDetailsBinding;
import com.squareup.picasso.Picasso;

public class PhotoDetailsActivity extends AppCompatActivity {

    private TextView location;
    private TextView name;
    private TextView title;
    private ImageView fullImage;
    private ImageView officeImage;
    private Toolbar toolbar;
    private String locationDetails;
    private Picasso picasso;
    private ConstraintLayout layout;
    private ActivityPhotoDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotoDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        location = binding.photoDetailsActivityLocation;
        name = binding.photoDetailsActivityName;
        title = binding.photoDetailsActivityOffice;
        fullImage = binding.photoDetailsActivityBigImage;
        officeImage = binding.photoDetailsActivityOfficeImage;
        toolbar = binding.photoDetailsActivityToolbar;
        toolbar.setTitle("Civil Advocacy");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        layout = binding.photoDetailsLayout;
        //layout = findViewById(R.id.photoDetailsLayout);
        picasso = Picasso.get();
        Intent intent = getIntent();
        if(intent.hasExtra("officialsDetails") && intent.hasExtra("locationString")){
       // if(intent.hasExtra("officialsDetails")){
            Officials officials = (Officials) intent.getSerializableExtra("officialsDetails");
            locationDetails = (String) intent.getSerializableExtra("locationString");
            location.setText(locationDetails);
            name.setText(officials.getOfficialsName());
            title.setText(officials.getOfficialsTitle());
            picasso.load(officials.getOfficialsPhotoUrl()).into(fullImage);
            if(officials.getOfficialsParty().equals("(Democratic Party)")){
                picasso.load(R.drawable.dem_logo).into(officeImage);
                layout.setBackgroundColor(Color.BLUE);
            }
            else if(officials.getOfficialsParty().equals("(Republican Party)")){
                picasso.load(R.drawable.rep_logo).into(officeImage);
                layout.setBackgroundColor(Color.RED);
            }
            else if(officials.getOfficialsParty().equals("(Nonpartisan)")){
                //picasso.load(R.drawable.rep_logo).into(officeImage);
                officeImage.setVisibility(View.INVISIBLE);
                layout.setBackgroundColor(Color.BLACK);
            }


        }
    }
}