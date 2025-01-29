package br.edu.utfpradroaldoferreira;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PessoasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPessoas;
    private RecyclerView.LayoutManager layoutManager;
    private PessoaRecyclerViewAdapter pessoaRecyclerViewAdapter;
    private PessoaRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    private List<Pessoa> listaPessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);

        recyclerViewPessoas = findViewById(R.id.recyclerViewPessoas);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewPessoas.setLayoutManager(layoutManager);
        recyclerViewPessoas.setHasFixedSize(true);
        recyclerViewPessoas.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        onItemClickListener = new PessoaRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Pessoa pessoa = listaPessoas.get(position);
                Toast.makeText(getApplicationContext(),
                        getString(R.string.pessoa_de_nome) + pessoa.getNome() + getString(R.string.foi_clicada),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Pessoa pessoa = listaPessoas.get(position);
                Toast.makeText(getApplicationContext(),
                        getString(R.string.pessoa_de_nome) + pessoa.getNome() + getString(R.string.recebeu_click_longo),
                        Toast.LENGTH_LONG).show();
            }
        };

        popularListaPessoas();
    }

    private void popularListaPessoas() {

        String[] pessoas_nomes = getResources().getStringArray(R.array.pessoas_nome);
        int[] pessoas_medias = getResources().getIntArray(R.array.pessoas_medias);
        int[] pessoas_bolsistas = getResources().getIntArray(R.array.pessoas_bolsistas);
        int[] pessoas_tipos = getResources().getIntArray(R.array.pessoas_tipos);
        int[] pessoas_maos_usadas = getResources().getIntArray(R.array.pessoas_maos_usadas);

        listaPessoas = new ArrayList<>();

        Pessoa pessoa;
        boolean bolsista;
        MaoUsada maoUsada;
        //cria um array com todas as contantes do enum
        MaoUsada[] maosUsadas = MaoUsada.values();

        for (int cont = 0; cont < pessoas_nomes.length; cont++) {
            // redundante usar bolsista = (pessoas_bolsistas[cont] == 1 ? true : false);
            bolsista = pessoas_bolsistas[cont] == 1;
            maoUsada = maosUsadas[pessoas_maos_usadas[cont]];

            pessoa = new Pessoa(
                    pessoas_nomes[cont],
                    pessoas_medias[cont],
                    bolsista,
                    pessoas_tipos[cont],
                    maoUsada
            );
            listaPessoas.add(pessoa);
        }

        pessoaRecyclerViewAdapter = new PessoaRecyclerViewAdapter(this, listaPessoas, onItemClickListener);

        recyclerViewPessoas.setAdapter(pessoaRecyclerViewAdapter);
    }
}