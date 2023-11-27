package com.moeabdel.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moeabdel.assignment4.databinding.ActivityOfficialBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class OfficialActivity extends AppCompatActivity {

    private TextView officialsTitle;
    private ConstraintLayout layout;
    private Toolbar toolbar;
    private TextView officialsName;
    private TextView officialsParty;
    private TextView topTextView;
    private ImageView facebook;
    private ImageView twitter;
    private ImageView youtube;
    private String officialsCity;
    private String officialsState;
    private String officialsZip;
    private String officialsUrl;
    private String officialsPhotoUrl;
    private String officialsFacebook;
    private String locationString;

    private ActivityOfficialBinding binding;


    private ImageView officialsImage;
    private ImageView partyImage;
    private TextView officialsAddress;
    private TextView officialsPhone;
    private TextView officialsEmail;
    private TextView officialsWebsite;
    private TextView emailHeader;

    private Picasso picasso;
    private TextView addressHeader;
    private TextView phoneHeader;


    public boolean displayOrNo = false;
    private boolean hasFacebook;
    private boolean hasTwitter;
    private boolean hasYoutube;
    private String dummyAddress = "";
    private String facebookUrl;
    private String twitterUrl;
    private String youtubeUrl;
    private String socialMediaId;
    private static final String TAG = "OfficialActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOfficialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.officialsActivityToolbar;
        toolbar.setTitle("Civil Advocacy");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.officialActivityWholePage);
        officialsName = binding.officialActivityName;
        officialsTitle = binding.officialActivityTitle;
        officialsParty = binding.officialActivityParty;
        officialsPhone = binding.officialActivityPhoneText;
        officialsEmail = binding.officialActivityEmailText;
        officialsWebsite = binding.officialActivityWebsiteText;
        officialsImage = binding.officialActivityPic;
        partyImage = binding.officialActivityPartyImageView;
        officialsAddress = binding.officialActivityFullAddressText;
        topTextView = binding.officialsActivityTopTextView;
        emailHeader = binding.officialActivityEmailHeader;
        facebook = binding.officialActivityFacebookImage;
        twitter = binding.officialActivityTwitterImage;
        youtube = binding.officialActivityYoutubeImage;
        addressHeader = binding.addressHeader;
        phoneHeader = binding.phoneHeader;

        picasso = Picasso.get();
        Intent intent = getIntent();
        if(intent.hasExtra("officialsDataToSend") && intent.hasExtra("locationDetails")){
       // if(intent.hasExtra("officialsDataToSend")){
        Officials officials = (Officials) intent.getSerializableExtra("officialsDataToSend");
         locationString = (String)intent.getSerializableExtra("locationDetails");
        officialsCity = officials.getCity();
        socialMediaId = officials.getSocialMediaId();
        officialsState = officials.getState();
        officialsUrl = officials.getOfficialsUrl();
        officialsPhotoUrl = officials.getOfficialsPhotoUrl();
        officialsFacebook = officials.getOfficialsFacebook();
        officialsZip = officials.getZip();
         hasFacebook = officials.getFacebookStatus();
         hasTwitter = officials.getTwitterStatus();
         hasYoutube = officials.getYoutubeStatus();
        officialsName.setText(officials.getOfficialsName());
        String name = officials.getOfficialsName();
        officialsTitle.setText(officials.getOfficialsTitle());
        topTextView.setText(locationString);
        officialsParty.setText("(" + officials.getOfficialsParty() + ")");
        String addressLine1 = officials.getOfficialsAddressLine1();
        String addressLine2 = officials.getOfficialsAddressLine2();
         String tempAddress = String.format("%s %s %s, %s %s", addressLine1, addressLine2, officialsCity, officialsState, officialsZip);
         String formattedAddress = String.format("%s%s%n%s, %s %s", addressLine1, addressLine2, officialsCity, officialsState, officialsZip);
        officialsAddress.setText(tempAddress);
        officialsPhone.setText(officials.getOfficialsPhone());
        officialsEmail.setText(officials.getOfficialsEmail());
        officialsWebsite.setText(officials.getOfficialsUrl());
        facebookUrl = officials.getFacebookUrl();
        twitterUrl = officials.getTwitterUrl();
        youtubeUrl = officials.getYoutubeUrl();
            if(!officials.getOfficialsPhotoUrl().isEmpty()){
                picasso.load(officialsPhotoUrl).error(R.drawable.brokenimage).into(officialsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        displayOrNo = true;

                    }

                    @Override
                    public void onError(Exception e) {

                        displayOrNo = false;
                    }
                });
            }

            else {
                picasso.load(R.drawable.missing).into(officialsImage);
                displayOrNo = false;
            }
       // picasso.load(officials.getOfficialsPhotoUrl()).error(R.drawable.missing).into(officialsImage);
        if(officials.getOfficialsParty().equals("Democratic Party")){
            picasso.load(R.drawable.dem_logo).into(partyImage);
            layout.setBackgroundColor(Color.BLUE);

        }
        else if(officials.getOfficialsParty().equals("Republican Party")){
            picasso.load(R.drawable.rep_logo).into(partyImage);
            layout.setBackgroundColor(Color.RED);
        }
        else if(officials.getOfficialsParty().equals("Nonpartisan")){
            layout.setBackgroundColor(Color.BLACK);
        }
        if(officialsEmail.getText().toString().equals("")){
            officialsEmail.setVisibility(View.GONE);
            emailHeader.setVisibility(View.GONE);
        }
        if(addressLine1.equals("")){
                officialsAddress.setVisibility(View.GONE);
                addressHeader.setVisibility(View.GONE);
            }
        if(officialsPhone.getText().toString().equals("")){
                officialsPhone.setVisibility(View.GONE);
                phoneHeader.setVisibility(View.GONE);
            }
        if(hasFacebook){
            facebook.setVisibility(View.VISIBLE);
        }
        if(hasTwitter){
            twitter.setVisibility(View.VISIBLE);
        }
        if(hasYoutube){
            youtube.setVisibility(View.VISIBLE);
        }
        }
        Linkify.addLinks(officialsAddress, Linkify.MAP_ADDRESSES);
        Linkify.addLinks(officialsEmail, Linkify.ALL);
        Linkify.addLinks(officialsWebsite, Linkify.ALL);
        Linkify.addLinks(officialsPhone, Linkify.ALL);
        officialsEmail.setLinkTextColor(Color.WHITE);
       officialsPhone.setLinkTextColor(Color.WHITE);
        officialsAddress.setLinkTextColor(Color.WHITE);
        officialsWebsite.setLinkTextColor(Color.WHITE);

    }

    public void goToImage(View v){

        if(displayOrNo){
            Intent intent = new Intent(this, PhotoDetailsActivity.class);
            Officials officialDataToSend = new Officials(socialMediaId,facebookUrl, twitterUrl, youtubeUrl,hasFacebook, hasTwitter, hasYoutube,
                    officialsTitle.getText().toString(),officialsName.getText().toString(),
                    officialsAddress.getText().toString(), dummyAddress, officialsCity,
                    officialsState, officialsZip, officialsParty.getText().toString(),
                    officialsPhone.getText().toString(),officialsUrl, officialsEmail.getText().toString(),
                    officialsPhotoUrl, officialsFacebook);
            intent.putExtra("officialsDetails", officialDataToSend);
            intent.putExtra("locationString", locationString);
            startActivity(intent);

        }
        else{

            Toast.makeText(this,"No Available Image to Zoom", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToMap(View v){
        TextView addressTextView = (TextView) v;
        String address = addressTextView.getText().toString();
        Uri mapUrl = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent intent = new Intent(Intent.ACTION_VIEW, mapUrl);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No App to handle address request", Toast.LENGTH_SHORT).show();
        }
    }
    public void goToEmail(View v){
        TextView emailTextView = (TextView) v;
        String email = emailTextView.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto"));
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No App to handle email request", Toast.LENGTH_SHORT).show();
        }
    }

    public void makeCall(View v){
        TextView phoneString = (TextView) v;
        String phone = phoneString.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No App to handle call", Toast.LENGTH_SHORT).show();
        }
    }
    public void goToUrl(View v){
        TextView urlString = (TextView) v;
        String url = urlString.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No App to handle url request", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToPartyWebsite(View v){
        String partyString = officialsParty.getText().toString();
        if(partyString.equals("(Democratic Party)")){
            String demoUrl = "https://democrats.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(demoUrl));
            startActivity(intent);
        }
        else if(partyString.equals("(Republican Party)")){
            String repubUrl = "https://www.gop.com/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repubUrl));
            startActivity(intent);
        }
        else if(partyString.equals("(Nonpartisan)")){

        }
    }

    public void goToFacebook(View v){
        if(hasFacebook && facebookUrl != null){
            Intent intent;

            if (isPackageInstalled("com.facebook.katana")) {
                String facebookUrlToUse = "fb://facewebmodal/f?href=" + Uri.encode(facebookUrl);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrlToUse));
            } else {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
            }

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No App to handle facebook request", Toast.LENGTH_SHORT).show();
            }

        }
        }


    public void goToTwitter(View v){
        if(hasTwitter && twitterUrl != null){
            String twitterAppUrl = "twitter://user?screen_name=" + socialMediaId;
            Intent intent;
            if (isPackageInstalled("com.twitter.android")) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
            } else {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
            }
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "No App to handle twitter request", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void goToYoutube(View v){
        if(hasYoutube && youtubeUrl != null){
            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(youtubeUrl));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            else{

                Toast.makeText(this, "No App to handle youtube request", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isPackageInstalled(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}