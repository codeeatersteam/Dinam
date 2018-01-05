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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.kernel.RequestHandler;
import com.codeeatersteam.dinam.net.ApiLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.Daybetween;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ImageTostring;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.controlchampsvide;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.dateActuelle;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.userConnected;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.verifierEmail;

public class Register extends AppCompatActivity {
    private static final int IMG_REQUEST = 1;
    EditText nom,prenoms,email,telephone,password;
    Button imageBtn, dateBtn, saveBtn;
    ImageView imageUpload;
    Spinner sexe,diplome;
    int year_x,month_x,day_x;
    static final int DIALOG_ID_NAISSANCE = 1;
    String datenaissance,dateactuel;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (userConnected(this)){
            ouvrirActivite(this,ConteneurPrincipal.class);
        }

        nom = (EditText) findViewById(R.id.nomRegister);
        prenoms = (EditText) findViewById(R.id.prenomsRegister);
        email= (EditText) findViewById(R.id.emailRegister);
        telephone= (EditText) findViewById(R.id.telephoneRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        sexe = (Spinner) findViewById(R.id.sexe_Register_spinner);
        diplome=(Spinner)findViewById(R.id.diplome_Register_spinner) ;
        dateBtn = (Button) findViewById(R.id.btnRegisterDateNaissance);
        saveBtn = (Button) findViewById(R.id.btnRegisterUser);
        imageBtn  = (Button) findViewById(R.id.btnRegisterChooseImage);
        imageUpload = (ImageView)findViewById(R.id.imageRegister);

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();


            }
        });

        final DbBuilder dbBuilder = new DbBuilder(this);

        //on ajoute les diplomes
        final ArrayList<String> listeDiplomes = dbBuilder.listeDesDiplomes();
        ArrayAdapter<String> adapterDiplomes= new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinner_txt,listeDiplomes);
        diplome.setAdapter(adapterDiplomes);
        diplome.setSelection(diplome.getCount()-1);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_NAISSANCE);

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (verifierDonnees()){
                    StringRequest request = new StringRequest(Request.Method.POST, ApiLinks.API_REGISTER_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject objetfinal = new JSONObject(response);
//
//                                JSONArray array = jsonObject.getJSONArray("user");
//                                DbBuilder builder = new DbBuilder(v.getContext());
//                                SQLiteDatabase database = builder.getWritableDatabase();
//
//                                for (int i = 0; i <= array.length(); i++) {
//                                    JSONObject objetfinal = array.getJSONObject(i);
                                    PreferencesUtilisateur.getInstance(v.getContext()).setId(objetfinal.getString("id"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setNom(objetfinal.getString("name"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setPrenoms(objetfinal.getString("prenoms"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setDatenaissance(objetfinal.getString("datenaissance"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setTelephone(objetfinal.getString("telephone"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setEmail(objetfinal.getString("email"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setMondiplome(objetfinal.getString("id_diplomes"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setMonimage(objetfinal.getString("image"));
                                PreferencesUtilisateur.getInstance(v.getContext()).setSexe(objetfinal.getString("sexe"));

                                    try {
                                        Glide.with(v.getContext()).load(ApiLinks.API_IMAGES_USERS_URL+objetfinal.getString("image") ).downloadOnly(100, 100);
                                    }catch (IllegalArgumentException e){

                                    }
                                Toasty.success(v.getContext(), "Connexion réussie", Toast.LENGTH_LONG, true).show();

                                    ouvrirActivite(v.getContext(),ConteneurPrincipal.class);


                               // }

                            } catch (JSONException e) {
                                Toasty.error(v.getContext(), "Cet utilisateur existe déjà.", Toast.LENGTH_LONG, true).show();

                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(v.getContext(), "Le serveur n'a pas repondu, verifiez votre connexion.", Toast.LENGTH_LONG, true).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("name", nom.getText().toString());
                            params.put("prenoms", prenoms.getText().toString());
                            params.put("email", email.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("datenaissance", datenaissance);
                            params.put("telephone", telephone.getText().toString());
                            params.put("sexe", sexe.getSelectedItem().toString());
                            params.put("id_diplomes", String.valueOf(dbBuilder.idDiplomeFromNom(diplome.getSelectedItem().toString())));
                            params.put("id_roles", "1");
                            params.put("image", FonctionsUtiles.ImageTostring(bitmap));
                            params.put("imagenom",dateActuelle()+email.getText().toString()+".jpeg");
                            

                            return params;

                        }
                    };
                    RequestHandler.getInstance(v.getContext()).addToRequestQueue(request);

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
                imageUpload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==DIALOG_ID_NAISSANCE){
            Calendar c = Calendar.getInstance();
            year_x = c.get(Calendar.YEAR);
            month_x = c.get(Calendar.MONTH);
            day_x = c.get(Calendar.DAY_OF_MONTH);
            dateactuel= year_x+"-"+month_x+"-"+day_x;
            datenaissance = year_x+"-"+month_x+"-"+day_x;
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
            datenaissance = year_x+"-"+month_x+"-"+day_x;
            dateBtn.setText("Date de naissance: "+datenaissance);
        }
    };

    private boolean  verifierDonnees(){
        if ( !controlchampsvide(nom)  && !controlchampsvide(prenoms) && !controlchampsvide(password)
                && !controlchampsvide(telephone) && !verifierEmail(email) ) {
            if (datenaissance!=null) {



                if (Daybetween(dateactuel, datenaissance, "yyyy-mm-dd") < 6205) {
                    return true;
                } else {
                    Toasty.error(this, "Vous devez avoir au moins 17 ans.", Toast.LENGTH_LONG, true).show();
                    return false;

                }
            } else {
                Toasty.error(this, "Veuillez renseigner votre date de naissance.", Toast.LENGTH_LONG, true).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (userConnected(this)) {
            ouvrirActivite(this, ConteneurPrincipal.class);
        }
    }
}
