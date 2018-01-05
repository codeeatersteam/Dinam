package com.codeeatersteam.dinam.net;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by pondikpa on 27/11/17.
 */

public class EnvoyerCv extends AsyncTask<Void,Void,Void> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        StringRequest request = new StringRequest(Request.Method.POST, ApiLinks.API_ENVOIE_CV_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
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
