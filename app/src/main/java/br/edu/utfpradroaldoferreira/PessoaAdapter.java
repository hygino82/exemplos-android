package br.edu.utfpradroaldoferreira;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PessoaAdapter extends BaseAdapter {

    private Context context;
    private List<Pessoa> listaPessoas;
    private String[] tipos;

    private static class PessoaHolder {
        public TextView textViewValorNome;
        public TextView textViewValorMedia;
        public TextView textViewValorBolsista;
        public TextView textViewValorTipo;
        public TextView textViewValorMaoUsada;
    }

    public PessoaAdapter(Context context, List<Pessoa> listaPessoas) {
        this.context = context;
        this.listaPessoas = listaPessoas;

        tipos = context.getResources().getStringArray(R.array.tipos);
    }

    @Override
    public int getCount() {
        return listaPessoas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPessoas.get(position);
    }

    @Override
    public long getItemId(int position) {
        //não temos id por enquanto
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PessoaHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.linha_lista_pessoas, parent, false);

            holder = new PessoaHolder();

            holder.textViewValorNome = convertView.findViewById(R.id.textViewValorNome);
            holder.textViewValorMedia = convertView.findViewById(R.id.textViewValorMedia);
            holder.textViewValorBolsista = convertView.findViewById(R.id.textViewValorBolsista);
            holder.textViewValorTipo = convertView.findViewById(R.id.textViewValorTipo);
            holder.textViewValorMaoUsada = convertView.findViewById(R.id.textViewValorMaoUsada);

            convertView.setTag(holder);
        } else {
            holder = (PessoaHolder) convertView.getTag();
        }

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

        return convertView;
    }
}
