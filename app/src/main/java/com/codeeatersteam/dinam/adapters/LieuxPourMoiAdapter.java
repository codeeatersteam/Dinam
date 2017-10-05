package com.codeeatersteam.dinam.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.LieuxDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.net.ApiLinks;
import com.codeeatersteam.dinam.ui.LireLieu;

import java.util.ArrayList;

/**
 * Created by pondikpa on 21/06/17.
 */

public class LieuxPourMoiAdapter extends RecyclerView.Adapter<LieuxPourMoiAdapter.ViewHolder> {
    private ArrayList<LieuxDao> daoList;
    private Context context;

    public LieuxPourMoiAdapter(ArrayList<LieuxDao> daoList, Context context) {
        this.daoList = daoList;
        this.context = context;
    }

    @Override
    public LieuxPourMoiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lieu,parent,false);
        return new LieuxPourMoiAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LieuxPourMoiAdapter.ViewHolder holder, int position) {
        DbBuilder dbBuilder = new DbBuilder(context);

        final LieuxDao lieuxDao= daoList.get(position);
        holder.nom.setText(lieuxDao.getNom());
        System.out.println(dbBuilder.nomTypeLieuFromId(lieuxDao.getTypelieu()));
        holder.type.setText(dbBuilder.nomTypeLieuFromId(lieuxDao.getTypelieu()));

        holder.adresse.setText(lieuxDao.getAdresse());
        holder.telephone.setText(lieuxDao.getTelephone());
        holder.siteweb.setText(lieuxDao.getSiteweb());
        holder.id.setText(String.valueOf(lieuxDao.getId()));
        try{

        Glide.with(context).load(ApiLinks.API_IMAGES_LIEUX_URL+lieuxDao.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imagelieu);
        }catch (IllegalArgumentException e){

        }
    }

    @Override
    public int getItemCount() {
        return daoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagelieu;
        TextView type,nom,adresse,telephone,siteweb,id;
        public ViewHolder(View itemView) {
            super(itemView);
            imagelieu = (ImageView)itemView.findViewById(R.id.imagelieu) ;
            type=(TextView)itemView.findViewById(R.id.typelieu);
            nom =(TextView)itemView.findViewById(R.id.nomlieu);
            adresse=(TextView)itemView.findViewById(R.id.adresselieu);
            telephone=(TextView)itemView.findViewById(R.id.contactlieu);
            siteweb=(TextView)itemView.findViewById(R.id.sitelieu);
            id = (TextView)itemView.findViewById(R.id.idLieutxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,LireLieu.class);
                    intent.putExtra("identifiant",id.getText().toString());
                    context.startActivity(intent);

                }
            });
        }
    }
}
