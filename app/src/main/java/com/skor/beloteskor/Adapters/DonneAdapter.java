package com.skor.beloteskor.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skor.beloteskor.Model.DonneScoreDetails;
import com.skor.beloteskor.R;
import com.skor.beloteskor.ViewHolders.DonneDetailsViewHolder;
import com.skor.beloteskor.ViewHolders.DonneViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import static android.content.ContentValues.TAG;

public class DonneAdapter extends ExpandableRecyclerViewAdapter <DonneViewHolder,DonneDetailsViewHolder>{

    private String [] message = {"0", "0", "0", "0", "0"};
    private RecyclerView.AdapterDataObserver mAdapterDataObserver;


    public DonneAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);

    }

    @Override
    public DonneViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_donne_score, parent, false);

        return new DonneViewHolder(view);
    }

    @Override
    public DonneDetailsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_donne_score_details, parent, false);

        return new DonneDetailsViewHolder(view);
    }



    @Override
    public void onBindChildViewHolder(final DonneDetailsViewHolder holder, final int flatPosition, ExpandableGroup group, final int childIndex) {

        Log.d(TAG, "coucou Child : " + String.valueOf(flatPosition));

        DonneScoreDetails donneScoreDetails = (DonneScoreDetails) group.getItems().get(childIndex);

        holder.setPlayer1Name(donneScoreDetails.getPlayer1Name());
        holder.setPlayer2Name(donneScoreDetails.getPlayer2Name());
        holder.setPlayer3Name(donneScoreDetails.getPlayer3Name());
        holder.setPlayer4Name(donneScoreDetails.getPlayer4Name());

       /* holder.getEssai().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                message[flatPosition-1] = holder.getNameEssai();




            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG, "coucou Child suite : " + String.valueOf(message[flatPosition-1]));
                Log.d(TAG, "coucou Child suite 1 : " + String.valueOf(flatPosition-1));


            }
        });*/

        message[flatPosition-1] = holder.getNameEssai();
        Log.d(TAG, "coucou Child suite : " + String.valueOf(message[flatPosition-1]));
        Log.d(TAG, "coucou Child suite 1 : " + String.valueOf(flatPosition-1));


    }

    @Override
    public void onBindGroupViewHolder(final DonneViewHolder holder, int flatPosition, ExpandableGroup group) {


        Log.d(TAG, "coucou Father: " + String.valueOf(flatPosition));

        for (int i = 0; i <4 ; i++) {
            Log.d(TAG, "coucou x: " + String.valueOf(message[i]));

        }


        DonneScoreDetails donneScoreDetails = (DonneScoreDetails) group.getItems().get(0);

        holder.setNumDonne(flatPosition + 1);
        holder.setScoreEquipeA(Integer.parseInt(message[flatPosition]));
        holder.setScoreEquipeB(donneScoreDetails.getScoreDonneEquipeB());

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
