package com.codeeatersteam.dinam.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeeatersteam.dinam.daos.DomainesOffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_UPDATED_AT;

/**
 * Created by pondikpa on 16/07/17.
 */

public class RecupererDomaines extends AsyncTask<Void,Void,Void> {
    Context ctx;

    public RecupererDomaines(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        StringRequest request = new StringRequest(Request.Method.GET, ApiLinks.API_RECUPERER_DOMAINES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("domaines");
                    DbBuilder builder = new DbBuilder(ctx);
                    SQLiteDatabase database = builder.getWritableDatabase();
                    DomainesOffresDao domaine ;
                    for (int i=0;i<=array.length();i++){
                        JSONObject objetfinal = array.getJSONObject(i);
                        domaine=new DomainesOffresDao(objetfinal.getInt(COLUMN_DOMAINE_ID),
                                objetfinal.getString(COLUMN_DOMAINE_NOM),
                                objetfinal.getString(COLUMN_DOMAINE_DESCRIPTION),
                                objetfinal.getString(COLUMN_DOMAINE_CREATED_AT),
                                objetfinal.getString(COLUMN_DOMAINE_UPDATED_AT));

                        builder.enregistrerDomainesOffreDepuisApi(domaine.getId(),
                                domaine.getNom(),
                                domaine.getDescription(),
                                domaine.getCreated_at(),
                                domaine.getUpdated_at(),
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
