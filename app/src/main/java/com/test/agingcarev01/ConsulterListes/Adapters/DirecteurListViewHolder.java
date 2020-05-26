package com.test.agingcarev01.ConsulterListes.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.R;

public class DirecteurListViewHolder extends RecyclerView.ViewHolder {

    public TextView nomDirec,prenomDirec,emailDirec;
    public ImageView DirecItemModifier, DirecItemArchiver;

    public DirecteurListViewHolder(@NonNull View itemView) {
        super(itemView);
        nomDirec = itemView.findViewById(R.id.DirecItemNom);
        prenomDirec = itemView.findViewById(R.id.DirecItemPrenom);
        emailDirec = itemView.findViewById(R.id.DirecItemEmail);
        DirecItemModifier = itemView.findViewById(R.id.DirecItemModifier);
        DirecItemArchiver = itemView.findViewById(R.id.DirecItemArchiver);
    }
}
