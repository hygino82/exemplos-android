package br.edu.utfpradroaldoferreira;

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
        radioGroupMaoUsada.clearCheck();
        spinnerTipo.setSelection(0);

        editTextNome.requestFocus();

        Toast.makeText(this,
                R.string.campos_limpos,
                Toast.LENGTH_LONG).show();
    }

    public void salvarValores(View view) {

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

        Intent intentResposta = new Intent();

        intentResposta.putExtra(KEY_NOME, nome);
        intentResposta.putExtra(KEY_MEDIA, media);
        intentResposta.putExtra(KEY_BOLSISTA, bolsista);
        intentResposta.putExtra(KEY_TIPO, tipo);
        intentResposta.putExtra(KEY_MAO_USADA, maoUsada.toString());

        setResult(PessoaActivity.RESULT_OK, intentResposta);

        finish();
    }
}