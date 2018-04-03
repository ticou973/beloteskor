package com.skor.beloteskor.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by thierrycouilleault on 23/01/2018.
 */


@Database(entities ={Partie.class, Joueur.class, Equipe.class, Donne.class }, version =4)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {


    public abstract PartieDao partieDao();
    public abstract JoueurDao joueurDao();
    public abstract DonneDao donneDao();
    public abstract EquipeDao EquipeDao();

}
