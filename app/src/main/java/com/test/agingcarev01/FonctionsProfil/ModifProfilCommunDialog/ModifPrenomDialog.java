package com.test.agingcarev01.FonctionsProfil.ModifProfilCommunDialog;

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

public class ModifPrenomDialog extends AppCompatDialogFragment {
    private EditText prenomText;
    private ModifPrenomDialogListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_modif_prenom, null);

        builder.setView(view)
                .setTitle("Modifier Prenom")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String nvPrenom = prenomText.getText().toString().toLowerCase();
                        //verifier non vide
                        if (!(nvPrenom.isEmpty())) {
                            listner.applyNvPrenom(nvPrenom);
                        }
                    }
                });
        prenomText = view.findViewById(R.id.modifPrenom);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (ModifPrenomDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "ajouter ModifPrenomDialogListner");
        }
    }

    public interface ModifPrenomDialogListner {
        void applyNvPrenom(String prenom);
    }

}
