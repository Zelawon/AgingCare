package com.test.agingcarev01.FonctionsInfirmier.RendezVous;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.RendezVousClasse;
import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.RendezVousListViewHolder> {
    private Activity context;
    private List<RendezVousClasse> data;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onIconClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class RendezVousListViewHolder extends RecyclerView.ViewHolder {

        public TextView dateRDVItemInf, tempRDVItemInf, nomRDItemInf, nomResRDV, prenomResRDV;
        public ImageView viewInfRDV;

        public RendezVousListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            dateRDVItemInf = itemView.findViewById(R.id.dateRDVItemInf);
            tempRDVItemInf = itemView.findViewById(R.id.tempRDVItemInf);
            nomRDItemInf = itemView.findViewById(R.id.nomRDItemInf);
            nomResRDV = itemView.findViewById(R.id.nomResRDV);
            prenomResRDV = itemView.findViewById(R.id.prenomResRDV);

            viewInfRDV = itemView.findViewById(R.id.viewInfRDV);
            viewInfRDV.setOnClickListener(new View.OnClickListener() {
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

    public RendezVousAdapter(Activity context, List<RendezVousClasse> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RendezVousListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rendez_vous_infirmier, parent, false);
        return new RendezVousListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RendezVousListViewHolder holder, int position) {
        RendezVousClasse rendezVousClasse = data.get(position);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Resident");
        Query query = ref.orderByChild("id").equalTo(Integer.valueOf(rendezVousClasse.getIdResident()));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                    ResidentClasse residentClasse = tierSnapshot.getValue(ResidentClasse.class);
                    holder.nomResRDV.setText(residentClasse.getNom());
                    holder.prenomResRDV.setText(residentClasse.getPrenom());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.dateRDVItemInf.setText(rendezVousClasse.getDateRDV());
        holder.tempRDVItemInf.setText(String.valueOf(rendezVousClasse.getTimeRDV()));
        holder.nomRDItemInf.setText(rendezVousClasse.getNomRDV());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
