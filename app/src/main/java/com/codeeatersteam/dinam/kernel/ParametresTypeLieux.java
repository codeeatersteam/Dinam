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
        lieulist.add(0,TOUS_COMMUN_AUX_PREFERENCES);

        CharSequence[] typelieux = new CharSequence[lieulist.size()];
        typelieux = lieulist.toArray(typelieux);
        final CharSequence[] listetypelieux = typelieux;

        builder.setTitle("Choisissez votre domaine de preference").setSingleChoiceItems(listetypelieux, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typelieuxchoisi = (String) listetypelieux[which];


            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (typelieuxchoisi==null){

                }else {
                    PreferencesUtilisateur.getInstance(getActivity()).setTypeLieuxPref(typelieuxchoisi);
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
