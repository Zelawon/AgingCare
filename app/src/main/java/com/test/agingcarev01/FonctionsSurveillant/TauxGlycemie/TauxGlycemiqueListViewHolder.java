package com.test.agingcarev01.FonctionsSurveillant.TauxGlycemie;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class TauxGlycemiqueListViewHolder extends RecyclerView.ViewHolder {
    public TextView tauxGlyceItemDate, tauxGlycemiqueItem, meureItem, tauxItemNote;
    public ImageView tauxItemArchiver;

    public TauxGlycemiqueListViewHolder(@NonNull View itemView) {
        super(itemView);
        tauxGlyceItemDate = itemView.findViewById(R.id.tauxGlyceItemDate);
        tauxGlycemiqueItem = itemView.findViewById(R.id.tauxGlycemiqueItem);
        meureItem = itemView.findViewById(R.id.meureItem);
        tauxItemNote = itemView.findViewById(R.id.tauxItemNote);
        tauxItemArchiver = itemView.findViewById(R.id.tauxItemArchiver);
    }
}
