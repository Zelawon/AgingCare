package com.test.agingcarev01.FonctionsProfil.ModifierProfilResidentDialog;

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

public class ModiftypeSanguinDialog extends AppCompatDialogFragment {
    private RadioButton modifTypeSangApos, modifTypeSangAneg,
            modifTypeSangBpos, modifTypeSangBneg,
            modifTypeSangABpos, modifTypeSangABneg,
            modifTypeSangOpos, modifTypeSangOneg;
    private ModiftypeSanguinDialogListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_modif_type_sanguin, null);

        builder.setView(view)
                .setTitle("Modifier Type Sanguin")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (modifTypeSangApos.isChecked()) {
                            final String nvType = "A+";
                            listner.applyNvType(nvType);
                        } else if (modifTypeSangAneg.isChecked()) {
                            final String nvType = "A-";
                            listner.applyNvType(nvType);
                        } else if (modifTypeSangBpos.isChecked()) {
                            final String nvType = "B+";
                            listner.applyNvType(nvType);
                        } else if (modifTypeSangBneg.isChecked()) {
                            final String nvType = "B-";
                            listner.applyNvType(nvType);
                        } else if (modifTypeSangABpos.isChecked()) {
                            final String nvType = "AB+";
                            listner.applyNvType(nvType);
                        } else if (modifTypeSangABneg.isChecked()) {
                            final String nvType = "AB-";
                            listner.applyNvType(nvType);
                        } else if (modifTypeSangOpos.isChecked()) {
                            final String nvType = "O+";
                            listner.applyNvType(nvType);
                        } else if (modifTypeSangOneg.isChecked()) {
                            final String nvType = "O-";
                            listner.applyNvType(nvType);
                        }
                    }
                });
        modifTypeSangApos = view.findViewById(R.id.modifTypeSangApos);
        modifTypeSangAneg = view.findViewById(R.id.modifTypeSangAneg);
        modifTypeSangBpos = view.findViewById(R.id.modifTypeSangBpos);
        modifTypeSangBneg = view.findViewById(R.id.modifTypeSangBneg);
        modifTypeSangABpos = view.findViewById(R.id.modifTypeSangABpos);
        modifTypeSangABneg = view.findViewById(R.id.modifTypeSangABneg);
        modifTypeSangOpos = view.findViewById(R.id.modifTypeSangOpos);
        modifTypeSangOneg = view.findViewById(R.id.modifTypeSangOneg);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (ModiftypeSanguinDialog.ModiftypeSanguinDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "ajouter ModiftypeSanguinDialogListner");
        }
    }

    public interface ModiftypeSanguinDialogListner {
        void applyNvType(String type);
    }
}
