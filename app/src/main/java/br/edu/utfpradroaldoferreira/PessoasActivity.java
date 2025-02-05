package br.edu.utfpradroaldoferreira;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

        setTitle(getString(R.string.controle_de_pessoas));

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

        listaPessoas = new ArrayList<>();

        pessoaRecyclerViewAdapter = new PessoaRecyclerViewAdapter(this, listaPessoas, onItemClickListener);

        recyclerViewPessoas.setAdapter(pessoaRecyclerViewAdapter);
    }

    public void abrirSobre(View view) {

        Intent intentAbertura = new Intent(this, SobreActivity.class);

        startActivity(intentAbertura);
    }

    ActivityResultLauncher<Intent> launcherNovaPessoa = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == PessoasActivity.RESULT_OK) {

                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {

                            String nome = bundle.getString(PessoaActivity.KEY_NOME);
                            int media = bundle.getInt(PessoaActivity.KEY_MEDIA);
                            boolean bolsista = bundle.getBoolean(PessoaActivity.KEY_BOLSISTA);
                            int tipo = bundle.getInt(PessoaActivity.KEY_TIPO);
                            String maoUsadaTexto = bundle.getString(PessoaActivity.KEY_MAO_USADA);

                            Pessoa pessoa = new Pessoa(nome, media, bolsista, tipo, MaoUsada.valueOf(maoUsadaTexto));

                            listaPessoas.add(pessoa);

                            pessoaRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

    public void abrirNovaPessoa(View view) {

        Intent intentAbertura = new Intent(this, PessoaActivity.class);

        launcherNovaPessoa.launch(intentAbertura);
    }
}