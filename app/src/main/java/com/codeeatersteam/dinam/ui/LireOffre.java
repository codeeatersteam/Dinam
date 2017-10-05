package com.codeeatersteam.dinam.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_AUDIENCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_CLOTURE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DIPLOME_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DOMAINE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_EMAIL;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_POSTE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SALAIRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TYPE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_OFFRES;

public class LireOffre extends AppCompatActivity {
    TextView description,poste,salaire,contact,date,domaine,diplome,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lire_offre);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        description = (TextView)findViewById(R.id.descriptionlectureoffre);
        poste= (TextView)findViewById(R.id.postelectureoffretxt);
        contact= (TextView)findViewById(R.id.contactlectureoffretxt);
        salaire= (TextView)findViewById(R.id.salairelecureoffretxt);
        date= (TextView)findViewById(R.id.datelectureoffretxt);
        domaine= (TextView)findViewById(R.id.domainelectureoffre);
        diplome= (TextView)findViewById(R.id.diplomelectureoffre);
        type= (TextView)findViewById(R.id.typelectureoffre);
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
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_EMAIL)));

            poste.setText(offre.getPoste());
            description.setText(offre.getDescription());
            contact.setText(offre.getTelephone() + "\n "+offre.getEmail());
            salaire.setText(offre.getSalaire());
            date.setText("Du "+offre.getDate_audience()+" au "+offre.getDate_fin());
            domaine.setText(builder.nomDomaineFromId(offre.getDomaine()));
            diplome.setText(builder.nomDiplomeFromId(offre.getDiplome()));
            type.setText(builder.nomTypeOffreFromId(offre.getTypeoffre()));

        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
