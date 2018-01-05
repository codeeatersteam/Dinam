package com.codeeatersteam.dinam.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codeeatersteam.dinam.R;

import static com.codeeatersteam.dinam.kernel.Core.APP_VERSION;
import static com.codeeatersteam.dinam.kernel.Core.TEAM_NAME;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.affecterPolice;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConteneurAbout.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConteneurAbout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConteneurAbout extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView logotext,teamtext,versiontext;

    private OnFragmentInteractionListener mListener;

    public ConteneurAbout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConteneurAbout.
     */
    // TODO: Rename and change types and number of parameters
    public static ConteneurAbout newInstance(String param1, String param2) {
        ConteneurAbout fragment = new ConteneurAbout();
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
        final View rootView= inflater .inflate(R.layout.fragment_conteneur_about, container, false);

        teamtext= (TextView)rootView.findViewById(R.id.about_app_team);
        versiontext= (TextView)rootView.findViewById(R.id.about_app_version);
        logotext = (TextView)rootView.findViewById(R.id.about_app_name);
        logotext.setTextSize(45);

        affecterPolice(rootView.getContext(),logotext);

        teamtext.setText(TEAM_NAME);
        versiontext.setText(APP_VERSION);
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
