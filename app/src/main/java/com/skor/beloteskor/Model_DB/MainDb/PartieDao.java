package com.skor.beloteskor.Model_DB.MainDb;

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
    Partie loadPartieById(int partieId);

    @Query("SELECT * FROM partie")
    List<Partie> getAllParties();

    @Query("SELECT * FROM partie ORDER BY partieId DESC LIMIT 1")
    Partie getLastPartie();

    @Query("SELECT * FROM partie WHERE ((A_1_nom_joueur IN (:name)) OR (A_2_nom_joueur IN (:name)) OR (B_1_nom_joueur IN (:name)) OR (B_2_nom_joueur) IN (:name))")
    List<Partie> getPartiesByPlayerName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPartie(Partie partie);

    @Update
    void updatePartie(Partie partie);

    @Query("DELETE FROM partie")
    void deleteAll();

}
