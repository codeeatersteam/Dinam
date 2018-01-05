package com.codeeatersteam.dinam.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.Core;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.Daybetween;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.controlchampsvide;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.verifierEmail;

public class ProposerEvenement extends AppCompatActivity {

    ImageView imageView;
    Spinner typeevenement;
    EditText nom,lieu,description,telephone;
    Button photoBtn,dateBtn,saveBtn;
    int year_x,month_x,day_x;
    static final int DIALOG_ID_DATE = 1;
    String dateevenement,dateactuel;
    Bitmap bitmap;
    private static final int IMG_REQUEST = 1;

    boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvel_evenement);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        imageView = (ImageView)findViewById(R.id.imageViewEvenement);
        nom = (EditText)findViewById(R.id.nomNewEvenement);
        lieu= (EditText)findViewById(R.id.lieuNewEvenement);
        description= (EditText)findViewById(R.id.descriptionNewEvenement);
        telephone= (EditText)findViewById(R.id.telephoneNewEvenenement);
        typeevenement= (Spinner) findViewById(R.id.type_evenement_spinner);
        photoBtn= (Button) findViewById(R.id.btnChoisirImageEvenement);
        dateBtn= (Button) findViewById(R.id.btnDateEvenement);
        saveBtn= (Button) findViewById(R.id.btnEnregistrerEvenement);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_DATE);
            }
        });

        final DbBuilder dbBuilder = new DbBuilder(this);

        //on ajoute les type d'evenements
        final ArrayList<String> listeTypeEvenements = dbBuilder.listeDesTypesEvenements();
        ArrayAdapter<String> adapterEvenements= new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinner_txt,listeTypeEvenements);
        typeevenement.setAdapter(adapterEvenements);
        typeevenement.setSelection(typeevenement.getCount()-1);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierDonnees()){
                    SQLiteDatabase database = dbBuilder.getWritableDatabase();

                    dbBuilder.enregistrerEvenementEnLocal(dbBuilder.idDernierEvenement()+10000, 0,dbBuilder.idTypeEvenementFromNom(typeevenement.getSelectedItem().toString()),
                           nom.getText().toString(),telephone.getText().toString(), FonctionsUtiles.ImageTostring(bitmap),dateevenement,description.getText().toString(),lieu.getText().toString(),
                            dateactuel,dateactuel,database);

                    SweetAlertDialog sDialog;
                    sDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Evenement enregistré!")
                            .setContentText("Nous traiterons ce cas \n très prochainement");

                    sDialog.setCanceledOnTouchOutside(false);
                    sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            ouvrirActivite(sweetAlertDialog.getContext(), ConteneurPrincipal.class);
                        }
                    });
                    sDialog.show();

                }
            }
        });







//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isImageFitToScreen) {
//                    isImageFitToScreen=false;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
//                    imageView.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreen=true;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//            }
//        });
    }


    private void selectImageFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                }, 10);
            }

        }else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,IMG_REQUEST);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==DIALOG_ID_DATE){
            Calendar c = Calendar.getInstance();
            year_x = c.get(Calendar.YEAR);
            month_x = c.get(Calendar.MONTH);
            day_x = c.get(Calendar.DAY_OF_MONTH);
            dateactuel= year_x+"-"+month_x+"-"+day_x;
            dateevenement = year_x+"-"+month_x+"-"+day_x;
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,dpickerListenerNaissance,year_x,month_x,day_x);
            datePickerDialog.setCanceledOnTouchOutside(false);
            return  datePickerDialog;

        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListenerNaissance= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x =year;
            month_x = month;
            day_x = dayOfMonth;
            dateevenement = year_x+"-"+month_x+"-"+day_x;
            dateBtn.setText("Date de l'evenement: "+dateevenement);
        }
    };

    private boolean  verifierDonnees(){
        if ( !controlchampsvide(nom)  && !controlchampsvide(lieu) && !controlchampsvide(telephone)
                && !controlchampsvide(telephone)  ) {
            if (dateevenement!=null) {



                if (Daybetween(dateactuel, dateevenement, "yyyy-mm-dd") > 0) {
                    return true;
                } else {
                    Toasty.error(this, "Cet evenement est déjà passé.", Toast.LENGTH_LONG, true).show();
                    return false;

                }
            } else {
                Toasty.error(this, "Veuillez renseigner la date de l'evenement.", Toast.LENGTH_LONG, true).show();
                return false;
            }
        }
        return false;
    }
    

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
