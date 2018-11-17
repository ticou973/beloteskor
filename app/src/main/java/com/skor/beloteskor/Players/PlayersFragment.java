package com.skor.beloteskor.Players;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skor.beloteskor.MainActivity;
import com.skor.beloteskor.Model_DB.MainDb.Joueur;
import com.skor.beloteskor.R;

import java.util.List;


public class PlayersFragment extends Fragment {

    private OnPlayersFragmentInteractionListener mListener;
    private RecyclerView recyclerViewPlayers;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private PlayerAdapter playerAdapter;

    private List<Joueur> joueurs;


    public PlayersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlayersFragmentInteractionListener) {
            mListener = (OnPlayersFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_players, container, false);

        recyclerViewPlayers=view.findViewById(R.id.recyclerview_players);
        layoutManager= new LinearLayoutManager(context);
        recyclerViewPlayers.setLayoutManager(layoutManager);

        playerAdapter=new PlayerAdapter(joueurs);

        recyclerViewPlayers.setAdapter(playerAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPlayersFragmentInteraction();
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnPlayersFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPlayersFragmentInteraction();
    }

    private void initData() {
        joueurs=MainActivity.beloteSkorDb.joueurDao().getAllJoueurs();
    }
}

