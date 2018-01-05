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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.baoyz.widget.PullRefreshLayout;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.adapters.CvAdapter;
import com.codeeatersteam.dinam.adapters.OffresAdapter;
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConteneurCv.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConteneurCv#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConteneurCv extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PullRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    CvAdapter adapter;
    ArrayList<OffresDao> offresDaos;
    Spinner domainesCv;

    private OnFragmentInteractionListener mListener;

    public ConteneurCv() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConteneurCv.
     */
    // TODO: Rename and change types and number of parameters
    public static ConteneurCv newInstance(String param1, String param2) {
        ConteneurCv fragment = new ConteneurCv();
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
        View rootView = inflater.inflate(R.layout.fragment_conteneur__cv, container, false);
        refreshLayout = (PullRefreshLayout)rootView.findViewById(R.id.pullRefreshCv);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_cv);
        domainesCv = (Spinner) rootView.findViewById(R.id.spinnerCvDomaine);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        offresDaos = new ArrayList<>();
        adapter = new CvAdapter(offresDaos,rootView.getContext());
        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                recupererCV();
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);

                    }
                },3000);

            }

        });



        //on ajoute les domaines
        DbBuilder dbBuilder = new DbBuilder(rootView.getContext());
        final ArrayList<String> listeDom  = dbBuilder.listeDesDomaines();
        listeDom.add("Tous les domaines");
        ArrayAdapter<String> adapterdomaine= new ArrayAdapter<String>(rootView.getContext(),R.layout.spinner_layout,R.id.spinner_txt,listeDom);
        domainesCv.setAdapter(adapterdomaine);
        domainesCv.setSelection(domainesCv.getCount()-1);
        recupererCV();

        return rootView;
    }





    public void recupererCV(){

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
