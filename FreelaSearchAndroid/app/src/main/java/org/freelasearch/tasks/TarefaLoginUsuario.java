package org.freelasearch.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.service.UsuarioService;
import org.freelasearch.utils.ExceptionFreelaSearch;

/**
 * Created by msms on 20/03/2016.
 */
public class TarefaLoginUsuario extends AsyncTask<DtoUsuario, String, DtoUsuario> {

    private Context context;
    private TarefaInterface ti;
    private ProgressDialog progress;
    private String mensagem;

    public TarefaLoginUsuario(Context context, TarefaInterface ti) {
        this.context = context;
        this.ti = ti;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setMessage("Procurando perfil...");
        progress.show();
    }

    @Override
    protected DtoUsuario doInBackground(DtoUsuario... dtoUsuario) {
        UsuarioService service = new UsuarioService();
        DtoUsuario dto = dtoUsuario[0];
        try {
            dto = service.login(dto);
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
    protected void onPostExecute(DtoUsuario dto) {
        progress.dismiss();
        if (mensagem != null) {
            ti.retorno(mensagem);
        } else {
            ti.retorno(dto);
        }
    }

}
