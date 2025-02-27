package br.edu.utfpradroaldoferreira;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.utfpradroaldoferreira.modelo.Pessoa;

public class PessoaRecyclerViewAdapter extends RecyclerView.Adapter<PessoaRecyclerViewAdapter.PessoaHolder> {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private OnCreateContextMenu onCreateContextMenu;
    private OnContextMenuClickListener onContextMenuClickListener;


    private Context context;
    private List<Pessoa> listaPessoas;
    private String[] tipos;

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    interface OnCreateContextMenu {
        void onCreateContextMenu(ContextMenu menu,
                                 View view,
                                 ContextMenu.ContextMenuInfo menuInfo,
                                 int position,
                                 MenuItem.OnMenuItemClickListener menuItemClickListener);
    }

    interface OnContextMenuClickListener {
        boolean onContextMenuClickListener(MenuItem menuItem, int position);
    }

    public PessoaRecyclerViewAdapter(Context context, List<Pessoa> listaPessoas) {
        this.context = context;
        this.listaPessoas = listaPessoas;
        tipos = context.getResources().getStringArray(R.array.tipos);
    }

    public class PessoaHolder extends RecyclerView.ViewHolder
            implements
            View.OnClickListener,
            View.OnLongClickListener,
            View.OnCreateContextMenuListener {
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
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {

            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
            return false;
        }

        MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if (onContextMenuClickListener != null) {
                    onContextMenuClickListener.onContextMenuClickListener(item, getAdapterPosition());
                    return true;
                }
                return false;
            }
        };

        public MenuItem.OnMenuItemClickListener getOnMenuItemClickListener() {
            return onMenuItemClickListener;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (onCreateContextMenu != null) {
                onCreateContextMenu.onCreateContextMenu(menu,
                        v,
                        menuInfo,
                        getAdapterPosition(),
                        onMenuItemClickListener);
            }
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

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnCreateContextMenu getOnCreateContextMenu() {
        return onCreateContextMenu;
    }

    public void setOnCreateContextMenu(OnCreateContextMenu onCreateContextMenu) {
        this.onCreateContextMenu = onCreateContextMenu;
    }

    public OnContextMenuClickListener getOnContextMenuClickListener() {
        return onContextMenuClickListener;
    }

    public void setOnContextMenuClickListener(OnContextMenuClickListener onContextMenuClickListener) {
        this.onContextMenuClickListener = onContextMenuClickListener;
    }
}
