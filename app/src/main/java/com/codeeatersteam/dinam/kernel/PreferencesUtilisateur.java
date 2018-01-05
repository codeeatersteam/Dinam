package com.codeeatersteam.dinam.kernel;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.codeeatersteam.dinam.kernel.Core.DATENAISSANCE_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.DOMAINE_OFFRE_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.EMAIL_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.ID_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.MONDIPLOME_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.MONIMAGE_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.NOM_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.PREFERENCES_NAME;
import static com.codeeatersteam.dinam.kernel.Core.PRENOMS_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.SEXE_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.TELEPHONE_PREFERENCE;
import static com.codeeatersteam.dinam.kernel.Core.TYPE_EVENEMENT_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.TYPE_LIEU_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.TYPE_OFFRE_PREFERE;
import static com.codeeatersteam.dinam.kernel.Core.WELCOME_STATE;

/**
 * Created by pondikpa on 15/06/17.
 */

public class PreferencesUtilisateur {
    Context ctx;
    String domaineOffrePref,typeOffrePref,typeEvenementPref,typeLieuxPref,welcome,id,nom,prenoms,datenaissance,email,telephone,mondiplome,monimage,sexe;
    private static PreferencesUtilisateur mInstance;
    ArrayList<String> preferencesDomaines,preferencesTypeOffres,preferencesTypeEvenements,preferencesTypeLieux;




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


    public String getId() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID_PREFERENCE,null);
    }

    public void setId(String id) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_PREFERENCE, id);
        editor.apply();
    }

    public String getNom() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NOM_PREFERENCE,null);
    }

    public void setNom(String nom) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NOM_PREFERENCE, nom);
        editor.apply();
    }

    public String getPrenoms() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PRENOMS_PREFERENCE,null);
    }

    public void setPrenoms(String prenoms) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PRENOMS_PREFERENCE, prenoms);
        editor.apply();
    }

    public String getDatenaissance() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DATENAISSANCE_PREFERENCE,null);
    }

    public void setDatenaissance(String datenaissance) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATENAISSANCE_PREFERENCE, datenaissance);
        editor.apply();
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_PREFERENCE,null);
    }

    public void setEmail(String email) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_PREFERENCE, email);
        editor.apply();
    }

    public String getTelephone() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TELEPHONE_PREFERENCE,null);
    }

    public void setTelephone(String telephone) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TELEPHONE_PREFERENCE, telephone);
        editor.apply();
    }

    public String getMondiplome() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MONDIPLOME_PREFERENCE,null);
    }

    public void setMondiplome(String mondiplome) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MONDIPLOME_PREFERENCE, mondiplome);
        editor.apply();
    }

    public String getMonimage() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MONIMAGE_PREFERENCE,null);
    }

    public void setMonimage(String monimage) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MONIMAGE_PREFERENCE, monimage);
        editor.apply();
    }

    public String getSexe() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SEXE_PREFERENCE,null);
    }

    public void setSexe(String sexe) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SEXE_PREFERENCE, sexe);
        editor.apply();
    }

    public ArrayList<String> getPreferencesDomaines() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Set<String> set  = sharedPreferences.getStringSet(DOMAINE_OFFRE_PREFERE, null);
        preferencesDomaines = new ArrayList<>(set);
       // return sharedPreferences.getString(DOMAINE_OFFRE_PREFERE,null);
       return preferencesDomaines;


    }

    public void setPreferencesDomaines(ArrayList<String> preferencesDomaines) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(preferencesDomaines);
        editor.putStringSet(DOMAINE_OFFRE_PREFERE, set);
        editor.commit();
    }

    public ArrayList<String> getPreferencesTypeOffres() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(TYPE_OFFRE_PREFERE, null);
        preferencesTypeOffres=new ArrayList<>(set);
        return preferencesTypeOffres;
    }

    public void setPreferencesTypeOffres(ArrayList<String> preferencesTypeOffres) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(preferencesTypeOffres);
        editor.putStringSet(TYPE_OFFRE_PREFERE, set);
        editor.commit();
    }

    public ArrayList<String> getPreferencesTypeEvenements() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(TYPE_EVENEMENT_PREFERE, null);
        preferencesTypeEvenements=new ArrayList<>(set);
        return preferencesTypeEvenements;
    }

    public void setPreferencesTypeEvenements(ArrayList<String> preferencesTypeEvenements) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(preferencesTypeEvenements);
        editor.putStringSet(TYPE_EVENEMENT_PREFERE, set);
        editor.commit();
    }

    public ArrayList<String> getPreferencesTypeLieux() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(TYPE_LIEU_PREFERE, null);
        preferencesTypeLieux=new ArrayList<>(set);
        return preferencesTypeLieux;
    }

    public void setPreferencesTypeLieux(ArrayList<String> preferencesTypeLieux) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(preferencesTypeLieux);
        editor.putStringSet(TYPE_LIEU_PREFERE, set);
        editor.commit();
    }
}
