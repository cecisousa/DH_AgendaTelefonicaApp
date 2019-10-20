package com.example.agendatelefonicaapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.agendatelefonicaapp.model.Contato;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface ContatoDao {

    @Insert
    void insertContato (Contato contato);

    @Delete
    void deleteContato (Contato contato);

    @Update
    void updateContato (Contato contato);

    @Query("SELECT * FROM contatos")
    Observable<List<Contato>> getAllContacts();

    @Query("SELECT * FROM contatos WHERE id = :id")
    Contato getById(long id);

    @Query("SELECT * FROM contatos WHERE nome = :nomeContato")
    Contato getByNome (String nomeContato);

}