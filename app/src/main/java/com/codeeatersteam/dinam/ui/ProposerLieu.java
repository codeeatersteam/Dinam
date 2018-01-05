package com.codeeatersteam.dinam.ui;

import android.Manifest;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.DbBuilder;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.Daybetween;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ImageTostring;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.controlchampsvide;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.dateActuelle;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;

public class ProposerLieu extends AppCompatActivity {
    ImageView imageView;
    Spinner typelieu;
    EditText nom,adresse,description,telephone,siteweb;
    Button photoBtn,saveBtn;
    Bitmap bitmap;
    private static final int IMG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposer_lieu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView =(ImageView)findViewById(R.id.imageViewLieu);
        typelieu=(Spinner)findViewById(R.id.type_lieu_spinner);
        nom=(EditText)findViewById(R.id.nomNewLieu);
        adresse=(EditText)findViewById(R.id.adressNewLieu);
        description=(EditText)findViewById(R.id.descriptionNewLieu);
        telephone=(EditText)findViewById(R.id.telephoneNewLieu);
        siteweb=(EditText)findViewById(R.id.urlNewLieu);
        photoBtn=(Button) findViewById(R.id.btnChoisirImageLieu);
        saveBtn=(Button) findViewById(R.id.btnEnregistrerLieu);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        final DbBuilder dbBuilder = new DbBuilder(this);

        //on ajoute les type de lieux
        final ArrayList<String> listeTypeLieux = dbBuilder.listeDesTypesLieux();
        ArrayAdapter<String> adapterLieux= new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinner_txt,listeTypeLieux);
        typelieu.setAdapter(adapterLieux);
        typelieu.setSelection(typelieu.getCount()-1);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierDonnees()){
                    SQLiteDatabase database = dbBuilder.getWritableDatabase();
                    dbBuilder.enregistrerLieuEnLocal(dbBuilder.idDernierLieu()+10000,0,dbBuilder.idTypeLieuFromNom(typelieu.getSelectedItem().toString()),nom.getText().toString(),
                            telephone.getText().toString(),adresse.getText().toString(),description.getText().toString(),siteweb.getText().toString(),ImageTostring(bitmap),dateActuelle(),dateActuelle(),database);
                    SweetAlertDialog sDialog;
                    sDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Lieu enregistré!")
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

    private boolean  verifierDonnees(){
        if ( !controlchampsvide(nom)  && !controlchampsvide(adresse) && !controlchampsvide(telephone)
                && !controlchampsvide(telephone)   && !controlchampsvide(siteweb)  ) {
          return  true;
        }
        return false;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
