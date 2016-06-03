package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaInscricao;
import org.freelasearch.utils.EstadoUtils;

import java.util.Collections;
import java.util.List;

public class InscricaoDetalharActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private SharedPreferences sharedpreferences;

    private DtoInscricao inscricao;
    private Toolbar mToolbar;
    private AsyncTaskListaInscricao mAsyncTaskListaInscricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao_detalhar);

        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getSerializable("inscricao") != null) {
                inscricao = (DtoInscricao) getIntent().getExtras().getSerializable("inscricao");
            } else if (getIntent().getExtras().getInt("id") != 0) {
                buscarAnuncioPorId();
            } else {
                Toast.makeText(this, "Falha ao carregar o anúncio", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Falha ao carregar o anúncio", Toast.LENGTH_SHORT).show();
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

        preencherTela();
    }

    private void buscarAnuncioPorId() {
        mAsyncTaskListaInscricao = new AsyncTaskListaInscricao();
        mAsyncTaskListaInscricao.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                List<DtoInscricao> listAux = (List<DtoInscricao>) obj;
                inscricao = listAux.get(0);
                preencherTela();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getApplicationContext(), "Erro ao buscar a inscrição", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        mAsyncTaskListaInscricao.execute(Collections.singletonMap("id", getIntent().getExtras().getInt("id")));
    }

    private void preencherTela() {
        ImageView nhmAnunciante = (ImageView) findViewById(R.id.nhm_anunciante);
        TextView tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        TextView tvAnunciante = (TextView) findViewById(R.id.tv_anunciante);
        TextView tvLocalizacao = (TextView) findViewById(R.id.tv_localizacao);
        TextView tvDescricao = (TextView) findViewById(R.id.tv_descricao);

        if (inscricao != null) {
            DtoAnuncio anuncio = inscricao.getAnuncio();
            if (anuncio.getAnunciante().getUsuario().getUrlFoto() != null && !anuncio.getAnunciante().getUsuario().getUrlFoto().trim().isEmpty()) {
                Picasso.with(this).load(anuncio.getAnunciante().getUsuario().getUrlFoto())
                        .placeholder(R.drawable.default_profile).error(R.drawable.default_profile).fit().into(nhmAnunciante);
            }

            tvTitulo.setText(anuncio.getTitulo());

            tvAnunciante.setText(anuncio.getAnunciante().getUsuario().getNome());

            tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new EstadoUtils().getDescriptionByUf(anuncio.getLocalizacao().getEstado()));

            tvDescricao.setText(anuncio.getDescricao());

        }
    }

    @Override
    public void onClick(View v) {
    }
}
