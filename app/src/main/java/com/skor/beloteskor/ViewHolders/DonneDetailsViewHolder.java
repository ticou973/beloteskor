package com.skor.beloteskor.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.skor.beloteskor.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class DonneDetailsViewHolder extends ChildViewHolder{

    private TextView player1Name, player2Name, player3Name, player4Name;

    public DonneDetailsViewHolder(View itemView) {
        super(itemView);

        player1Name = itemView.findViewById(R.id.details_donne_player1_name);
        player2Name = itemView.findViewById(R.id.details_donne_player2_name);
        player3Name = itemView.findViewById(R.id.details_donne_player3_name);
        player4Name = itemView.findViewById(R.id.details_donne_player4_name);

    }

    public void setPlayer1Name (String name){

        player1Name.setText(name);
    }

    public void setPlayer2Name (String name){

        player2Name.setText(name);
    }

    public void setPlayer3Name (String name){

        player3Name.setText(name);
    }

    public void setPlayer4Name (String name){

        player4Name.setText(name);
    }

}
