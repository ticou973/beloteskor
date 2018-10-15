package com.skor.beloteskor.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;
import com.skor.beloteskor.Model.DonneScore;
import com.skor.beloteskor.R;
import com.skor.beloteskor.ViewHolders.DonneViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DonneAdapter extends RecyclerView.Adapter<DonneViewHolder> {

    List<DonneScore> donnesScore;
    private ArrayList<Boolean> isExpanded = new ArrayList<>();
    private float beginX, beginY;
    private int width;
    private int numberPickerposition = 0; //Center


    private Context mContext;
    private GestureDetector detector;

    private OnDonneAdapterListener mListener;


    public DonneAdapter(List<DonneScore> donnesScore) {

        this.donnesScore = donnesScore;

    }

    public interface OnDonneAdapterListener {

        String[] onDonneAdapterPlayers();

    }


    @Override
    public DonneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_donne_score, parent, false);
        return new DonneViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final DonneViewHolder holder, final int position) {


//todo a enlever lorsque les scores arriveront
        DonneScore donneScore= donnesScore.get(position);
        int scoreA = donneScore.getScoreDonneA();
        int scoreB = donneScore.getScoreDonneB();


        // Get the application context
        mContext = holder.itemView.getContext();

        //todo revoir le isExpanded
        isExpanded.add(false);

        //get the players with an interface
        getPlayers();

        //gestion du ViewHolder

        //CardView Parent


        holder.setScoreEquipeA(scoreA);
        holder.setScoreEquipeB(scoreB);
        holder.setNumDonne(position + 1);
        holder.getCardViewDonneDetails().setVisibility(View.GONE);


        //CardView Child


        holder.getCardViewDonne().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gestion de l'item courant

                if (!isExpanded.get(position)) {

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


                    holder.getBelote_team1().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked && holder.getBelote_team2().isChecked()) {

                                holder.getBelote_team2().setChecked(false);
                                holder.getBelote_team2().setAlpha(0.3f);

                                //todo voir si cela fonctionne avec buttonView et pour les autres plus bas
                                holder.getBelote_team1().setAlpha(1.0f);


                            } else if (isChecked && !holder.getBelote_team2().isChecked()) {


                                holder.getBelote_team2().setAlpha(0.3f);
                                holder.getBelote_team1().setAlpha(1.0f);

                            } else if (!isChecked && !holder.getBelote_team2().isChecked()) {

                                holder.getBelote_team1().setAlpha(1.0f);
                                holder.getBelote_team2().setAlpha(1.0f);
                            }


                        }
                    });

                    holder.getBelote_team2().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked && holder.getBelote_team1().isChecked()) {
                                holder.getBelote_team1().setChecked(false);
                                holder.getBelote_team1().setAlpha(0.3f);
                                holder.getBelote_team2().setAlpha(1.0f);


                            } else if (isChecked && !holder.getBelote_team1().isChecked()) {

                                holder.getBelote_team1().setAlpha(0.3f);
                                holder.getBelote_team2().setAlpha(1.0f);

                            } else if (!isChecked && !holder.getBelote_team1().isChecked()) {

                                holder.getBelote_team1().setAlpha(1.0f);
                                holder.getBelote_team2().setAlpha(1.0f);

                            }


                        }
                    });

                    //Gestion du capot

                    holder.getCapot_team1().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked && holder.getCapot_team2().isChecked()) {
                                holder.getCapot_team2().setChecked(false);
                                holder.getCapot_team2().setAlpha(0.3f);
                                holder.getCapot_team1().setAlpha(1.0f);


                            } else if (isChecked && !holder.getCapot_team2().isChecked()) {

                                holder.getCapot_team1().setAlpha(1.0f);
                                holder.getCapot_team2().setAlpha(0.3f);

                            } else if (!isChecked && !holder.getCapot_team2().isChecked()) {
                                holder.getCapot_team1().setAlpha(1.0f);
                                holder.getCapot_team2().setAlpha(1.0f);


                            }


                        }
                    });

                    holder.getCapot_team2().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked && holder.getCapot_team1().isChecked()) {
                                holder.getCapot_team1().setChecked(false);
                                holder.getCapot_team1().setAlpha(0.3f);
                                holder.getCapot_team2().setAlpha(1.0f);

                            } else if (isChecked && !holder.getCapot_team1().isChecked()) {

                                holder.getCapot_team2().setAlpha(1.0f);
                                holder.getCapot_team1().setAlpha(0.3f);

                            } else if (!isChecked && !holder.getCapot_team1().isChecked()) {
                                holder.getCapot_team1().setAlpha(1.0f);
                                holder.getCapot_team2().setAlpha(1.0f);


                            }

                        }
                    });


                    //Gestion de la couleur prise

                    holder.getPreneur_carreau().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //todo vérifier si on change par v
                            holder.setColorPreneurCouleur(holder.getPreneur_carreau(), true);
                            holder.setColorPreneurCouleur(holder.getPreneur_coeur(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_pique(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_trefle(), false);

                        }
                    });

                    holder.getPreneur_pique().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.setColorPreneurCouleur(holder.getPreneur_carreau(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_coeur(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_pique(), true);
                            holder.setColorPreneurCouleur(holder.getPreneur_trefle(), false);
                        }
                    });

                    holder.getPreneur_coeur().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.setColorPreneurCouleur(holder.getPreneur_carreau(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_coeur(), true);
                            holder.setColorPreneurCouleur(holder.getPreneur_pique(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_trefle(), false);
                        }
                    });

                    holder.getPreneur_trefle().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            holder.setColorPreneurCouleur(holder.getPreneur_carreau(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_coeur(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_pique(), false);
                            holder.setColorPreneurCouleur(holder.getPreneur_trefle(), true);

                        }
                    });

                    //Gestion du preneur

                    holder.getPlayer1Name().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //todo vérifier si on change par v
                            holder.setColorPlayerPreneur((holder.getPlayer1Name()), true);
                            holder.setColorPlayerPreneur((holder.getPlayer2Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer3Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer4Name()), false);

                        }
                    });

                    holder.getPlayer2Name().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.setColorPlayerPreneur((holder.getPlayer1Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer2Name()), true);
                            holder.setColorPlayerPreneur((holder.getPlayer3Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer4Name()), false);
                        }
                    });

                    holder.getPlayer3Name().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.setColorPlayerPreneur((holder.getPlayer1Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer2Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer3Name()), true);
                            holder.setColorPlayerPreneur((holder.getPlayer4Name()), false);
                        }
                    });

                    holder.getPlayer4Name().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            holder.setColorPlayerPreneur((holder.getPlayer1Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer2Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer3Name()), false);
                            holder.setColorPlayerPreneur((holder.getPlayer4Name()), true);

                        }
                    });


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

                               // holder.setColorFlLeft(true);
                                //holder.setColorFlRight(false);


                                float endX = beginX - width / 2 - 40;
                                Toast.makeText(mContext, String.valueOf(endX), Toast.LENGTH_SHORT).show();
                                float endY = beginY;


                                holder.animateNumberPicker(endX, endY);


                            } else if (e2.getX() > e1.getX() && Math.abs(e2.getY() - e1.getY()) < 100) {

                                numberPickerposition = 2; //Right

                                //holder.setColorFlRight(true);
                               // holder.setColorFlLeft(false);

                                float endX = beginX + width / 2 + 40;
                                Toast.makeText(mContext, String.valueOf(endX), Toast.LENGTH_SHORT).show();

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

                    holder.getNumberPicker().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            width = v.getWidth();
                            beginX = v.getLeft();
                            beginY = v.getTop();


                            return detector.onTouchEvent(event);
                        }
                    });


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
