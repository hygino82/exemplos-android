package br.edu.utfpradroaldoferreira;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        setTitle(R.string.sobre);
    }

    public void abrirSiteAutoria(View view) {
        String endereco = "http://lattes.cnpq.br/3956885446716361";
        abrirSite(endereco);
    }

    private void abrirSite(String endereco) {
        Intent intentAbertura = new Intent(Intent.ACTION_VIEW);

        intentAbertura.setData(Uri.parse(endereco));

        if (intentAbertura.resolveActivity(getPackageManager()) != null) {
            startActivity(intentAbertura);
        } else {
            Toast.makeText(this,
                            R.string.nenhum_aplicativo_para_abrir_paginas_web
                            , Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void enviarEmailAutor(View view) {
        enviarEmail(
                new String[]{"hygino@msn.com"},
                "Contato pelo aplicativo");
    }

    public void enviarEmail(String[] enderecos, String assunto) {
        Intent intentAbertura = new Intent(Intent.ACTION_SENDTO);
        intentAbertura.setData(Uri.parse("mailto:"));
        intentAbertura.putExtra(Intent.EXTRA_EMAIL, enderecos);
        intentAbertura.putExtra(Intent.EXTRA_SUBJECT, assunto);

        if (intentAbertura.resolveActivity(getPackageManager()) != null) {
            startActivity(intentAbertura);
        } else {
            Toast.makeText(this,
                            R.string.nenhum_aplicativo_para_enviar_e_mail
                            , Toast.LENGTH_LONG)
                    .show();
        }
    }

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();

        if (idMenuItem == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }*/
}