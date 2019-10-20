package com.example.agendatelefonicaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.agendatelefonicaapp.R;
import com.example.agendatelefonicaapp.adapter.RecyclerViewAdapter;
import com.example.agendatelefonicaapp.data.ContatoDao;
import com.example.agendatelefonicaapp.data.Database;
import com.example.agendatelefonicaapp.interfaces.OnClick;
import com.example.agendatelefonicaapp.model.Contato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements OnClick {
    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Contato> listaContato = new ArrayList<>();
    private TextInputLayout nomeContato;
    private TextInputLayout telefoneContato;
    private ContatoDao contatoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeContato.getEditText().getText().toString();
                String telefone = telefoneContato.getEditText().getText().toString();

                new Thread(() -> {
                    Contato contato = new Contato(nome, telefone);

                    if(nome != null) {
                        contatoDao.insertContato(contato);
                    }
                }).start();

                buscaTodosContatos();
            }

        });
    }

    private void initViews(){
        btnAdd = findViewById(R.id.btnAdd);
        nomeContato = findViewById(R.id.textInputLayoutNome);
        telefoneContato = findViewById(R.id.textInputLayoutTelefone);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(listaContato, this);
        contatoDao = Database.getDatabase(this).contatoDao();
    }

    @Override
    public void OnClick(Contato contato) {
        nomeContato.getEditText().setText(contato.getNomeContato());
        telefoneContato.getEditText().setText(contato.getTelefoneContato());
    }

    private void buscaTodosContatos(){
        contatoDao.getAllContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contatos -> {
                    adapter.atualizaListaContato(contatos);
                }, throwable -> {
                    Log.i("TAG", "Método getAllContacts" + throwable.getMessage());
                });
    }
}
