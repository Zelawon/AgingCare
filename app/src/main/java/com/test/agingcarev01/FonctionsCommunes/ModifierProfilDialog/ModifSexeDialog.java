package com.test.agingcarev01.FonctionsCommunes.ModifierProfilDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.test.agingcarev01.R;

public class ModifSexeDialog extends AppCompatDialogFragment {

    private RadioButton modifRadioHommeInf,modifRadioFemmeInf;
    private ModifSexeDialogListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_modif_sexe,null);

        builder.setView(view)
                .setTitle("Modifier Sexe")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (modifRadioHommeInf.isChecked()){
                            final String nvSexe = "homme";
                            listner.applyNvSexe(nvSexe);
                        }else if (modifRadioFemmeInf.isChecked()){
                            final String nvSexe = "femme";
                            listner.applyNvSexe(nvSexe);
                        }
                    }
                });
        modifRadioHommeInf = view.findViewById(R.id.modifRadioHommeInf);
        modifRadioFemmeInf = view.findViewById(R.id.modifRadioFemmeInf);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (ModifSexeDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"ajouter ModifSexeDialogListner");
        }
    }

    public interface ModifSexeDialogListner {
        void applyNvSexe(String sexe);
    }
}
