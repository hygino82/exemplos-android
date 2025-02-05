package br.edu.utfpradroaldoferreira;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PessoaActivity extends AppCompatActivity {
    public static final String KEY_NOME = "KEY_NOME";
    public static final String KEY_MEDIA = "KEY_MEDIA";
    public static final String KEY_BOLSISTA = "KEY_BOLSISTA";
    public static final String KEY_TIPO = "KEY_TIPO";
    public static final String KEY_MAO_USADA = "KEY_MAO_USADA";
    private EditText editTextNome, editTextMedia;
    private CheckBox checkBoxBolsista;
    private RadioGroup radioGroupMaoUsada;
    private Spinner spinnerTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);
        setTitle(getString(R.string.cadastro_de_pessoas));

        editTextNome = findViewById(R.id.editTextNome);
        editTextMedia = findViewById(R.id.editTextMedia);
        checkBoxBolsista = findViewById(R.id.checkBoxBolsista);
        radioGroupMaoUsada = findViewById(R.id.radioGroupMaoUsada);
        spinnerTipo = findViewById(R.id.spinnerTipo);
    }

    public void limparCampos(View view) {
        editTextNome.setText(null);
        editTextMedia.setText(null);
        checkBoxBolsista.setChecked(false);
        editTextNome.requestFocus();
        radioGroupMaoUsada.clearCheck();
        spinnerTipo.setSelection(0);
        Toast.makeText(this, R.string.campos_limpos, LENGTH_LONG).show();
    }

    public void salvarCampos(View view) {
        String nome = editTextNome.getText().toString();

        if (nome.trim().isEmpty()) {
            Toast.makeText(this, R.string.faltou_entrar_com_o_nome, LENGTH_LONG).show();

            //retorna o foco
            editTextNome.requestFocus();
            // editTextNome.setSelection(0, editTextNome.getText().toString().length());

            return;
        }

        final String mediaString = editTextMedia.getText().toString();

        if (mediaString.trim().isEmpty()) {
            Toast.makeText(this, R.string.faltou_entrar_com_a_media, LENGTH_LONG).show();
            editTextMedia.requestFocus();
            return;
        }

        nome = nome.trim();
        int media = 0;
        try {
            media = Integer.parseInt(mediaString);

            if (media < 0 || media > 100) {
                Toast.makeText(this, R.string.a_media_informada_tem_um_valor_invalido, LENGTH_LONG).show();
                editTextMedia.requestFocus();
                //seleciona a parte que está com erro
                editTextMedia.setSelection(0, editTextMedia.getText().toString().length());
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.valor_numerico_invalido, LENGTH_LONG).show();
            editTextMedia.requestFocus();
            editTextMedia.setSelection(0, editTextMedia.getText().toString().length());
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
                    LENGTH_LONG).show();
            return;
        }
        //é retornado om objeto por isso é feito cast para String
        int tipo = (int) spinnerTipo.getSelectedItem();

        if (tipo == AdapterView.INVALID_POSITION) {
            Toast.makeText(this,
                    R.string.o_spinner_tipo_nao_possui_valores,
                    LENGTH_LONG).show();
            return;
        }

        boolean bolsista = checkBoxBolsista.isChecked();

        Intent intentResposta = new Intent();
        intentResposta.putExtra(KEY_NOME, nome);
        intentResposta.putExtra(KEY_MEDIA, media);
        intentResposta.putExtra(KEY_BOLSISTA, bolsista);
        intentResposta.putExtra(KEY_TIPO, tipo);
        intentResposta.putExtra(KEY_MAO_USADA, maoUsada.toString());//enum

        setResult(PessoaActivity.RESULT_OK, intentResposta);

        finish();//força o fechamento da activity
    }
}