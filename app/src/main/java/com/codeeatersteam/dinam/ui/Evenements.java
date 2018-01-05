package com.codeeatersteam.dinam.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.adapters.EvenementsAdapter;
import com.codeeatersteam.dinam.daos.EvenementsDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.net.RecupererEvenements;
import com.codeeatersteam.dinam.net.RecupererTypesEvenement;

import java.util.ArrayList;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DATE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_IMAGE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_LIEU;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TYPE_EVENEMENT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_EVENEMENT_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_EVENEMENTS_EN_LOCAL_APPROUVES;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Evenements.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Evenements#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Evenements extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    EvenementsAdapter adapter;
    ArrayList<EvenementsDao> evenementsDaos;
    SwipeRefreshLayout layoutswipe;
    PullRefreshLayout refreshLayout;



    private OnFragmentInteractionListener mListener;

    public Evenements() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Evenements.
     */
    // TODO: Rename and change types and number of parameters
    public static Evenements newInstance(String param1, String param2) {
        Evenements fragment = new Evenements();
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

    public void  evenenementsEnLocal(){
        int id,type,etat;
        String nom,telephone, image,description,dateevenement,lieu,siteweb,created_at,updated_at;
        evenementsDaos.clear();
        DbBuilder builder = new DbBuilder(this.getContext());
        SQLiteDatabase database = builder.getReadableDatabase();
        Cursor cursor = database.rawQuery(LISTES_EVENEMENTS_EN_LOCAL_APPROUVES,null);

        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ID));

            type = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_TYPE_EVENEMENT));
            etat = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ETAT));
            nom = cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_NOM));
            telephone = cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_TELEPHONE));
            image = cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_IMAGE));
            description = cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_DESCRIPTION));
            dateevenement = cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_DATE));
            lieu = cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_LIEU));
            created_at  = cursor.getString(cursor.getColumnIndex(COLUMN_EVENEMENT_CREATED_AT));
            updated_at  = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_EVENEMENT_UPDATED_AT));
            evenementsDaos.add(new EvenementsDao(id,etat,type,nom,telephone,image,dateevenement,description,lieu,created_at,updated_at));


        }
        adapter.notifyDataSetChanged();
        cursor.close();
        database.close();
        builder.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView= inflater .inflate(R.layout.evenements, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_evenements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        evenementsDaos = new ArrayList<>();
        adapter = new EvenementsAdapter(evenementsDaos,rootView.getContext());
        recyclerView.setAdapter(adapter);
        evenenementsEnLocal();

        refreshLayout = (PullRefreshLayout)rootView.findViewById(R.id.pullRefreshEvents);


        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (FonctionsUtiles.isConnectedInternet(rootView.getContext())){

                    RecupererTypesEvenement recupererType = new RecupererTypesEvenement(rootView.getContext());
                    recupererType.execute();
                    RecupererEvenements recupererEvenements = new RecupererEvenements(rootView.getContext());
                    recupererEvenements.execute();

                    evenenementsEnLocal();
                }

                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);

                    }
                },5000);

            }

        });

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