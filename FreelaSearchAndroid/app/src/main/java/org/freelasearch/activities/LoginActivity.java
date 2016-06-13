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
import org.freelasearch.tasks.impl.AsyncTaskLoginUsuario;
import org.freelasearch.utils.MD5;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private AsyncTaskLoginUsuario mAsyncTaskLoginUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatButton btnConectar = (AppCompatButton) findViewById(R.id.btn_conectar);
        btnConectar.setOnClickListener(this);
    }

    public void login() {
        if (((EditText) findViewById(R.id.email)).getText().toString().isEmpty()
                || ((EditText) findViewById(R.id.senha)).getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        mAsyncTaskLoginUsuario = new AsyncTaskLoginUsuario();
        mAsyncTaskLoginUsuario.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("id", ((DtoUsuario) obj).getId());
                editor.putString("nome", ((DtoUsuario) obj).getNome());
                editor.putString("email", ((DtoUsuario) obj).getEmail());
                editor.putString("profile_pic", ((DtoUsuario) obj).getUrlFoto());
                editor.commit();

                Intent activity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(activity);
                finish();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        DtoUsuario dto = new DtoUsuario();
        dto.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
        dto.setSenha(MD5.convertTo(((EditText) findViewById(R.id.senha)).getText().toString()));
        mAsyncTaskLoginUsuario.execute(dto);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conectar:
                login();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskLoginUsuario != null) {
            mAsyncTaskLoginUsuario.cancel(true);
        }
    }
}
