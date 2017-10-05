package com.codeeatersteam.dinam.ui;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.net.EnvoyerEmplois;
import com.codeeatersteam.dinam.net.RecupererDiplomes;
import com.codeeatersteam.dinam.net.RecupererDomaines;
import com.codeeatersteam.dinam.net.RecupererEvenements;
import com.codeeatersteam.dinam.net.RecupererLieux;
import com.codeeatersteam.dinam.net.RecupererOffres;
import com.codeeatersteam.dinam.net.RecupererTypesEvenement;
import com.codeeatersteam.dinam.net.RecupererTypesLieu;
import com.codeeatersteam.dinam.net.RecupererTypesOffre;

import static com.codeeatersteam.dinam.kernel.Core.NON_COMMUN_AUX_PREFERENCES;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.affecterPolice;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.isConnectedInternet;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;

public class Conteneur extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
   public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferencesUtilisateur.getInstance(this).getWelcome() == null ||
                PreferencesUtilisateur.getInstance(this).getWelcome().equals(NON_COMMUN_AUX_PREFERENCES)) {
            ouvrirActivite(this, Welcome.class);
            finish();
        } else {
            setContentView(R.layout.activity_conteneur);

            DbBuilder dbBuilder = new DbBuilder(this);
            System.out.println(PreferencesUtilisateur.getInstance(this).getWelcome());


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setTextSize(35);
            affecterPolice(this, toolbarTitle);
            setSupportActionBar(toolbar);


            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tabLayout.setSelectedTabIndicatorColor(this.getColor(R.color.White));
            }


            fab = (FloatingActionButton) findViewById(R.id.fab);
            if (!dbBuilder.listeDesDomaines().isEmpty() && !dbBuilder.listeDesDiplomes().isEmpty() && !dbBuilder.listeDesTypesOffres().isEmpty()) {
                fab.setVisibility(View.VISIBLE);
            } else {

                fab.setVisibility(View.INVISIBLE);
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ouvrirActivite(view.getContext(), ProposerOffre.class);
                }
            });


        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conteneur, menu);
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
        }else if (id == R.id.about_app) {
            ouvrirActivite(this,About.class);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.offres, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new Offres();
                case 1:

                    return new Evenements();
                case 2:
                    return new Lieux();
                case 3:
                    return new OffresPourMoi();
                case 4:
                    return new EvenementsPourMoi();
                case 5:
                    return new LieuxPourMoi();
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "OFFRES PROFESSIONNELLES";
                case 1:

                    return "EVENEMENTS AU TOGO";
                case 2:
                    return "LIEUX DE REFERENCES";
                case 3:
                    return "OFFRES POUR MOI";
                case 4:
                    return "EVENEMENTS POUR MOI";
                case 5:
                    return "LIEUX POUR MOI";
            }
            return null;
        }
    }
}
