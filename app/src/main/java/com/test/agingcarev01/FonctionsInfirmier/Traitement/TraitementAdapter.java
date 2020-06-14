package com.test.agingcarev01.FonctionsInfirmier.Traitement;

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
import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.Classe.TraitementClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class TraitementAdapter extends RecyclerView.Adapter<TraitementAdapter.TraitementListViewHolder> {
    private Activity context;
    private List<TraitementClasse> data;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onIconClick(int position);
    }

    public static class TraitementListViewHolder extends RecyclerView.ViewHolder {

        public TextView nomTraitementItem, typeTraitementItem, doseTraitementItem,prenomResidentTraitementItem,
                uniteTraitementItem, nomResidentTraitementItem, temp1TraitementItem, temp2TraitementItem,
                temp3TraitementItem, temp4TraitementItem, temp5TraitementItem, temp6TraitementItem;
        public ImageView TraitementItemConsulter;

        public TraitementListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            prenomResidentTraitementItem=itemView.findViewById(R.id.prenomResidentTraitementItem);
            nomResidentTraitementItem = itemView.findViewById(R.id.nomResidentTraitementItem);
            nomTraitementItem = itemView.findViewById(R.id.nomTraitementItem);
            typeTraitementItem = itemView.findViewById(R.id.typeTraitementItem);
            doseTraitementItem = itemView.findViewById(R.id.doseTraitementItem);
            uniteTraitementItem = itemView.findViewById(R.id.uniteTraitementItem);
            temp1TraitementItem = itemView.findViewById(R.id.temp1TraitementItem);
            temp2TraitementItem = itemView.findViewById(R.id.temp2TraitementItem);
            temp3TraitementItem = itemView.findViewById(R.id.temp3TraitementItem);
            temp4TraitementItem = itemView.findViewById(R.id.temp4TraitementItem);
            temp5TraitementItem = itemView.findViewById(R.id.temp5TraitementItem);
            temp6TraitementItem = itemView.findViewById(R.id.temp6TraitementItem);

            TraitementItemConsulter = itemView.findViewById(R.id.TraitementItemConsulter);
            TraitementItemConsulter.setOnClickListener(new View.OnClickListener() {
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

    public TraitementAdapter(Activity context, List<TraitementClasse> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TraitementListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_traitement_infirmier, parent, false);
        return new TraitementListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final TraitementListViewHolder holder, int position) {
        TraitementClasse traitementClasse = data.get(position);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Resident");
        Query query = ref.orderByChild("id").equalTo(Integer.valueOf(traitementClasse.getIdResident()));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                    ResidentClasse residentClasse = tierSnapshot.getValue(ResidentClasse.class);
                    holder.nomResidentTraitementItem.setText(residentClasse.getNom());
                    holder.prenomResidentTraitementItem.setText(residentClasse.getPrenom());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.nomTraitementItem.setText(traitementClasse.getNom());
        holder.typeTraitementItem.setText(String.valueOf(traitementClasse.getType()));
        holder.doseTraitementItem.setText(String.valueOf(traitementClasse.getDose()));
        holder.uniteTraitementItem.setText(String.valueOf(traitementClasse.getUnite()));
        holder.temp1TraitementItem.setText(traitementClasse.getTemp1());
        holder.temp2TraitementItem.setText(traitementClasse.getTemp2());
        holder.temp3TraitementItem.setText(traitementClasse.getTemp3());
        holder.temp4TraitementItem.setText(traitementClasse.getTemp4());
        holder.temp5TraitementItem.setText(traitementClasse.getTemp5());
        holder.temp6TraitementItem.setText(traitementClasse.getTemp6());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
