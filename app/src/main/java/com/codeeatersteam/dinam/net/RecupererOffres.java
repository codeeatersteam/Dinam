package com.codeeatersteam.dinam.net;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TYPE_OFFRE;
import static com.codeeatersteam.dinam.net.ApiLinks.API_RECUPERER_OFFRES_URL;

/**
 * Created by pondikpa on 15/06/17.
 */

public class RecupererOffres extends AsyncTask<Void,Void,Void> {
    Context ctx;

    public RecupererOffres(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        StringRequest request = new StringRequest(Request.Method.GET, API_RECUPERER_OFFRES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                JSONArray array = jsonObject.getJSONArray("offres");
                DbBuilder builder = new DbBuilder(ctx);
                SQLiteDatabase database = builder.getWritableDatabase();
                    OffresDao offre ;
                    for (int i=0;i<=array.length();i++) {
                        JSONObject objetfinal = array.getJSONObject(i);
                        offre = new OffresDao(objetfinal.getInt(COLUMN_OFFRE_ID),
                                objetfinal.getInt(COLUMN_OFFRE_DOMAINE_OFFRE),
                                objetfinal.getInt(COLUMN_OFFRE_TYPE_OFFRE),
                                objetfinal.getInt(COLUMN_OFFRE_DIPLOME_OFFRE),
                                objetfinal.getString(COLUMN_OFFRE_SALAIRE),
                                objetfinal.getString(COLUMN_OFFRE_DESCRIPTION),
                                objetfinal.getString(COLUMN_OFFRE_TELEPHONE),
                                objetfinal.getString(COLUMN_OFFRE_POSTE),
                                objetfinal.getString(COLUMN_OFFRE_DATE_AUDIENCE),
                                objetfinal.getString(COLUMN_OFFRE_DATE_CLOTURE),
                                objetfinal.getString(COLUMN_OFFRE_EMAIL));

                        builder.enregistrerOffreDepuisApi(offre.getId(),
                                offre.getPoste(),
                                offre.getSalaire(),
                                offre.getDate_audience(),
                                offre.getDate_fin(),
                                offre.getDescription(),
                                offre.getEmail(),
                                offre.getTelephone(),
                                offre.getTypeoffre(),
                                offre.getDomaine(),
                                offre.getDiplome(),
                                database);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestHandler.getInstance(ctx).addToRequestQueue(request);
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
