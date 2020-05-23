package com.test.agingcarev01.ConsulterListes.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.Classe.InfirmierClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class InfirmierListAdapter extends RecyclerView.Adapter<InfirmierListViewHolder> {

    private Activity context;
    List<InfirmierClasse> data;


    public InfirmierListAdapter(Activity context, List<InfirmierClasse> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public InfirmierListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_infirmier,parent,false);

        return new InfirmierListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfirmierListViewHolder holder, int position) {
        InfirmierClasse infClasse = data.get(position);
        holder.emailInf.setText(infClasse.getEmail());
        holder.nomInf.setText(infClasse.getNom());
        holder.prenomInf.setText(infClasse.getPrenom());
        holder.sexeInf.setText((infClasse.getSexe()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
