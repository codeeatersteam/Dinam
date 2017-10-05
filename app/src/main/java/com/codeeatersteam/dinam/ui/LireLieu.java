package com.codeeatersteam.dinam.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.LieuxDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.net.ApiLinks;

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

public class LireLieu extends AppCompatActivity {
    ImageView imagelieu;
    TextView nom,adresse,type,description,contact,site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lire_lieu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        nom = (TextView)findViewById(R.id.nomLieuTxt);
        adresse = (TextView)findViewById(R.id.adresseLieuTxt);
        type = (TextView)findViewById(R.id.typeLieuTxt);
        description = (TextView)findViewById(R.id.descriptionLieuTxt);
        contact = (TextView)findViewById(R.id.contactLieuTxt);
        site = (TextView)findViewById(R.id.siteLieuTxt);
        imagelieu = (ImageView) findViewById(R.id.imageLieuLire);
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
            contact.setText(lieu.getTelephone());
            site.setText(lieu.getSiteweb());
            Glide.with(this).load(ApiLinks.API_IMAGES_LIEUX_URL+lieu.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imagelieu);


        }


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
