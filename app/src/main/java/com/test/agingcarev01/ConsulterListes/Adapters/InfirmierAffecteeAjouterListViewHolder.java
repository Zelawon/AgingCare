package com.test.agingcarev01.ConsulterListes.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class InfirmierAffecteeAjouterListViewHolder extends RecyclerView.ViewHolder{
    public TextView nomInf,prenomInf,sexeInf;
    public ImageView infAffecterAjouterItemCheck;

    public InfirmierAffecteeAjouterListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomInf = itemView.findViewById(R.id.infAffecterAjouterItemNom);
        prenomInf = itemView.findViewById(R.id.infAffecterAjouterItemPrenom);
        sexeInf = itemView.findViewById(R.id.infAffecterAjouterItemSexe);
        infAffecterAjouterItemCheck = itemView.findViewById(R.id.infAffecterAjouterItemCheck);
    }
}
