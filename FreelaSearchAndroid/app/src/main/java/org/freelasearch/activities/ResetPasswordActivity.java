package org.freelasearch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import org.freelasearch.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        AppCompatButton btnConectar = (AppCompatButton) findViewById(R.id.btn_resetar);
        btnConectar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resetar:
                Toast.makeText(getApplicationContext(), "Sua nova senha foi enviada para o e-mail cadastrado.", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
