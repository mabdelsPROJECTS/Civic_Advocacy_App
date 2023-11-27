package com.moeabdel.assignment4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.moeabdel.assignment4.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private String twitterUrl;
    private String facebookUrl;
    private String youtubeUrl;
    private String socialMediaId;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_NUMB = 111;
    private String startingUrl = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyCDmyF270Zjlstl8lKDuv-M7NgL-MvMKoE";

    private String firstQuery;
    private RequestQueue queue;
    private TextView textView;
    private ArrayList<Officials> officialsArrayList = new ArrayList<>();

    private RecyclerView recyclerView;

    private OfficialsAdapter officialsAdapter;
    private static String locationString = "Unspecified Location";
    private boolean noPermission = false;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.mainActivityToolbar;
        toolbar.setTitle("Civil Advocacy");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        textView = binding.locationTextView;
        recyclerView = binding.mainActRecyclerView;
        officialsAdapter = new OfficialsAdapter(officialsArrayList, this);
        recyclerView.setAdapter(officialsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        determineLocation();


    }

    private void startScreenPermission() {
        if(!hasConnection() || !noPermission){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("Data cannot be accessed/loaded without an internet connection.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            textView.setText("No Data For Location");

        }
    }

    private void determineLocation(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_NUMB);
                //noPermission = false;
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {

                    if (location != null) {
                        locationString = getPlace(location);
                        String[] newLocationString = locationString.split("\n");
                        textView.setText(newLocationString[0]);
                        doDownload(locationString);
                    }
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_opt_menu, menu);
        return true;
    }
    private String getPlace(Location loc) {

        StringBuilder sb = new StringBuilder();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                String addr = addresses.get(0).getAddressLine(0);
                sb.append(String.format(
                        Locale.getDefault(),
                        "%s%n%nProvider: %s%n%n%.5f, %.5f",
                        addr, loc.getProvider(),
                        loc.getLatitude(), loc.getLongitude()));
            } else {
                sb.append("Can not get location data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_NUMB) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    noPermission = true;
                    determineLocation();
                    startScreenPermission();
                } else {
                    noPermission = false;
                    startScreenPermission();
                    textView.setText("Location permission was denied - cannot determine address");
                }
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.infoMainActivity) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.searchMainActivity) {
            if (!hasConnection()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No Network Connection");
                builder.setMessage("Data cannot be accessed/loaded without an internet connection.");
                //builder.setCancelable(false);
                //builder.setIcon(R.drawable.logo);

                AlertDialog dialog = builder.create();
                dialog.show();
                //textView.setText("No Data For Location");
            } else {
                //Toast.makeText(this, "You Clicked the Search Icon", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter Address");
                final EditText input = new EditText(this);
                input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                builder.setView(input);
                builder.setCancelable(false);
                builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firstQuery = input.getText().toString();
                        textView.setText(firstQuery);
                        int length = officialsArrayList.size();
                        for (int j = length - 1; j >= 0; j--) {
                            officialsArrayList.remove(j);
                            officialsAdapter.notifyItemRemoved(j);
                        }
                        doDownload(firstQuery);

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else

    {
        return super.onOptionsItemSelected(item);
    }
        return true;
}

    private void doDownload(String input) {
        Uri.Builder urlBuilder = Uri.parse(startingUrl).buildUpon();

        urlBuilder.appendQueryParameter("address", input);
        String urlToUse = urlBuilder.build().toString();
        Response.Listener<JSONObject> listener =

                response -> parseJSON(response.toString());

        Response.ErrorListener error = error1 -> Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error);


        queue.add(jsonObjectRequest);


    }
    public void parseJSON(String s) {
        try {
            JSONObject jsonData = new JSONObject(s);
            JSONObject normalizedInput = jsonData.getJSONObject("normalizedInput");
            if (normalizedInput.length() == 0) {
                Toast.makeText(this, "No data was found", Toast.LENGTH_SHORT).show();
            } else {

                JSONArray offices = jsonData.getJSONArray("offices");
                JSONArray officials = jsonData.getJSONArray("officials");
                for (int i = 0; i < offices.length(); i++) {
                    boolean hasFacebook = false;
                    boolean hasTwitter = false;
                    boolean hasYoutube = false;
                    String facebookId = "";
                    String twitterId = "";
                    String youtubeId = "";
                    String officialsName = " ";
                    String officialsAddressLine1 = "";
                    String officialsAddressLine2 = "";
                    String officialsCity = "";
                    String officialsState = "";
                    String officialsZip = "";
                    String officialsParty = "";
                    String officialsPhone = "";
                    String officialsUrl = "";
                    String officialsEmail = "";
                    String officialsPhotoUrl = "";
                    String officialsFacebookChannel = "";
                    JSONObject data = offices.getJSONObject(i);
                    String officialIndex = data.optString("officialIndices");
                    JSONArray officialIndexArray = data.optJSONArray("officialIndices");

                    String title = data.optString("name");

                    Log.d(TAG, "parseJSON: " + officialIndex);
                    for (int m = 0; m < officialIndexArray.length(); m++) {
                        String numb = officialIndexArray.getString(m);
                        int startIndex = Integer.parseInt(numb);

                        for (int j = startIndex; j <= startIndex; j++) {
                            JSONObject secondData = officials.getJSONObject(j);
                            officialsName = secondData.optString("name");
                            if (secondData.has("address")) {
                                JSONArray officialsAddressArray = secondData.getJSONArray("address");
                                JSONObject officialsData = officialsAddressArray.getJSONObject(0);
                                officialsAddressLine1 = officialsData.optString("line1");

                                officialsAddressLine2 = officialsData.optString("line2");
                                officialsCity = officialsData.optString("city");
                                officialsState = officialsData.optString("state");
                                officialsZip = officialsData.optString("zip");
                            }
                            officialsParty = secondData.optString("party");
                            if (secondData.has("phones")) {
                                JSONArray officialPhoneArray = secondData.getJSONArray("phones");
                                officialsPhone = officialPhoneArray.getString(0);
                            }
                            JSONArray officialsUrlArray = secondData.getJSONArray("urls");
                            officialsUrl = officialsUrlArray.getString(0);
                            if (secondData.has("emails")) {
                                JSONArray officialsEmailArray = secondData.getJSONArray("emails");
                                officialsEmail = officialsEmailArray.getString(0);
                            }

                            officialsPhotoUrl = secondData.optString("photoUrl");
                            if (secondData.has("channels")) {
                                JSONArray officialsChannelArray = secondData.getJSONArray("channels");
                                for (int n = 0; n < officialsChannelArray.length(); n++) {
                                    JSONObject socialMediaObj = officialsChannelArray.getJSONObject(n);
                                    String socialMediaType = socialMediaObj.getString("type");
                                     socialMediaId = socialMediaObj.getString("id");

                                    if(socialMediaType.equals("Facebook")){
                                        facebookUrl = "https://www.facebook.com/" + socialMediaId;
                                        hasFacebook = true;
                                    }
                                    else if(socialMediaType.equals("Twitter")){
                                        twitterUrl = "https://www.twitter.com/" + socialMediaId;
                                        hasTwitter = true;
                                    }
                                    else if(socialMediaType.equals("Youtube")){
                                        youtubeUrl = "https://www.youtube.com/watch/" + socialMediaId;
                                        hasYoutube = true;
                                    }
                                }
                            }
                        }
                        Officials officialsData = new Officials(socialMediaId,facebookUrl, twitterUrl, youtubeUrl,hasFacebook, hasTwitter, hasYoutube, title, officialsName,
                                officialsAddressLine1, officialsAddressLine2,
                                officialsCity, officialsState, officialsZip, officialsParty, officialsPhone, officialsUrl,
                                officialsEmail, officialsPhotoUrl, officialsFacebookChannel);
                        officialsArrayList.add(officialsData);
                        officialsAdapter.notifyItemInserted(i);
                    }

                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean hasConnection(){
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return(networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public void testButton(View v){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Officials officials = officialsArrayList.get(pos);
        Intent intent = new Intent(this, OfficialActivity.class);
        String titleToSend = officials.getOfficialsTitle();
        boolean hasFacebook = officials.getFacebookStatus();
        boolean hasTwitter = officials.getTwitterStatus();
        boolean hasYoutube = officials.getYoutubeStatus();
        String nameToSend = officials.getOfficialsName();
        String addressLine1ToSend = officials.getOfficialsAddressLine1();
        String addressLine2ToSend = officials.getOfficialsAddressLine2();
        String cityToSend = officials.getCity();
        String stateToSend = officials.getState();
        String zipToSend = officials.getZip();
        String partyToSend = officials.getOfficialsParty();
        String phoneToSend = officials.getOfficialsPhone();
        String urlToSend = officials.getOfficialsUrl();
        String emailToSend = officials.getOfficialsEmail();
        String photoUrlToSend = officials.getOfficialsPhotoUrl();
        String facebookChannelToSend = officials.getOfficialsFacebook();
        String facebookUrl = officials.getFacebookUrl();
        String twitterUrl = officials.getTwitterUrl();
        String youtubeUrl = officials.getYoutubeUrl();
        String socialMediaId = officials.getSocialMediaId();

        Officials officialDataToSend = new Officials(socialMediaId,facebookUrl, twitterUrl, youtubeUrl,hasFacebook, hasTwitter, hasYoutube,titleToSend, nameToSend, addressLine1ToSend, addressLine2ToSend,
                cityToSend, stateToSend,zipToSend,partyToSend, phoneToSend, urlToSend,
                emailToSend, photoUrlToSend, facebookChannelToSend);
        intent.putExtra("officialsDataToSend", officialDataToSend);
        intent.putExtra("locationDetails", textView.getText().toString());

        startActivity(intent);

    }
}