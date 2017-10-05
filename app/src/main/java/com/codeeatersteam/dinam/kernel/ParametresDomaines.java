package com.codeeatersteam.dinam.kernel;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.codeeatersteam.dinam.ui.Personnalisation;

import java.util.ArrayList;

import static com.codeeatersteam.dinam.kernel.Core.TOUS_COMMUN_AUX_PREFERENCES;

/**
 * Created by pondikpa on 09/08/17.
 */

public class ParametresDomaines extends DialogFragment {

    String domainchoisi;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DbBuilder databaseBuilder = new DbBuilder(getActivity());
       ArrayList<String> domlist = databaseBuilder.listeDesDomaines();
        domlist.add(0,TOUS_COMMUN_AUX_PREFERENCES);

        CharSequence[] domaines = new CharSequence[domlist.size()];
        domaines = domlist.toArray(domaines);
        final CharSequence[] listedomaines = domaines;

        builder.setTitle("Choisissez votre domaine de preference").setSingleChoiceItems(listedomaines, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                domainchoisi = (String) listedomaines[which];


            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (domainchoisi==null){

                }else {
                    PreferencesUtilisateur.getInstance(getActivity()).setDomaineOffrePref(domainchoisi);
                    FonctionsUtiles.ouvrirActivite(getActivity(), Personnalisation.class);

                }

            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();

    }


}
