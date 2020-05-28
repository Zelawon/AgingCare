package com.test.agingcarev01.FonctionsSurveillant;

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

public class AjouterPoidsCibleDialog extends AppCompatDialogFragment {
    private EditText modiPoidsCible;
    private AjouterVideDialogListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_ajout_poids_cible,null);

        builder.setView(view)
                .setTitle("Modifier Poids Ciblé")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String nvPoidsCible = modiPoidsCible.getText().toString().toLowerCase();
                        //verifier non vide
                        if ( !(nvPoidsCible.isEmpty()) ){
                            Float value=Float.valueOf(nvPoidsCible);
                            listner.applyNvPoidsCible(value);
                        }
                    }
                });
        modiPoidsCible = view.findViewById(R.id.modifVide);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (AjouterVideDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"ajouter AjoutVideDialogListner");
        }
    }

    public interface AjouterVideDialogListner {
        void applyNvPoidsCible(Float poidsCible);
    }
}