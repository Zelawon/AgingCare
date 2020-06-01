package com.test.agingcarev01.FonctionsSurveillant.Poids;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class PoidsListViewHolder extends RecyclerView.ViewHolder{
    public TextView poidsItemDate,poidsItemPoids,poidsItemNote;
    public ImageView poidsItemArchiver;

    public PoidsListViewHolder(@NonNull View itemView) {
        super(itemView);
        poidsItemDate = itemView.findViewById(R.id.poidsItemDate);
        poidsItemPoids = itemView.findViewById(R.id.poidsItemPoids);
        poidsItemNote = itemView.findViewById(R.id.poidsItemNote);
        poidsItemArchiver = itemView.findViewById(R.id.poidsItemArchiver);
    }
}
