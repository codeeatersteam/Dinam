package com.codeeatersteam.dinam.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.net.EnvoyerEmplois;
import com.codeeatersteam.dinam.net.RecupererDiplomes;
import com.codeeatersteam.dinam.net.RecupererDomaines;
import com.codeeatersteam.dinam.net.RecupererEvenements;
import com.codeeatersteam.dinam.net.RecupererLieux;
import com.codeeatersteam.dinam.net.RecupererOffres;
import com.codeeatersteam.dinam.net.RecupererTypesEvenement;
import com.codeeatersteam.dinam.net.RecupererTypesLieu;
import com.codeeatersteam.dinam.net.RecupererTypesOffre;

import static com.codeeatersteam.dinam.kernel.Core.OUI_COMMUN_AUX_PREFERENCES;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.affecterPolice;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.initialiserPreferences;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.isConnectedInternet;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;

public class Welcome extends AppCompatActivity {
   private static int SPLASH_TIME_OUT = 4000;
    TextView logotext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        logotext = (TextView)findViewById(R.id.welcome_app_name);
        logotext.setTextSize(70);
        affecterPolice(this,logotext);
        initialiserPreferences(this);

        if (Build.VERSION.SDK_INT > 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (isConnectedInternet(this)) {

            try {
                RecupererDomaines recupererDomaines = new RecupererDomaines(this);
                recupererDomaines.execute();
                RecupererTypesOffre recupererTypesOffre = new RecupererTypesOffre(this);
                recupererTypesOffre.execute();
                RecupererTypesEvenement recupererTypesEvenement = new RecupererTypesEvenement(this);
                recupererTypesEvenement.execute();
                RecupererTypesLieu recupererTypesLieu = new RecupererTypesLieu(this);
                recupererTypesLieu.execute();
                RecupererDiplomes recupererDiplomes = new RecupererDiplomes(this);
                recupererDiplomes.execute();
                RecupererOffres recupererOffres = new RecupererOffres(this);
                recupererOffres.execute();
                RecupererEvenements recupererEvenements = new RecupererEvenements(this);
                recupererEvenements.execute();
                RecupererLieux recupererLieux = new RecupererLieux(this);
                recupererLieux.execute();
                EnvoyerEmplois envoyerEmplois = new EnvoyerEmplois(this);
                envoyerEmplois.execute();

            }catch (Exception e){

            }


        }else {
            Toast.makeText(this,"Aucune connexion internet, veuillez vous connecter",Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PreferencesUtilisateur.getInstance(Welcome.this).setWelcome(OUI_COMMUN_AUX_PREFERENCES);
                ouvrirActivite(Welcome.this,Conteneur.class);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
