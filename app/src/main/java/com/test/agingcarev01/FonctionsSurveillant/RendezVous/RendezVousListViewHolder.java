package com.test.agingcarev01.FonctionsSurveillant.RendezVous;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class RendezVousListViewHolder extends RecyclerView.ViewHolder {

    public TextView dateRDVItem, tempRDVItem, nomRDItem, lieuRDVItem, detailRDVItem, numeroRDVItem;
    public ImageView RDVItemArchiver;

    public RendezVousListViewHolder(@NonNull View itemView) {
        super(itemView);
        dateRDVItem = itemView.findViewById(R.id.dateRDVItem);
        tempRDVItem = itemView.findViewById(R.id.tempRDVItem);
        nomRDItem = itemView.findViewById(R.id.nomRDItem);
        lieuRDVItem = itemView.findViewById(R.id.lieuRDVItem);
        detailRDVItem = itemView.findViewById(R.id.detailRDVItem);
        numeroRDVItem = itemView.findViewById(R.id.numeroRDVItem);
        RDVItemArchiver = itemView.findViewById(R.id.RDVItemArchiver);
    }
}
