package com.skor.beloteskor.Scores.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.shawnlin.numberpicker.NumberPicker;
import com.skor.beloteskor.Model_DB.MainDb.Donne;
import com.skor.beloteskor.R;
import com.skor.beloteskor.Scores.ViewHolders.DonneViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DonneAdapter extends RecyclerView.Adapter<DonneViewHolder> {

    List<Donne> donnes;
    private ArrayList<Boolean> isExpanded = new ArrayList<>();
    private float beginX, beginY;
    private int width;
    private int numberPickerposition = 0; //Center
    private int scoreA, scoreB;



    private Context mContext;
    private GestureDetector detector;

    private OnDonneAdapterListener mListener;


    public DonneAdapter(List<Donne> donnes) {
        this.donnes = donnes;
    }

    public interface OnDonneAdapterListener {
        String[] onDonneAdapterPlayers();
    }


                                    //LifeCycle
    @Override
    public DonneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_donne_score, parent, false);
        return new DonneViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final DonneViewHolder holder, final int position) {

        //todo voir comment le recycler view se place toujours à la fin (dernier item)

        Donne donne= donnes.get(position);
        scoreA = donne.getScore1();
        scoreB = donne.getScore2();

        // Get the application context
        mContext = holder.itemView.getContext();

                                 //ViewHolder

        initCardViewParent(holder,position);
        initCarrdViewChild(holder,position);

    }

    @Override
    public int getItemCount() {
        return donnes.size();
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


                            // Méthodes Adapter

    private void initCardViewParent(DonneViewHolder holder, int position) {
        holder.setScoreEquipeA(scoreA);
        holder.setScoreEquipeB(scoreB);
        holder.setNumDonne(position + 1);
        isExpanded.add(false);


        //todo revoir gestion de l'ouverture initiale
        /*if (position == donnes.size()-1) {
            holder.getCardViewDonne().performClick();
            Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
        }else{
            holder.getCardViewDonneDetails().setVisibility(View.GONE);
            isExpanded.set(position,false);
        }*/

        holder.getCardViewDonneDetails().setVisibility(View.GONE);

    }

    private void initCarrdViewChild(final DonneViewHolder holder, final int position) {

        //get the players with an interface
        //todo à voir avec la bdd pour les players
        getPlayers();

        holder.getCardViewDonne().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gestion de l'item courant

                if (!isExpanded.get(position)) {

                    //ouverture de l'item courant
                    holder.expand(position + 1);
                    isExpanded.set(position, true);


                    //Récupération des noms de joueurs
                    holder.setPlayer1Name(getPlayers()[0]);
                    holder.setPlayer2Name(getPlayers()[1]);
                    holder.setPlayer3Name(getPlayers()[2]);
                    holder.setPlayer4Name(getPlayers()[3]);

                    //Gestion de la belote et capot
                    holder.getBelote_team1().setChecked(false);
                    holder.getBelote_team2().setChecked(false);
                    holder.getCapot_team1().setChecked(false);
                    holder.getCapot_team2().setChecked(false);
                    holder.getBelote_team1().setBackgroundResource(R.drawable.radius_button_accent);
                    holder.getBelote_team2().setBackgroundResource(R.drawable.radius_button_accent);
                    holder.getCapot_team1().setBackgroundResource(R.drawable.radius_button_accent);
                    holder.getCapot_team2().setBackgroundResource(R.drawable.radius_button_accent);

                    setListenerChecked(holder.getBelote_team1(),holder.getBelote_team2());
                    setListenerChecked(holder.getBelote_team2(),holder.getBelote_team1());
                    setListenerChecked(holder.getCapot_team1(),holder.getCapot_team2());
                    setListenerChecked(holder.getCapot_team2(),holder.getCapot_team1());



                    //Gestion de la couleur prise
                    setListenerClickCouleur(holder, holder.getPreneur_carreau(),holder.getPreneur_coeur(),holder.getPreneur_pique(),holder.getPreneur_trefle());
                    setListenerClickCouleur(holder, holder.getPreneur_coeur(),holder.getPreneur_carreau(),holder.getPreneur_pique(),holder.getPreneur_trefle());
                    setListenerClickCouleur(holder, holder.getPreneur_pique(),holder.getPreneur_coeur(),holder.getPreneur_carreau(),holder.getPreneur_trefle());
                    setListenerClickCouleur(holder, holder.getPreneur_trefle(),holder.getPreneur_coeur(),holder.getPreneur_pique(),holder.getPreneur_carreau());



                    //Gestion du preneur

                    setListenerClickPreneur(holder, holder.getPlayer1Name(),holder.getPlayer2Name(),holder.getPlayer3Name(),holder.getPlayer4Name());
                    setListenerClickPreneur(holder, holder.getPlayer2Name(),holder.getPlayer1Name(),holder.getPlayer3Name(),holder.getPlayer4Name());
                    setListenerClickPreneur(holder, holder.getPlayer3Name(),holder.getPlayer2Name(),holder.getPlayer1Name(),holder.getPlayer4Name());
                    setListenerClickPreneur(holder, holder.getPlayer4Name(),holder.getPlayer2Name(),holder.getPlayer3Name(),holder.getPlayer1Name());



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


                            //todo gérer le fling gauche et droite pour envoyer le numberpicker et le fling vertical pour selection numberpicker. peut être dupliquer dans le scroll

                            if (e2.getX() < e1.getX() && Math.abs(e2.getY() - e1.getY()) < 100) {

                                numberPickerposition = 1; //Left


                                float endX = beginX - width / 2 - 40;
                                Toast.makeText(mContext, String.valueOf(endX), Toast.LENGTH_SHORT).show();
                                float endY = beginY;


                                holder.animateNumberPicker(endX, endY);


                            } else if (e2.getX() > e1.getX() && Math.abs(e2.getY() - e1.getY()) < 100) {

                                numberPickerposition = 2; //Right

                                float endX = beginX + width / 2 + 40;

                                float endY = beginY;


                                holder.animateNumberPicker(endX, endY);


                            } else if (Math.abs(e2.getY() - e1.getY()) > 100) {

                                holder.getNumberPicker().setOnTouchListener(null);

                            }

                            return true;
                        }
                    });

                    //todo gerer le perform Click

                    //Gestion du mouvement OnFling

                    holder.getNumberPicker().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    holder.getNumberPicker().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            width = v.getWidth();
                            beginX = v.getLeft();
                            beginY = v.getTop();


                            return detector.onTouchEvent(event);
                        }
                    });




                    //todo voir si intéressant de gérer le capot à 0 plutot que touche
                    holder.getNumberPicker().setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                            if (numberPickerposition == 1) {

                                holder.setScoreEquipeA(newVal);
                                holder.setScoreEquipeB(162 - newVal);

                            } else if (numberPickerposition == 2) {

                                holder.setScoreEquipeB(newVal);
                                holder.setScoreEquipeA(162 - newVal);
                            }
                        }
                    });

                } else {
                    isExpanded.set(position, false);
                    holder.collapse(position + 1);
                    scoreA = holder.getScoreEquipeA();
                    scoreB = holder.getScoreEquipeB();

                }
            }
        });

    }

    private void setListenerClickPreneur(final DonneViewHolder holder, final TextView mainJoueur, final TextView secondJoueur, final TextView thirdJoueur, final TextView fourthJoueur) {

        mainJoueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.setColorPlayerPreneur(mainJoueur, true);
                holder.setColorPlayerPreneur(secondJoueur, false);
                holder.setColorPlayerPreneur(thirdJoueur, false);
                holder.setColorPlayerPreneur(fourthJoueur, false);

            }
        });


    }

    private void setListenerClickCouleur(final DonneViewHolder holder, final ImageView mainCouleur, final ImageView secondCouleur, final ImageView thirdCouleur, final ImageView fourthCouleur) {
        mainCouleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setColorPreneurCouleur(mainCouleur, true);
                holder.setColorPreneurCouleur(secondCouleur, false);
                holder.setColorPreneurCouleur(thirdCouleur, false);
                holder.setColorPreneurCouleur(fourthCouleur, false);

            }
        });

    }

    private void setListenerChecked(ToggleButton mainTb, final ToggleButton secondTb) {

        mainTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && secondTb.isChecked()) {

                    secondTb.setChecked(false);
                    secondTb.setAlpha(0.3f);
                    buttonView.setAlpha(1.0f);


                } else if (isChecked && !secondTb.isChecked()) {

                    secondTb.setAlpha(0.3f);
                    buttonView.setAlpha(1.0f);

                } else if (!isChecked && !secondTb.isChecked()) {

                    buttonView.setAlpha(1.0f);
                    secondTb.setAlpha(1.0f);
                }
            }
        });
    }


    //todo voir si simplifiable le notify avec le ViewModel
    public void setNotifyDonneAdapter(List<Donne> donnes){

        this.donnes = donnes;
        notifyDataSetChanged();

    }


    public String[] getPlayers() {

        if (mListener != null) {
            mListener.onDonneAdapterPlayers();

            return mListener.onDonneAdapterPlayers();
        }

        return null;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }


}
