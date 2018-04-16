package com.skor.beloteskor.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
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

public class DonneAdapter extends ExpandableRecyclerViewAdapter <DonneViewHolder,DonneDetailsViewHolder>{

    private String message;


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
    public void onBindChildViewHolder(final DonneDetailsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        DonneScoreDetails donneScoreDetails = (DonneScoreDetails) group.getItems().get(childIndex);

        holder.setPlayer1Name(donneScoreDetails.getPlayer1Name());
        holder.setPlayer2Name(donneScoreDetails.getPlayer2Name());
        holder.setPlayer3Name(donneScoreDetails.getPlayer3Name());
        holder.setPlayer4Name(donneScoreDetails.getPlayer4Name());

        holder.getEssai().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                message = holder.getNameEssai();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onBindGroupViewHolder(final DonneViewHolder holder, int flatPosition, ExpandableGroup group) {

        DonneScoreDetails donneScoreDetails = (DonneScoreDetails) group.getItems().get(0);

        holder.setNumDonne(flatPosition + 1);
        holder.setScoreEquipeA(donneScoreDetails.getScoreDonneEquipeA());
        holder.setScoreEquipeB(donneScoreDetails.getScoreDonneEquipeB());





    }


}
