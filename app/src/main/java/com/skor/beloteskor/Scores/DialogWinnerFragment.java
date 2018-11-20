package com.skor.beloteskor.Scores;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.skor.beloteskor.R;

public class DialogWinnerFragment extends DialogFragment {

    public DialogWinnerFragment() {
        // Required empty public constructor
    }



    public interface DialogWinnerFragmentListener {
        void onDialogWinnerPositiveClick(DialogFragment dialog);
        void onDialogWinnerNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    private DialogWinnerFragment.DialogWinnerFragmentListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (DialogWinnerFragment.DialogWinnerFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " doit implémenter DialogListener");
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
        builder.setView(inflater.inflate(R.layout.fragment_dialog_winner, null));


        builder.setMessage("Voulez-vous recommencer une nouvelle partie avec les mêmes équipes ?");

        builder.setTitle("Partie terminée !!!");


        builder.setPositiveButton("Nouvelle partie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                mListener.onDialogWinnerPositiveClick(DialogWinnerFragment.this);
            }
        })
                .setNegativeButton("Fin de partie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogWinnerNegativeClick(DialogWinnerFragment.this);
                    }
                });

        return builder.create();
    }

}
