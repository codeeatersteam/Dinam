package com.codeeatersteam.dinam.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.LieuxDao;
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
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ADRESSE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_IMAGE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_SITE_WEB;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_TYPE_LIEU;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_LIEUX;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.autoLinksMode;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.isConnectedInternet;

public class LireLieu extends AppCompatActivity {
    ImageView imagelieu;
    AutoLinkTextView nom,adresse,type,description,contact,site;
    boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lire_lieu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        nom = (AutoLinkTextView)findViewById(R.id.nomLieuTxt);
        adresse = (AutoLinkTextView)findViewById(R.id.adresseLieuTxt);
        type = (AutoLinkTextView)findViewById(R.id.typeLieuTxt);
        description = (AutoLinkTextView)findViewById(R.id.descriptionLieuTxt);
        contact = (AutoLinkTextView)findViewById(R.id.contactLieuTxt);
        site = (AutoLinkTextView) findViewById(R.id.siteLieuTxt);
        imagelieu = (ImageView) findViewById(R.id.imageLieuLire);

       autoLinksMode(this,contact);
        autoLinksMode(this,nom);
        autoLinksMode(this,type);
        autoLinksMode(this,description);
        autoLinksMode(this,site);
        autoLinksMode(this,adresse);


        DbBuilder builder = new DbBuilder(this);
        SQLiteDatabase database = builder.getReadableDatabase();
        System.out.println(String.valueOf(this.getIntent().getExtras().getString("iden")));
        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_LIEUX+" WHERE "+COLUMN_LIEU_ID+"" +
                " ="+this.getIntent().getExtras().getString("identifiant"),null);



        while (cursor.moveToNext()){

            LieuxDao lieu = new LieuxDao(
              cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ID)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ETAT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_TYPE_LIEU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_NOM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_TELEPHONE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_ADRESSE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_SITE_WEB)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_CREATED_AT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_UPDATED_AT))
            );
            nom.setText(lieu.getNom());
            adresse.setText(lieu.getAdresse());
            type.setText(builder.nomTypeLieuFromId(lieu.getTypelieu()));
            description.setText(lieu.getDescription());
            contact.setAutoLinkText(lieu.getTelephone());
            site.setAutoLinkText( lieu.getSiteweb());
            Glide.with(this).load(ApiLinks.API_IMAGES_LIEUX_URL+lieu.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imagelieu);


        }

        imagelieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imagelieu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 550));
                    imagelieu.setScaleType(ImageView.ScaleType.CENTER);
                }else{
                    isImageFitToScreen=true;
                    imagelieu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imagelieu.setScaleType(ImageView.ScaleType.FIT_XY);
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
