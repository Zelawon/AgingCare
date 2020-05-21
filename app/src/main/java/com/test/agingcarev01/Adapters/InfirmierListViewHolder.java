package com.test.agingcarev01.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class InfirmierListViewHolder extends RecyclerView.ViewHolder {

    public TextView nomInf,prenomInf,emailInf,sexeInf;
    public Button InfItemModifier, InfItemArchiver;

    public InfirmierListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomInf = itemView.findViewById(R.id.infItemNom);
        prenomInf = itemView.findViewById(R.id.infItemPrenom);
        emailInf = itemView.findViewById(R.id.infItemEmail);
        sexeInf = itemView.findViewById(R.id.infItemSexe);
    }
}
