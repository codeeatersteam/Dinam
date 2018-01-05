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
        CharSequence[] types = new CharSequence[typeOffres.size()];
        types = typeOffres.toArray(types);
        ArrayList<String> preferesdepuis =PreferencesUtilisateur.getInstance(getActivity()).getPreferencesTypeOffres();
//        boolean [] ras =new boolean[preferesdepuis.size()];
//        for (int i=0;i<preferesdepuis.size();i++){
//            ras.equals(i);
//        }

        final CharSequence[] listeTypesOffres = types;
        final ArrayList<String> selectionnes = new ArrayList<String>();


                builder.setTitle("Choisissez votre domaine de preference").setMultiChoiceItems(listeTypesOffres, null,  new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            selectionnes.add(String.valueOf(listeTypesOffres[which]));
                        }else if (selectionnes.contains(listeTypesOffres[which])){
                            selectionnes.remove(listeTypesOffres[which]);
                        }

                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectionnes.size()>0){
                    PreferencesUtilisateur.getInstance(getActivity()).setPreferencesTypeOffres(selectionnes);
                }
                else {
                    PreferencesUtilisateur.getInstance(getActivity()).getPreferencesTypeOffres().clear();
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
