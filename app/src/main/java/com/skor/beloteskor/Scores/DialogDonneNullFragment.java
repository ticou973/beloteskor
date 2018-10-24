package com.skor.beloteskor.Scores;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.skor.beloteskor.R;


public class DialogDonneNullFragment extends DialogFragment {

    private boolean choice;


    public DialogDonneNullFragment() {
        // Required empty public constructor
    }



    public interface DialogDonneNullFragmentListener {
        void onDialogDonnePositiveClick(DialogFragment dialog);
        void onDialogDonneNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    private DialogDonneNullFragment.DialogDonneNullFragmentListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (DialogDonneNullFragment.DialogDonneNullFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " doit implémenter DialogListener");
        }
    }

    //todo voir nouveau framework pour boite dialogue
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_dialog_donne_null, null));


        builder.setMessage("Les scores de la donne actuelle sont nuls. Voulez-vous vraiment passer à la donne suivante ?");

        builder.setTitle("Donne actuelle non renseignée");


        builder.setPositiveButton("Oui !", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                 mListener.onDialogDonnePositiveClick(DialogDonneNullFragment.this);
            }
        })
                .setNegativeButton("Modifier donne actuelle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                       mListener.onDialogDonneNegativeClick(DialogDonneNullFragment.this);
                    }
                });

        return builder.create();

    }

}
