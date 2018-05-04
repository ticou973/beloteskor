package com.skor.beloteskor.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.skor.beloteskor.Model.DonneScore;
import com.skor.beloteskor.R;
import com.skor.beloteskor.ViewHolders.DonneViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DonneAdapter extends RecyclerView.Adapter <DonneViewHolder>{

    List<DonneScore> donnesScore;
    private ArrayList<Boolean> isExpanded = new ArrayList<>();
    private float beginX, beginY;


    private Context mContext;
    private GestureDetector detector;

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
    public void onBindViewHolder(final DonneViewHolder holder, final int position) {

        // Get the application context
        mContext = holder.itemView.getContext();
        isExpanded.add(false);

        //get the players with an interface
        getPlayers();

        //gestion du ViewHolder

        //CardView Parent


        holder.setScoreEquipeA(0);
        holder.setScoreEquipeB(0);
        holder.setNumDonne(position + 1);
        holder.getCardViewDonneDetails().setVisibility(View.GONE);


        //CardView Child

        holder.getCardViewDonne().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gestion de l'item courant

                if (!isExpanded.get(position)){

                    //ouverture de l'item courant
                    holder.expand(position + 1);

                    //fermeture des autres items

                    //todo gérer la fermeture des autres items

                    /*for (int i = 0; i <donnesScore.size() ; i++) {

                        if (i != position && isExpanded.get(i)) {

                            holder.collapse(i+1);

                        }else {

                            Toast.makeText(mContext, "coucou", Toast.LENGTH_SHORT).show();
                        }
                    }*/



                    isExpanded.set(position,true);

                    //Récupération des noms de joueurs
                    holder.setPlayer1Name(getPlayers()[0]);
                    holder.setPlayer2Name(getPlayers()[1]);
                    holder.setPlayer3Name(getPlayers()[2]);
                    holder.setPlayer4Name(getPlayers()[3]);


                   //NumberPickerScore

                      //gestion du detector

                    detector = new GestureDetector(mContext, new GestureDetector.OnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public void onShowPress(MotionEvent e) {

                        }

                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {

                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                            //Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();

                            //todo gérer le fling gauche et droite pour envoyer le numberpicker et le fling vertical pour selection numberpicker. peut être dupliquer dans le scroll

                            if(e2.getX()< e1.getX()){

                                holder.setColorFlLeft(true);
                                holder.setColorFlRight(false);


                                float endX = beginX/4;
                                float endY = beginY;

                                //Toast.makeText(mContext, String.valueOf(endX) + String.valueOf(endY), Toast.LENGTH_SHORT).show();

                                //Toast.makeText(mContext, "mvt à gauche", Toast.LENGTH_SHORT).show();
                                holder.animateNumberPicker(endX,endY);

                            }else{

                                holder.setColorFlRight(true);
                                holder.setColorFlLeft(false);

                                float endX = beginX + beginX*3/4;
                                float endY = beginY;

                                //Toast.makeText(mContext, String.valueOf(endX) + String.valueOf(endY), Toast.LENGTH_SHORT).show();

                                holder.animateNumberPicker(endX,endY);

                                //Toast.makeText(mContext, "mvt à droite", Toast.LENGTH_SHORT).show();

                            }

                            holder.getNumberPicker().setOnTouchListener(null);

                            return true;
                        }
                    });

                    //todo gerer le perform Click

                    //Gestion du mouvement OnFling

                    holder.getNumberPicker().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            beginX = v.getLeft();
                            beginY = v.getTop();
                            //Toast.makeText(mContext, String.valueOf(beginX) + String.valueOf(beginY), Toast.LENGTH_SHORT).show();

                            return detector.onTouchEvent(event);
                        }
                    });





                } else {
                    isExpanded.set(position, false);
                    holder.collapse(position+1);
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
