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
import com.codeeatersteam.dinam.ui.LireOffre;

import java.util.ArrayList;

/**
 * Created by pondikpa on 19/08/17.
 */

public class OffresPourMoiAdapter extends RecyclerView.Adapter<OffresPourMoiAdapter.ViewHolder> {
    private ArrayList<OffresDao> daoList;
    private Context context;

    public OffresPourMoiAdapter(ArrayList<OffresDao> daoList, Context context) {
        this.daoList = daoList;
        this.context = context;
    }

    @Override
    public OffresPourMoiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_offre,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(OffresPourMoiAdapter.ViewHolder holder, int position) {
        DbBuilder dbBuilder = new DbBuilder(context);

        final OffresDao offresDao = daoList.get(position);
        holder.domaine.setText(dbBuilder.nomDomaineFromId(offresDao.getDomaine()));
        holder.diplome.setText(dbBuilder.nomDiplomeFromId(offresDao.getDiplome()));
        holder.poste.setText(offresDao.getPoste());
        holder.contact.setText(" "+offresDao.getTelephone());
        holder.type.setText(dbBuilder.nomTypeOffreFromId(offresDao.getTypeoffre()));
        holder.salaire.setText(offresDao.getSalaire()+" FCFA");
        holder.date.setText("Du "+offresDao.getDate_audience()+" au "+offresDao.getDate_fin());

        holder.identifant.setText(String.valueOf(offresDao.getId()));

    }

    @Override
    public int getItemCount() {
        return daoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView domaine,type,poste,diplome,salaire,contact,date,identifant;
        ImageView etatsync;
        Button detailsvisite;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
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
}
