package com.skor.beloteskor.Statistics;

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
import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.R;

import java.util.List;


public class StatisticsFragment extends Fragment {

    private OnStatisticsFragmentInteractionListener mListener;

    private List<Partie> parties;
    private RecyclerView recyclerviewParties;
    private RecyclerView.LayoutManager layoutManager;
    private StatisticsAdapter statisticsAdapter;
    private Context context;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStatisticsFragmentInteractionListener) {
            mListener = (OnStatisticsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

    }

//todo gérer le cas de départ "pas de parties

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        recyclerviewParties= view.findViewById(R.id.recyclerview_parties);

        layoutManager = new LinearLayoutManager(context);
        recyclerviewParties.setLayoutManager(layoutManager);

        statisticsAdapter = new StatisticsAdapter(parties);

        recyclerviewParties.setAdapter(statisticsAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onStatisticsFragmentInteraction();
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnStatisticsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStatisticsFragmentInteraction();
    }

    private void initData() {

        parties =MainActivity.beloteSkorDb.partieDao().getAllParties();

    }
}
