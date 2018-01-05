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
import com.codeeatersteam.dinam.daos.LieuxDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
import static com.codeeatersteam.dinam.kernel.Core.LISTES_LIEUX_EN_LOCAL_NON_SYNCHRONISES;
import static com.codeeatersteam.dinam.net.ApiLinks.API_ENVOIE_LIEUX_URL;

/**
 * Created by pondikpa on 21/06/17.
 */

public class EnvoyerLieux extends AsyncTask<Void,Void,Void> {
    Context context;

    public EnvoyerLieux(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        final DbBuilder dbBuilder = new DbBuilder(context);
        SQLiteDatabase readerDatabase = dbBuilder.getReadableDatabase();
        SQLiteDatabase writerDatabase = dbBuilder.getWritableDatabase();
        Cursor cursor = readerDatabase.rawQuery(LISTES_LIEUX_EN_LOCAL_NON_SYNCHRONISES, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                final LieuxDao lieux = new LieuxDao(cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ID)),

                        cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ETAT)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_TYPE_LIEU)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_NOM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_TELEPHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_ADRESSE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_SITE_WEB)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_CREATED_AT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_UPDATED_AT)));


                StringRequest stringRequest = new StringRequest(Request.Method.POST, API_ENVOIE_LIEUX_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject object = new JSONObject(response);

                            if (object.getBoolean("status")) {
                                dbBuilder.updateLieuSync(String.valueOf(lieux.getId()));
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
                        params.put(COLUMN_LIEU_NOM, lieux.getNom());
                        params.put(COLUMN_LIEU_ADRESSE, lieux.getAdresse());
                        params.put(COLUMN_LIEU_TYPE_LIEU, String.valueOf(lieux.getTypelieu()));
                        params.put(COLUMN_LIEU_IMAGE, lieux.getImage());
                        params.put(COLUMN_LIEU_DESCRIPTION, lieux.getDescription());
                        params.put("imagenom", FonctionsUtiles.dateActuelle()+lieux.getNom()+ PreferencesUtilisateur.getInstance(context).getId()+".jpg");
                        params.put(COLUMN_LIEU_TELEPHONE, lieux.getTelephone());
                        params.put(COLUMN_LIEU_SITE_WEB, lieux.getSiteweb());
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
