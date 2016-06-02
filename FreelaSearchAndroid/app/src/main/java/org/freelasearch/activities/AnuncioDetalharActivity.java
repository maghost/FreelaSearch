package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaAnuncio;
import org.freelasearch.utils.EstadoUtils;

import java.util.Collections;
import java.util.List;

public class AnuncioDetalharActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private DtoAnuncio anuncio;
    private Toolbar mToolbar;
    private Context context = this;
    private AsyncTaskListaAnuncio mAsyncTaskListaAnuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_detalhar);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getSerializable("anuncio") != null) {
                anuncio = (DtoAnuncio) getIntent().getExtras().getSerializable("anuncio");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_inscrever_se:
                Toast.makeText(this, "Inscrever-se", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_logar_freelancer:
                Toast.makeText(this, "Logar como Freelancer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_logar_anunciante:
                Toast.makeText(this, "Logar como Anunciante", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void buscarAnuncioPorId() {
        mAsyncTaskListaAnuncio = new AsyncTaskListaAnuncio();
        mAsyncTaskListaAnuncio.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                List<DtoAnuncio> listAux = (List<DtoAnuncio>) obj;
                anuncio = listAux.get(0);
                preencherTela();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getApplicationContext(), "Erro ao buscar o anúncio", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        mAsyncTaskListaAnuncio.execute(Collections.singletonMap("id", getIntent().getExtras().getInt("id")));
    }

    private void preencherTela() {
        ImageView nhmAnunciante = (ImageView) findViewById(R.id.nhm_anunciante);
        TextView tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        TextView tvAnunciante = (TextView) findViewById(R.id.tv_anunciante);
        TextView tvLocalizacao = (TextView) findViewById(R.id.tv_localizacao);
        TextView tvDescricao = (TextView) findViewById(R.id.tv_descricao);
        AppCompatButton btnInscrever = (AppCompatButton) findViewById(R.id.btn_inscrever_se);
        LinearLayout llLogarFreelancer = (LinearLayout) findViewById(R.id.ll_logar_freelancer);
        AppCompatButton btnLogarFreelancer = (AppCompatButton) findViewById(R.id.btn_logar_freelancer);
        AppCompatButton btnLogarAnunciante = (AppCompatButton) findViewById(R.id.btn_logar_anunciante);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (anuncio != null) {
            if (anuncio.getAnunciante().getUsuario().getUrlFoto() != null && !anuncio.getAnunciante().getUsuario().getUrlFoto().trim().isEmpty()) {
                Picasso.with(this).load(anuncio.getAnunciante().getUsuario().getUrlFoto())
                        .placeholder(R.drawable.default_profile).error(R.drawable.default_profile).fit().into(nhmAnunciante);
            }

            tvTitulo.setText(anuncio.getTitulo());

            tvAnunciante.setText(anuncio.getAnunciante().getUsuario().getNome());

            tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new EstadoUtils().getDescriptionByUf(anuncio.getLocalizacao().getEstado()));

            tvDescricao.setText(anuncio.getDescricao());

            // Se anúncio não for inativo (status != 1) deve mostrar algum botão de ação
            if (anuncio.getStatus() != 1) {
                btnInscrever.setOnClickListener(this);

                btnLogarFreelancer.setOnClickListener(this);

                btnLogarAnunciante.setOnClickListener(this);

                // Lógicas para exibir ou ocultar botões/funcionaldades

                SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                if (sharedpreferences.getInt("id", 0) != anuncio.getAnunciante().getUsuario().getId()) {
                    if (sharedpreferences.getInt("freelancer", 0) != 0) {
                        btnInscrever.setVisibility(View.VISIBLE);
                    } else {
                        llLogarFreelancer.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (sharedpreferences.getInt("anunciante", 0) == 0) {
                        btnLogarAnunciante.setVisibility(View.VISIBLE);

                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Apenas com o perfil de anunciante é possível criar/editar anúncios", Snackbar.LENGTH_LONG).setAction("Ação não permitida", null).show();
                            }
                        });
                    } else {
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, AnuncioActivity.class);
                                intent.putExtra("anuncio", anuncio);
                                startActivity(intent);
                            }
                        });
                    }
                    fab.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getBoolean("backMeusAnuncios")) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("idNavigationItem", R.id.nav_meus_anuncios);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaAnuncio != null) {
            mAsyncTaskListaAnuncio.cancel(true);
        }
    }
}
