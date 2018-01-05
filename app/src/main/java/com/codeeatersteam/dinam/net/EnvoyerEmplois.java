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
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
import static com.codeeatersteam.dinam.kernel.Core.LISTES_OFFRES_EN_LOCAL_NON_SYNCHRONISES;
import static com.codeeatersteam.dinam.net.ApiLinks.API_ENVOIE_OFFRES_URL;

/**
 * Created by pondikpa on 21/06/17.
 */

public class EnvoyerEmplois extends AsyncTask<Void,Void,Void> {
    Context context;

    public EnvoyerEmplois(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        final DbBuilder dbBuilder = new DbBuilder(context);
        SQLiteDatabase readerDatabase = dbBuilder.getReadableDatabase();
        SQLiteDatabase writerDatabase = dbBuilder.getWritableDatabase();
        Cursor cursor = readerDatabase.rawQuery(LISTES_OFFRES_EN_LOCAL_NON_SYNCHRONISES, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                final OffresDao offre = new OffresDao(cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_ID)),

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


                StringRequest stringRequest = new StringRequest(Request.Method.POST, API_ENVOIE_OFFRES_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject object = new JSONObject(response);

                            if (object.getBoolean("status")) {
                                dbBuilder.updateOffreSync(String.valueOf(offre.getId()));
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
                        params.put(COLUMN_OFFRE_POSTE, offre.getPoste());
                        params.put(COLUMN_OFFRE_SALAIRE, offre.getSalaire());
                        params.put(COLUMN_OFFRE_EMAIL, offre.getEmail());
                        params.put(COLUMN_OFFRE_DATE_AUDIENCE, offre.getDate_audience());
                        params.put(COLUMN_OFFRE_DATE_CLOTURE, offre.getDate_fin());
                        params.put(COLUMN_OFFRE_DESCRIPTION, offre.getDescription());
                        params.put(COLUMN_OFFRE_DOMAINE_OFFRE, String.valueOf(offre.getDomaine()));
                        params.put(COLUMN_OFFRE_DIPLOME_OFFRE, String.valueOf(offre.getDiplome()));
                        params.put(COLUMN_OFFRE_TYPE_OFFRE, String.valueOf(offre.getTypeoffre()));
                        params.put(COLUMN_OFFRE_TELEPHONE, offre.getTelephone());
                        params.put(COLUMN_OFFRE_SOURCE, offre.getSource());
                        params.put("id_users", "1");


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
