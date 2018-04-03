package com.skor.beloteskor.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

/**
 * Created by thierrycouilleault on 25/01/2018.
 */


@Dao
public interface EquipeDao {

    @Insert
    void insertAll(Equipe equipe);
}
