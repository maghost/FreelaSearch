package org.freelasearch.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.service.CategoriaService;
import org.freelasearch.utils.ExceptionFreelaSearch;

import java.io.IOException;

public class CadastroCategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_categoria);
    }

    public void cadastrarCategoria(View view) throws IOException {
        if (((EditText) findViewById(R.id.nome)).getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campo(s) obrigatório(s) não preenchido(s).", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Cadastrando categoria...");
        progress.show();

        new Thread() {
            public void run() {
                DtoCategoria dto = new DtoCategoria();
                dto.nome = ((EditText) findViewById(R.id.nome)).getText().toString();
                dto.descricao = ((EditText) findViewById(R.id.descricao)).getText().toString();

                try {
                    CategoriaService categoriaService = new CategoriaService();
                    categoriaService.save(dto);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EditText txtNome = (EditText) findViewById(R.id.nome);
                            EditText txtDescricao = (EditText) findViewById(R.id.descricao);
                            txtNome.setText("");
                            txtNome.requestFocus();
                            txtDescricao.setText("");

                            progress.dismiss();

                            Toast.makeText(getApplicationContext(), "Categoria cadastrada com sucesso.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final ExceptionFreelaSearch ex) {
                    Log.e("ExceptionFreelaSearch", "Exception: " + ex.getMessage());

                    progress.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception ex) {
                    Log.e("Exception", "Exception: " + ex.getMessage());
                    progress.dismiss();
                }
            }
        }.start();
    }

    public void voltarMainActivity(View view) {
        Intent activity = new Intent(this, MainActivity.class);
        startActivity(activity);
    }
}
