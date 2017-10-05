package com.codeeatersteam.dinam.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.codeeatersteam.dinam.daos.EvenementsDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DATE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_IMAGE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_LIEU;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_SITE_WEB;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TYPE_EVENEMENT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_UPDATED_AT;

/**
 * Created by pondikpa on 15/06/17.
 */

public class RecupererEvenements extends AsyncTask<Void,Void,Void>{
    Context ctx;

    public RecupererEvenements(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        StringRequest request = new StringRequest(Request.Method.GET, ApiLinks.API_RECUPERER_EVENEMENTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("evenements");
                    DbBuilder builder = new DbBuilder(ctx);
                    SQLiteDatabase database = builder.getWritableDatabase();
                    EvenementsDao evenement ;
                    for (int i=0;i<=array.length();i++){
                        JSONObject objetfinal = array.getJSONObject(i);
                        evenement=new EvenementsDao(objetfinal.getInt(COLUMN_EVENEMENT_ID),
                                objetfinal.getInt(COLUMN_EVENEMENT_ETAT),
                                objetfinal.getInt(COLUMN_EVENEMENT_TYPE_EVENEMENT),
                                objetfinal.getString(COLUMN_EVENEMENT_NOM),
                                objetfinal.getString(COLUMN_EVENEMENT_TELEPHONE),
                                objetfinal.getString(COLUMN_EVENEMENT_IMAGE),
                                objetfinal.getString(COLUMN_EVENEMENT_DATE),
                                objetfinal.getString(COLUMN_EVENEMENT_DESCRIPTION),
                                objetfinal.getString(COLUMN_EVENEMENT_LIEU),
                                objetfinal.getString(COLUMN_EVENEMENT_SITE_WEB),
                                objetfinal.getString(COLUMN_EVENEMENT_CREATED_AT),
                                objetfinal.getString(COLUMN_EVENEMENT_UPDATED_AT));

                        builder.enregistrerEvenementDepuisApi(evenement.getId(),
                                evenement.getEtat(),
                                evenement.getTypeevenement(),
                                evenement.getNom(),
                                evenement.getTelephone(),
                                evenement.getImage(),
                                evenement.getDate_evenement(),
                                evenement.getDescription(),
                                evenement.getLieu(),
                                evenement.getSiteweb(),
                                evenement.getCreated_at(),
                                evenement.getUpdated_at(),
                                database);
                        try {
                            Glide.with(ctx).load(ApiLinks.API_IMAGES_EVENEMENTS_URL + objetfinal.getString(COLUMN_EVENEMENT_IMAGE)).downloadOnly(100, 100);
                        }catch (IllegalArgumentException e){

                        }



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
