package com.example.security_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StatsScreen extends AppCompatActivity {
    Button returnHome;
    Button contactInfo;

    Intent homeScreen;

    TextView totalNum;
    TextView infectedNum;
    TextView cleanNum;
    TextView tip;

    int scanCounter;
    int virusCounter;
    int cleanCounter;
    int randomTipNum;

    SharedPreferences totalScans;
    SharedPreferences infectedScans;
    SharedPreferences cleanScans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_screen);

        returnHome = findViewById(R.id.returnHome);
        contactInfo = findViewById(R.id.contactInfo);
        tip = findViewById(R.id.tip);

        totalScans = getSharedPreferences("scanCounter", 0);
        infectedScans = getSharedPreferences("virusCounter", 0);
        cleanScans = getSharedPreferences("cleanCounter", 0);

        scanCounter = totalScans.getInt("scanCounter", 0);
        virusCounter = infectedScans.getInt("virusCounter", 0);
        cleanCounter = cleanScans.getInt("cleanCounter", 0);

        totalNum = findViewById(R.id.totalNum);
        infectedNum = findViewById(R.id.infectedNum);
        cleanNum = findViewById(R.id.cleanNum);
        randomTipNum = getIntent().getIntExtra("randomTipNum", 1);

        getTip();

        totalNum.setText("Total Scans: " + scanCounter);
        cleanNum.setText("Clean Scans: " + cleanCounter);
        infectedNum.setText("Virus Scans: " + virusCounter);


    }

    public void actionsV2(View view) {
        if (view == returnHome)
        {
            homeScreen = new Intent(this, MainActivity.class);
            startActivity(homeScreen);
        }

        else if (view == contactInfo)
        {
            String[] emails = {"viruswatchers@gmail.com","dummymail@gmail.com"};

            Intent messageUs = new Intent(Intent.ACTION_SEND);
            messageUs.setType("text/plain");
            messageUs.putExtra(Intent.EXTRA_EMAIL,emails);
            messageUs.putExtra(Intent.EXTRA_SUBJECT, "customer request");
            messageUs.putExtra(Intent.EXTRA_TEXT, "Hi, I'd like ");
            startActivity(Intent.createChooser(messageUs, "Send Email"));
            Toast.makeText(StatsScreen.this, "delete the second email address", Toast.LENGTH_LONG).show();
        }
    }

    public void getTip()
    {
        if (randomTipNum == 1)
        {
            tip.setText("Remember to scan your device daily!");
        }

        else if (randomTipNum == 2)
        {
            tip.setText("Thanks to our app, X viruses have been discovered");
        }

        else if (randomTipNum == 3)
        {
            tip.setText("tip number 3 is the best tip");
        }

        else if (randomTipNum == 4)
        {
            tip.setText("Never open an email from an address you don't recognize!");
        }

        else
        {
            tip.setText("Did you know? Every day thousands of viruses are made. Make sure your phone doesn't get infected");
        }
    }
}