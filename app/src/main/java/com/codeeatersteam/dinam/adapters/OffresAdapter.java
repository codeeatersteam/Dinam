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

import com.codeeatersteam.dinam.R;
import com.codeeatersteam.dinam.daos.OffresDao;
import com.codeeatersteam.dinam.kernel.DbBuilder;
import com.codeeatersteam.dinam.kernel.FonctionsUtiles;
import com.codeeatersteam.dinam.ui.Conteneur;
import com.codeeatersteam.dinam.ui.LireOffre;
import com.codeeatersteam.dinam.ui.Offres;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pondikpa on 21/06/17.
 */

public class OffresAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> daoList;
    private Context context;
    private static final int OFFRE_ITEM_VIEW_TYPE =0;
    private static final int AD_VIEW_TYPE = 4;


    public OffresAdapter(List<Object> daoList, Context context) {
        this.daoList = daoList;
        this.context = context;
    }

    public class OffreItemViewHolder extends RecyclerView.ViewHolder {
        TextView domaine,type,poste,diplome,salaire,contact,date,identifant;
        ImageView etatsync;
        Button detailsvisite;
        public LinearLayout linearLayout;


        public OffreItemViewHolder(View itemView) {
            super(itemView);

            domaine = (TextView)itemView.findViewById(R.id.domainetxt);
            type = (TextView)itemView.findViewById(R.id.typetxt);
            salaire = (TextView)itemView.findViewById(R.id.salairetxt);
            contact = (TextView) itemView.findViewById(R.id.contacttxt);
            poste = (TextView) itemView.findViewById(R.id.postetxt);
            date = (TextView) itemView.findViewById(R.id.dateoffretxt);
            identifant = (TextView) itemView.findViewById(R.id.idoffretxt);
            diplome=(TextView) itemView.findViewById(R.id.iddiplometxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,LireOffre.class);
                    intent.putExtra("iden",identifant.getText().toString());
                    context.startActivity(intent);

                }
            });


        }


    }

    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder{
        NativeExpressAdViewHolder(View view){
            super(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position % Offres.ITEMS_PER_AD ==0) ? AD_VIEW_TYPE : OFFRE_ITEM_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case OFFRE_ITEM_VIEW_TYPE:
            default:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_offre,parent,false);
                return new OffreItemViewHolder(v);
            case AD_VIEW_TYPE:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ad_card,parent,false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);



        }


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewTYpe = getItemViewType(position);


        switch (viewTYpe) {
            case OFFRE_ITEM_VIEW_TYPE:
                DbBuilder dbBuilder = new DbBuilder(context);
                OffreItemViewHolder offreItemViewHolder = (OffreItemViewHolder) holder;


                OffresDao offresDao = (OffresDao) daoList.get(position);
                offreItemViewHolder.domaine.setText(dbBuilder.nomDomaineFromId(offresDao.getDomaine()));
                offreItemViewHolder.diplome.setText(dbBuilder.nomDiplomeFromId(offresDao.getDiplome()));
                offreItemViewHolder.poste.setText(offresDao.getPoste());
                offreItemViewHolder.contact.setText(" " + offresDao.getTelephone());
                offreItemViewHolder.type.setText(dbBuilder.nomTypeOffreFromId(offresDao.getTypeoffre()));
                offreItemViewHolder.salaire.setText(offresDao.getSalaire() + " FCFA");
                offreItemViewHolder.date.setText("Du " + offresDao.getDate_audience() + " au " + offresDao.getDate_fin());

                offreItemViewHolder.identifant.setText(String.valueOf(offresDao.getId()));
                break;
            case AD_VIEW_TYPE:
                default:
                NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder)holder;
                NativeExpressAdView adView = (NativeExpressAdView)daoList.get(position);
                ViewGroup adCardView = (ViewGroup)nativeExpressHolder.itemView;
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }

                    // Add the Native Express ad to the native express ad view.
                    if (FonctionsUtiles.isConnectedInternet(context)) {
                        adCardView.addView(adView);
                    }




        }
    }

    @Override
    public int getItemCount() {
        return daoList.size();
    }






}
