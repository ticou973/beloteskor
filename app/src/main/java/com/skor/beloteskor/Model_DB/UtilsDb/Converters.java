package com.skor.beloteskor.Model_DB.UtilsDb;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by thierrycouilleault on 23/01/2018.
 */

public class Converters {
    @TypeConverter
    public static int couleurToCodeCouleur(Couleur couleur) {

        if (couleur == Couleur.CARREAU) {

            return 1;

        }else if (couleur == Couleur.COEUR) {

            return 2;
        }else if (couleur == Couleur.PIQUE) {

            return 3;
        }else if (couleur == Couleur.TREFLE) {

            return 4;
        }

        return 0;
    }

    @TypeConverter
    public static Couleur CodeCouleurToCouleur (int codeCouleur) {

        if (codeCouleur == 1) {

            return Couleur.CARREAU;

        }else if (codeCouleur == 2) {

            return Couleur.COEUR;

        }else if (codeCouleur == 3) {

            return Couleur.PIQUE;

        }else if (codeCouleur == 4) {

            return Couleur.TREFLE;
        }

        return null;

    }

    @TypeConverter
    public static int modeEquipeToCodeModeEquipe(ModeEquipe modeEquipe){

        if(modeEquipe == ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME)  return 1;
        if(modeEquipe == ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF)  return 2;
        if(modeEquipe == ModeEquipe.MODE_EQUIPE_DYNAMIQUE)  return 3;
        if(modeEquipe == ModeEquipe.MODE_INDIVIDUEL)  return 4;

        return 0;
    }

    @TypeConverter
    public static ModeEquipe codeModeEquipeToModeEquipe (int codeModeEquipe) {
        if (codeModeEquipe == 1) return ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME;
        if (codeModeEquipe == 2) return ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF;
        if (codeModeEquipe == 3) return ModeEquipe.MODE_EQUIPE_DYNAMIQUE;
        if (codeModeEquipe == 4) return ModeEquipe.MODE_INDIVIDUEL;

        return null;
    }

    @TypeConverter
    public static SensJeu CodeSensJeutoSensJeu (Boolean codeSensJeu) {
        if (codeSensJeu) return SensJeu.SENS_AIGUILLE;
        if(codeSensJeu==false) return SensJeu.SENS_INVERSE_AIGUILLE;

        return null;
    }

    @TypeConverter
    public static  Boolean SensJeutoCodeSensJeu (SensJeu sensJeu) {

        if(sensJeu==SensJeu.SENS_AIGUILLE) return true;
        if(sensJeu==SensJeu.SENS_INVERSE_AIGUILLE) return false;

        return true;
    }

}
