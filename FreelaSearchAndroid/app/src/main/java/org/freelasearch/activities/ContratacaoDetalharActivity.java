package org.freelasearch.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaContratacao;
import org.freelasearch.utils.EstadoUtils;

import java.util.Collections;
import java.util.List;

public class ContratacaoDetalharActivity extends AppCompatActivity implements View.OnClickListener {

    private DtoContratacao contratacao;
    private Toolbar mToolbar;
    private AsyncTaskListaContratacao mAsyncTaskListaContratacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratacao_detalhar);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getSerializable("contratacao") != null) {
                contratacao = (DtoContratacao) getIntent().getExtras().getSerializable("contratacao");
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
        mAsyncTaskListaContratacao = new AsyncTaskListaContratacao();
        mAsyncTaskListaContratacao.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                List<DtoContratacao> listAux = (List<DtoContratacao>) obj;
                contratacao = listAux.get(0);
                preencherTela();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getApplicationContext(), "Erro ao buscar a contratação", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        mAsyncTaskListaContratacao.execute(Collections.singletonMap("id", getIntent().getExtras().getInt("id")));
    }

    private void preencherTela() {
        ImageView nhmAnunciante = (ImageView) findViewById(R.id.nhm_anunciante);
        ImageView nhmFreelancer = (ImageView) findViewById(R.id.nhm_freelancer);
        TextView tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        TextView tvAnunciante = (TextView) findViewById(R.id.tv_anunciante);
        TextView tvFreelancer = (TextView) findViewById(R.id.tv_freelancer);
        TextView tvLocalizacao = (TextView) findViewById(R.id.tv_localizacao);
        TextView tvDescricao = (TextView) findViewById(R.id.tv_descricao);

        if (contratacao != null) {
            DtoAnuncio anuncio = contratacao.getAnuncio();

            tvTitulo.setText(anuncio.getTitulo());

            tvAnunciante.setText("Anunciante: " + anuncio.getAnunciante().getUsuario().getNome());
            if (anuncio.getAnunciante().getUsuario().getUrlFoto() != null && !anuncio.getAnunciante().getUsuario().getUrlFoto().trim().isEmpty()) {
                Picasso.with(this).load(anuncio.getAnunciante().getUsuario().getUrlFoto())
                        .placeholder(R.drawable.default_profile).error(R.drawable.default_profile).fit().into(nhmAnunciante);
            }

            tvFreelancer.setText("Freelancer: " + contratacao.getFreelancer().getUsuario().getNome());
            if (contratacao.getFreelancer().getUsuario().getUrlFoto() != null && !contratacao.getFreelancer().getUsuario().getUrlFoto().trim().isEmpty()) {
                Picasso.with(this).load(contratacao.getFreelancer().getUsuario().getUrlFoto())
                        .placeholder(R.drawable.default_profile).error(R.drawable.default_profile).fit().into(nhmFreelancer);
            }

            tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new EstadoUtils().getDescriptionByUf(anuncio.getLocalizacao().getEstado()));

            tvDescricao.setText(anuncio.getDescricao());

        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contratacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_avaliar:
                showDialog();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // TODO: Arrumar a aparencia e lógica da avaliação
    public void showDialog() {
        AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        RatingBar rating = new RatingBar(this);
        rating.setMax(5);
        rating.setNumStars(5);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Vote!! ");
        popDialog.setView(rating);

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        popDialog.create();
        popDialog.show();
    }
}
