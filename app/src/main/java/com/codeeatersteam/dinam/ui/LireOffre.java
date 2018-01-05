package com.codeeatersteam.dinam.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
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
import com.luseen.autolinklibrary.AutoLinkTextView;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_AUDIENCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_CLOTURE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DIPLOME_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DOMAINE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_EMAIL;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_POSTE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SALAIRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SOURCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TYPE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_OFFRES;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.autoLinksMode;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.isConnectedInternet;

public class LireOffre extends AppCompatActivity {
    TextView poste,salaire,date,domaine,diplome,type,source;
    AutoLinkTextView contact,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lire_offre);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        description = (AutoLinkTextView) findViewById(R.id.descriptionlectureoffre);
        poste= (TextView)findViewById(R.id.postelectureoffretxt);
        contact= (AutoLinkTextView) findViewById(R.id.contactlectureoffretxt);
        salaire= (TextView)findViewById(R.id.salairelecureoffretxt);
        date= (TextView)findViewById(R.id.datelectureoffretxt);
        domaine= (TextView)findViewById(R.id.domainelectureoffre);
        diplome= (TextView)findViewById(R.id.diplomelectureoffre);
        type= (TextView)findViewById(R.id.typelectureoffre);
        source= (TextView)findViewById(R.id.sourceoffretxt);
        autoLinksMode(this,description);
        autoLinksMode(this,contact);
        DbBuilder builder = new DbBuilder(this);
        SQLiteDatabase database = builder.getReadableDatabase();
        System.out.println(String.valueOf(this.getIntent().getExtras().getString("iden")));
        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_OFFRES+" WHERE "+COLUMN_OFFRE_ID+"" +
                " ="+this.getIntent().getExtras().getString("iden"),null);
        while (cursor.moveToNext()){


            OffresDao offre = new OffresDao(cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_ID)),

                    cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_DOMAINE_OFFRE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_TYPE_OFFRE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_DIPLOME_OFFRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_SALAIRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_TELEPHONE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_POSTE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_DATE_AUDIENCE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_DATE_CLOTURE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_SOURCE)));

            poste.setText(offre.getPoste());
            description.setText(offre.getDescription());
            contact.setText(offre.getTelephone() + "\n "+offre.getEmail());
            salaire.setText(offre.getSalaire());
            date.setText("Du "+offre.getDate_audience()+" au "+offre.getDate_fin());
            domaine.setText(builder.nomDomaineFromId(offre.getDomaine()));
            diplome.setText(builder.nomDiplomeFromId(offre.getDiplome()));
            type.setText(builder.nomTypeOffreFromId(offre.getTypeoffre()));
            source.setText(offre.getSource());

        }

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
