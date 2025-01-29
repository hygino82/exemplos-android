package br.edu.utfpradroaldoferreira;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PessoaRecyclerViewAdapter extends RecyclerView.Adapter<PessoaRecyclerViewAdapter.PessoaHolder> {

    private OnItemClickListener onItemClickListener;
    private Context context;
    private List<Pessoa> listaPessoas;
    private String[] tipos;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public PessoaRecyclerViewAdapter(Context context, List<Pessoa> listaPessoas, OnItemClickListener listener) {
        this.context = context;
        this.listaPessoas = listaPessoas;
        this.onItemClickListener = listener;
        tipos = context.getResources().getStringArray(R.array.tipos);
    }

    public class PessoaHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textViewValorNome;
        public TextView textViewValorMedia;
        public TextView textViewValorBolsista;
        public TextView textViewValorTipo;
        public TextView textViewValorMaoUsada;

        public PessoaHolder(@NonNull View itemView) {
            super(itemView);

            textViewValorNome = itemView.findViewById(R.id.textViewValorNome);
            textViewValorMedia = itemView.findViewById(R.id.textViewValorMedia);
            textViewValorBolsista = itemView.findViewById(R.id.textViewValorBolsista);
            textViewValorTipo = itemView.findViewById(R.id.textViewValorTipo);
            textViewValorMaoUsada = itemView.findViewById(R.id.textViewValorMaoUsada);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int pos = getAdapterPosition();

                if (pos != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(v, pos);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {

            int pos = getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemLongClick(v, pos);
                return true;
            }
            return false;
        }
    }

    @NonNull
    @Override
    public PessoaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.linha_lista_pessoas, parent, false);

        return new PessoaHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaHolder holder, int position) {

        Pessoa pessoa = listaPessoas.get(position);

        holder.textViewValorNome.setText(pessoa.getNome());
        holder.textViewValorMedia.setText(String.valueOf(pessoa.getMedia()));

        if (pessoa.isBolsista()) {
            holder.textViewValorBolsista.setText(R.string.possui_bolsa);
        } else {
            holder.textViewValorBolsista.setText(R.string.nao_possui_bolsa);
        }

        //retorna a string com a posição onde foi guardado no array
        holder.textViewValorTipo.setText(tipos[pessoa.getTipo()]);

        //usar na escolha de região
        switch (pessoa.getMaoUsada()) {
            case Direita:
                holder.textViewValorMaoUsada.setText(R.string.direita);
                break;
            case Esquerda:
                holder.textViewValorMaoUsada.setText(R.string.esquerda);
                break;
            case Ambas:
                holder.textViewValorMaoUsada.setText(R.string.ambas);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listaPessoas.size();
    }
}
