package com.test.agingcarev01.FonctionsSurveillant.AffecterInfirmier;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class InfirmierAffecteeSupprimerListViewHolder extends RecyclerView.ViewHolder{
    public TextView nomInf,prenomInf,sexeInf;
    public ImageView infAffecterSupprimerItemCheck;

    public InfirmierAffecteeSupprimerListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomInf = itemView.findViewById(R.id.infAffecterSupprimerItemNom);
        prenomInf = itemView.findViewById(R.id.infAffecterSupprimerItemPrenom);
        sexeInf = itemView.findViewById(R.id.infAffecterSupprimerItemSexe);
        infAffecterSupprimerItemCheck = itemView.findViewById(R.id.infAffecterSupprimerItemCheck);
    }
}
