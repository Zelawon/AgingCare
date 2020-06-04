package com.test.agingcarev01.FonctionsSurveillant.Maladie;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class MaladieListViewHolder extends RecyclerView.ViewHolder {
    public TextView nomMaladie;
    public ImageView maladieItemArchiver;

    public MaladieListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomMaladie = itemView.findViewById(R.id.nomMaladieItem);
        maladieItemArchiver = itemView.findViewById(R.id.maladieItemArchiver);
    }
}
