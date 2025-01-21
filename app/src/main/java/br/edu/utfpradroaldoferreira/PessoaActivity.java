package br.edu.utfpradroaldoferreira;

import static android.widget.Toast.LENGTH_LONG;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PessoaActivity extends AppCompatActivity {
    private EditText editTextNome, editTextMedia;
    private CheckBox checkBoxBolsista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        editTextNome = findViewById(R.id.editTextNome);
        editTextMedia = findViewById(R.id.editTextMedia);
        checkBoxBolsista = findViewById(R.id.checkBoxBolsista);
    }

    public void limparCampos(View view) {
        editTextNome.setText(null);
        editTextMedia.setText(null);
        checkBoxBolsista.setChecked(false);
        editTextNome.requestFocus();
        Toast.makeText(this, R.string.campos_limpos, LENGTH_LONG).show();
    }

    public void salvarCampos(View view) {
        String nome = editTextNome.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {
            Toast.makeText(this, R.string.faltou_entrar_com_o_nome, LENGTH_LONG).show();

            //retorna o foco
            editTextNome.requestFocus();
            // editTextNome.setSelection(0, editTextNome.getText().toString().length());

            return;
        }

        final String mediaString = editTextMedia.getText().toString();

        if (mediaString == null || mediaString.trim().isEmpty()) {
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

        boolean bolsista = checkBoxBolsista.isChecked();

        Toast.makeText(this,
                getString(R.string.nome_valor) + nome + '\n'
                        + getString(R.string.media_valor) + media + '\n'
                        + (bolsista ? getString(R.string.possui_bolsa) : getString(R.string.nao_possui_bolsa)),
                LENGTH_LONG).show();
    }
}