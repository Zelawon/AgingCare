package com.test.agingcarev01.ConsulterListes.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class ResidentListAdapter extends RecyclerView.Adapter<ResidentListViewHolder> {

    private Activity context;
    List<ResidentClasse> data;


    public ResidentListAdapter(Activity context, List<ResidentClasse> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ResidentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_resident,parent,false);

        return new ResidentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResidentListViewHolder holder, int position) {
        ResidentClasse resClasse = data.get(position);
        holder.nomRes.setText(resClasse.getNom());
        holder.prenomRes.setText(resClasse.getPrenom());
        holder.sexeRes.setText((resClasse.getSexeRes()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
