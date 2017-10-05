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
import com.codeeatersteam.dinam.daos.LieuxDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by pondikpa on 15/06/17.
 */

public class RecupererLieux  extends AsyncTask<Void,Void,Void> {
    Context ctx;

    public RecupererLieux(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        StringRequest request = new StringRequest(Request.Method.GET, ApiLinks.API_RECUPERER_LIEUX_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("lieux");
                    DbBuilder builder = new DbBuilder(ctx);
                    SQLiteDatabase database = builder.getWritableDatabase();
                    LieuxDao lieu ;
                    for (int i=0;i<=array.length();i++){
                        JSONObject objetfinal = array.getJSONObject(i);
                        lieu=new LieuxDao(objetfinal.getInt(COLUMN_LIEU_ID),
                                objetfinal.getInt(COLUMN_LIEU_ETAT),
                                objetfinal.getInt(COLUMN_LIEU_TYPE_LIEU),
                                objetfinal.getString(COLUMN_LIEU_NOM),
                                objetfinal.getString(COLUMN_LIEU_TELEPHONE),
                                objetfinal.getString(COLUMN_LIEU_ADRESSE),
                                objetfinal.getString(COLUMN_LIEU_DESCRIPTION),
                                objetfinal.getString(COLUMN_LIEU_SITE_WEB),
                                objetfinal.getString(COLUMN_LIEU_IMAGE),
                                objetfinal.getString(COLUMN_LIEU_CREATED_AT),
                                objetfinal.getString(COLUMN_LIEU_UPDATED_AT));

                        builder.enregistrerLieuDepuisApi(lieu.getId(),
                                lieu.getEtat(),
                                lieu.getTypelieu(),
                                lieu.getNom(),
                                lieu.getTelephone(),
                                lieu.getAdresse(),
                                lieu.getDescription(),
                                lieu.getSiteweb(),
                                lieu.getImage(),
                                lieu.getCreated_at(),
                                lieu.getUpdated_at(),
                                database);
                        try {
                            Glide.with(ctx).load(ApiLinks.API_IMAGES_LIEUX_URL + objetfinal.getString(COLUMN_LIEU_IMAGE)).downloadOnly(100, 100);
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
