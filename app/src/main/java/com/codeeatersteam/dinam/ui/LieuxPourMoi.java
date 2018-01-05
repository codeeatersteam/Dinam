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
import com.codeeatersteam.dinam.adapters.LieuxAdapter;
import com.codeeatersteam.dinam.daos.LieuxDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;

import java.util.ArrayList;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ADRESSE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_IMAGE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_SITE_WEB;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_TYPE_LIEU;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_LIEUX_EN_LOCAL_APPROUVES;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_LIEUX;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LieuxPourMoi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LieuxPourMoi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LieuxPourMoi extends Fragment {
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
    LieuxAdapter adapter;
    ArrayList<LieuxDao> lieuxDaos;

    public LieuxPourMoi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LieuxPourMoi.
     */
    // TODO: Rename and change types and number of parameters
    public static LieuxPourMoi newInstance(String param1, String param2) {
        LieuxPourMoi fragment = new LieuxPourMoi();
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
        final View rootView= inflater .inflate(R.layout.fragment_lieux_pour_moi, container, false);
        proposition = (Button) rootView.findViewById(R.id.proposerPersonalisationLieuBtn);
        try{
            if (PreferencesUtilisateur.getInstance(rootView.getContext()).getPreferencesTypeLieux().size()<=0){
                proposition.setVisibility(View.VISIBLE);
                proposition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FonctionsUtiles.ouvrirActivite(rootView.getContext(),Personnalisation.class);
                    }
                });
            }else {
                proposition.setVisibility(View.INVISIBLE);
                recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_lieux_pour_moi);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                lieuxDaos = new ArrayList<>();
                adapter = new LieuxAdapter(rootView.getContext(),lieuxDaos);
                recyclerView.setAdapter(adapter);
                lieuxPourMoiEnLocal();
            }

        }catch (NullPointerException ex){
            proposition.setVisibility(View.VISIBLE);
            proposition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FonctionsUtiles.ouvrirActivite(rootView.getContext(),Personnalisation.class);
                }
            });

        }



        return rootView;

    }

    public void lieuxPourMoiEnLocal(){
        ArrayList<String>lieuxSelectionnes = PreferencesUtilisateur.getInstance(this.getContext()).getPreferencesTypeLieux();
        int id,etat,typelieu;
        String nom,telephone,adresse,description,siteweb,image,created_at,updated_at;
        lieuxDaos.clear();
        DbBuilder builder = new DbBuilder(this.getContext());
        SQLiteDatabase database = builder.getReadableDatabase();
        String query =  "SELECT * FROM " + TABLE_LIEUX ;

        Cursor cursor = database.rawQuery(LISTES_LIEUX_EN_LOCAL_APPROUVES,null);

        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ID));

            typelieu = cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_TYPE_LIEU));
            etat = cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ETAT));
            nom = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_NOM));
            image = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_IMAGE));
            description = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_DESCRIPTION));
            telephone = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_TELEPHONE));
            adresse = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_ADRESSE));
            siteweb = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_SITE_WEB));
            created_at  = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_CREATED_AT));
            updated_at  = cursor.getString(cursor.getColumnIndex(COLUMN_LIEU_UPDATED_AT));
            for (int i=0;i<lieuxSelectionnes.size();i++){
                if (builder.nomTypeLieuFromId(typelieu).equals(lieuxSelectionnes.get(i))){
                    lieuxDaos.add(new LieuxDao( id,  etat,  typelieu,  nom,  telephone,  adresse,
                            description,  siteweb,  image,  created_at,  updated_at));
                }
            }



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
