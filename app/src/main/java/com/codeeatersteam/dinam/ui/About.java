package com.codeeatersteam.dinam.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codeeatersteam.dinam.R;

import static com.codeeatersteam.dinam.kernel.Core.APP_VERSION;
import static com.codeeatersteam.dinam.kernel.Core.TEAM_NAME;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.affecterPolice;

public class About extends AppCompatActivity {
    TextView logotext,teamtext,versiontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        teamtext= (TextView)findViewById(R.id.about_app_team);
        versiontext= (TextView)findViewById(R.id.about_app_version);
        logotext = (TextView)findViewById(R.id.about_app_name);
        logotext.setTextSize(45);

        affecterPolice(this,logotext);

        teamtext.setText(TEAM_NAME);
        versiontext.setText(APP_VERSION);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
