package com.codeeatersteam.dinam.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeeatersteam.dinam.daos.DiplomesDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_UPDATED_AT;

/**
 * Created by pondikpa on 18/07/17.
 */

public class RecupererDiplomes extends AsyncTask {
    Context ctx;

    public RecupererDiplomes(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        StringRequest request = new StringRequest(Request.Method.GET, ApiLinks.API_RECUPERER_DIPLOMES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                DiplomesDao diplome;
                try {
                    jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("diplomes");
                    DbBuilder builder = new DbBuilder(ctx);
                    SQLiteDatabase database = builder.getWritableDatabase();
                    for (int i=0;i<=array.length();i++) {
                        JSONObject objetfinal = array.getJSONObject(i);
                        diplome = new DiplomesDao(objetfinal.getInt(COLUMN_DIPLOME_ID),
                                objetfinal.getString(COLUMN_DIPLOME_NOM),
                                objetfinal.getString(COLUMN_DIPLOME_DESCRIPTION),
                                objetfinal.getString(COLUMN_DIPLOME_CREATED_AT),
                                objetfinal.getString(COLUMN_DIPLOME_UPDATED_AT));


                        builder.enregistrerDiplomesDepuisApi(diplome.getId(),
                                diplome.getNom(),
                                diplome.getDescription(),
                                diplome.getCreated_at(),
                                diplome.getUpdated_at(),
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
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
