package com.example.agendatelefonicaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendatelefonicaapp.R;
import com.example.agendatelefonicaapp.interfaces.OnClick;
import com.example.agendatelefonicaapp.model.Contato;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Contato> contatoList;
    private OnClick listener;

    public RecyclerViewAdapter(List<Contato> contatoList, OnClick listener){
        this.contatoList = contatoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_agenda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contato contato = contatoList.get(position);
        holder.onBind(contato);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(contato);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contatoList.size();
    }

    public void atualizaListaContato(List<Contato> listaContatos){
        this.contatoList.clear();
        this.contatoList = listaContatos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeContato;
        private TextView telefoneContato;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeContato = itemView.findViewById(R.id.textViewNome);
            telefoneContato = itemView.findViewById(R.id.textViewTelefone);
        }

        public void onBind(Contato contato) {
            nomeContato.setText(contato.getNomeContato());
            telefoneContato.setText(contato.getTelefoneContato());
        }
    }
}
