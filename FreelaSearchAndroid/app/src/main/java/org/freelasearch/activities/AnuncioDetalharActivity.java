package org.freelasearch.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.utils.Estado;

public class AnuncioDetalharActivity extends AppCompatActivity implements View.OnClickListener {

    private DtoAnuncio anuncio;
    private Toolbar mToolbar;
    private static final String PREF_NAME = "SignupActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_detalhar);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getSerializable("anuncio") != null) {
            anuncio = (DtoAnuncio) getIntent().getExtras().getSerializable("anuncio");
        } else {
            Toast.makeText(this, "Falha ao carregar anúncio", Toast.LENGTH_SHORT).show();
            finish();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Detalhes do Anúncio");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        tvTitulo.setText(anuncio.getDescricao());

        TextView tvAnunciante = (TextView) findViewById(R.id.tv_anunciante);
        tvAnunciante.setText(anuncio.getAnunciante().getUsuario().getNome());

        TextView tvLocalizacao = (TextView) findViewById(R.id.tv_localizacao);
        tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new Estado().getByUf(anuncio.getLocalizacao().getEstado()));

        TextView tvDescricao = (TextView) findViewById(R.id.tv_descricao);
        tvDescricao.setText(anuncio.getDescricao());

        AppCompatButton btnInscrever = (AppCompatButton) findViewById(R.id.btn_inscrever_se);
        btnInscrever.setOnClickListener(this);

        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.getString("perfil", "").equals("freelancer")) {
            btnInscrever.setVisibility(View.VISIBLE);
        } else {
            btnInscrever.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_inscrever_se:
                Toast.makeText(this, "Inscrição pendente.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
