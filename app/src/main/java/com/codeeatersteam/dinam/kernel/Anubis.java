package com.codeeatersteam.dinam.kernel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.codeeatersteam.dinam.net.EnvoyerEmplois;
import com.codeeatersteam.dinam.net.EnvoyerEvenements;
import com.codeeatersteam.dinam.net.EnvoyerLieux;
import com.codeeatersteam.dinam.net.RecupererDiplomes;
import com.codeeatersteam.dinam.net.RecupererDomaines;
import com.codeeatersteam.dinam.net.RecupererEvenements;
import com.codeeatersteam.dinam.net.RecupererLieux;
import com.codeeatersteam.dinam.net.RecupererOffres;
import com.codeeatersteam.dinam.net.RecupererTypesEvenement;
import com.codeeatersteam.dinam.net.RecupererTypesLieu;
import com.codeeatersteam.dinam.net.RecupererTypesOffre;

/**
 * Created by pondikpa on 20/08/17.
 */

public class Anubis extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (FonctionsUtiles.isConnectedInternet(context)) {
            try {
                RecupererDomaines recupererDomaines = new RecupererDomaines(context);
                recupererDomaines.execute();
                RecupererTypesOffre recupererTypesOffre = new RecupererTypesOffre(context);
                recupererTypesOffre.execute();
                RecupererTypesEvenement recupererTypesEvenement = new RecupererTypesEvenement(context);
                recupererTypesEvenement.execute();
                RecupererTypesLieu recupererTypesLieu = new RecupererTypesLieu(context);
                recupererTypesLieu.execute();
                RecupererDiplomes recupererDiplomes = new RecupererDiplomes(context);
                recupererDiplomes.execute();
                RecupererOffres recupererOffres = new RecupererOffres(context);
                recupererOffres.execute();
                RecupererEvenements recupererEvenements = new RecupererEvenements(context);
                recupererEvenements.execute();
                RecupererLieux recupererLieux = new RecupererLieux(context);
                recupererLieux.execute();
                EnvoyerEmplois envoyerEmplois = new EnvoyerEmplois(context);
                envoyerEmplois.execute();
                EnvoyerEvenements envoyerEvenements = new EnvoyerEvenements(context);
                envoyerEvenements.execute();
                EnvoyerLieux envoyerLieux = new EnvoyerLieux(context);
                envoyerLieux.execute();
            }catch (Exception e){

            }
        }
    }
}
