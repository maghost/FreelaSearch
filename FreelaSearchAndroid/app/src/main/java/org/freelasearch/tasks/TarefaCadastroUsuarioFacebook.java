package org.freelasearch.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.service.UsuarioService;
import org.freelasearch.utils.ExceptionFreelaSearch;

/**
 * Created by msms on 29/02/2016.
 */
public class TarefaCadastroUsuarioFacebook extends AsyncTask<DtoUsuario, String, DtoUsuario> {

    private Context context;
    private TarefaInterface ti;

    public TarefaCadastroUsuarioFacebook(Context context, TarefaInterface ti) {
        this.context = context;
        this.ti = ti;
    }

    @Override
    protected DtoUsuario doInBackground(DtoUsuario... dtoUsuario) {
        UsuarioService service = new UsuarioService();
        DtoUsuario dto = dtoUsuario[0];
        try {
            service.save(dto);
        } catch (Exception ex) {
        }
        return dto;
    }

}
