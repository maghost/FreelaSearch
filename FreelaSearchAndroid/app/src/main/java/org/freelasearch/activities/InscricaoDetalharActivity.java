package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.utils.EstadoUtils;
import org.freelasearch.utils.RoundedCornersTransformation;

public class InscricaoDetalharActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private DtoAnuncio anuncio;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_detalhar);

        // TODO: ATUALIZAR ESSA CLASSE QUANDO FOR COMEÇAR A MEXER, PEGAR O "ANUNCIO  DETALHAR" COMO EXEMPLO

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getSerializable("anuncio") != null) {
            anuncio = (DtoAnuncio) getIntent().getExtras().getSerializable("anuncio");
        } else {
            Toast.makeText(this, "Falha ao carregar anúncio", Toast.LENGTH_SHORT).show();
            finish();
        }

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView nhmAnunciante = (ImageView) findViewById(R.id.nhm_anunciante);
        Picasso.with(this).load(anuncio.getAnunciante().getUsuario().getUrlFoto()).placeholder(R.drawable.no_image).error(R.drawable.no_image).fit().
                transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_LEFT)).
                transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT)).
                into(nhmAnunciante);

        TextView tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        tvTitulo.setText(anuncio.getTitulo());

        TextView tvAnunciante = (TextView) findViewById(R.id.tv_anunciante);
        tvAnunciante.setText(anuncio.getAnunciante().getUsuario().getNome());

        TextView tvLocalizacao = (TextView) findViewById(R.id.tv_localizacao);
        tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new EstadoUtils().getDescriptionByUf(anuncio.getLocalizacao().getEstado()));

        TextView tvDescricao = (TextView) findViewById(R.id.tv_descricao);
        tvDescricao.setText(anuncio.getDescricao());

        AppCompatButton btnInscrever = (AppCompatButton) findViewById(R.id.btn_inscrever_se);
        btnInscrever.setOnClickListener(this);

        AppCompatButton btnLogarFreelancer = (AppCompatButton) findViewById(R.id.btn_logar_freelancer);
        btnLogarFreelancer.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InscricaoDetalharActivity.this, AnuncioActivity.class);
                intent.putExtra("anuncio", anuncio);
                startActivity(intent);
            }
        });

        // Lógicas para exibir ou ocultar botões/funcionaldades
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.getInt("id", 0) != anuncio.getAnunciante().getUsuario().getId()) {
            if (sharedpreferences.getInt("freelancer", 0) != 0) {
                btnInscrever.setVisibility(View.VISIBLE);
            } else {
                btnLogarFreelancer.setVisibility(View.VISIBLE);
            }
        } else {
            fab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_inscrever_se:
                Toast.makeText(this, "Inscrição pendente", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_logar_freelancer:
                Intent intent = getIntent();
                intent.getExtras().clear();
                finish();
                startActivity(intent);
                break;
        }
    }
}
