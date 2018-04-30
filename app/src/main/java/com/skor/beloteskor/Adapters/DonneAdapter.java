package com.skor.beloteskor.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skor.beloteskor.Model.DonneScore;
import com.skor.beloteskor.R;
import com.skor.beloteskor.ViewHolders.DonneViewHolder;

import java.util.List;

public class DonneAdapter extends RecyclerView.Adapter <DonneViewHolder>{

    List<DonneScore> donnesScore;
    private boolean isExpanded = false;
    private String player1, player2, player3, player4;


    private OnDonneAdapterListener mListener;


    public DonneAdapter(List<DonneScore> donnesScore) {

        this.donnesScore = donnesScore;

    }

    public interface OnDonneAdapterListener {

        public String[] onDonneAdapterPlayers();

    }


    @Override
    public DonneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_donne_score, parent, false);
        return new DonneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DonneViewHolder holder, int position) {

        //get the players with an interface
        getPlayers();

        //gestion du ViewHolder

        holder.setScoreEquipeA(donnesScore.get(position).getScoreDonneA());
        holder.setScoreEquipeB(donnesScore.get(position).getScoreDonneB());
        holder.setNumDonne(position + 1);

        holder.getCardViewDonne().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpanded){
                    isExpanded = true;

                    holder.getCardViewDonneDetails().setVisibility(View.VISIBLE);
                    holder.expand();
                    holder.setPlayer1Name(getPlayers()[0]);
                    holder.setPlayer2Name(getPlayers()[1]);
                    holder.setPlayer3Name(getPlayers()[2]);
                    holder.setPlayer4Name(getPlayers()[3]);

                } else {
                    isExpanded = false;
                    holder.getCardViewDonneDetails().setVisibility(View.GONE);
                    holder.collapse();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return donnesScore.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        Context context = recyclerView.getContext();

        if (context instanceof OnDonneAdapterListener) {
            mListener = (OnDonneAdapterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        mListener = null;

    }


    public String[] getPlayers() {

        if (mListener != null) {
            mListener.onDonneAdapterPlayers();

            return mListener.onDonneAdapterPlayers();
        }

        return null;
    }


}
