package br.edu.utfpradroaldoferreira;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PessoaActivity extends AppCompatActivity {

    public static final String KEY_NOME = "KEY_NOME";
    public static final String KEY_MEDIA = "KEY_MEDIA";
    public static final String KEY_BOLSISTA = "KEY_BOLSISTA";
    public static final String KEY_TIPO = "KEY_TIPO";
    public static final String KEY_MAO_USADA = "KEY_MAO_USADA";

    public static final String KEY_MODO = "MODO";
    //define o modo em que a activity de cadastro será aberta

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


        Intent intentAbertura = getIntent();
        Bundle bundle = intentAbertura.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(KEY_MODO);

            if (modo == MODO_NOVO) {
                setTitle(getString(R.string.nova_pessoa));
            } else {
                setTitle(getString(R.string.editar_pessoa));
                //extrai dados vindos do bundle
                String nome = bundle.getString(PessoaActivity.KEY_NOME);
                int media = bundle.getInt(PessoaActivity.KEY_MEDIA);
                boolean bolsista = bundle.getBoolean(PessoaActivity.KEY_BOLSISTA);
                int tipo = bundle.getInt(PessoaActivity.KEY_TIPO);
                String maoUsadaTexto = bundle.getString(PessoaActivity.KEY_MAO_USADA);

                MaoUsada maoUsada = MaoUsada.valueOf(maoUsadaTexto);

                pessoaOriginal = new Pessoa(nome, media, bolsista, tipo, maoUsada);

                editTextNome.setText(nome);
                editTextMedia.setText(String.valueOf(media));
                checkBoxBolsista.setChecked(bolsista);
                spinnerTipo.setSelection(tipo);

                if (maoUsada == MaoUsada.Direita) {
                    radioButtonDireita.setChecked(true);

                } else if (maoUsada == MaoUsada.Esquerda) {
                    radioButtonEsquerda.setChecked(true);
                } else if (maoUsada == MaoUsada.Ambas) {
                    radioButtonAmbas.setChecked(true);
                }
            }
        }
    }

    public void limparCampos() {

        editTextNome.setText(null);
        editTextMedia.setText(null);
        checkBoxBolsista.setChecked(false);
        radioGroupMaoUsada.clearCheck();
        spinnerTipo.setSelection(0);

        editTextNome.requestFocus();

        Toast.makeText(this,
                R.string.campos_limpos,
                Toast.LENGTH_LONG).show();
    }

    public void salvarValores() {

        String nome = editTextNome.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {

            Toast.makeText(this,
                    R.string.faltou_entrar_com_o_nome,
                    Toast.LENGTH_LONG).show();

            editTextNome.requestFocus();
            return;
        }

        nome = nome.trim();

        String mediaString = editTextMedia.getText().toString();

        if (mediaString == null || mediaString.trim().isEmpty()) {

            Toast.makeText(this,
                    R.string.faltou_entrar_com_a_media,
                    Toast.LENGTH_LONG).show();

            editTextMedia.requestFocus();
            return;
        }

        int media = 0;

        try {
            media = Integer.parseInt(mediaString);

        } catch (NumberFormatException e) {

            Toast.makeText(this,
                    R.string.faltou_entrar_com_a_media,
                    Toast.LENGTH_LONG).show();

            editTextMedia.requestFocus();
            editTextMedia.setSelection(0, editTextMedia.getText().toString().length());
            return;
        }

        if (media < 0 || media > 100) {

            Toast.makeText(this,
                    R.string.a_media_informada_tem_um_valor_invalido,
                    Toast.LENGTH_LONG).show();

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
            Toast.makeText(this,
                    R.string.faltou_preencher_a_mao_usada,
                    Toast.LENGTH_LONG).show();
            return;
        }


        int tipo = spinnerTipo.getSelectedItemPosition();

        if (tipo == AdapterView.INVALID_POSITION) {

            Toast.makeText(this,
                    R.string.o_spinner_tipo_nao_possui_valores,
                    Toast.LENGTH_LONG).show();
            return;
        }

        boolean bolsista = checkBoxBolsista.isChecked();

        if (modo == MODO_EDITAR &&
                nome.equalsIgnoreCase(pessoaOriginal.getNome()) &&
                bolsista == pessoaOriginal.isBolsista() &&
                media == pessoaOriginal.getMedia() &&
                maoUsada == pessoaOriginal.getMaoUsada() &&
                tipo == pessoaOriginal.getTipo()) {
                //valores são iguais aos anteriores
            setResult(PessoaActivity.RESULT_CANCELED);
            finish();
            return;
        }


        Intent intentResposta = new Intent();

        intentResposta.putExtra(KEY_NOME, nome);
        intentResposta.putExtra(KEY_MEDIA, media);
        intentResposta.putExtra(KEY_BOLSISTA, bolsista);
        intentResposta.putExtra(KEY_TIPO, tipo);
        intentResposta.putExtra(KEY_MAO_USADA, maoUsada.toString());

        setResult(PessoaActivity.RESULT_OK, intentResposta);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pessoa_opcoes, menu);
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
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}