package com.test.agingcarev01.FonctionsSurveillant.Traitement;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class TraitementListViewHolder extends RecyclerView.ViewHolder {
    public TextView nomTraitementItem, typeTraitementItem, doseTraitementItem,
            uniteTraitementItem, dateDebutTraitementItem, dateFinTraitementItem,
            repetitionTraitementItem,temp1TraitementItem,temp2TraitementItem,
            temp3TraitementItem,temp4TraitementItem,temp5TraitementItem,temp6TraitementItem;
    public ImageView TraitementItemArchiver;

    public TraitementListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomTraitementItem = itemView.findViewById(R.id.nomTraitementItem);
        typeTraitementItem = itemView.findViewById(R.id.typeTraitementItem);
        doseTraitementItem = itemView.findViewById(R.id.doseTraitementItem);
        uniteTraitementItem = itemView.findViewById(R.id.uniteTraitementItem);
        dateDebutTraitementItem = itemView.findViewById(R.id.dateDebutTraitementItem);
        dateFinTraitementItem = itemView.findViewById(R.id.dateFinTraitementItem);
        repetitionTraitementItem = itemView.findViewById(R.id.repetitionTraitementItem);
        temp1TraitementItem = itemView.findViewById(R.id.temp1TraitementItem);
        temp2TraitementItem = itemView.findViewById(R.id.temp2TraitementItem);
        temp3TraitementItem = itemView.findViewById(R.id.temp3TraitementItem);
        temp4TraitementItem = itemView.findViewById(R.id.temp4TraitementItem);
        temp5TraitementItem = itemView.findViewById(R.id.temp5TraitementItem);
        temp6TraitementItem = itemView.findViewById(R.id.temp6TraitementItem);
        TraitementItemArchiver = itemView.findViewById(R.id.TraitementItemArchiver);
    }
}
