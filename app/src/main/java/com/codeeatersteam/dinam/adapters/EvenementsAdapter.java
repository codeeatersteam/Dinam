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
import com.codeeatersteam.dinam.daos.EvenementsDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.net.ApiLinks;
import com.codeeatersteam.dinam.ui.Evenements;
import com.codeeatersteam.dinam.ui.LireEvenement;

import java.util.ArrayList;

/**
 * Created by pondikpa on 21/06/17.
 */

public class EvenementsAdapter extends RecyclerView.Adapter<EvenementsAdapter.ViewHolder> {
    private ArrayList<EvenementsDao> daoList;
    private Context context;

    public EvenementsAdapter(ArrayList<EvenementsDao> daoList, Context context) {
        this.daoList = daoList;
        this.context = context;
    }

    @Override
    public EvenementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_evenement,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EvenementsAdapter.ViewHolder holder, int position) {
        DbBuilder dbBuilder = new DbBuilder(context);

        final EvenementsDao evenementsDao= daoList.get(position);
        holder.id.setText(String.valueOf(evenementsDao.getId()));
        holder.nom.setText(evenementsDao.getNom());
        holder.lieu.setText(evenementsDao.getLieu());
        holder.type.setText(dbBuilder.nomTypeEvenementFromId(evenementsDao.getTypeevenement()));
        holder.date.setText(evenementsDao.getDate_evenement());
     try{

        Glide.with(context).load(ApiLinks.API_IMAGES_EVENEMENTS_URL+evenementsDao.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageevenement);
     }catch (IllegalArgumentException e){

     }
    }

    @Override
    public int getItemCount() {
        return daoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageevenement;
        TextView nom,lieu,date,type,id;
        public ViewHolder(View itemView) {
            super(itemView);
            imageevenement = (ImageView)itemView.findViewById(R.id.imageevent) ;
            nom =(TextView)itemView.findViewById(R.id.nomevent);
            lieu=(TextView)itemView.findViewById(R.id.lieuevent);
            date=(TextView)itemView.findViewById(R.id.dateevent);
            type=(TextView)itemView.findViewById(R.id.typeevent);
            id = (TextView)itemView.findViewById(R.id.ideventtxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,LireEvenement.class);
                    intent.putExtra("identifiant",id.getText().toString());
                    context.startActivity(intent);

                }
            });
        }
    }
}