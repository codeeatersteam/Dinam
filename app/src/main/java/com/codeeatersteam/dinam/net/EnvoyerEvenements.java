package com.codeeatersteam.dinam.net;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeeatersteam.dinam.daos.EvenementsDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_EVENEMENTS_EN_LOCAL_NON_SYNCHRONISES;
import static com.codeeatersteam.dinam.net.ApiLinks.API_ENVOIE_EVENEMENTS_URL;

/**
 * Created by pondikpa on 21/06/17.
 */

public class EnvoyerEvenements extends AsyncTask<Void,Void,Void> {
    Context context;

    public EnvoyerEvenements(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        final DbBuilder dbBuilder = new DbBuilder(context);
        SQLiteDatabase readerDatabase = dbBuilder.getReadableDatabase();
        SQLiteDatabase writerDatabase = dbBuilder.getWritableDatabase();
        Cursor cursor = readerDatabase.rawQuery(LISTES_EVENEMENTS_EN_LOCAL_NON_SYNCHRONISES, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                final EvenementsDao evenements = new EvenementsDao(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ID)),

                        cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ETAT)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_TYPE_EVENEMENT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_NOM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_TELEPHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_LIEU)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_CREATED_AT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_UPDATED_AT)));


                StringRequest stringRequest = new StringRequest(Request.Method.POST, API_ENVOIE_EVENEMENTS_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject object = new JSONObject(response);

                            if (object.getBoolean("status")) {
                                dbBuilder.updateEvenementSync(String.valueOf(evenements.getId()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put(COLUMN_EVENEMENT_NOM, evenements.getNom());
                        params.put(COLUMN_EVENEMENT_LIEU, evenements.getLieu());
                        params.put(COLUMN_EVENEMENT_TYPE_EVENEMENT, String.valueOf(evenements.getTypeevenement()));
                        params.put(COLUMN_EVENEMENT_DATE, evenements.getDate_evenement());
                        params.put(COLUMN_EVENEMENT_IMAGE, evenements.getImage());
                        params.put(COLUMN_EVENEMENT_DESCRIPTION, evenements.getDescription());
                        params.put("imagenom", FonctionsUtiles.dateActuelle()+evenements.getNom()+ PreferencesUtilisateur.getInstance(context).getId()+".jpg");
                        params.put(COLUMN_EVENEMENT_TELEPHONE, evenements.getTelephone());
                        params.put("id_users",  PreferencesUtilisateur.getInstance(context).getId());


                        return params;
                    }
                };

                RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
