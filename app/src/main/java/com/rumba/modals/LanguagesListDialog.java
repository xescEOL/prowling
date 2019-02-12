package com.rumba.modals;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.rumba.prowling.R;

/**
 * Fragmento con un diálogo personalizado
 */
public class LanguagesListDialog extends DialogFragment {
    private static final String TAG = LanguagesListDialog.class.getSimpleName();

    public LanguagesListDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    /**
     * Crea un diálogo con personalizado para comportarse
     * como formulario de login
     *
     * @return Diálogo
     */
    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.modal_languages, null);

        builder.setView(v);

        Button exit = (Button) v.findViewById(R.id.butCancel);

        exit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Crear Cuenta...
                        dismiss();
                    }
                }
        );

        return builder.create();
    }

}


