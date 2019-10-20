package com.example.agendatelefonicaapp.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.agendatelefonicaapp.model.Contato;

@androidx.room.Database(entities = {Contato.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {


    private static volatile Database INSTANCE;

    public abstract ContatoDao contatoDao();

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, Database.class, "contatos_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
