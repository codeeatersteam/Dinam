package com.codeeatersteam.dinam.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codeeatersteam.dinam.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LireCv extends AppCompatActivity {

    PDFView pdfView;
   // PulseView pulseView;
    String url_cv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lire_cv);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  pulseView= (PulseView) findViewById(R.id.pulseViewlireCv);

        pdfView = (PDFView)findViewById(R.id.pdfViewerCv);

        url_cv ="http://ancestralauthor.com/download/sample.pdf";
        new recupererCVPDF().execute(url_cv);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    class recupererCVPDF extends AsyncTask<String,Void,InputStream>{
        InputStream inputStream = null;

        @Override
        protected void onPreExecute() {
          //  pulseView.setVisibility(View.VISIBLE);
          //  pulseView.startPulse();
        }

        @Override
        protected InputStream doInBackground(String... params) {


            try {
               URL url = new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            if (urlConnection.getResponseCode()==200){
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }


        @Override
        protected void onPostExecute(InputStream inputStream) {
            //pulseView.finishPulse();
            //pulseView.setVisibility(View.GONE);
            pdfView.fromStream(inputStream).load();
        }
    }

}
