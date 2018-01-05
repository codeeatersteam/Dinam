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
 * Created by pondikpa on 22/09/17.
 */

public class ParametresTypeLieux extends DialogFragment{

        String typelieuxchoisi;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DbBuilder databaseBuilder = new DbBuilder(getActivity());
        final ArrayList<String> lieulist = databaseBuilder.listeDesTypesLieux();

        CharSequence[] typelieux = new CharSequence[lieulist.size()];
        typelieux = lieulist.toArray(typelieux);
        final CharSequence[] listetypelieux = typelieux;
            final ArrayList<String> selectionnes = new ArrayList<String>();

        builder.setTitle("Choisissez votre lieu de preference").setMultiChoiceItems(listetypelieux, null,  new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    selectionnes.add(String.valueOf(listetypelieux[which]));
                }else if (selectionnes.contains(listetypelieux[which])){
                    selectionnes.remove(listetypelieux[which]);
                }

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectionnes.size()>0){
                    PreferencesUtilisateur.getInstance(getActivity()).setPreferencesTypeLieux(selectionnes);
                }
                else {
                    PreferencesUtilisateur.getInstance(getActivity()).getPreferencesTypeLieux().clear();
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
