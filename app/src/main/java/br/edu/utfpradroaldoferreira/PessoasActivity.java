package br.edu.utfpradroaldoferreira;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PessoasActivity extends AppCompatActivity {

    private ListView listViewPessoas;
    private List<Pessoa> listaPessoas;

    private PessoaAdapter pessoaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);

        listViewPessoas = findViewById(R.id.listViewPessoas);
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

        pessoaAdapter = new PessoaAdapter(this, listaPessoas);

        listViewPessoas.setAdapter(pessoaAdapter);
    }
}