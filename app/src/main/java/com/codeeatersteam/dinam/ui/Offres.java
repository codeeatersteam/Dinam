package com.codeeatersteam.dinam.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.adapters.OffresAdapter;
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.net.RecupererDiplomes;
import com.codeeatersteam.dinam.net.RecupererDomaines;
import com.codeeatersteam.dinam.net.RecupererOffres;
import com.codeeatersteam.dinam.net.RecupererTypesOffre;

import java.util.ArrayList;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_AUDIENCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_CLOTURE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DIPLOME_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DOMAINE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_EMAIL;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_POSTE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SALAIRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SOURCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TYPE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_OFFRES_EN_LOCAL_APPROUVES;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.Daybetween;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.dateActuelle;

//import com.google.android.gms.ads.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Offres.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Offres#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Offres extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    PullRefreshLayout refreshLayout;

    private RecyclerView recyclerView;
    OffresAdapter adapter;
    ArrayList<OffresDao> offresDaos;
    public static  final int ITEMS_PER_AD = 4;
    public static  final int NATIVE_EXPRESS_AD_HEIGHT = 100;




    public Offres() {
        // Required empty public constructor


    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment offres.
     */
    // TODO: Rename and change types and number of parameters
    public static Offres newInstance(String param1, String param2) {
        Offres fragment = new Offres();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }



    public void  OffresEnLocal(){
        int id,type,domaine,diplome;
        String salaire,description,telephone,poste,date_audience,date_fin,email,source;
        offresDaos.clear();
        DbBuilder builder = new DbBuilder(this.getContext());
        SQLiteDatabase database = builder.getReadableDatabase();
        Cursor cursor = database.rawQuery(LISTES_OFFRES_EN_LOCAL_APPROUVES,null);

        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_ID));

            type = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_TYPE_OFFRE));
            domaine = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_DOMAINE_OFFRE));
            diplome = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_DIPLOME_OFFRE));
            salaire = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_SALAIRE));
            description = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_DESCRIPTION));
            telephone = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_TELEPHONE));
            poste = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_POSTE));
            date_audience = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_DATE_AUDIENCE));
            date_fin = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_DATE_CLOTURE));
            email  = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_EMAIL));
            source  = cursor.getString(cursor.getColumnIndex(COLUMN_OFFRE_SOURCE));

            if (Daybetween(dateActuelle(),date_fin,"yyyy-mm-dd")>=0) {
                //System.out.println(String.valueOf(Daybetween(dateActuelle(),date_fin,"yyyy-mm-dd"))+"  "+poste+"  "+dateActuelle()+"  "+date_fin);
                offresDaos.add(new OffresDao(id, domaine, type, diplome, salaire, description, telephone, poste, date_audience, date_fin, email, source));
            }


        }
        adapter.notifyDataSetChanged();
        cursor.close();
        database.close();
        builder.close();


    }

//
//private void addNativeExpressAds() {
//
//        // Loop through the items array and place a new Native Express ad in every ith position in
//        // the items List.
//        for (int i = 0; i <= offresDaos.size(); i += ITEMS_PER_AD) {
//            final NativeExpressAdView adView = new NativeExpressAdView(Offres.this.getContext());
//            offresDaos.add(i, adView);
//        }
//    }
//
//    private void setUpAndLoadNativeExpressAds() {
//        // Use a Runnable to ensure that the RecyclerView has been laid out before setting the
//        // ad size for the Native Express ad. This allows us to set the Native Express ad's
//        // width to match the full width of the RecyclerView.
//        recyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                final float scale = Offres.this.getContext().getResources().getDisplayMetrics().density;
//                // Set the ad size and ad unit ID for each Native Express ad in the items list.
//                for (int i = 0; i <= offresDaos.size(); i += ITEMS_PER_AD) {
//                    final NativeExpressAdView adView =
//                            (NativeExpressAdView) offresDaos.get(i);
//                    final CardView cardView = (CardView) Offres.this.getActivity().findViewById(R.id.ad_card);
//                    final int adWidth = cardView.getWidth() - cardView.getPaddingLeft()
//                            - cardView.getPaddingRight();
//                    AdSize adSize = new AdSize((int) (adWidth / scale), NATIVE_EXPRESS_AD_HEIGHT);
//                    adView.setAdSize(adSize);
//                    adView.setAdUnitId(AD_OFFRES_UNIT_ID);
//                }
//
//                // Load the first Native Express ad in the items list.
//                loadNativeExpressAd(0);
//            }
//        });
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }

//    private void loadNativeExpressAd(final int index) {
//
//        if (index >= offresDaos.size()) {
//            return;
//        }
//
//        Object item = offresDaos.get(index);
//        if (!(item instanceof NativeExpressAdView)) {
//            throw new ClassCastException("Expected item at index " + index + " to be a Native"
//                    + " Express ad.");
//        }
//
//        final NativeExpressAdView adView = (NativeExpressAdView) item;
//
//        // Set an AdListener on the NativeExpressAdView to wait for the previous Native Express ad
//        // to finish loading before loading the next ad in the items list.
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                // The previous Native Express ad loaded successfully, call this method again to
//                // load the next ad in the items list.
//                loadNativeExpressAd(index + ITEMS_PER_AD);
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // The previous Native Express ad failed to load. Call this method again to load
//                // the next ad in the items list.
//                Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to"
//                        + " load the next Native Express ad in the items list.");
//                loadNativeExpressAd(index + ITEMS_PER_AD);
//            }
//        });
//
//        // Load the Native Express ad.
//        adView.loadAd(new AdRequest.Builder().build());
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView= inflater .inflate(R.layout.offres, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_offres);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        offresDaos = new ArrayList<>();
        adapter = new OffresAdapter(offresDaos,rootView.getContext());
        recyclerView.setAdapter(adapter);

        refreshLayout = (PullRefreshLayout)rootView.findViewById(R.id.pullRefreshOffres);


        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (FonctionsUtiles.isConnectedInternet(rootView.getContext())){

                    RecupererDiplomes recupererDiplomes = new RecupererDiplomes(rootView.getContext());
                    recupererDiplomes.execute();
                    RecupererDomaines recupererDomaines = new RecupererDomaines(rootView.getContext());
                    recupererDomaines.execute();
                    RecupererTypesOffre recupererTypesOffre = new RecupererTypesOffre(rootView.getContext());
                    recupererTypesOffre.execute();
                    RecupererOffres recupererOffres = new RecupererOffres(rootView.getContext());
                    recupererOffres.execute();

                    OffresEnLocal();
                }

                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);

                    }
                },5000);

            }

        });


        if (ActivityCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.CALL_PHONE
                }, 10);
            }
        }


        OffresEnLocal();



        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
