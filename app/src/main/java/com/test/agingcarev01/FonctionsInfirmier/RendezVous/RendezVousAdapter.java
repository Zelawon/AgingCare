package com.test.agingcarev01.FonctionsInfirmier.RendezVous;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.Classe.RendezVousClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.RendezVousListViewHolder> {
    private Activity context;
    private List<RendezVousClasse> data;

    public static class RendezVousListViewHolder extends RecyclerView.ViewHolder {

        public TextView dateRDVItem, tempRDVItem, nomRDItem, lieuRDVItem, detailRDVItem, numeroRDVItem;
        public ImageView RDVItemArchiver;

        public RendezVousListViewHolder(@NonNull View itemView) {
            super(itemView);
            dateRDVItem = itemView.findViewById(R.id.dateRDVItem);
            tempRDVItem = itemView.findViewById(R.id.tempRDVItem);
            nomRDItem = itemView.findViewById(R.id.nomRDItem);
            lieuRDVItem = itemView.findViewById(R.id.lieuRDVItem);
            detailRDVItem = itemView.findViewById(R.id.detailRDVItem);
            numeroRDVItem = itemView.findViewById(R.id.numeroRDVItem);
            RDVItemArchiver = itemView.findViewById(R.id.RDVItemArchiver);
        }
    }

    public RendezVousAdapter(Activity context, List<RendezVousClasse> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RendezVousListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rendez_vous_resident, parent, false);
        return new RendezVousListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RendezVousListViewHolder holder, int position) {
        RendezVousClasse rendezVousClasse = data.get(position);
        holder.dateRDVItem.setText(rendezVousClasse.getDateRDV());
        holder.tempRDVItem.setText(String.valueOf(rendezVousClasse.getTimeRDV()));
        holder.nomRDItem.setText(rendezVousClasse.getNomRDV());
        holder.lieuRDVItem.setText(rendezVousClasse.getLieuRDV());
        holder.detailRDVItem.setText(rendezVousClasse.getNotesRDV());
        holder.numeroRDVItem.setText(rendezVousClasse.getNumTelRDV());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
