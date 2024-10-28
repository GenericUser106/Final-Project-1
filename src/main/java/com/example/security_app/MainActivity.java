package com.example.security_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button initScan;
    Button goToStats;
    Button contactInfo;
    Button resolve;

    int scanCounter = 0;
    int virusCounter = 0;
    int cleanCounter = 0;
    int randomTipNum = 0;

    SharedPreferences totalScans;
    SharedPreferences infectedScans;
    SharedPreferences cleanScans;

    SharedPreferences.Editor editTotal;
    SharedPreferences.Editor editInfected;
    SharedPreferences.Editor editClean;

    TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initScan = findViewById(R.id.initScan);
        goToStats = findViewById(R.id.goToStats);
        contactInfo = findViewById(R.id.contactInfo);
        resolve = findViewById(R.id.resolve);

        totalScans = getSharedPreferences("scanCounter", 0);
        infectedScans = getSharedPreferences("virusCounter", 0);
        cleanScans = getSharedPreferences("cleanCounter", 0);

        scanCounter = totalScans.getInt("scanCounter", 0);
        virusCounter = infectedScans.getInt("virusCounter", 0);
        cleanCounter = cleanScans.getInt("cleanCounter", 0);

        tip = findViewById(R.id.tip);

        getTip();
    }

    public void actions(View view) {
        if (view == initScan)
        {
            ProgressDialog scan;
            scan = ProgressDialog.show(this, "", "Scanning...");
            int hasVirus = (int)(Math.random() * 2 - 1 + 1) + 1;

            if (hasVirus == 1)
            {
                Boolean timer = new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        scan.dismiss();
                        Toast.makeText(MainActivity.this, "no viruses found", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
                cleanCounter++;
                editClean = cleanScans.edit();
                editClean.putInt("cleanCounter", cleanCounter);
                editClean.commit();
            }

            else
            {
                Boolean timer = new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        scan.dismiss();
                        Toast.makeText(MainActivity.this, "viruses found, please take action", Toast.LENGTH_LONG).show();
                        resolve.setVisibility(View.VISIBLE);
                    }
                }, 5000);
                virusCounter++;
                editInfected = infectedScans.edit();
                editInfected.putInt("virusCounter", virusCounter);
                editInfected.commit();
            }
            scanCounter++;
            editTotal = totalScans.edit();
            editTotal.putInt("scanCounter", scanCounter);
            editTotal.commit();
        }

        else if (view == goToStats)
        {
            Intent statsScreen = new Intent(this, StatsScreen.class);
            statsScreen.putExtra("randomTipNum", randomTipNum);
            startActivity(statsScreen);

            //Toast.makeText(MainActivity.this, "the number is " + cleanCounter, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(MainActivity.this, "delete the second email address", Toast.LENGTH_LONG).show();
        }

        else if (view == resolve)
        {
            ProgressDialog remove;
            remove = ProgressDialog.show(this, "", "Removing Viruses...");
            Boolean timer = new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    remove.dismiss();
                    Toast.makeText(MainActivity.this, "Viruses Removed", Toast.LENGTH_SHORT).show();
                }
            }, 5000);

            Boolean timer1 = new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    System.exit(0);
                }
            }, 6000);
        }
    }

    public void getTip()
    {
        randomTipNum = (int)(Math.random() * 5 - 1 + 1) + 1;

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