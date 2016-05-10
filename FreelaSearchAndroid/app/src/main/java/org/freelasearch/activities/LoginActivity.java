package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.tasks.TarefaInterface;
import org.freelasearch.tasks.impl.TarefaLoginUsuario;

import java.io.IOException;
import java.text.ParseException;

public class LoginActivity extends AppCompatActivity implements TarefaInterface {

    private static final String PREF_NAME = "SignupActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) throws IOException, ParseException {
        if (((EditText) findViewById(R.id.email)).getText().toString().isEmpty()
                || ((EditText) findViewById(R.id.senha)).getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        DtoUsuario dto = new DtoUsuario();
        dto.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
        dto.setSenha(((EditText) findViewById(R.id.senha)).getText().toString());

        TarefaLoginUsuario tarefa = new TarefaLoginUsuario(this, this);
        tarefa.execute(dto);
    }

    @Override
    public void retorno(Object obj) {
        if (obj instanceof String) {
            Toast.makeText(getApplicationContext(), (String) obj, Toast.LENGTH_LONG).show();
        }

        if (obj instanceof DtoUsuario) {
            SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("id", ((DtoUsuario) obj).getId());
            editor.putString("nome", ((DtoUsuario) obj).getNome());
            editor.putString("email", ((DtoUsuario) obj).getEmail());
            editor.commit();

            Intent activity = new Intent(this, MainActivity.class);
            startActivity(activity);
            finish();
        }
    }

    public void abrirSignupActivity(View view) {
        Intent activity = new Intent(this, SignupActivity.class);
        startActivity(activity);
        finish();
    }

    public void abrirResetPasswordActivity(View view) {
        Intent activity = new Intent(this, ResetPasswordActivity.class);
        startActivity(activity);
        finish();
    }
}
