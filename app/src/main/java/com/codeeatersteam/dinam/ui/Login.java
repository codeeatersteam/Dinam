package com.codeeatersteam.dinam.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.TypeLieuxDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.kernel.RequestHandler;
import com.codeeatersteam.dinam.net.ApiLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_IMAGE;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.ouvrirActivite;
import static com.codeeatersteam.dinam.kernel.FonctionsUtiles.userConnected;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView loginCardBtn;
    EditText email, password;

    TextView inscription;

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        final View rootView= inflater.inflate(R.layout.fragment_login, container, false);


        email=(EditText)rootView.findViewById(R.id.emailLogin);
        password=(EditText)rootView.findViewById(R.id.PasswordLogin);
        loginCardBtn=(CardView)rootView.findViewById(R.id.loginCardBtn);
        loginCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (FonctionsUtiles.isConnectedInternet(v.getContext())) {
                    if (verifierValeurs()) {
                        StringRequest request = new StringRequest(Request.Method.POST, ApiLinks.API_LOGIN_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject objetfinal = new JSONObject(response);
                                    DbBuilder builder = new DbBuilder(v.getContext());
                                    SQLiteDatabase database = builder.getWritableDatabase();
                                    PreferencesUtilisateur.getInstance(v.getContext()).setId(objetfinal.getString("id"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setNom(objetfinal.getString("name"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setPrenoms(objetfinal.getString("prenoms"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setDatenaissance(objetfinal.getString("datenaissance"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setTelephone(objetfinal.getString("telephone"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setEmail(objetfinal.getString("email"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setMondiplome(objetfinal.getString("id_diplomes"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setMonimage(objetfinal.getString("image"));
                                    PreferencesUtilisateur.getInstance(v.getContext()).setSexe(objetfinal.getString("sexe"));

                                    try {
                                        Glide.with(v.getContext()).load(ApiLinks.API_IMAGES_USERS_URL+objetfinal.getString("image") ).downloadOnly(100, 100);
                                    }catch (IllegalArgumentException e){

                                    }
                                    Toasty.success(v.getContext(), "Connexion réussie", Toast.LENGTH_LONG, true).show();

                                    ouvrirActivite(v.getContext(),ConteneurPrincipal.class);




                                } catch (JSONException e) {
                                    Toasty.error(v.getContext(), "Email ou mot de passe incorrects.", Toast.LENGTH_LONG, true).show();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toasty.error(v.getContext(), "Verifiez votre connexion internet.", Toast.LENGTH_LONG, true).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("email", email.getText().toString());
                                params.put("password", password.getText().toString());

                                return params;

                            }
                        };
                        RequestHandler.getInstance(v.getContext()).addToRequestQueue(request);
                    }
                }
            }
        });

        inscription = (TextView)rootView.findViewById(R.id.goRegister);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FonctionsUtiles.ouvrirActivite(v.getContext(),Register.class);
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

    private boolean verifierValeurs(){
        if (!FonctionsUtiles.controlchampsvide(email) && !FonctionsUtiles.controlchampsvide(password) && !FonctionsUtiles.verifierEmail(email)){
            return true;
        }
        return false;


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
