package com.codeeatersteam.dinam.kernel;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.ui.ConteneurPrincipal;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

import static android.content.Context.NOTIFICATION_SERVICE;
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

    //fonction de convertion d'images en String
    public static String ImageTostring(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }

//  lire le numero de la carte sim 
//    public static void numeroDeTelephone(Context ctx){
//        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                ctx.getApplicationInfo().requestPermissions(new String[]{
//                        Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE
//                }, 10);
//            }
//
//        }else {
//            TelephonyManager telemamanger = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//            String getSimSerialNumber = telemamanger.getSimSerialNumber();
//            String getSimNumber = telemamanger.getLine1Number();
//            Toasty.warning(ctx, getSimSerialNumber, Toast.LENGTH_LONG, true).show();
//        }
//    }

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
            else{
                Toasty.warning(ctx, "Veuillez verifier connexion internet.", Toast.LENGTH_LONG, true).show();
                return false;}

        }
        else {
            Toasty.error(ctx, "Veuillez vous connecter à internet.", Toast.LENGTH_LONG, true).show();
            return false;}
    }

    public static AutoLinkTextView autoLinksMode(final Context ctx, final AutoLinkTextView textView){
        textView.addAutoLinkMode(
                AutoLinkMode.MODE_HASHTAG,
                AutoLinkMode.MODE_PHONE,
                AutoLinkMode.MODE_URL,
                AutoLinkMode.MODE_MENTION);
        textView.setHashtagModeColor(ContextCompat.getColor(ctx, R.color.Green_500));
        textView.setPhoneModeColor(ContextCompat.getColor(ctx, R.color.Green_500));
        textView.setCustomModeColor(ContextCompat.getColor(ctx, R.color.Green_500));
        textView.setUrlModeColor(ContextCompat.getColor(ctx, R.color.Blue_500));
        textView.setMentionModeColor(ContextCompat.getColor(ctx, R.color.Green_500));
        textView.setEmailModeColor(ContextCompat.getColor(ctx, R.color.Green_500));
        textView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                if (autoLinkMode.equals(AutoLinkMode.MODE_PHONE)){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", matchedText, null));



                    ctx.startActivity(callIntent);
                }else if (autoLinkMode.equals(AutoLinkMode.MODE_URL)){
                    Toast.makeText(ctx," "+autoLinkMode,Toast.LENGTH_LONG).show();
                }
            }
        });

        return textView;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);

    }


    public static void initialiserPreferences(Context ctx){
        ArrayList<String> init = new ArrayList<>();

            PreferencesUtilisateur.getInstance(ctx).setPreferencesDomaines(init);
            PreferencesUtilisateur.getInstance(ctx).setPreferencesTypeOffres(init);
        PreferencesUtilisateur.getInstance(ctx).setPreferencesTypeEvenements(init);
        PreferencesUtilisateur.getInstance(ctx).setPreferencesTypeLieux(init);






    }

    public static  void createNotification(Context ctx,int NOTIFICATION_ID,int REQUEST_CODE,String message){
        final NotificationManager mNotification = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(ctx,ConteneurPrincipal.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(ctx,
                REQUEST_CODE, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(ctx)
                .setWhen(System.currentTimeMillis())
                .setTicker("Dinam")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Dinam")
                .setContentText(message)
                .setContentIntent(pendingIntent);

        mNotification.notify(NOTIFICATION_ID, builder.build());
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


    public static  boolean userConnected(Context ctx){
        if (PreferencesUtilisateur.getInstance(ctx).getEmail()!=null){
            return true;
        }
        else return false;

    }

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
