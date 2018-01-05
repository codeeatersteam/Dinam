package com.codeeatersteam.dinam.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.EvenementsDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.net.ApiLinks;
import com.codeeatersteam.dinam.net.EnvoyerEmplois;
import com.codeeatersteam.dinam.net.EnvoyerEvenements;
import com.codeeatersteam.dinam.net.EnvoyerLieux;
import com.codeeatersteam.dinam.net.RecupererDiplomes;
import com.codeeatersteam.dinam.net.RecupererDomaines;
import com.codeeatersteam.dinam.net.RecupererEvenements;
import com.codeeatersteam.dinam.net.RecupererLieux;
import com.codeeatersteam.dinam.net.RecupererOffres;
import com.codeeatersteam.dinam.net.RecupererTypesEvenement;
import com.codeeatersteam.dinam.net.RecupererTypesLieu;
import com.codeeatersteam.dinam.net.RecupererTypesOffre;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DATE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_IMAGE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_LIEU;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TYPE_EVENEMENT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_EVENEMENT_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_EVENEMENTS;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.isConnectedInternet;

public class LireEvenement extends AppCompatActivity {
    ImageView imageevenement;
    TextView nom,lieu,date,description,site,telephone;
    boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lire_evenement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imageevenement = (ImageView)findViewById(R.id.imageEventLire);
        nom = (TextView)findViewById(R.id.nomEventTxt);
        lieu = (TextView)findViewById(R.id.lieuEventTxt);
        date = (TextView)findViewById(R.id.dateEventTxt);
        description = (TextView)findViewById(R.id.descriptionEventTxt);
        telephone = (TextView)findViewById(R.id.telephoneEventTxt);
        DbBuilder builder = new DbBuilder(this);
        SQLiteDatabase database = builder.getReadableDatabase();
        System.out.println(String.valueOf(this.getIntent().getExtras().getString("iden")));
        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_EVENEMENTS+" WHERE "+COLUMN_EVENEMENT_ID+"" +
                " ="+this.getIntent().getExtras().getString("identifiant"),null);
        while (cursor.moveToNext()){
            EvenementsDao evenement = new EvenementsDao(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ID)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ETAT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_TYPE_EVENEMENT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_NOM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_TELEPHONE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_LIEU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_CREATED_AT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_EVENEMENT_UPDATED_AT))
            );

            Glide.with(this).load(ApiLinks.API_IMAGES_EVENEMENTS_URL+evenement.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageevenement);
            nom.setText(evenement.getNom());
            lieu.setText(evenement.getLieu());
            date.setText(evenement.getDate_evenement());
            description.setText(evenement.getDescription());
            telephone.setText(evenement.getTelephone());


        }

        imageevenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imageevenement.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 550));
                    imageevenement.setScaleType(ImageView.ScaleType.CENTER);
                }else{
                    isImageFitToScreen=true;
                    imageevenement.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageevenement.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });
        if(isConnectedInternet(this)){
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
                EnvoyerEvenements envoyerEvenements = new EnvoyerEvenements(this);
                envoyerEvenements.execute();
                EnvoyerLieux envoyerLieux = new EnvoyerLieux(this);
                envoyerLieux.execute();
            }catch (Exception e){

            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
