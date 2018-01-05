package com.codeeatersteam.dinam.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FilePath;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.net.ApiLinks;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.codeeatersteam.dinam.net.ApiLinks.API_ENVOIE_CV_URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemberSpace.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemberSpace#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberSpace extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView nom,prenoms,datenaissance,sexe,telephone,email,diplome;
    ImageView imageuser;
    boolean isImageFitToScreen;
    Button attacherCV, uploadCvBtn;

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;


    //Uri to store the image uri
    private Uri filePath;

    private OnFragmentInteractionListener mListener;

    public MemberSpace() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberSpace.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberSpace newInstance(String param1, String param2) {
        MemberSpace fragment = new MemberSpace();
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

        requestStoragePermission();

        final View rootView= inflater.inflate(R.layout.fragment_member_space, container, false);
        DbBuilder builder = new DbBuilder(rootView.getContext());
        nom = (TextView)rootView.findViewById(R.id.nomMembre);
        prenoms = (TextView)rootView.findViewById(R.id.prenomsMembre);
        datenaissance = (TextView)rootView.findViewById(R.id.datenaissanceMembre);
        sexe = (TextView)rootView.findViewById(R.id.sexeMembre);
        telephone = (TextView)rootView.findViewById(R.id.telephoneMembre);
        email = (TextView)rootView.findViewById(R.id.emailMembre);
        diplome = (TextView)rootView.findViewById(R.id.diplomeMembre);
        imageuser = (ImageView) rootView.findViewById(R.id.imageMembre);
        attacherCV = (Button)rootView.findViewById(R.id.attacherCVBtn);
        uploadCvBtn= (Button)rootView.findViewById(R.id.uploadCVBtn);

        attacherCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });
        uploadCvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCV();
            }
        });

        nom.setText(PreferencesUtilisateur.getInstance(rootView.getContext()).getNom());
        prenoms.setText(PreferencesUtilisateur.getInstance(rootView.getContext()).getPrenoms());
        datenaissance.setText(PreferencesUtilisateur.getInstance(rootView.getContext()).getDatenaissance());
        telephone.setText(PreferencesUtilisateur.getInstance(rootView.getContext()).getTelephone());
        email.setText(PreferencesUtilisateur.getInstance(rootView.getContext()).getEmail());
        sexe.setText(PreferencesUtilisateur.getInstance(rootView.getContext()).getSexe());
        diplome.setText(builder.nomDiplomeFromId(Integer.parseInt(PreferencesUtilisateur.getInstance(rootView.getContext()).getMondiplome())));

        try {
            Glide.with(rootView.getContext()).load(ApiLinks.API_IMAGES_USERS_URL+PreferencesUtilisateur.getInstance(rootView.getContext()).getMonimage() ).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageuser);
        }catch (Exception e){

            imageuser.setImageDrawable(getResources().getDrawable(R.drawable.avatar));

        }

        imageuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imageuser.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 550));
                    imageuser.setScaleType(ImageView.ScaleType.CENTER);
                }else{
                    isImageFitToScreen=true;
                    imageuser.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageuser.setScaleType(ImageView.ScaleType.FIT_XY);
                }
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

    public void uploadCV() {
        //getting name for the image
        String name = PreferencesUtilisateur.getInstance(getActivity()).getEmail();

        //getting the actual path of the image
        String path = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            path = FilePath.getPath(getContext(), filePath);
            uploadCV();
        }

        if (path == null) {

            Toast.makeText(getActivity(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(getActivity(), uploadId, API_ENVOIE_CV_URL)
                        .addFileToUpload(path, "cv") //Adding file
                        .addParameter("nom", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choisir Pdf"), PICK_PDF_REQUEST);



    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();


        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
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
