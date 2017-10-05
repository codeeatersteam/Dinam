package com.codeeatersteam.dinam.kernel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeeatersteam.dinam.kernel.Core.NON_COMMUN_AUX_PREFERENCES;
import static com.codeeatersteam.dinam.kernel.Core.TOUS_COMMUN_AUX_PREFERENCES;

/**
 * Created by pondikpa on 15/06/17.
 * @author pondikpa Tchabao
 * @version 1.0
 * @description contient les fonctions necessaires au fonctionnement de l'application
 */

public class FonctionsUtiles {



    public static void ecrireDansLeJournal(){
        //ecrire dans le fichier journal

    }

    public static  void ouvrirActivite(Context ctx, Class activite){
        //ouvre une nouvelle activite sans passer de parametres
        Intent intent = new Intent(ctx, activite);
        ctx.startActivity(intent);

    }

    //fonction de verification de la connexion internet
    public static boolean isConnectedInternet(Context ctx)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
        {
            NetworkInfo.State networkState = networkInfo.getState();
            if (networkState.compareTo(NetworkInfo.State.CONNECTED) == 0)
            {
                return true;
            }
            else return false;
        }
        else return false;
    }


    public static boolean  verifierEmail(EditText champ){
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(champ.getText().toString());
        if (!m.matches()) {
            champ.setFocusable(true);
            View focus = null;
            focus = champ;
            champ.setError("Format de l'email incorrect");
            focus.requestFocus();
            return true;
        }

            return false;

    }

    public static String dateActuelle(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);

    }


    public static void initialiserPreferences(Context ctx){
        if (PreferencesUtilisateur.getInstance(ctx).getDomaineOffrePref() ==null){
            PreferencesUtilisateur.getInstance(ctx).setDomaineOffrePref(TOUS_COMMUN_AUX_PREFERENCES);

        }
        if (PreferencesUtilisateur.getInstance(ctx).getTypeOffrePref() ==null){
            PreferencesUtilisateur.getInstance(ctx).setTypeOffrePref(TOUS_COMMUN_AUX_PREFERENCES);
        }
        if (PreferencesUtilisateur.getInstance(ctx).getWelcome() ==null){
            PreferencesUtilisateur.getInstance(ctx).setWelcome(TOUS_COMMUN_AUX_PREFERENCES);
        }

        if (PreferencesUtilisateur.getInstance(ctx).getTypeEvenementPref() ==null){
            PreferencesUtilisateur.getInstance(ctx).setTypeEvenementPref(TOUS_COMMUN_AUX_PREFERENCES);
        }

        if (PreferencesUtilisateur.getInstance(ctx).getTypeLieuxPref() ==null){
            PreferencesUtilisateur.getInstance(ctx).setTypeLieuxPref(TOUS_COMMUN_AUX_PREFERENCES);
        }
    }

//    public static boolean preferencesNonParametres(Context ctx){
//        if (!PreferencesUtilisateur.getInstance(ctx).getDomaineOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES) && !PreferencesUtilisateur.getInstance(ctx).getTypeOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES)){
//            return false;
//        }else if (PreferencesUtilisateur.getInstance(ctx).getDomaineOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES) && !PreferencesUtilisateur.getInstance(ctx).getTypeOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES)){
//            return false;
//        }
//        else if (!PreferencesUtilisateur.getInstance(ctx).getDomaineOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES) && PreferencesUtilisateur.getInstance(ctx).getTypeOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES)){
//            return false;
//        }
//        else {
//            return true;
//        }
//
//
//    }

    public  static void affecterPolice(Context ctx,TextView txt){
        //affecte la police du titre au composant passe en parametre
        Typeface myTypeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/Jeans.ttf");
        txt.setTypeface(myTypeface);
    }

    public static boolean controlchampsvide(EditText champ){
        //verifie si le champ est vide
        if (champ.getText().toString().length() ==0){
            champ.setFocusable(true);
            View focus = null;
            focus = champ;
            champ.setError("Ce champ est requis");
            focus.requestFocus();
            return true;

        }else {
            return false;
        }


    }

    public static int Daybetween(String date1,String date2,String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date Date1 = null,Date2 = null;
        try{
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return (int) ((Date2.getTime() - Date1.getTime())/(24*60*60*1000));
    }



}
