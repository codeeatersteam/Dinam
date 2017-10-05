package com.codeeatersteam.dinam.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.ParametresDomaines;
import com.codeeatersteam.dinam.kernel.ParametresTypeEvenements;
import com.codeeatersteam.dinam.kernel.ParametresTypeLieux;
import com.codeeatersteam.dinam.kernel.ParametresTypeOffres;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;

public class Personnalisation extends AppCompatActivity {

     public Button domainePref,typeOffrePref,typeEvenementPref,typeLieuPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnalisation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        domainePref = (Button)findViewById(R.id.domaineOffrePrefBtn);
        typeOffrePref = (Button)findViewById(R.id.typeOffrePrefBtn);
        typeEvenementPref = (Button)findViewById(R.id.typeEvenementPrefBtn);
        typeLieuPref = (Button)findViewById(R.id.typeLieuPrefBtn);

        domainePref.setText("Domaine d'offre: "+ PreferencesUtilisateur.getInstance(this).getDomaineOffrePref());
        typeOffrePref.setText("Type d'offre: " + PreferencesUtilisateur.getInstance(this).getTypeOffrePref());
        typeEvenementPref.setText("Type d'evenement: " + PreferencesUtilisateur.getInstance(this).getTypeEvenementPref());
        typeLieuPref.setText("Type de lieu: " + PreferencesUtilisateur.getInstance(this).getTypeLieuxPref());

        domainePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParametresDomaines domaines = new ParametresDomaines();
                domaines.show(getFragmentManager(),"domaines");



            }
        });

        typeOffrePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParametresTypeOffres typeOffres = new ParametresTypeOffres();
                typeOffres.show(getFragmentManager(),"typeOffres");
            }
        });

        typeEvenementPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParametresTypeEvenements typeEvenements = new ParametresTypeEvenements();
                typeEvenements.show(getFragmentManager(),"typeevenements");
            }
        });
        typeLieuPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParametresTypeLieux typeLieux = new ParametresTypeLieux();
                typeLieux.show(getFragmentManager(),"typelieux");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("on pause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        domainePref.setText("Domaine d'offre: "+ PreferencesUtilisateur.getInstance(Personnalisation.this).getDomaineOffrePref());
        typeOffrePref.setText("Type d'offre: " + PreferencesUtilisateur.getInstance(Personnalisation.this).getTypeOffrePref());
        System.out.println("on resume");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        FonctionsUtiles.ouvrirActivite(this,Conteneur.class );
        super.onBackPressed();
    }
}
