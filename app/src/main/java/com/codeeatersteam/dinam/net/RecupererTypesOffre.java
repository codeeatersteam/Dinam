package com.codeeatersteam.dinam.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeeatersteam.dinam.daos.TypeOffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_UPDATED_AT;

/**
 * Created by pondikpa on 16/07/17.
 */

public class RecupererTypesOffre extends AsyncTask<Void,Void,Void> {
    Context ctx;

    public RecupererTypesOffre(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        StringRequest request = new StringRequest(Request.Method.GET, ApiLinks.API_RECUPERER_TYPESOFFRES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                TypeOffresDao typeOffre;
                try {
                    jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("typeoffres");
                    DbBuilder builder = new DbBuilder(ctx);
                    SQLiteDatabase database = builder.getWritableDatabase();
                    for (int i=0;i<=array.length();i++) {
                        JSONObject objetfinal = array.getJSONObject(i);
                        typeOffre = new TypeOffresDao(objetfinal.getInt(COLUMN_TYPE_OFFRE_ID),
                                objetfinal.getString(COLUMN_TYPE_OFFRE_NOM),
                                objetfinal.getString(COLUMN_TYPE_OFFRE_DESCRIPTION),
                                objetfinal.getString(COLUMN_TYPE_OFFRE_CREATED_AT),
                                objetfinal.getString(COLUMN_TYPE_OFFRE_UPDATED_AT));


                        builder.enregistrerTypeOffreDepuisApi(typeOffre.getId(),
                                typeOffre.getNom(),
                                typeOffre.getDescription(),
                                typeOffre.getCreated_at(),
                                typeOffre.getUpdated_at(),
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
