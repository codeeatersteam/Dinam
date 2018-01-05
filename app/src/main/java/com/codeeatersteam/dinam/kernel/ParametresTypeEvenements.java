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

public class ParametresTypeEvenements extends DialogFragment{

        String typeevenementchoisi;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DbBuilder databaseBuilder = new DbBuilder(getActivity());
        final ArrayList<String> evenementlist = databaseBuilder.listeDesTypesEvenements();

        CharSequence[] typeevenements = new CharSequence[evenementlist.size()];
        typeevenements = evenementlist.toArray(typeevenements);
        final CharSequence[] listetypeevenements = typeevenements;
            final ArrayList<String> selectionnes = new ArrayList<String>();

        builder.setTitle("Choisissez votre evenement de preference").setMultiChoiceItems(listetypeevenements, null,  new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    selectionnes.add(String.valueOf(listetypeevenements[which]));
                }else if (selectionnes.contains(listetypeevenements[which])){
                    selectionnes.remove(listetypeevenements[which]);
                }

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectionnes.size()>0){
                    PreferencesUtilisateur.getInstance(getActivity()).setPreferencesTypeEvenements(selectionnes);

                }else {
                    PreferencesUtilisateur.getInstance(getActivity()).getPreferencesTypeEvenements().clear();


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
