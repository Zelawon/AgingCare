package com.test.agingcarev01.FonctionsSurveillant.Maladie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.test.agingcarev01.R;

public class AjouterMaladieDialog extends AppCompatDialogFragment {
    private EditText nomMaladie;
    private AjoutMaladieDialogListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_ajout_maladie,null);

        builder.setView(view)
                .setTitle("Ajouter Maladie")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String nvNom = nomMaladie.getText().toString().toLowerCase();
                        //verifier non vide
                        if ( !(nvNom.isEmpty()) ){
                            listner.applyNvNom(nvNom);
                        }
                    }
                });
        nomMaladie = view.findViewById(R.id.modifNom);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (AjoutMaladieDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"ajouter ModifPrenomDialogListner");
        }
    }

    public interface AjoutMaladieDialogListner {
        void applyNvNom(String nom);
    }
}