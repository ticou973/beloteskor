package com.skor.beloteskor.Scores.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.shawnlin.numberpicker.NumberPicker;
import com.skor.beloteskor.MainActivity;
import com.skor.beloteskor.Model_DB.MainDb.Donne;
import com.skor.beloteskor.Model_DB.MainDb.Equipe;
import com.skor.beloteskor.Model_DB.MainDb.Joueur;
import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.Model_DB.UtilsDb.AnnoncesDonne;
import com.skor.beloteskor.Model_DB.UtilsDb.Couleur;
import com.skor.beloteskor.Model_DB.UtilsDb.ModeEquipe;
import com.skor.beloteskor.Model_DB.UtilsDb.TypeAnnonce;
import com.skor.beloteskor.R;
import com.skor.beloteskor.Scores.ViewHolders.DonneViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DonneAdapter extends RecyclerView.Adapter<DonneViewHolder> {

    private List<Donne> donnes;
    private Donne currentDonne;
    //todo voir si isexpanded est nécessairement une liste ? le recyclerview le gère peut être ?
    private List<Boolean> isExpanded = new ArrayList<>();
    private List<Boolean> isPreneurChecked = new ArrayList<>();
    private List<Boolean> isScoreModified = new ArrayList<>();
    private float beginX, beginY;
    private int width;
    private int numberPickerposition = 0; //Center
    private int scoreA, scoreB, scoreExtraA, scoreExtraB, primeCarreValet,primeCarre9,scoreNumberPicker, scoreBelote1,scoreBelote2, scoreAnnonces1, scoreAnnonces2;
    private Joueur preneur;
    private Couleur couleur;
    private Equipe belote= new Equipe("NoBelote");
    private Equipe capot= new Equipe("NoCapot");
    private AnnoncesDonne annoncesDonne;
    private Equipe equipeA,equipeB,equipeNull;
    private Partie lastPartie;
    private String lastTypeAnnonce,lastModeEquipe;
    private final static String TAG="coucou";
    private int i;

    private Context mContext;
    private GestureDetector detector;

    private OnDonneAdapterListener mListener;


    public DonneAdapter(List<Donne> donnes) {
        this.donnes = donnes;
    }

    public interface OnDonneAdapterListener {
        String[] onDonneAdapterPlayers();
        void onDonneAdapterUpdateDonne(int numDonne);
        void onDonneAdapterSetTotalScore();
        void onDonneAdapterDisplayScoreTotal();
        void onDonneAdapterUpDateTotalScore();
        void onDonneAdapterDisplayTotalScoreAnonyme();
        void onDonneAdapterTestFinPartie();
    }

                                    //LifeCycle
    @Override
    public DonneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_donne_score, parent, false);

        initData();

        return new DonneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DonneViewHolder holder, final int position) {

        //todo voir comment le recycler view se place toujours à la fin (dernier item)
        currentDonne = donnes.get(position);
        scoreA = currentDonne.getScore1();
        scoreB = currentDonne.getScore2();

        Log.i(TAG, "onBindViewHolder: "+ scoreA);

        //todo vérifier que le number picker a été utilisé et ne pas valider juste une belote par exemple


       /* Log.i(TAG, "onBindViewHolder: " + position);
        Log.i(TAG, "onBindViewHolder: "+ currentDonne.getNumDonne()+" "+currentDonne.getCapot()+ currentDonne.getBelote());
        Log.i(TAG, "onBindViewHolder: "+scoreA+" "+scoreB);*/

        //init donnes et types de parties
        holder.setGestionScoreGone();
        holder.setCardViewAnnoncesGone();
        holder.setCardViewCarreGone();
        holder.setCardViewAnnoncesBtnGone();

        //get the players with an interface
        //todo à voir avec la bdd pour les players
        getPlayers();

        // Get the application context
        mContext = holder.itemView.getContext();
                                 //ViewHolder
        initCardViewParent(holder,position);
        initCardViewChild(holder,position);
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
        //todo le parent reste vert lorsque on add une nelle donne et la précédente non fermée
        //todo important cela ne modifie pas que l'UI mais aussi le score final

        /*if (position == donnes.size()-1) {
            holder.getCardViewDonne().performClick();
            Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
        }else{
            holder.getCardViewDonneDetails().setVisibility(View.GONE);
            isExpanded.set(position,false);
        }*/
        holder.getCardViewDonneDetails().setVisibility(View.GONE);
    }

    private void initCardViewChild(final DonneViewHolder holder, final int position) {
        isPreneurChecked.add(false);

        //todo faire une demande de modif à l'utilisateur pour éviter les erreurs

        holder.getCardViewDonne().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gestion de l'item courant
                if (!isExpanded.get(position)) {

                    if(isScoreModified.get(position)){
                        isScoreModified.set(position,false);
                        currentDonne = donnes.get(position);
                        scoreA = currentDonne.getScore1();
                        scoreB = currentDonne.getScore2();
                    }
                    Log.i(TAG, "onClick: expand "+ isScoreModified);
                    Log.i(TAG, "onClick: expand "+ scoreA);

                    //ouverture de l'item courant
                    holder.expand(position + 1);
                    isExpanded.set(position, true);

                  //  Log.i(TAG, "onClick:A "+position+isExpanded.get(position)+isPreneurChecked.get(position));

                    if(isPreneurChecked.get(position)){
                        holder.setGestionScoreVisible();
                        holder.getCardviewAnnoncesBtn().setVisibility(View.VISIBLE);

                        //todo voir pour simplifier cela car on veut la carte avec Annonces ouverte aussi
                        if(holder.getAnnonces_team1().isChecked()||holder.getAnnonces_team2().isChecked()){
                            holder.getCardViewAnnonces().setVisibility(View.VISIBLE);

                            if(holder.getCarre_team2().isChecked()||holder.getCarre_team1().isChecked()){

                                holder.getCardViewCarre().setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    //Récupération des noms de joueurs
                    holder.setPlayer1Name(getPlayers()[0]);
                    holder.setPlayer2Name(getPlayers()[1]);
                    holder.setPlayer3Name(getPlayers()[2]);
                    holder.setPlayer4Name(getPlayers()[3]);

                    //Gestion du preneur
                    setListenerClickPreneur(holder, position, holder.getPlayer1Name(),holder.getPlayer2Name(),holder.getPlayer3Name(),holder.getPlayer4Name());
                    setListenerClickPreneur(holder, position, holder.getPlayer2Name(),holder.getPlayer1Name(),holder.getPlayer3Name(),holder.getPlayer4Name());
                    setListenerClickPreneur(holder, position, holder.getPlayer3Name(),holder.getPlayer2Name(),holder.getPlayer1Name(),holder.getPlayer4Name());
                    setListenerClickPreneur(holder, position, holder.getPlayer4Name(),holder.getPlayer2Name(),holder.getPlayer3Name(),holder.getPlayer1Name());


                    //Gestion de la couleur prise
                    setListenerClickCouleur(holder, holder.getPreneur_carreau(),holder.getPreneur_coeur(),holder.getPreneur_pique(),holder.getPreneur_trefle());
                    setListenerClickCouleur(holder, holder.getPreneur_coeur(),holder.getPreneur_carreau(),holder.getPreneur_pique(),holder.getPreneur_trefle());
                    setListenerClickCouleur(holder, holder.getPreneur_pique(),holder.getPreneur_coeur(),holder.getPreneur_carreau(),holder.getPreneur_trefle());
                    setListenerClickCouleur(holder, holder.getPreneur_trefle(),holder.getPreneur_coeur(),holder.getPreneur_pique(),holder.getPreneur_carreau());

                    //NumberPickerScore
                    initNumberPicker(holder, position);

                    //gestion de la belote et du capot
                    setListenerChecked(holder, position, holder.getBelote_team1(),holder.getBelote_team2());
                    setListenerChecked(holder, position, holder.getBelote_team2(),holder.getBelote_team1());
                    setListenerChecked(holder, position, holder.getCapot_team1(),holder.getCapot_team2());
                    setListenerChecked(holder, position, holder.getCapot_team2(),holder.getCapot_team1());

                    //gestion des annonces
                    holder.setListenerAnnoncesChecked(holder.getAnnonces_team1(),holder.getAnnonces_team2());
                    holder.setListenerAnnoncesChecked(holder.getAnnonces_team2(),holder.getAnnonces_team1());

                    setListenerClickButton(holder, position, holder.getTierce_team1(),holder.getNbTierce_team1());
                    setListenerClickButton(holder, position, holder.getTierce_team2(),holder.getNbTierce_team2());
                    setListenerClickButton(holder, position, holder.getCinquante_team1(),holder.getNbCinquante_team1());
                    setListenerClickButton(holder, position, holder.getCent_team1(),holder.getNbCent_team1());
                    setListenerClickButton(holder, position, holder.getCinquante_team2(),holder.getNbCinquante_team2());
                    setListenerClickButton(holder, position, holder.getCent_team2(),holder.getNbCent_team2());

                    setListenerClickCarre(holder, holder.getCarre_team1());
                    setListenerClickCarre(holder, holder.getCarre_team2());

                    //gestion des carrés
                    setListenerClickButton(holder, position, holder.getCarre_autre_team1(),holder.getNbCarre_autre_team1());
                    setListenerClickButton(holder, position, holder.getCarre_autre_team2(),holder.getNbCarre_autre_team2());
                    setListenerToggleV9(holder, position, holder.getCarre_valet_team1());
                    setListenerToggleV9(holder, position, holder.getCarre_valet_team2());
                    setListenerToggleV9(holder, position, holder.getCarre_9_team1());
                    setListenerToggleV9(holder, position, holder.getCarre_9_team2());


                    //collapse de la donne
                } else {

                    /*if(!isScoreModified.get(position)){
                        Log.i(TAG, "onClick: hello");
                        scoreA=MainActivity.beloteSkorDb.donneDao().getDonnebyNumDonne(position+1,lastPartie.getPartieId()).getScore1();
                    }*/

                    Log.i(TAG, "onClick: collapse "+ isScoreModified);
                    Log.i(TAG, "onClick: collapse "+ scoreA);
                    isExpanded.set(position, false);
                    holder.collapse(position + 1);
                    holder.setCardViewAnnoncesBtnGone();
                    holder.setCardViewAnnoncesGone();
                    holder.setCardViewCarreGone();

                    capot = holder.getCapot();
                    belote = holder.getBelote();
                    annoncesDonne=holder.getAnnoncesDonne();

                    //Log.i(TAG, "onClick:B "+position+isExpanded.get(position)+isPreneurChecked.get(position));

                   if(annoncesDonne.getEquipeAnnonces().getNomEquipe()=="EquipeA"){
                        annoncesDonne.setEquipeAnnonces(equipeA);
                        annoncesDonne.setNbTierce(Integer.parseInt(holder.getNbTierce_team1().getText().toString()));
                        annoncesDonne.setNbCinquante(Integer.parseInt(holder.getNbCinquante_team1().getText().toString()));
                        annoncesDonne.setNbCent(Integer.parseInt(holder.getNbCent_team1().getText().toString()));
                        annoncesDonne.setNbCarreAutre(Integer.parseInt(holder.getNbCarre_autre_team1().getText().toString()));

                        if(holder.getCarre_valet_team1().isChecked()){ annoncesDonne.setCarreValet(true);
                        }else{ annoncesDonne.setCarreValet(false); }

                        if(holder.getCarre_9_team1().isChecked()){ annoncesDonne.setCarre9(true);
                        }else{ annoncesDonne.setCarre9(false);}


                    }else if(annoncesDonne.getEquipeAnnonces().getNomEquipe()=="EquipeB"){
                        annoncesDonne.setEquipeAnnonces(equipeB);
                        annoncesDonne.setNbTierce(Integer.parseInt(holder.getNbTierce_team2().getText().toString()));
                        annoncesDonne.setNbCinquante(Integer.parseInt(holder.getNbCinquante_team2().getText().toString()));
                        annoncesDonne.setNbCent(Integer.parseInt(holder.getNbCent_team2().getText().toString()));
                        annoncesDonne.setNbCarreAutre(Integer.parseInt(holder.getNbCarre_autre_team2().getText().toString()));

                       if(holder.getCarre_valet_team2().isChecked()){ annoncesDonne.setCarreValet(true);
                       }else{ annoncesDonne.setCarreValet(false); }

                       if(holder.getCarre_9_team2().isChecked()){ annoncesDonne.setCarre9(true);
                       }else { annoncesDonne.setCarre9(false); }

                    }

                   Log.i(TAG, "upDateCurrentDonneC: " + scoreA + " " + scoreB + " " + preneur.getNomJoueur()+ " " +
                            couleur + " " + belote.getNomEquipe() + " "+ capot.getNomEquipe() + " "+ (position+1)+ " "+annoncesDonne.getEquipeAnnonces().getNomEquipe()
                            + " "+ annoncesDonne.getNbTierce() + " " + annoncesDonne.getNbCinquante()+" "+
                            annoncesDonne.getNbCent()+" "+ annoncesDonne.getNbCarreAutre()+" "+
                            annoncesDonne.isCarreValet()+" "+annoncesDonne.isCarre9());

                    //Log.i(TAG, "onClickscore: "+isScoreModified);

                    if(isScoreModified.get(position)){
                        upDatecurrentDonne(position+1);
                    }

                    setTotalScore();
                    upDateTotalScore();

                    if(lastModeEquipe.equals(ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF.toString())) {

                        displayTotalScore();

                    }else if(lastModeEquipe.equals(ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME.toString())){

                        displayTotalScoreAnonyme();
                    }

                    testFinPartie();

                }
            }
        });
    }

    private void testFinPartie() {

        //Log.i(TAG, "testFinPartie: ");

        if (mListener != null) {
            mListener.onDonneAdapterTestFinPartie();
        }

    }

    private void displayTotalScoreAnonyme() {

        if (mListener != null) {
            mListener.onDonneAdapterDisplayTotalScoreAnonyme();
        }
    }

    private void displayTotalScore() {

        if (mListener != null) {
            mListener.onDonneAdapterDisplayScoreTotal();
        }
    }

    private void upDateTotalScore() {
        if (mListener != null) {
            mListener.onDonneAdapterUpDateTotalScore();
        }

    }

    private void setTotalScore() {

        if (mListener != null) {
            mListener.onDonneAdapterSetTotalScore();
        }

    }

    private void upDatecurrentDonne(int numDonne) {

        if (mListener != null) {
            mListener.onDonneAdapterUpdateDonne(numDonne);
        }

    }

    public void setListenerChecked(final DonneViewHolder holder, final int position, final ToggleButton mainTb, final ToggleButton secondTb) {

        mainTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && secondTb.isChecked()) {
                    holder.setColorBeloteCapot(mainTb,secondTb,1.0f,0.3f);
                    mainTb.setChecked(true);
                    secondTb.setChecked(false);

                } else if (isChecked && !secondTb.isChecked()) {
                    holder.setColorBeloteCapot(mainTb,secondTb,1.0f,0.3f);
                    mainTb.setChecked(true);
                    secondTb.setChecked(false);

                } else if (!isChecked && !secondTb.isChecked()) {
                    mainTb.setChecked(false);
                    secondTb.setChecked(false);
                    holder.setColorBeloteCapot(mainTb,secondTb,1.0f,1.0f);
                }

                calculScoreDonne(holder);
                displayDonneScore(holder);
                isScoreModified.set(position,true);


                if(holder.getCapot_team1().isChecked()){ capot.setNomEquipe("EquipeA");
                }else if(holder.getCapot_team2().isChecked()){ capot.setNomEquipe("EquipeB");
                }else { capot.setNomEquipe("NoCapot");
                }

                if(holder.getBelote_team1().isChecked()){ belote.setNomEquipe("EquipeA");
                }else if(holder.getBelote_team2().isChecked()){ belote.setNomEquipe("EquipeB");
                }else{ belote.setNomEquipe("NoBelote");
                }
            }


        });

    }

    //todo voir pour déplacer ses méthodes dans ViewHolder
    //todo voir pour mettre le number picker du côté opposé au preneur
    private void initNumberPicker(final DonneViewHolder holder, final int position) {
        //gestion du detector
        detector = new GestureDetector(mContext, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //todo gérer le fling gauche et droite pour envoyer le numberpicker et le fling vertical pour selection numberpicker. peut être dupliquer dans le scroll

                if (e2.getX() < e1.getX() && Math.abs(e2.getY() - e1.getY()) < 100) {
                    numberPickerposition = 1; //Left
                    float endX = beginX - width / 2 - 40;
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
                //todo voir pour numberpicker au centre
                return true;
            }
            @Override
            public boolean onDown(MotionEvent e) {return true;}
            @Override
            public void onShowPress(MotionEvent e) { }
            @Override
            public boolean onSingleTapUp(MotionEvent e) {return true;}
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { return true; }
            @Override
            public void onLongPress(MotionEvent e) { }
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

        //todo voir si intéressant de gérer le capot à 0 plutot que touche
        holder.getNumberPicker().setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                scoreNumberPicker = newVal;
                calculScoreDonne(holder);
                displayDonneScore(holder);
                isScoreModified.set(position,true);

            }
        });
    }

    private void displayDonneScore(DonneViewHolder holder) {
        holder.setScoreEquipeA(scoreA);
        holder.setScoreEquipeB(scoreB);
    }

    private void setListenerClickPreneur(final DonneViewHolder holder, final int position, final TextView mainJoueur, final TextView secondJoueur, final TextView thirdJoueur, final TextView fourthJoueur) {

        mainJoueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setColorPlayerPreneur(mainJoueur, true);
                holder.setColorPlayerPreneur(secondJoueur, false);
                holder.setColorPlayerPreneur(thirdJoueur, false);
                holder.setColorPlayerPreneur(fourthJoueur, false);

                preneur = new Joueur(mainJoueur.getText().toString());
                holder.setGestionScoreVisible();
                isPreneurChecked.set(position,true);

                if(lastTypeAnnonce.equals(TypeAnnonce.SANS_ANNONCE.toString())){
                    //todo voir ce if utile ainsi que le else
                    //Log.i(TAG, "onBindViewHolder: coucou adapter");

                }else if(lastTypeAnnonce.equals(TypeAnnonce.AVEC_ANNONCES.toString())){
                   // Log.i(TAG, "onBindViewHolder: hello adapter");
                    holder.setGestionScoreVisible();
                    holder.setCardViewAnnoncesBtnVisible();

                }else{
                    //Log.i(TAG, "onBindViewHolder: hello rien");
                }

                if(!isScoreModified.get(position)&&holder.getScoreEquipeA()==0&&holder.getScoreEquipeB()==0){
                    Log.i(TAG, "onClick: preneur hello");
                    scoreA=0;
                    scoreB=0;
                }else {
                    calculScoreDonne(holder);
                }
                displayDonneScore(holder);

                isScoreModified.set(position,true);
                Log.i(TAG, "onClick: preneur "+ isScoreModified);

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

                if(mainCouleur.getId()==R.id.preneur_carreau){
                    couleur = Couleur.CARREAU;
                }else if (mainCouleur.getId()==R.id.preneur_coeur){
                    couleur = Couleur.COEUR;
                }else if (mainCouleur.getId()==R.id.preneur_pique){
                    couleur = Couleur.PIQUE;
                }else if (mainCouleur.getId()==R.id.preneur_trefle){
                    couleur = Couleur.TREFLE;
                }
            }
        });

    }

    private void setListenerToggleV9(final DonneViewHolder holder, final int position, final ToggleButton mainTb) {

        mainTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mainTb.setBackgroundResource(R.drawable.radius_button_accent);

                if(holder.getCarre_valet_team1().isChecked()){

                    annoncesDonne.setCarreValet(true);

                }else if(holder.getCarre_team2().isChecked()){

                }

                if(holder.getCarre_9_team1().isChecked()){

                }else if(holder.getCarre_team2().isChecked()){

                }

                calculScoreDonne(holder);
                displayDonneScore(holder);
                isScoreModified.set(position,true);
            }
        });
    }

    private void setListenerClickButton(final DonneViewHolder holder, final int position, final Button mainBtn, final TextView mainTv) {
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=holder.getNbAnnonces(mainTv);
                i++;
                if(i>4) {
                    i=0;
                    holder.setColorAnnonce(mainBtn,false);
                    mainTv.setText(String.valueOf(i));

                }else{
                    mainTv.setText(String.valueOf(i));
                    holder.setColorAnnonce(mainBtn,true);
                }

                calculScoreDonne(holder);
                displayDonneScore(holder);
                isScoreModified.set(position,true);
            }
        });
    }

    private void setListenerClickCarre(final DonneViewHolder holder, final ToggleButton mainTb) {

        mainTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //todo voir pour éviter un gros bouton sur Carré
                mainTb.setBackgroundResource(R.drawable.radius_button_accent);

                if(mainTb.isChecked()){

                    holder.setCardViewCarreVisible();

                    if(mainTb==holder.getCarre_team1()){

                        holder.setToggleGone(holder.getCarre_9_team2(),holder.getCarre_valet_team2());
                        holder.setButtonGone(holder.getCarre_autre_team2());
                        holder.setTextViewGone(holder.getNbCarre_autre_team2());
                        holder.setToggleVisible(holder.getCarre_9_team1(),holder.getCarre_valet_team1());
                        holder.setButtonVisible(holder.getCarre_autre_team1());
                        holder.setTextViewVisible(holder.getNbCarre_autre_team1());


                    }else if(mainTb==holder.getCarre_team2()){

                        holder.setToggleGone(holder.getCarre_9_team1(),holder.getCarre_valet_team1());
                        holder.setButtonGone(holder.getCarre_autre_team1());
                        holder.setTextViewGone(holder.getNbCarre_autre_team1());
                        holder.setToggleVisible(holder.getCarre_9_team2(),holder.getCarre_valet_team2());
                        holder.setButtonVisible(holder.getCarre_autre_team2());
                        holder.setTextViewVisible(holder.getNbCarre_autre_team2());
                    }

                }else{

                    holder.setCardViewCarreGone();
                }
            }
        });
    }


    private void initData() {
        //Appel de la dernière partie
        lastPartie = MainActivity.beloteSkorDb.partieDao().getLastPartie();

        lastModeEquipe=lastPartie.getType().getModeEquipe();

        lastTypeAnnonce=lastPartie.getType().getTypeAnnonce();

        equipeA = new Equipe("EquipeA");
        equipeB = new Equipe("EquipeB");
        equipeNull = new Equipe("NoAnnonces");

        annoncesDonne=new AnnoncesDonne(equipeNull,0,0,0,0,false,false);

        //todo vérifier inutilité de cette initialisation
        scoreA=0;
        scoreB=0;
        isScoreModified.add(false);
        Log.i(TAG, "initData: ");
    }

    private void calculScoreDonne(DonneViewHolder holder){
        scoreExtraA =0;
        scoreExtraB = 0;
        primeCarreValet = 0;
        primeCarre9 =0;
        scoreBelote1 =0;
        scoreAnnonces1=0;
        scoreBelote2=0;
        scoreAnnonces2 =0;

        //prise en charge de la belote
        if(holder.getBelote_team1().isChecked()){

            scoreBelote1 = 20;
            scoreExtraA+=scoreBelote1;
        }else if(holder.getBelote_team2().isChecked()){
            scoreBelote2=20;
            scoreExtraB+=scoreBelote2;
        }

        //gestion des annonces

        //gestion des carréd de valet et de 9
        if(holder.getCarre_valet_team1().isChecked()||holder.getCarre_valet_team2().isChecked()){
            primeCarreValet = 200;
        }

        if(holder.getCarre_9_team1().isChecked()||holder.getCarre_9_team2().isChecked()){
            primeCarre9 = 150;
        }


        if(holder.getAnnonces_team1().isChecked()){

            scoreAnnonces1=(20*Integer.parseInt(holder.getNbTierce_team1().getText().toString()))+(50*Integer.parseInt(holder.getNbCinquante_team1().getText().toString()))+
                    (100*Integer.parseInt(holder.getNbCent_team1().getText().toString()))+(100*Integer.parseInt(holder.getNbCarre_autre_team1().getText().toString()))+primeCarreValet+primeCarre9;

            scoreExtraA+=scoreAnnonces1;

        }else if(holder.getAnnonces_team2().isChecked()){

            scoreAnnonces2=(20*Integer.parseInt(holder.getNbTierce_team2().getText().toString()))+(50*Integer.parseInt(holder.getNbCinquante_team2().getText().toString()))+
                    (100*Integer.parseInt(holder.getNbCent_team2().getText().toString()))+(100*Integer.parseInt(holder.getNbCarre_autre_team2().getText().toString()))+primeCarreValet+primeCarre9;

            scoreExtraB+=scoreAnnonces2;
        }

        //prise en charge du capot

        if(holder.getCapot_team1().isChecked()){
            scoreExtraA+=252;
            scoreA=scoreExtraA;
            scoreB=scoreExtraB;
            return;

        } else if (holder.getCapot_team2().isChecked()){
            scoreExtraB+=252;
            scoreB=scoreExtraB;
            scoreA=scoreExtraA;
            return;
        }

        //cas hors capot

        if(numberPickerposition==1){
            scoreA = scoreExtraA + scoreNumberPicker;
            scoreB = scoreExtraB + (162-scoreNumberPicker);
        } else if(numberPickerposition==2){
            scoreB = scoreExtraB + scoreNumberPicker;
            scoreA = scoreExtraA + (162-scoreNumberPicker);
        }else{
            scoreA = scoreExtraA;
            scoreB = scoreExtraB;
        }



        //todo faire gestion du litige si nécessaire
        if(scoreA<scoreB && (preneur.getNomJoueur().equals(holder.getPlayer1Name().getText().toString())||preneur.getNomJoueur().equals(holder.getPlayer2Name().getText().toString()))){

            scoreA=scoreBelote1;
            scoreB=162+scoreExtraB;

        }else if(scoreA>scoreB && (preneur.getNomJoueur().equals(holder.getPlayer3Name().getText().toString())||preneur.getNomJoueur().equals(holder.getPlayer4Name().getText().toString()))){

            scoreB=scoreBelote2;
            scoreA=162+scoreExtraA;
        }

    }

    //todo voir si simplifiable le notify avec le ViewModel
    public void setNotifyDonneAdapter(List<Donne> donnes){

        this.donnes = donnes;
        notifyDataSetChanged();

    }

    public String[] getPlayers() {

        if(lastModeEquipe.equals(ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF.toString())){

            if (mListener != null) {
            mListener.onDonneAdapterPlayers();

            return mListener.onDonneAdapterPlayers();
            }

         }else if(lastModeEquipe.equals(ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME.toString())){

//todo voir comment repasser par les strings pour les noms
            String[] listPlayers = {"Us1","Us2","You1","You2"};

            return listPlayers;
        }

        return null;
    }

    public int getScoreA() { return scoreA; }

    public int getScoreB() { return scoreB; }

    public Joueur getPreneur() { return preneur; }

    public Couleur getCouleur() { return couleur; }

    public Equipe getBelote() { return belote; }

    public Equipe getCapot() { return capot; }

    public AnnoncesDonne getAnnoncesDonne() { return annoncesDonne; }
}
