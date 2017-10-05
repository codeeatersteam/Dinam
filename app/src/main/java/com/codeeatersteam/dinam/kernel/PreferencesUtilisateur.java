package com.codeeatersteam.dinam.kernel;

import android.content.Context;
import android.content.SharedPreferences;

import static com.codeeatersteam.dinam.kernel.Core.DOMAINE_OFFRE_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.PREFERENCES_NAME;
import static com.codeeatersteam.dinam.kernel.Core.TYPE_EVENEMENT_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.TYPE_LIEU_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.TYPE_OFFRE_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.WELCOME_STATE;

/**
 * Created by pondikpa on 15/06/17.
 */

public class PreferencesUtilisateur {
    Context ctx;
    String domaineOffrePref,typeOffrePref,typeEvenementPref,typeLieuxPref,welcome;
    private static PreferencesUtilisateur mInstance;



    public PreferencesUtilisateur(Context ctx) {
        this.ctx = ctx;

    }

    public static synchronized  PreferencesUtilisateur getInstance(Context ctx){
        if (mInstance==null){
            mInstance = new PreferencesUtilisateur(ctx);

        }
        return mInstance;
    }

    public PreferencesUtilisateur() {
    }

    public String getWelcome() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WELCOME_STATE,null);
    }

    public void setWelcome(String welcome) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(WELCOME_STATE,welcome);
        editor.apply();
    }

    public String getDomaineOffrePref() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DOMAINE_OFFRE_PREFERE,null);
    }

    public void setDomaineOffrePref(String domaineOffrePref) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(DOMAINE_OFFRE_PREFERE,domaineOffrePref);
        editor.apply();
    }

    public String getTypeOffrePref() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TYPE_OFFRE_PREFERE,null);
    }

    public void setTypeOffrePref(String typeOffrePref) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(TYPE_OFFRE_PREFERE,typeOffrePref);
        editor.apply();
    }

    public String getTypeEvenementPref() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TYPE_EVENEMENT_PREFERE,null);
    }

    public void setTypeEvenementPref(String typeEvenementPref) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(TYPE_EVENEMENT_PREFERE,typeEvenementPref);
        editor.apply();
    }

    public String getTypeLieuxPref() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TYPE_LIEU_PREFERE,null);
    }

    public void setTypeLieuxPref(String typeLieuxPref) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TYPE_LIEU_PREFERE, typeLieuxPref);
        editor.apply();
    }
}
