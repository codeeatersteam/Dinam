package com.codeeatersteam.dinam.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.kernel.PreferencesUtilisateur;
import com.codeeatersteam.dinam.net.ApiLinks;
import com.codeeatersteam.dinam.ui.LireCv;
import com.codeeatersteam.dinam.ui.LireOffre;

import java.util.ArrayList;

/**
 * Created by pondikpa on 21/06/17.
 */

public class CvAdapter extends RecyclerView.Adapter<CvAdapter.ViewHolder> {
    private ArrayList<OffresDao> daoList;
    private Context context;

    public CvAdapter(ArrayList<OffresDao> daoList, Context context) {
        this.daoList = daoList;
        this.context = context;
    }

    @Override
    public CvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cv,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(CvAdapter.ViewHolder holder, int position) {
        DbBuilder dbBuilder = new DbBuilder(context);

        final OffresDao offresDao = daoList.get(position);

        holder.poste.setText(offresDao.getPoste());
        try {
            Glide.with(context).load(ApiLinks.API_IMAGES_USERS_URL+ PreferencesUtilisateur.getInstance(context).getMonimage() ).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageUser);
        }catch (Exception e){



        }


    }

    @Override
    public int getItemCount() {
        return daoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView domaine,type,poste,diplome,salaire,contact,date,identifant;
        ImageView imageUser;
        Button consulterCv;
        public LinearLayout linearLayout;


        public ViewHolder(final View itemView) {
            super(itemView);


            poste = (TextView) itemView.findViewById(R.id.nomCVTxt);
            imageUser = (ImageView) itemView.findViewById(R.id.imageCVuser);
            consulterCv = (Button)itemView.findViewById(R.id.btnConsulterCV);

            consulterCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FonctionsUtiles.ouvrirActivite(itemView.getContext(), LireCv.class);
                }
            });



        }


    }



}
