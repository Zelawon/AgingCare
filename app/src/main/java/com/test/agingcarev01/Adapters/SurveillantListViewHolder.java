package com.test.agingcarev01.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class SurveillantListViewHolder extends RecyclerView.ViewHolder {

    public TextView nomSurv,prenomSurv,emailSurv;
    public Button survItemModifier,survItemArchiver;

    public SurveillantListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomSurv = itemView.findViewById(R.id.survItemNom);
        prenomSurv = itemView.findViewById(R.id.survItemPrenom);
        emailSurv = itemView.findViewById(R.id.survItemEmail);
    }
}
