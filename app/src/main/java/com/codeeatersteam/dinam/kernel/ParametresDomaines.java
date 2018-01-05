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
       final ArrayList<String> domlist = databaseBuilder.listeDesDomaines();

        CharSequence[] domaines = new CharSequence[domlist.size()];
        domaines = domlist.toArray(domaines);
        final CharSequence[] listedomaines = domaines;
        final ArrayList<String> selectionnes = new ArrayList<String>();

        builder.setTitle("Selection").setMultiChoiceItems(listedomaines, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    selectionnes.add(String.valueOf(listedomaines[which]));
                }else if (selectionnes.contains(listedomaines[which])){
                    selectionnes.remove(listedomaines[which]);
                }

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (selectionnes.size()>0){
                    PreferencesUtilisateur.getInstance(getActivity()).setPreferencesDomaines(selectionnes);
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
