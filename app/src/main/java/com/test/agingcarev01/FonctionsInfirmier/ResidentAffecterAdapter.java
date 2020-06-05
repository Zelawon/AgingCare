package com.test.agingcarev01.FonctionsInfirmier;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class ResidentAffecterAdapter extends RecyclerView.Adapter<ResidentAffecterAdapter.ResidentAffecterListViewHolder> {
    private Activity context;
    private List<ResidentClasse> data;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onIconClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ResidentAffecterListViewHolder extends RecyclerView.ViewHolder {

        public TextView resAffecterItemNom, resAffecterItemPrenom, resAffecterItemSexe;
        public ImageView resAffecterItemCheck;

        public ResidentAffecterListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            resAffecterItemNom = itemView.findViewById(R.id.resAffecterItemNom);
            resAffecterItemPrenom = itemView.findViewById(R.id.resAffecterItemPrenom);
            resAffecterItemSexe = itemView.findViewById(R.id.resAffecterItemSexe);
            resAffecterItemCheck = itemView.findViewById(R.id.resAffecterItemCheck);

            resAffecterItemCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onIconClick(position);
                        }
                    }
                }
            });
        }
    }

    public ResidentAffecterAdapter(Activity context, List<ResidentClasse> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ResidentAffecterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_resident_affecter, parent, false);
        return new ResidentAffecterListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ResidentAffecterListViewHolder holder, int position) {
        ResidentClasse residentClasse = data.get(position);
        holder.resAffecterItemNom.setText(residentClasse.getNom());
        holder.resAffecterItemPrenom.setText((residentClasse.getPrenom()));
        holder.resAffecterItemSexe.setText(residentClasse.getSexeRes());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
