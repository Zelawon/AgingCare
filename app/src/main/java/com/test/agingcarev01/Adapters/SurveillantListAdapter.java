package com.test.agingcarev01.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.Classe.SurveillantClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class SurveillantListAdapter extends RecyclerView.Adapter<SurveillantListViewHolder> {

    private Activity context;
    List<SurveillantClasse> data;


    public SurveillantListAdapter(Activity context, List<SurveillantClasse> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public SurveillantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_surveillant,parent,false);

        return new SurveillantListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveillantListViewHolder holder, int position) {
        SurveillantClasse survClasse = data.get(position);
        holder.emailSurv.setText(survClasse.getEmail());
        holder.nomSurv.setText(survClasse.getNom());
        holder.prenomSurv.setText(survClasse.getPrenom());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
