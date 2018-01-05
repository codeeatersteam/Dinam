package com.codeeatersteam.dinam.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.battleent.ribbonviews.RibbonLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.LieuxDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.net.ApiLinks;
import com.codeeatersteam.dinam.ui.LireLieu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pondikpa on 18/11/17.
 */

 class customViewHolder extends RecyclerView.ViewHolder{
    RibbonLayout ribbonLayout;
    ImageView imageView;
    TextView id;

    public customViewHolder(View itemView) {
        super(itemView);

        ribbonLayout=(RibbonLayout)itemView.findViewById(R.id.lieuribbonlayout);
        imageView=(ImageView)itemView.findViewById(R.id.lieuribbonimage);

        id = (TextView)itemView.findViewById(R.id.idLieutxt);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),LireLieu.class);
                intent.putExtra("identifiant",id.getText().toString());
                v.getContext().startActivity(intent);

            }
        });
    }
}


public class LieuxAdapter extends RecyclerView.Adapter<customViewHolder>{
    Context ctx;
    ArrayList<LieuxDao> lieuxDao;

    public LieuxAdapter(Context ctx, ArrayList<LieuxDao> lieuxDao) {
        this.ctx = ctx;
        this.lieuxDao = lieuxDao;
    }

    @Override
    public customViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.lieux_ribbon,parent,false);
        return new customViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(customViewHolder holder, int position) {
        DbBuilder builder = new DbBuilder(ctx);
        LieuxDao lieu = lieuxDao.get(position);
        holder.ribbonLayout.setShowBottom(true);
        holder.ribbonLayout.setShowHeader(true);
        holder.ribbonLayout.setHeaderText(builder.nomTypeLieuFromId(lieu.getTypelieu()));
        holder.ribbonLayout.setBottomText(lieu.getNom());
        holder.id.setText(String.valueOf(lieu.getId()));
        Glide.with(ctx).load(ApiLinks.API_IMAGES_LIEUX_URL+lieu.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return lieuxDao.size();
    }
}
