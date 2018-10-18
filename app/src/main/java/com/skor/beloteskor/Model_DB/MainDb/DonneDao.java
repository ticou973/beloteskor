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
public interface DonneDao {

    @Query("SELECT * FROM donne")
    LiveData<List<Donne>> getAllDonnes();


    @Query("SELECT * FROM donne WHERE num_partie IN (:partieId)")
    LiveData<List<Donne>> getAllDonnesPartiesCourantes(int partieId);

    @Query("SELECT * FROM donne WHERE donneId IN (:donneId)")
    LiveData<Donne> loadDonneById(int donneId);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Donne donne);

    @Update
    void updateDonne(Donne donne);

}
