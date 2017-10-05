package com.codeeatersteam.dinam.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.DbBuilder;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.Daybetween;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.controlchampsvide;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.verifierEmail;

public class ProposerOffre extends AppCompatActivity {
    Button debutDateAudience;
    Button finDateAudience;
    Button enregistrerOffre;
    EditText telephone,email,description,salaireAPourvoir,posteAPourvoir;
    Spinner domaine,type,diplome;
    int year_x,month_x,day_x;
    static final int DIALOG_ID_DEBUT = 1;
    static final int DIALOG_ID_FIN = 2;
    String datedebut,datefin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_proposer_offre);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        debutDateAudience = (Button)findViewById(R.id.btnDateAudience);
        finDateAudience = (Button)findViewById(R.id.btnDateCloture);
        enregistrerOffre = (Button)findViewById(R.id.btnEnregistrerOffre);
        posteAPourvoir = (EditText)findViewById(R.id.posteNewOffre);
        salaireAPourvoir =(EditText)findViewById(R.id.salaireNewoffre);
        description = (EditText)findViewById(R.id.descriptionNewOffre);
        telephone = (EditText)findViewById(R.id.telephoneNewOffre);
        email = (EditText)findViewById(R.id.emailNewOffre);
        domaine = (Spinner) findViewById(R.id.domaine_offre_spinner);
        type = (Spinner)findViewById(R.id.type_offre_spinner) ;
        diplome=(Spinner)findViewById(R.id.diplome_offre_spinner) ;
        final DbBuilder dbBuilder = new DbBuilder(this);

        //on ajoute les domaines
        final ArrayList<String> listeDom  = dbBuilder.listeDesDomaines();
        ArrayAdapter<String> adapterdomaine= new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinner_txt,listeDom);
        domaine.setAdapter(adapterdomaine);
        domaine.setSelection(domaine.getCount()-1);

        //on ajoute les types d'offres
        final ArrayList<String> listeTypeOffres  = dbBuilder.listeDesTypesOffres();
        ArrayAdapter<String> adapterTO= new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinner_txt,listeTypeOffres);
        type.setAdapter(adapterTO);
        type.setSelection(type.getCount()-1);

        //on ajoute les diplomes
        final ArrayList<String> listeDiplomes = dbBuilder.listeDesDiplomes();
        ArrayAdapter<String> adapterDiplomes= new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinner_txt,listeDiplomes);
        diplome.setAdapter(adapterDiplomes);
        diplome.setSelection(diplome.getCount()-1);

        debutDateAudience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_DEBUT);

            }
        });

        finDateAudience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_FIN);

            }
        });



        enregistrerOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierDonnees() && verifierDates()) {

                    if (Daybetween(datefin, datedebut, "yyyy-mm-dd") >= 0) {

                        SweetAlertDialog dialog;
                        dialog = new SweetAlertDialog(v.getContext(),SweetAlertDialog.ERROR_TYPE);
                        dialog.setTitle("Oops");
                        dialog.setTitleText("La date de cloture ne peut etre \n anterieur a la date d'audience");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                    } else {

                        DbBuilder builder = new DbBuilder(v.getContext());
                        SQLiteDatabase database = builder.getWritableDatabase();
                        builder.enregistrerOffreEnLocal(builder.idDernierOffre() + 1, posteAPourvoir.getText().toString(),
                                salaireAPourvoir.getText().toString(), datedebut, datefin, description.getText().toString(),
                                email.getText().toString(), telephone.getText().toString(),
                                dbBuilder.idTypeOffreFromNom(type.getSelectedItem().toString()),
                                dbBuilder.idDomaineFromNom(domaine.getSelectedItem().toString()),
                                dbBuilder.idDiplomeFromNom(diplome.getSelectedItem().toString()), database);
                        SweetAlertDialog sDialog;
                        sDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Offre enregistre!")
                                .setContentText("Nous traiterons votre offre \n tres prochainement");

                        sDialog.setCanceledOnTouchOutside(false);
                        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                ouvrirActivite(sweetAlertDialog.getContext(), Conteneur.class);
                            }
                        });
                        sDialog.show();


                    }
                }

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==DIALOG_ID_DEBUT){
            Calendar c = Calendar.getInstance();
            year_x = c.get(Calendar.YEAR);
            month_x = c.get(Calendar.MONTH);
            day_x = c.get(Calendar.DAY_OF_MONTH);
          DatePickerDialog datePickerDialog = new DatePickerDialog(this,dpickerListenerdebut,year_x,month_x,day_x);
            datePickerDialog.setCanceledOnTouchOutside(false);
            return  datePickerDialog;

        }
        if (id==DIALOG_ID_FIN){
            Calendar c = Calendar.getInstance();
            year_x = c.get(Calendar.YEAR);
            month_x = c.get(Calendar.MONTH);
            day_x = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,dpickerListenerfin,year_x,month_x,day_x);

            datePickerDialog.setCanceledOnTouchOutside(false);
            return  datePickerDialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListenerdebut = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x =year;
            month_x = month;
            day_x = dayOfMonth;
            datedebut = year_x+"-"+month_x+"-"+day_x;
            debutDateAudience.setText("Date de debut: "+datedebut);
        }
    };
    private DatePickerDialog.OnDateSetListener dpickerListenerfin = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x =year;
            month_x = month;
            day_x = dayOfMonth;
            datefin = year_x+"-"+month_x+"-"+day_x;
            finDateAudience.setText("Date de fin: "+datefin);
        }
    };

    private boolean  verifierDonnees(){
        if ( !controlchampsvide(posteAPourvoir)  && !controlchampsvide(salaireAPourvoir) && !controlchampsvide(description)
                 && !controlchampsvide(telephone) && !verifierEmail(email)){
            return true;
        }
        return false;
    }


    private boolean verifierDates(){
        if (datedebut !=null && datefin !=null){
            return  true;
        }else {

            SweetAlertDialog dialog;
            dialog = new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE);
            dialog.setTitle("Oops");
            dialog.setTitleText("Veuillez renseigner les dates s'il vous plait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            return false;

        }

    }




}
