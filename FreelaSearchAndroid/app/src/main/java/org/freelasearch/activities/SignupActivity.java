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
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskUsuario;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private AsyncTaskUsuario mAsyncTaskUsuario;

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

        mAsyncTaskUsuario = new AsyncTaskUsuario();
        mAsyncTaskUsuario.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                // TODO: Arrumar esse método também, deve conter o id do usuário e sua foto
                SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("nome", ((DtoUsuario) obj).getNome());
                editor.putString("email", ((DtoUsuario) obj).getEmail());
                editor.commit();

                Intent activity = new Intent(SignupActivity.this, PerfisActivity.class);
                startActivity(activity);
                finish();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(SignupActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        DtoUsuario dto = new DtoUsuario();
        dto.setNome(((EditText) findViewById(R.id.nome)).getText().toString());
        dto.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
        dto.setSenha(((EditText) findViewById(R.id.senha)).getText().toString());
        mAsyncTaskUsuario.execute(dto);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskUsuario != null) {
            mAsyncTaskUsuario.cancel(true);
        }
    }
}
