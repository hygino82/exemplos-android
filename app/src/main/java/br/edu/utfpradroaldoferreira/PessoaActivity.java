package br.edu.utfpradroaldoferreira;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import br.edu.utfpradroaldoferreira.modelo.MaoUsada;
import br.edu.utfpradroaldoferreira.modelo.Pessoa;
import br.edu.utfpradroaldoferreira.persistencia.PessoasDatabase;
import br.edu.utfpradroaldoferreira.utils.UtilsAlert;

public class PessoaActivity extends AppCompatActivity {
    public static final String KEY_ID = "ID";
    public static final String KEY_MODO = "MODO";
    //define o modo em que a activity de cadastro será aberta

    public static final String KEY_SUGERIR_TIPO = "SUGERIR_TIPO";
    public static final String KEY_ULTIMO_TIPO = "ULTIMO_TIPO";

    public static final int MODO_NOVO = 0;
    public static final int MODO_EDITAR = 1;

    private EditText editTextNome, editTextMedia;
    private CheckBox checkBoxBolsista;
    private RadioGroup radioGroupMaoUsada;
    //precisa mapear os radio buttons
    private RadioButton radioButtonDireita, radioButtonEsquerda, radioButtonAmbas;
    private Spinner spinnerTipo;

    //usado para pegar o modo que o cadastro será aberto
    private int modo;
    private Pessoa pessoaOriginal;

    //valores iniciais
    private boolean sugerirTipo = false;
    private int ultimoTipo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        //setTitle(getString(R.string.cadastro_de_pessoas));

        editTextNome = findViewById(R.id.editTextNome);
        editTextMedia = findViewById(R.id.editTextMedia);
        checkBoxBolsista = findViewById(R.id.checkBoxBolsista);
        radioGroupMaoUsada = findViewById(R.id.radioGroupMaoUsada);
        spinnerTipo = findViewById(R.id.spinnerTipo);

        radioButtonDireita = findViewById(R.id.radioButtonDireita);
        radioButtonAmbas = findViewById(R.id.radioButtonAmbas);
        radioButtonEsquerda = findViewById(R.id.radioButtonEsquerda);

        //inicializa ao abrir a activity
        lerPreferencias();

        Intent intentAbertura = getIntent();
        Bundle bundle = intentAbertura.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(KEY_MODO);

            if (modo == MODO_NOVO) {
                setTitle(getString(R.string.nova_pessoa));

                if (sugerirTipo) {
                    spinnerTipo.setSelection(ultimoTipo);
                }

            } else {
                setTitle(getString(R.string.editar_pessoa));

                long id = bundle.getLong(KEY_ID);

                PessoasDatabase database = PessoasDatabase.getInstance(this);

                pessoaOriginal = database.getPessoaDao().queryForId(id);

                editTextNome.setText(pessoaOriginal.getNome());
                editTextMedia.setText(String.valueOf(pessoaOriginal.getMedia()));
                checkBoxBolsista.setChecked(pessoaOriginal.isBolsista());
                spinnerTipo.setSelection(pessoaOriginal.getTipo());

                MaoUsada maoUsada = pessoaOriginal.getMaoUsada();

                if (maoUsada == MaoUsada.Direita) {
                    radioButtonDireita.setChecked(true);

                } else if (maoUsada == MaoUsada.Esquerda) {
                    radioButtonEsquerda.setChecked(true);
                } else if (maoUsada == MaoUsada.Ambas) {
                    radioButtonAmbas.setChecked(true);
                }

                editTextNome.requestFocus();
                editTextNome.setSelection(editTextNome.getText().length());
            }
        }
    }

    public void limparCampos() {

        final String nome = editTextNome.getText().toString();
        final String media = editTextMedia.getText().toString();
        final boolean bolsista = checkBoxBolsista.isChecked();
        final int radioButtonId = radioGroupMaoUsada.getCheckedRadioButtonId();
        final int tipo = spinnerTipo.getSelectedItemPosition();

        final ScrollView scrollView = findViewById(R.id.main);
        final View viewComFoco = scrollView.findFocus();

        editTextNome.setText(null);
        editTextMedia.setText(null);
        checkBoxBolsista.setChecked(false);
        radioGroupMaoUsada.clearCheck();
        spinnerTipo.setSelection(0);

        editTextNome.requestFocus();

        Snackbar snackbar = Snackbar.make(scrollView,
                R.string.as_entradas_foram_apagadas,
                Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.desfazer, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editTextNome.setText(nome);
                editTextMedia.setText(media);
                checkBoxBolsista.setChecked(bolsista);

                if (radioButtonId == R.id.radioButtonDireita) {
                    radioButtonDireita.setChecked(true);
                } else if (radioButtonId == R.id.radioButtonEsquerda) {
                    radioButtonEsquerda.setChecked(true);
                } else if (radioButtonId == R.id.radioButtonAmbas) {
                    radioButtonAmbas.setChecked(true);
                }

                spinnerTipo.setSelection(tipo);

                if (viewComFoco != null) {
                    viewComFoco.requestFocus();
                }
            }
        });

        snackbar.show();
    }

    public void salvarValores() {

        String nome = editTextNome.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {

            UtilsAlert.mostrarAviso(this, R.string.faltou_entrar_com_o_nome);

            editTextNome.requestFocus();
            return;
        }

        nome = nome.trim();

        String mediaString = editTextMedia.getText().toString();

        if (mediaString == null || mediaString.trim().isEmpty()) {

            UtilsAlert.mostrarAviso(this, R.string.faltou_entrar_com_a_media);

            editTextMedia.requestFocus();
            return;
        }

        int media = 0;

        try {
            media = Integer.parseInt(mediaString);

        } catch (NumberFormatException e) {

            UtilsAlert.mostrarAviso(this, R.string.faltou_entrar_com_a_media);

            editTextMedia.requestFocus();
            editTextMedia.setSelection(0, editTextMedia.getText().toString().length());
            return;
        }

        if (media < 0 || media > 100) {
            UtilsAlert.mostrarAviso(this, R.string.a_media_informada_tem_um_valor_invalido);

            editTextMedia.requestFocus();
            editTextMedia.setSelection(0, editTextMedia.getText().toString().length());
            return;
        }

        int radioButtonId = radioGroupMaoUsada.getCheckedRadioButtonId();

        MaoUsada maoUsada;

        if (radioButtonId == R.id.radioButtonDireita) {
            maoUsada = MaoUsada.Direita;
        } else if (radioButtonId == R.id.radioButtonEsquerda) {
            maoUsada = MaoUsada.Esquerda;
        } else if (radioButtonId == R.id.radioButtonAmbas) {
            maoUsada = MaoUsada.Ambas;
        } else {
            UtilsAlert.mostrarAviso(this, R.string.faltou_preencher_a_mao_usada);
            return;
        }


        int tipo = spinnerTipo.getSelectedItemPosition();

        if (tipo == AdapterView.INVALID_POSITION) {
            UtilsAlert.mostrarAviso(this, R.string.o_spinner_tipo_nao_possui_valores);
            return;
        }

        boolean bolsista = checkBoxBolsista.isChecked();

        Pessoa pessoa = new Pessoa(nome, media, bolsista, tipo, maoUsada);

        if (pessoa.equals(pessoaOriginal)) {
            //valores iguais aos anteriores, não precisa salvar nada
            setResult(PessoaActivity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent intentResposta = new Intent();

        PessoasDatabase database = PessoasDatabase.getInstance(this);

        if (modo == MODO_NOVO) {
            long novoId = database.getPessoaDao().insert(pessoa);
            //novoId=-33;//teste
            if (novoId <= 0) {//se houver erro na persistencia nao gera o id
                UtilsAlert.mostrarAviso(this, R.string.erro_ao_tentar_inserir);
                return;
            }

            pessoa.setId(novoId);
        } else {
            pessoa.setId(pessoaOriginal.getId());

            int quantidadeAlterada = database.getPessoaDao().update(pessoa);

            if (quantidadeAlterada != 1) {
                UtilsAlert.mostrarAviso(this, R.string.erro_ao_tentar_atualizar);
            }
        }

        salvarUltimoTipo(tipo);

        intentResposta.putExtra(KEY_ID, pessoa.getId());

        setResult(PessoaActivity.RESULT_OK, intentResposta);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pessoa_opcoes, menu);
        return true;
    }

    @Override//sempre chamado ao exibir o menu
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menuItemSugerirTipo);
        item.setChecked(sugerirTipo);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSalvar) {
            salvarValores();
            return true;
        } else if (idMenuItem == R.id.menuItemLimpar) {
            limparCampos();
            return true;
        } else if (idMenuItem == R.id.menuItemSugerirTipo) {
            boolean valor = !item.isChecked();
            salvarSugerirTipo(valor);
            item.setChecked(valor);

            if (sugerirTipo) {
                spinnerTipo.setSelection(ultimoTipo);
            }

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void lerPreferencias() {
        SharedPreferences shared = getSharedPreferences(PessoasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        sugerirTipo = shared.getBoolean(KEY_SUGERIR_TIPO, sugerirTipo);
        ultimoTipo = shared.getInt(KEY_ULTIMO_TIPO, ultimoTipo);
    }

    private void salvarSugerirTipo(boolean novoValor) {
        SharedPreferences shared = getSharedPreferences(PessoasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(KEY_SUGERIR_TIPO, novoValor);

        editor.commit();

        sugerirTipo = novoValor;
    }

    private void salvarUltimoTipo(int novoValor) {
        SharedPreferences shared = getSharedPreferences(PessoasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(KEY_ULTIMO_TIPO, novoValor);

        editor.commit();

        ultimoTipo = novoValor;
    }
}