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
public interface DonneDao {

    @Query("SELECT * FROM donne")
    List<Donne> getAllDonnes();


    @Query("SELECT * FROM donne WHERE num_partie IN (:partieId)")
    List<Donne> getAllDonnesPartiesCourantes(int partieId);

    @Query("SELECT * FROM donne WHERE donneId IN (:donneId)")
    Donne loadDonneById(int donneId);

    @Query("SELECT * FROM donne ORDER BY donneId DESC LIMIT 1")
    Donne getLastDonne();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDonne(Donne donne);

    @Update
    void updateDonne(Donne donne);

}
