package br.edu.utfpradroaldoferreira.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import br.edu.utfpradroaldoferreira.R;

public final class UtilsAlert {

    private UtilsAlert() {
        // Para evitar que esta classe seja instanciada
    }

    public static void mostrarAviso(Context context, int idMensagem) {
        mostrarAviso(context, context.getString(idMensagem), null);
    }

    public static void mostrarAviso(Context context,
                                    int idMensagem,
                                    DialogInterface.OnClickListener listener) {
        mostrarAviso(context, context.getString(idMensagem), listener);
    }

    public static void mostrarAviso(Context context,
                                    String mensagem,
                                    DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(mensagem);

        /*builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ação a ser realizada quando clicar no botão
            }
        });   */

        builder.setNeutralButton(R.string.ok, listener);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void confirmarAcao(Context context, int idMensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao) {

        confirmarAcao(context, context.getString(idMensagem), listenerSim, listenerNao);
    }

    public static void confirmarAcao(Context context, String mensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmacao);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setPositiveButton(R.string.sim, listenerSim);
        builder.setNegativeButton(R.string.nao, listenerNao);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
