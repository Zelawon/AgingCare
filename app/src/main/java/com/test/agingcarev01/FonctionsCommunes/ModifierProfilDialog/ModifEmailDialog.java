package com.test.agingcarev01.FonctionsCommunes.ModifierProfilDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.test.agingcarev01.R;


public class ModifEmailDialog extends AppCompatDialogFragment {
    private EditText emailText;
    private ModifEmailDialogListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_modif_email,null);

        builder.setView(view)
                .setTitle("Modifier Email")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String nvEmail = emailText.getText().toString().toLowerCase();
                        //verifier non vide et test est format email
                        if ( (!(nvEmail.isEmpty())) && Patterns.EMAIL_ADDRESS.matcher(nvEmail).matches()){
                            listner.applyNvEmail(nvEmail);
                        }
                    }
                });
        emailText = view.findViewById(R.id.modifEmail);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (ModifEmailDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"ajouter ModifPrenomDialogListner");
        }
    }

    public interface ModifEmailDialogListner{
        void applyNvEmail(String email);
    }
}
