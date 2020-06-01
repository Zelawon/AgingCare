package com.test.agingcarev01.FonctionsSurveillant.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agingcarev01.Classe.InfirmierClasse;
import com.test.agingcarev01.R;

import java.util.List;

public class InfirmierAffecteeAjouterAdapter extends RecyclerView.Adapter<InfirmierAffecteeAjouterAdapter.InfirmierAffecteeAjouterListViewHolder>{
    private Activity context;
    private List<InfirmierClasse> data;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onIconClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class InfirmierAffecteeAjouterListViewHolder extends RecyclerView.ViewHolder {

        public TextView nomInf,prenomInf,sexeInf;
        public ImageView infAffecterAjouterItemCheck;

        public InfirmierAffecteeAjouterListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nomInf = itemView.findViewById(R.id.infAffecterAjouterItemNom);
            prenomInf = itemView.findViewById(R.id.infAffecterAjouterItemPrenom);
            sexeInf = itemView.findViewById(R.id.infAffecterAjouterItemSexe);
            infAffecterAjouterItemCheck = itemView.findViewById(R.id.infAffecterAjouterItemCheck);

            infAffecterAjouterItemCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!= null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onIconClick(position);
                        }
                    }
                }
            });
        }
    }

    public InfirmierAffecteeAjouterAdapter(Activity context, List<InfirmierClasse> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public InfirmierAffecteeAjouterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_infirmier_affectee_ajouter,parent,false);

        return new InfirmierAffecteeAjouterListViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InfirmierAffecteeAjouterListViewHolder holder, int position) {
        InfirmierClasse infirmierClasse = data.get(position);
        holder.nomInf.setText(infirmierClasse.getNom());
        holder.prenomInf.setText((infirmierClasse.getPrenom()));
        holder.sexeInf.setText(infirmierClasse.getSexe());
//        holder.infAffecterAjouterItemCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ConfirmerAffectationDialog(model);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
