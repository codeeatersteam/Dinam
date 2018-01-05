package com.codeeatersteam.dinam.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.net.EnvoyerEmplois;
import com.codeeatersteam.dinam.net.EnvoyerEvenements;
import com.codeeatersteam.dinam.net.EnvoyerLieux;
import com.codeeatersteam.dinam.net.RecupererDiplomes;
import com.codeeatersteam.dinam.net.RecupererDomaines;
import com.codeeatersteam.dinam.net.RecupererEvenements;
import com.codeeatersteam.dinam.net.RecupererLieux;
import com.codeeatersteam.dinam.net.RecupererOffres;
import com.codeeatersteam.dinam.net.RecupererTypesEvenement;
import com.codeeatersteam.dinam.net.RecupererTypesLieu;
import com.codeeatersteam.dinam.net.RecupererTypesOffre;

import static com.codeeatersteam.dinam.kernel.Core.NON_COMMUN_AUX_PREFERENCES;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.initialiserPreferences;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.isConnectedInternet;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.userConnected;

public class ConteneurPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_CODE = 5255555;
    private static final int NOTIFICATION_ID = 555;
    TextView nomUserNav;
    ImageView imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;
            System.out.println(verCode);
            if (verCode<5){
                DbBuilder builder =new DbBuilder(this);
                builder.onUpgrade(builder.getWritableDatabase(),1,2);
                initialiserPreferences(this);
                ouvrirActivite(this, Welcome.class);
                finish();

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (PreferencesUtilisateur.getInstance(this).getWelcome() == null ||
                PreferencesUtilisateur.getInstance(this).getWelcome().equals(NON_COMMUN_AUX_PREFERENCES)) {
            ouvrirActivite(this, Welcome.class);
            finish();
        } else {
            setContentView(R.layout.activity_conteneur_principal);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            displaySelectedMenu(R.id.offres);



            if(isConnectedInternet(this)){
                try {
                    RecupererDomaines recupererDomaines = new RecupererDomaines(this);
                    recupererDomaines.execute();
                    RecupererTypesOffre recupererTypesOffre = new RecupererTypesOffre(this);
                    recupererTypesOffre.execute();
                    RecupererTypesEvenement recupererTypesEvenement = new RecupererTypesEvenement(this);
                    recupererTypesEvenement.execute();
                    RecupererTypesLieu recupererTypesLieu = new RecupererTypesLieu(this);
                    recupererTypesLieu.execute();
                    RecupererDiplomes recupererDiplomes = new RecupererDiplomes(this);
                    recupererDiplomes.execute();
                    RecupererOffres recupererOffres = new RecupererOffres(this);
                    recupererOffres.execute();
                    RecupererEvenements recupererEvenements = new RecupererEvenements(this);
                    recupererEvenements.execute();
                    RecupererLieux recupererLieux = new RecupererLieux(this);
                    recupererLieux.execute();
                    EnvoyerEmplois envoyerEmplois = new EnvoyerEmplois(this);
                    envoyerEmplois.execute();
                    EnvoyerEvenements envoyerEvenements = new EnvoyerEvenements(this);
                    envoyerEvenements.execute();
                    EnvoyerLieux envoyerLieux = new EnvoyerLieux(this);
                    envoyerLieux.execute();
                }catch (Exception e){

                }
            }

//            View headerView = navigationView.inflateHeaderView(R.layout.nav_header_conteneur_principal);
//            nomUserNav = (TextView)headerView.findViewById(R.id.navProfilUserNom);
//            imageUser = (ImageView) headerView.findViewById(R.id.navProfilUserImg);
//            if (userConnected(this)){
//                nomUserNav.setText(PreferencesUtilisateur.getInstance(headerView.getContext()).getPrenoms()+" "+PreferencesUtilisateur.getInstance(headerView.getContext()).getNom());
//                try {
//                    Glide.with(headerView.getContext()).load(ApiLinks.API_IMAGES_USERS_URL+PreferencesUtilisateur.getInstance(headerView.getContext()).getMonimage() ).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageUser);
//                }catch (IllegalArgumentException e){
//
//                }
//
//            }



        }
    }

//    private final void createNotification(){
//        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        final Intent launchNotifiactionIntent = new Intent(this,ConteneurPrincipal.class);
//        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                REQUEST_CODE, launchNotifiactionIntent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Notification.Builder builder = new Notification.Builder(this)
//                .setWhen(System.currentTimeMillis())
//                .setTicker("Dinam")
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle("Dinam")
//                .setContentText("Nouvelles offres disponnibles")
//                .setContentIntent(pendingIntent);
//
//        mNotification.notify(NOTIFICATION_ID, builder.build());
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    private void displaySelectedMenu(int id){
        android.support.v4.app.Fragment fragment = null;
        switch (id){
            case R.id.offres:
                fragment = new ConteneurOffres();
                break;
            case R.id.evenements:
                fragment = new ConteneurEvenements();
                break;
            case R.id.lieux:
                fragment = new ConteneurLieux();
                break;
            case R.id.espace_membre:
                if (userConnected(this)){
                    fragment = new MemberSpace();
                }else {
                    fragment = new Login();
                }

                break;
//            case R.id.espace_cv:
//                fragment = new ConteneurCv();
//                break;
            case R.id.about:
                ouvrirActivite(this,About.class);
                break;
        }
        if (fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_conteneur_principal,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.conteneur_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ouvrirActivite(this,Personnalisation.class);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       displaySelectedMenu(id);

        return true;
    }
}
