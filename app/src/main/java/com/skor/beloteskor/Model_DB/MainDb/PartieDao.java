package com.skor.beloteskor.Model_DB.MainDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by thierrycouilleault on 23/01/2018.
 */

@Dao
public interface PartieDao {

    @Query("SELECT * FROM partie WHERE partieId IN (:partieId)")
    LiveData<Partie> loadPartieById(int partieId);

    @Query("SELECT * FROM partie")
    LiveData<List<Partie>> getAllParties();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPartie(Partie partie);

    @Update
    void updatePartie(Partie partie);

    /*@Query("SELECT * FROM partie WHERE nom_joueur1 IN (:nomJoueur)")
    List<Partie> loadPartiesByJoueur(String nomJoueur);*/

}
