package com.test.agingcarev01.ConsulterListes.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.Classe.DirecteurClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class DirecteurListAdapter extends RecyclerView.Adapter<DirecteurListViewHolder>{

    private Activity context;
    List<DirecteurClasse> data;

    public DirecteurListAdapter(Activity context, List<DirecteurClasse> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public DirecteurListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_directeur,parent,false);

        return new DirecteurListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirecteurListViewHolder holder, int position) {
        DirecteurClasse direcClasse = data.get(position);
        holder.emailDirec.setText(direcClasse.getEmail());
        holder.nomDirec.setText(direcClasse.getNom());
        holder.prenomDirec.setText(direcClasse.getPrenom());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
