package com.codeeatersteam.dinam.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.DbBuilder;
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

import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.isConnectedInternet;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.userConnected;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConteneurLieux.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConteneurLieux#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConteneurLieux extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentTabHost mTabHost;
    FloatingActionButton fab;

    private OnFragmentInteractionListener mListener;

    public ConteneurLieux() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConteneurLieux.
     */
    // TODO: Rename and change types and number of parameters
    public static ConteneurLieux newInstance(String param1, String param2) {
        ConteneurLieux fragment = new ConteneurLieux();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView=  inflater.inflate(R.layout.fragment_conteneur_lieux, container, false);
        mTabHost= (FragmentTabHost)rootView.findViewById(R.id.lieutabhost);
        mTabHost.setup(getActivity(),getChildFragmentManager(),R.id.lieutabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("alllieux").setIndicator("Tous les lieux"),Lieux.class,null);
        mTabHost.addTab(mTabHost.newTabSpec("mylieux").setIndicator("Lieux pour moi"),LieuxPourMoi.class,null);
        DbBuilder dbBuilder = new DbBuilder(rootView.getContext());

        fab = (FloatingActionButton) rootView.findViewById(R.id.fablieux);
        if (!dbBuilder.listeDesDomaines().isEmpty() && !dbBuilder.listeDesDiplomes().isEmpty() && !dbBuilder.listeDesTypesOffres().isEmpty() && userConnected(rootView.getContext())) {
            fab.setVisibility(View.VISIBLE);
        } else {

            fab.setVisibility(View.INVISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ouvrirActivite(view.getContext(), ProposerLieu.class);
            }
        });

        if(isConnectedInternet(rootView.getContext())){
            try {
                RecupererDomaines recupererDomaines = new RecupererDomaines(rootView.getContext());
                recupererDomaines.execute();
                RecupererTypesOffre recupererTypesOffre = new RecupererTypesOffre(rootView.getContext());
                recupererTypesOffre.execute();
                RecupererTypesEvenement recupererTypesEvenement = new RecupererTypesEvenement(rootView.getContext());
                recupererTypesEvenement.execute();
                RecupererTypesLieu recupererTypesLieu = new RecupererTypesLieu(rootView.getContext());
                recupererTypesLieu.execute();
                RecupererDiplomes recupererDiplomes = new RecupererDiplomes(rootView.getContext());
                recupererDiplomes.execute();
                RecupererOffres recupererOffres = new RecupererOffres(rootView.getContext());
                recupererOffres.execute();
                RecupererEvenements recupererEvenements = new RecupererEvenements(rootView.getContext());
                recupererEvenements.execute();
                RecupererLieux recupererLieux = new RecupererLieux(rootView.getContext());
                recupererLieux.execute();
                EnvoyerEmplois envoyerEmplois = new EnvoyerEmplois(rootView.getContext());
                envoyerEmplois.execute();
                EnvoyerEvenements envoyerEvenements = new EnvoyerEvenements(rootView.getContext());
                envoyerEvenements.execute();
                EnvoyerLieux envoyerLieux = new EnvoyerLieux(rootView.getContext());
                envoyerLieux.execute();
            }catch (Exception e){

            }
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



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
