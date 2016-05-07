package org.freelasearch.tasks.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.service.UsuarioService;
import org.freelasearch.tasks.TarefaInterface;
import org.freelasearch.utils.ExceptionFreelaSearch;

/**
 * Created by msms on 29/02/2016.
 */
public class TarefaCadastroUsuario extends AsyncTask<DtoUsuario, String, DtoUsuario> {

    private Context context;
    private TarefaInterface ti;
    private ProgressDialog progress;
    private String mensagem = "";

    public TarefaCadastroUsuario(Context context, TarefaInterface ti) {
        this.context = context;
        this.ti = ti;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setMessage("Cadastrando...");
        progress.show();
    }

    @Override
    protected DtoUsuario doInBackground(DtoUsuario... dtoUsuario) {
        UsuarioService service = new UsuarioService();
        DtoUsuario dto = dtoUsuario[0];
        try {
            service.save(dto);
        } catch (final ExceptionFreelaSearch ex) {
            mensagem = ex.getMessage();
            Log.e("ExceptionFreelaSearch", "Exception: " + ex.getMessage());
        } catch (Exception ex) {
            mensagem = ex.getMessage();
            Log.e("Exception", "Exception: " + ex.getMessage());
        }
        return dto;
    }

    @Override
    protected void onProgressUpdate(String... msg) {
    }

    @Override
    protected void onPostExecute(DtoUsuario dto) {
        progress.dismiss();
        if (mensagem != "") {
            ti.retorno(mensagem);
        } else {
            ti.retorno(dto);
        }
    }

}
