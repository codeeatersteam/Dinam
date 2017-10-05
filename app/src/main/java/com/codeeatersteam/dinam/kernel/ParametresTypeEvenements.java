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
        evenementlist.add(0,TOUS_COMMUN_AUX_PREFERENCES);

        CharSequence[] typeevenements = new CharSequence[evenementlist.size()];
        typeevenements = evenementlist.toArray(typeevenements);
        final CharSequence[] listetypeevenements = typeevenements;

        builder.setTitle("Choisissez votre domaine de preference").setSingleChoiceItems(listetypeevenements, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeevenementchoisi = (String) listetypeevenements[which];


            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (typeevenementchoisi==null){

                }else {
                    PreferencesUtilisateur.getInstance(getActivity()).setTypeEvenementPref(typeevenementchoisi);
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
