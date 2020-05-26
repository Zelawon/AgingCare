package com.test.agingcarev01.ConsulterListes.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public  class ResidentListViewHolder extends RecyclerView.ViewHolder {
    public TextView nomRes,prenomRes,sexeRes;
    public ImageView ResItemModifier, ResItemArchiver;

    public ResidentListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomRes = itemView.findViewById(R.id.resItemNom);
        prenomRes = itemView.findViewById(R.id.resItemPrenom);
        sexeRes = itemView.findViewById(R.id.resItemSexe);
        ResItemModifier = itemView.findViewById(R.id.resItemModifier);
        ResItemArchiver = itemView.findViewById(R.id.resItemArchiver);
    }
}
