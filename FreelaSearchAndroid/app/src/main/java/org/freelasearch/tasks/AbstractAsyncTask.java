package org.freelasearch.tasks;

import android.os.AsyncTask;

import org.freelasearch.R;

import java.io.IOException;

public abstract class AbstractAsyncTask<E, S> extends AsyncTask<E, String, S> {

    private AsyncTaskListener listener;
    private String mensagem;

    public void setAsyncTaskListener(AsyncTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onPreExecute();
    }

    @Override
    protected S doInBackground(E... params) {
        E dto = params[0];
        try {
            return executeService(dto);
        } catch (Exception ex) {
            mensagem = ex.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(S saida) {
        if (mensagem != null) {
            listener.onError(mensagem);
        } else {
            listener.onComplete(saida);
        }
    }

    protected abstract S executeService(E entrada) throws IOException;

}
