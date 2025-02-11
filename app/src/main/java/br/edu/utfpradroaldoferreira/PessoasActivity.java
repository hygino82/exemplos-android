package br.edu.utfpradroaldoferreira;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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

    private List<Pessoa> listaPessoas;

    //edicao
    private int posicaoSelecionada = -1;

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

        popularListaPessoas();
    }

    private void popularListaPessoas() {

        listaPessoas = new ArrayList<>();

        //para testes
        listaPessoas.add(new Pessoa("Gorete", 87, false, 1, MaoUsada.Direita));
        listaPessoas.add(new Pessoa("Juvenal", 68, true, 3, MaoUsada.Ambas));

        pessoaRecyclerViewAdapter = new PessoaRecyclerViewAdapter(this, listaPessoas);

        pessoaRecyclerViewAdapter.setOnCreateContextMenu(new PessoaRecyclerViewAdapter.OnCreateContextMenu() {
            @Override
            public void onCreateContextMenu(ContextMenu menu,
                                            View view,
                                            ContextMenu.ContextMenuInfo menuInfo,
                                            int position,
                                            MenuItem.OnMenuItemClickListener menuItemClickListener) {

                getMenuInflater().inflate(R.menu.pessoas_item_selecionado, menu);

                for (int i = 0; i < menu.size(); i++) {
                    menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
                }
            }
        });

        pessoaRecyclerViewAdapter.setOnContextMenuClickListener(new PessoaRecyclerViewAdapter.OnContextMenuClickListener() {
            @Override
            public boolean onContextMenuClickListener(MenuItem menuItem, int position) {
                int idMenuItem = menuItem.getItemId();

                if (idMenuItem == R.id.menuItemEditar) {
                    editarPessoa(position);
                    return true;
                } else if (idMenuItem == R.id.menuItemExcluir) {
                    excluirPessoa(position);
                    return true;
                } else {
                    return false;
                }
            }
        });

        pessoaRecyclerViewAdapter.setOnItemClickListener(new PessoaRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editarPessoa(position);
            }
        });

        recyclerViewPessoas.setAdapter(pessoaRecyclerViewAdapter);
    }

    public void abrirSobre() {

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

    public void abrirNovaPessoa() {

        Intent intentAbertura = new Intent(this, PessoaActivity.class);
        //alteração para abertura da tela
        intentAbertura.putExtra(PessoaActivity.KEY_MODO, PessoaActivity.MODO_NOVO);
        launcherNovaPessoa.launch(intentAbertura);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //trata os clicks do menu
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemAdicionar) {
            abrirNovaPessoa();
            return true;
        } else if (idMenuItem == R.id.menuItemSobre) {
            abrirSobre();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pessoas_opcoes, menu);
        //o retorno true faz com que seja exibido o menu
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.pessoas_item_selecionado, menu);
    }

    private void excluirPessoa(int position) {
        listaPessoas.remove(position);
        pessoaRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void editarPessoa(int posicao) {
        posicaoSelecionada = posicao;

        Pessoa pessoa = listaPessoas.get(posicaoSelecionada);
        Intent intentAbertura = new Intent(this, PessoaActivity.class);

        intentAbertura.putExtra(PessoaActivity.KEY_MODO, PessoaActivity.MODO_EDITAR);
        intentAbertura.putExtra(PessoaActivity.KEY_NOME, pessoa.getNome());
        intentAbertura.putExtra(PessoaActivity.KEY_MEDIA, pessoa.getMedia());
        intentAbertura.putExtra(PessoaActivity.KEY_BOLSISTA, pessoa.isBolsista());
        intentAbertura.putExtra(PessoaActivity.KEY_TIPO, pessoa.getTipo());
        intentAbertura.putExtra(PessoaActivity.KEY_MAO_USADA, pessoa.getMaoUsada().toString());

        launcherEditarPessoa.launch(intentAbertura);
    }

    ActivityResultLauncher<Intent> launcherEditarPessoa = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

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

                            Pessoa pessoa = listaPessoas.get(posicaoSelecionada);
                            pessoa.setNome(nome);
                            pessoa.setMedia(media);
                            pessoa.setBolsista(bolsista);
                            pessoa.setTipo(tipo);
                            pessoa.setMaoUsada(MaoUsada.valueOf(maoUsadaTexto));

                            pessoaRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                    posicaoSelecionada = -1;
                }
            });
}