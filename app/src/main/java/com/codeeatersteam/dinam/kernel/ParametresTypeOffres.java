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
 * Created by pondikpa on 10/08/17.
 */

public class ParametresTypeOffres extends DialogFragment {
    String typeOffre;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DbBuilder databaseBuilder = new DbBuilder(getActivity());
        final ArrayList<String> typeOffres = databaseBuilder.listeDesTypesOffres();
        typeOffres.add(0,TOUS_COMMUN_AUX_PREFERENCES);
        CharSequence[] types = new CharSequence[typeOffres.size()];
        types = typeOffres.toArray(types);
        final CharSequence[] listeTypesOffres = types;

        builder.setTitle("Choisissez votre domaine de preference").setSingleChoiceItems(listeTypesOffres, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeOffre = (String) listeTypesOffres[which];

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (typeOffre==null){

                }else {

                    PreferencesUtilisateur.getInstance(getActivity()).setTypeOffrePref(typeOffre);
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
