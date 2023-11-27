package com.moeabdel.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moeabdel.assignment4.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private TextView textView;

    private ActivityAboutBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.AboutActivityToolbar;
        textView = binding.AboutActivityTextView;
        toolbar.setTitle("Civil Advocacy");
        toolbar.setTitleTextColor(Color.WHITE);
        //toolbar.getOverflowIcon().setTint(Color.WHITE);
        setSupportActionBar(toolbar);
        //Linkify.addLinks(textView, Linkify.ALL);



    }

    public void onClick(View v) {
        String url = "https://developers.google.com/civic-information/";
        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}