package com.codeeatersteam.dinam.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.adapters.OffresPourMoiAdapter;
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;

import java.util.ArrayList;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_AUDIENCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_CLOTURE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DIPLOME_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DOMAINE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_EMAIL;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_POSTE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SALAIRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TYPE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TABLE_SYNCHRONIZED;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_OFFRES;
import static com.codeeatersteam.dinam.kernel.Core.TOUS_COMMUN_AUX_PREFERENCES;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OffresPourMoi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OffresPourMoi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffresPourMoi extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Button proposition;
    private RecyclerView recyclerView;
    OffresPourMoiAdapter adapter;
    ArrayList<OffresDao> offresDaos;

    public OffresPourMoi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OffresPourMoi.
     */
    // TODO: Rename and change types and number of parameters
    public static OffresPourMoi newInstance(String param1, String param2) {
        OffresPourMoi fragment = new OffresPourMoi();
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
        final View rootView= inflater .inflate(R.layout.fragment_offres_pour_moi, container, false);
        proposition = (Button) rootView.findViewById(R.id.proposerPersonalisationBtn);

        if (PreferencesUtilisateur.getInstance(rootView.getContext()).getDomaineOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES) &&
                PreferencesUtilisateur.getInstance(rootView.getContext()).getTypeOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES)){
            proposition.setVisibility(View.VISIBLE);
            proposition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FonctionsUtiles.ouvrirActivite(rootView.getContext(),Personnalisation.class);
                }
            });
        }else {
            proposition.setVisibility(View.INVISIBLE);
            recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_offre_pour_moi);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            offresDaos = new ArrayList<>();
            adapter = new OffresPourMoiAdapter(offresDaos,rootView.getContext());
            recyclerView.setAdapter(adapter);
            MesOffresEnLocal();
        }

        return rootView;
    }

    public void  MesOffresEnLocal(){
        int id,type,domaine,diplome;
        String salaire,description,telephone,poste,date_audience,date_fin,email;
        offresDaos.clear();
        DbBuilder builder = new DbBuilder(this.getContext());
        SQLiteDatabase database = builder.getReadableDatabase();
        String query = null;
        if (!PreferencesUtilisateur.getInstance(getActivity()).getDomaineOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES) &&
                !PreferencesUtilisateur.getInstance(getActivity()).getTypeOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES)){
             query = "SELECT * FROM "+TABLE_OFFRES+" WHERE "+COLUMN_OFFRE_DOMAINE_OFFRE+" = "+builder.idDomaineFromNom(PreferencesUtilisateur.getInstance(getActivity()).getDomaineOffrePref())+" " +
                    "AND "+COLUMN_OFFRE_TYPE_OFFRE+" = "+builder.idTypeOffreFromNom(PreferencesUtilisateur.getInstance(getActivity()).getTypeOffrePref())+" AND "+COLUMN_OFFRE_ETAT+" = "+COLUMN_TABLE_SYNCHRONIZED+" ORDER BY "+COLUMN_OFFRE_ID+" DESC";
        }
        else if (!PreferencesUtilisateur.getInstance(getActivity()).getDomaineOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES) &&
                PreferencesUtilisateur.getInstance(getActivity()).getTypeOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES)){
            query = "SELECT * FROM "+TABLE_OFFRES+" WHERE "+COLUMN_OFFRE_DOMAINE_OFFRE+" = "+builder.idDomaineFromNom(PreferencesUtilisateur.getInstance(getActivity()).getDomaineOffrePref())+" " +
                    "AND "+COLUMN_OFFRE_ETAT+" = "+COLUMN_TABLE_SYNCHRONIZED+" ORDER BY "+COLUMN_OFFRE_ID+" DESC";



        }
            else if (PreferencesUtilisateur.getInstance(getActivity()).getDomaineOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES) &&
                !PreferencesUtilisateur.getInstance(getActivity()).getTypeOffrePref().equals(TOUS_COMMUN_AUX_PREFERENCES)){
            query = "SELECT * FROM "+TABLE_OFFRES+" WHERE "+COLUMN_OFFRE_TYPE_OFFRE+" = "+builder.idTypeOffreFromNom(PreferencesUtilisateur.getInstance(getActivity()).getTypeOffrePref())+" AND "+COLUMN_OFFRE_ETAT+" = "+COLUMN_TABLE_SYNCHRONIZED+" ORDER BY "+COLUMN_OFFRE_ID+" DESC";

        }
       Cursor cursor =database.rawQuery(query,null);

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
            offresDaos.add(new OffresDao(id,domaine,type,diplome,salaire,description,telephone,poste,date_audience,date_fin,email));


        }
        adapter.notifyDataSetChanged();
        cursor.close();
        database.close();
        builder.close();
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
