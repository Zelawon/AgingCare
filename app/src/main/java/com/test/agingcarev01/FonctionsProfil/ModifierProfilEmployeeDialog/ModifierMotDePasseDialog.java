package com.test.agingcarev01.FonctionsProfil.ModifierProfilEmployeeDialog;

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

public class ModifierMotDePasseDialog extends AppCompatDialogFragment {
    private EditText passText,confPassText;
    private ModifMotDePasseDialogListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_modif_mot_de_passe,null);

        builder.setView(view)
                .setTitle("Modifier Mot De Passe")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String nvPass = passText.getText().toString();
                        final String nvConfPass = confPassText.getText().toString();
                        if ( (!(nvPass.isEmpty())) && (!(nvConfPass.isEmpty())) && (nvPass.equals(nvConfPass)) ){
                            listner.applyNvPass(nvPass);
                        }
                    }
                });
        passText = view.findViewById(R.id.nvMotPass);
        confPassText = view.findViewById(R.id.confNvMotPass);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (ModifMotDePasseDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"ajouter ModifPrenomDialogListner");
        }
    }

    public interface ModifMotDePasseDialogListner {
        void applyNvPass(String nvMotDePasse);
    }

}
