package com.skor.beloteskor.Scores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skor.beloteskor.MainActivity;
import com.skor.beloteskor.Model_DB.MainDb.Donne;
import com.skor.beloteskor.Model_DB.MainDb.Equipe;
import com.skor.beloteskor.Model_DB.MainDb.Joueur;
import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.Model_DB.UtilsDb.AnnoncesDonne;
import com.skor.beloteskor.Model_DB.UtilsDb.Couleur;
import com.skor.beloteskor.Model_DB.UtilsDb.ModeEquipe;
import com.skor.beloteskor.Model_DB.UtilsDb.SensJeu;
import com.skor.beloteskor.Model_DB.UtilsDb.Table;
import com.skor.beloteskor.Model_DB.UtilsDb.TypeDePartie;
import com.skor.beloteskor.R;
import com.skor.beloteskor.Scores.Adapters.DonneAdapter;

import java.util.List;


public class ScoresFragment extends Fragment {

    private OnScoresFragmentInteractionListener mListener;
    private RecyclerView scoreRecyclerView;
    private DonneAdapter donneAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView addDonneBtn;
    private int numCurrentDonne;

    public static final String EXTRA="com.skor.beloteskor.MESSAGE";
    private Context context;

    private static String TAG = "coucou";

    //todo vérifier si nécessaire à ce niveau ou dans le init
    //variables de parties

    private Partie lastPartie;
    private List<Donne> donnes;
    private Donne currentDonne, firstDonne, nextDonne;
    private TypeDePartie lastTypePartie;
    private Table lastTable;
    private Joueur lastPremierDistrib, currentPreneur;
    private String lastNomPremierDistrib,lastTypeJeu,lastTypeAnnonce,lastModeEquipe,lastJoueur1EqA,lastJoueur2EqA,lastJoueur1EqB,lastJoueur2EqB;
    private SensJeu lastSensJeu;
    private int lastScoreEquipeA, lastScoreEquipeB,lastNbPoints,lastNbDonnes, scoreA, scoreB,scoreTotalEquipe1,scoreTotalEquipe2;
    private boolean lastPartieterm;
    private Equipe lastEquipeA, lastEquipeB, belote, capot;
    private Couleur currentCouleur;
    private AnnoncesDonne annoncesDonne;

                                        //Constructeur
    public ScoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScoresFragmentInteractionListener) {
            mListener = (OnScoresFragmentInteractionListener) context;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scores, container, false);

        //Recycler View
        //todo voir pour additemdecoration pour les recyclerview
        scoreRecyclerView = view.findViewById(R.id.recycler_view_scores);
        layoutManager = new LinearLayoutManager(context);
        scoreRecyclerView.setLayoutManager(layoutManager);

        donneAdapter = new DonneAdapter(donnes);
        scoreRecyclerView.setAdapter(donneAdapter);

        //Button add donnes
        addDonneBtn = view.findViewById(R.id.donne_add_btn);

        //todo voir pour insérer le cas de la mauvaise donne qui donne 162
        addDonneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDonnesBtn();
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //Todo à mettre dans la main activity avec l'aide du listener, idem pour le bouton d'ajout d'une mène ce qui permettra de rajouter un élément à la liste ?

   public interface OnScoresFragmentInteractionListener {
        void onScoresDonneNullChoice();
        void onScoreChangePreneur(int score1, int score2);
        void onScoreDisplayScoreTotal(int score1, int score2);
        void onScoreChangeScoreTotal(int score1,int score2);
        void onScoreDialogWinner();
    }

                                        //METHODES INTERNES
    private void initData() {
        //Appel de la dernière partie

        lastPartie = MainActivity.beloteSkorDb.partieDao().getLastPartie();

        testTablePartie();

        //Donnes
        currentDonne = new Donne(lastPartie.getPartieId(),1,0,0);

        MainActivity.beloteSkorDb.donneDao().insertDonne(currentDonne);
        donnes = MainActivity.beloteSkorDb.donneDao().getAllDonnesPartiesCourantes(lastPartie.getPartieId());

        //displayLogTestTablePartie();

    }

    private void addDonnesBtn() {
        numCurrentDonne = donnes.size();
        upDateCurrentDonne(numCurrentDonne);

        if (scoreA == 0 && scoreB == 0) {
            showDialogModeEquipe();

        }else{
            setTotalScore();
            upDateTotalScore();
            testFinPartie();

            if (!lastPartie.isPartieterminee()){
                createDonne();
            }

        }
    }

    public void testFinPartie() {

        /*if(lastTypeJeu.equals(TypeJeu.DONNES.toString())){

            if(numCurrentDonne<lastNbDonnes){
                createDonne();
            }else{
                addDonneBtn.setVisibility(View.INVISIBLE);

                showDialogWinner();
                lastPartie.setPartieterminee(true);
                MainActivity.beloteSkorDb.partieDao().updatePartie(lastPartie);
            }

        }else if (lastTypeJeu.equals(TypeJeu.POINTS.toString())){

            if(scoreTotalEquipe1<lastNbPoints&&scoreTotalEquipe2<lastNbPoints){
                createDonne();
            }else{
                addDonneBtn.setVisibility(View.INVISIBLE);
                showDialogWinner();
                lastPartie.setPartieterminee(true);
                MainActivity.beloteSkorDb.partieDao().updatePartie(lastPartie);
            }
        }*/

        if((lastNbDonnes!=0 && numCurrentDonne>=lastNbDonnes)||(lastNbPoints!=0 && scoreTotalEquipe1>=lastNbPoints)||(lastNbPoints!=0 && scoreTotalEquipe2>=lastNbPoints)){

            //todo gérer le passage de donner pour le dialog fragment
            //todo gérer le cas d'égalité des joueurs
            addDonneBtn.setVisibility(View.INVISIBLE);
            showDialogWinner();
            lastPartie.setPartieterminee(true);
            MainActivity.beloteSkorDb.partieDao().updatePartie(lastPartie);
        }

    }

    private void showDialogWinner() {

        if (mListener != null) {
            mListener.onScoreDialogWinner();
        }

    }

    public void upDateTotalScore() {

        lastPartie.setScoreEquipeA(scoreTotalEquipe1);
        lastPartie.setScoreEquipeB(scoreTotalEquipe2);

        MainActivity.beloteSkorDb.partieDao().updatePartie(lastPartie);

    }

    public void setTotalScore() {

        scoreTotalEquipe1=0;
        scoreTotalEquipe2=0;

        for (Donne donne: donnes) {

            scoreTotalEquipe1 += donne.getScore1();
            scoreTotalEquipe2 += donne.getScore2();
        }

        }

    public void displayScoreTotal(int scoreTotalEquipe1, int scoreTotalEquipe2) {

        if (mListener != null) {
            mListener.onScoreDisplayScoreTotal(scoreTotalEquipe1,scoreTotalEquipe2);
        }
    }

    public void displayChangeScoreTotal(int scoreTotalEquipe1, int scoreTotalEquipe2){

        if (mListener != null) {
            mListener.onScoreChangeScoreTotal(scoreTotalEquipe1, scoreTotalEquipe2);
        }

    }


    public void createDonne() {

        //changement du donneur si nécessaire
        if(lastModeEquipe.equals(ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF.toString())) {

            if (mListener != null) {
               mListener.onScoreChangePreneur(scoreTotalEquipe1, scoreTotalEquipe2);
            }
        }else if(lastModeEquipe.equals(ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME.toString())){

            if (mListener != null) {
                mListener.onScoreChangeScoreTotal(scoreTotalEquipe1, scoreTotalEquipe2);
            }
        }

        nextDonne = new Donne(lastPartie.getPartieId(), donnes.size() + 1, 0, 0);
        MainActivity.beloteSkorDb.donneDao().insertDonne(nextDonne);
        donnes = MainActivity.beloteSkorDb.donneDao().getAllDonnesPartiesCourantes(lastPartie.getPartieId());
        donneAdapter.setNotifyDonneAdapter(donnes);
    }

    public void upDateCurrentDonne(int numDonne){

        scoreA = donneAdapter.getScoreA();
        scoreB = donneAdapter.getScoreB();
        currentCouleur= donneAdapter.getCouleur();
        currentPreneur=donneAdapter.getPreneur();
        belote =donneAdapter.getBelote();
        capot = donneAdapter.getCapot();
        annoncesDonne = donneAdapter.getAnnoncesDonne();

      Log.i(TAG, "upDateCurrentDonneA: " + scoreA + " " + scoreB + " " + currentPreneur.getNomJoueur()+ " " +
                currentCouleur + " " + belote.getNomEquipe() + " "+ capot.getNomEquipe() + " "+ numDonne+ " "+annoncesDonne.getEquipeAnnonces().getNomEquipe()
                + " "+ annoncesDonne.getNbTierce() + " " + annoncesDonne.getNbCinquante()+" "+
                annoncesDonne.getNbCent()+" "+ annoncesDonne.getNbCarreAutre()+" "+
                annoncesDonne.isCarreValet()+" "+annoncesDonne.isCarre9());

        currentDonne = MainActivity.beloteSkorDb.donneDao().getDonnebyNumDonne(numDonne,lastPartie.getPartieId());

        currentDonne.setScore1(scoreA);
        currentDonne.setScore2(scoreB);
        currentDonne.setCouleur(currentCouleur);
        currentDonne.setPreneur(currentPreneur);
        currentDonne.setBelote(belote);
        currentDonne.setCapot(capot);
        currentDonne.setAnnoncesDonne(annoncesDonne);

        MainActivity.beloteSkorDb.donneDao().updateDonne(currentDonne);

        donnes = MainActivity.beloteSkorDb.donneDao().getAllDonnesPartiesCourantes(lastPartie.getPartieId());


        //todo retirer après validation de l'update (test)
        firstDonne=MainActivity.beloteSkorDb.donneDao().getDonnebyNumDonne(numDonne,lastPartie.getPartieId());

       Log.i(TAG, "upDateCurrentDonneB: " + firstDonne.getScore1() + " " + firstDonne.getScore2() + " " + firstDonne.getPreneur().getNomJoueur()+ " " +
        firstDonne.getCouleur() + " " + firstDonne.getBelote().getNomEquipe() + " "+ firstDonne.getCapot().getNomEquipe()+ " "+ firstDonne.getNumDonne()+ " "+firstDonne.getAnnoncesDonne().getEquipeAnnonces().getNomEquipe()
        + " "+ firstDonne.getAnnoncesDonne().getNbTierce() + " " + firstDonne.getAnnoncesDonne().getNbCinquante()+" "+
        firstDonne.getAnnoncesDonne().getNbCent()+" "+ firstDonne.getAnnoncesDonne().getNbCarreAutre()+" "+
        firstDonne.getAnnoncesDonne().isCarreValet()+" "+firstDonne.getAnnoncesDonne().isCarre9());

        donneAdapter.setNotifyDonneAdapter(donnes);
    }

    private void showDialogModeEquipe() {

        if (mListener != null) {
            mListener.onScoresDonneNullChoice();
        }
    }


    //todo virer après test
    private void testTablePartie(){

        //todo inutile à virer quand bd sera opérationnelle

        lastTypePartie = lastPartie.getType();
        lastTable = lastPartie.getTable();
        lastPremierDistrib = lastPartie.getPremierDistributeur();
        lastNomPremierDistrib = lastPremierDistrib.getNomJoueur();
        lastSensJeu = lastPartie.getSensJeu();
        lastScoreEquipeA = lastPartie.getScoreEquipeA();
        lastScoreEquipeB = lastPartie.getScoreEquipeB();
        lastPartieterm = lastPartie.isPartieterminee();

        lastTypeJeu = lastTypePartie.getTypeJeu();
        lastTypeAnnonce = lastTypePartie.getTypeAnnonce();
        lastModeEquipe = lastTypePartie.getModeEquipe();
        lastNbPoints = lastTypePartie.getNbPoints();
        lastNbDonnes = lastTypePartie.getNbDonnes();

        lastEquipeA = lastTable.getEquipeA();
        lastEquipeB = lastTable.getEquipeB();

        lastJoueur1EqA = lastEquipeA.getJoueur1().getNomJoueur();
        lastJoueur2EqA = lastEquipeA.getJoueur2().getNomJoueur();
        lastJoueur1EqB = lastEquipeB.getJoueur1().getNomJoueur();
        lastJoueur2EqB = lastEquipeB.getJoueur2().getNomJoueur();
    }

    private void displayLogTestTablePartie() {

        //todo à retirer en production
        String newligne=System.getProperty("line.separator");

        Log.i(TAG, newligne + lastPartie.getPartieId() + newligne + "type jeu : " + lastTypeJeu + newligne + "type Annonce : " + lastTypeAnnonce + newligne + "Mode Equipe : " + lastModeEquipe + newligne +  "Nb Points : " + lastNbPoints + newligne + "Nb Donnes : " + lastNbDonnes
                + newligne + "joueurs : " + lastJoueur1EqA + ", "  + lastJoueur2EqA + ", " + lastJoueur1EqB + ", " + lastJoueur2EqB + newligne + "premier distributeur : " + lastNomPremierDistrib + newligne + "Sens Jeu : "
                + lastSensJeu + newligne + "lastScoreEqA : " + lastScoreEquipeA + newligne + "lastScoreEquipeB : " + lastScoreEquipeB + newligne + "statut partie : " + lastPartieterm + newligne +
                "numero donne : " + donnes.get(donnes.size()-1).getNumDonne() + newligne + "Score 1 : " + donnes.get(donnes.size()-1).getScore1());
    }


    public int getScoreTotalEquipe1() {
        return scoreTotalEquipe1;
    }

    public void setScoreTotalEquipe1(int scoreTotalEquipe1) {
        this.scoreTotalEquipe1 = scoreTotalEquipe1;
    }

    public int getScoreTotalEquipe2() {
        return scoreTotalEquipe2;
    }

    public void setScoreTotalEquipe2(int scoreTotalEquipe2) {
        this.scoreTotalEquipe2 = scoreTotalEquipe2;
    }
}
