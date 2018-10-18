package com.skor.beloteskor.Model_DB.MainDb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

/**
 * Created by thierrycouilleault on 25/01/2018.
 */


@Dao
public interface EquipeDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertEquipe(Equipe equipe);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Equipe ... equipes);
}
