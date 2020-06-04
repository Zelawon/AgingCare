package com.test.agingcarev01.FonctionsSurveillant.TensionArterielle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class TensionArterielleListViewHolder extends RecyclerView.ViewHolder {
    public TextView tensionArtItemDate, tensionArtItem, brasMesureItem, tensionArtItemNote;
    public ImageView tensionArtArchiver;

    public TensionArterielleListViewHolder(@NonNull View itemView) {
        super(itemView);
        tensionArtItemDate = itemView.findViewById(R.id.tensionArtItemDate);
        tensionArtItem = itemView.findViewById(R.id.tensionArtItem);
        brasMesureItem = itemView.findViewById(R.id.brasMesureItem);
        tensionArtItemNote = itemView.findViewById(R.id.tensionArtItemNote);
        tensionArtArchiver = itemView.findViewById(R.id.tensionArterielleArchiver);
    }
}
