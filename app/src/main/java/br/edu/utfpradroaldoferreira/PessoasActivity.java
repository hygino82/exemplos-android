package br.edu.utfpradroaldoferreira;

import static br.edu.utfpradroaldoferreira.PessoaActivity.KEY_ID;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import br.edu.utfpradroaldoferreira.modelo.Pessoa;
import br.edu.utfpradroaldoferreira.persistencia.PessoasDatabase;
import br.edu.utfpradroaldoferreira.utils.UtilsAlert;


public class PessoasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPessoas;
    private RecyclerView.LayoutManager layoutManager;
    private PessoaRecyclerViewAdapter pessoaRecyclerViewAdapter;

    private List<Pessoa> listaPessoas;

    private int posicaoSelecionada = -1;

    private ActionMode actionMode;

    private View viewSelecionada;
    private Drawable backgroundDrawable;

    public static final String ARQUIVO_PREFERENCIAS = "br.edu.utfpradroaldoferreira.PREFERENCIAS";
    public static final String KEY_ORDENACAO_ASCENDENTE = "ORDENACAO_ASCENDENTE";

    public static final boolean PADRAO_INICIAL_ORDENACAO_ASCENDENTE = true;
    private boolean ordenacaoAscendente = PADRAO_INICIAL_ORDENACAO_ASCENDENTE;

    private MenuItem menuItemOrdenacao;

    private final ActionMode.Callback actionCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.pessoas_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEditar) {
                editarPessoa();
                return true;
            } else if (idMenuItem == R.id.menuItemExcluir) {
                excluirPessoa();
                //remover mode.finish();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null) {
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode = null;
            viewSelecionada = null;
            backgroundDrawable = null;

            recyclerViewPessoas.setEnabled(true);
        }
    };

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

        lerPreferencias();
        popularListaPessoas();

        //ordenarLista();//usa por causa dos dados mocados remover na entrega da atividade
    }

    private void popularListaPessoas() {
        //carregar lista a partir do banco de dados
        PessoasDatabase database = PessoasDatabase.getInstance(this);

        if (ordenacaoAscendente) {
            listaPessoas = database.getPessoaDao().queryAllAscending();
        } else {
            listaPessoas = database.getPessoaDao().queryAllDownward();
        }


        pessoaRecyclerViewAdapter = new PessoaRecyclerViewAdapter(this, listaPessoas);

        pessoaRecyclerViewAdapter.setOnItemClickListener(new PessoaRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                posicaoSelecionada = position;
                editarPessoa();
            }
        });

        pessoaRecyclerViewAdapter.setOnItemLongClickListener(new PessoaRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                if (actionMode != null) {
                    return false;
                }

                posicaoSelecionada = position;

                viewSelecionada = view;
                backgroundDrawable = view.getBackground();

                view.setBackgroundColor(getColor(R.color.corSelecionado));

                recyclerViewPessoas.setEnabled(false);
                actionMode = startSupportActionMode(actionCallback);
                return true;
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

                            long id = bundle.getLong(KEY_ID);

                            PessoasDatabase database = PessoasDatabase.getInstance(PessoasActivity.this);

                            //recupera a pessoa pelo id
                            Pessoa pessoa = database.getPessoaDao().queryForId(id);

                            listaPessoas.add(pessoa);

                            ordenarLista();
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
        } else if (idMenuItem == R.id.menuItemOrdenacao) {
            salvarPreferenciaOrdenacaoAscendente(!ordenacaoAscendente);
            atualizarIconeOrdenacao();
            ordenarLista();
            return true;
        } else if (idMenuItem == R.id.menuItemRestaurar) {
            confirmarRestaurarPadroes();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pessoas_opcoes, menu);
        menuItemOrdenacao = menu.findItem(R.id.menuItemOrdenacao);
        //o retorno true faz com que seja exibido o menu
        return true;
    }

    //utilizado para carregar o ícone de ordenação aplicado por último
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        atualizarIconeOrdenacao();
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.pessoas_item_selecionado, menu);
    }

    private void excluirPessoa() {
        final Pessoa pessoa = listaPessoas.get(posicaoSelecionada);
        String mensagem = getString(R.string.deseja_apagar, pessoa.getNome());

        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PessoasDatabase database = PessoasDatabase.getInstance(PessoasActivity.this);
                int quantidadeAlterada = database.getPessoaDao().delete(pessoa);//remove do banco de dados

                if (quantidadeAlterada != 1) {
                    UtilsAlert.mostrarAviso(PessoasActivity.this, R.string.erro_ao_tentar_excluir);
                    return;
                }

                listaPessoas.remove(posicaoSelecionada);//excluí da memória
                pessoaRecyclerViewAdapter.notifyItemRemoved(posicaoSelecionada); // no caso do ListView use notifyDataSetChanged()
                actionMode.finish();
            }
        };

        UtilsAlert.confirmarAcao(this, mensagem, listenerSim, null);
    }

    private void editarPessoa() {

        Pessoa pessoa = listaPessoas.get(posicaoSelecionada);
        Intent intentAbertura = new Intent(this, PessoaActivity.class);

        intentAbertura.putExtra(PessoaActivity.KEY_MODO, PessoaActivity.MODO_EDITAR);
        intentAbertura.putExtra(KEY_ID, pessoa.getId());
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
                            //dados originais vindos o array list
                            final Pessoa pessoaOriginal = listaPessoas.get(posicaoSelecionada);

                            long id = bundle.getLong(KEY_ID);
                            final PessoasDatabase database = PessoasDatabase.getInstance(PessoasActivity.this);
                            //recupera a pessoa editada
                            final Pessoa pessoaEditada = database.getPessoaDao().queryForId(id);

                            listaPessoas.set(posicaoSelecionada, pessoaEditada);
                            ordenarLista();

                            final ConstraintLayout constraintLayout = findViewById(R.id.main);

                            Snackbar snackBar = Snackbar.make(constraintLayout,
                                    R.string.alteracao_realizada,
                                    Snackbar.LENGTH_LONG);


                            snackBar.setAction(R.string.desfazer, new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    //retorna os dados originais
                                    int quantidadeAlterada = database.getPessoaDao().update(pessoaOriginal);

                                    if (quantidadeAlterada != 1) {
                                        UtilsAlert.mostrarAviso(PessoasActivity.this, R.string.erro_ao_tentar_atualizar);
                                        return;
                                    }

                                    listaPessoas.remove(pessoaEditada);
                                    listaPessoas.add(pessoaOriginal);

                                    ordenarLista();
                                }
                            });

                            snackBar.show();
                        }
                    }
                    posicaoSelecionada = -1;

                    if (actionMode != null) {
                        actionMode.finish();
                    }
                }
            });

    private void ordenarLista() {
        if (ordenacaoAscendente) {
            Collections.sort(listaPessoas, Pessoa.ordenacaoCrescente);
        } else {
            Collections.sort(listaPessoas, Pessoa.ordenacaoDecrescente);
        }
        pessoaRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void lerPreferencias() {
        SharedPreferences shared = getSharedPreferences(PessoasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        ordenacaoAscendente = shared.getBoolean(KEY_ORDENACAO_ASCENDENTE, ordenacaoAscendente);
    }

    private void salvarPreferenciaOrdenacaoAscendente(boolean novoValor) {
        SharedPreferences shared = getSharedPreferences(PessoasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(KEY_ORDENACAO_ASCENDENTE, novoValor);

        editor.commit();

        ordenacaoAscendente = novoValor;
    }

    private void atualizarIconeOrdenacao() {
        if (ordenacaoAscendente) {
            menuItemOrdenacao.setIcon(R.drawable.ic_action_ascending_order);
        } else {
            menuItemOrdenacao.setIcon(R.drawable.ic_action_descending_order);
        }
    }

    private void restaurarPadroes() {
        SharedPreferences shared = getSharedPreferences(PessoasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.clear();//apaga tudo
        editor.commit();
        ordenacaoAscendente = PADRAO_INICIAL_ORDENACAO_ASCENDENTE;
    }

    private void confirmarRestaurarPadroes() {

        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                restaurarPadroes();
                atualizarIconeOrdenacao();
                ordenarLista();

                Toast.makeText(PessoasActivity.this,
                        R.string.as_configuracoes_voltaram_para_o_padrao_de_instalacao,
                        Toast.LENGTH_LONG).show();
            }
        };

        UtilsAlert.confirmarAcao(this, R.string.deseja_voltar_padroes, listenerSim, null);
    }
}