package com.skor.beloteskor.Scores;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.skor.beloteskor.R;


public class DialogModeEquipeFragment extends DialogFragment {

    public DialogModeEquipeFragment() {
        // Required empty public constructor
    }



    public interface DialogModeEquipeFragmentListener {
         void onDialogPositiveClick(DialogFragment dialog);
         void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    private DialogModeEquipeFragmentListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (DialogModeEquipeFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " doit implémenter BasicDialogListener");
        }
    }

//todo V1a voir nouveau framework pour boite dialogue
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_dialog_mode_equipe, null));


        builder.setMessage("Il manque les noms de certains joueurs. Voulez vous lancer une partie en mode Equipe (Vous et Nous) ou souhaitez vous compléter les noms manquants ?");

        builder.setTitle("Mode Equipe ou Mode Joueurs ?");


        builder.setPositiveButton("Modifier les noms", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                mListener.onDialogPositiveClick(DialogModeEquipeFragment.this);
            }
        })
                .setNegativeButton("Lancer en mode Equipe", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(DialogModeEquipeFragment.this);
                    }
                });

        return builder.create();

    }


}
