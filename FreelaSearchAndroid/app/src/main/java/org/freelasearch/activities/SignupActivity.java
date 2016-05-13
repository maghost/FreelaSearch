package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.tasks.impl.TarefaCadastroUsuario;
import org.freelasearch.tasks.TarefaInterface;

import java.io.IOException;
import java.text.ParseException;

public class SignupActivity extends AppCompatActivity implements TarefaInterface, View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        AppCompatButton btnSignup = (AppCompatButton) findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(this);
    }

    public void signup() {
        if (((EditText) findViewById(R.id.nome)).getText().toString().isEmpty()
                || ((EditText) findViewById(R.id.email)).getText().toString().isEmpty()
                || ((EditText) findViewById(R.id.senha)).getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        DtoUsuario dto = new DtoUsuario();
        dto.setNome(((EditText) findViewById(R.id.nome)).getText().toString());
        dto.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
        dto.setSenha(((EditText) findViewById(R.id.senha)).getText().toString());

        TarefaCadastroUsuario tarefa = new TarefaCadastroUsuario(this, this);
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
            editor.putString("nome", ((DtoUsuario) obj).getNome());
            editor.putString("email", ((DtoUsuario) obj).getEmail());
            editor.commit();

            Intent activity = new Intent(this, PerfisActivity.class);
            startActivity(activity);
            finish();
        }
    }

    public void abrirLoginActivity(View view) {
        Intent activity = new Intent(this, LoginActivity.class);
        startActivity(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                signup();
                break;
        }
    }
}
