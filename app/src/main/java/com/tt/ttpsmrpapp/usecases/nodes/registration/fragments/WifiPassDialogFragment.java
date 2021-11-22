package com.tt.ttpsmrpapp.usecases.nodes.registration.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tt.ttpsmrpapp.R;

public class WifiPassDialogFragment extends DialogFragment {

    public interface WifiPassDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String wifiPass);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    WifiPassDialogListener listener;


    private String title;

    public WifiPassDialogFragment(String title, WifiPassDialogListener listener) {
        this.title = title;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.password_dialog,null);

        final EditText wifiPassEditText = (EditText)view.findViewById(R.id.edit_text_dialog_password);
        builder.setView(view)
                .setTitle(title)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(WifiPassDialogFragment.this, wifiPassEditText.getText().toString());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(WifiPassDialogFragment.this);
                    }
                });
        return builder.create();
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (WifiPassDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement WifiPassDialogListener");
        }
    }*/


    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal_700));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.teal_700));
    }
}
