package org.freelasearch.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.adapters.InscricoesAnuncioAdapter;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskContratacao;
import org.freelasearch.tasks.impl.AsyncTaskListaInscricao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscricoesAnuncioActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private DtoAnuncio anuncio;
    private Toolbar mToolbar;
    private ProgressDialog progress;

    private RecyclerView mRecyclerView;
    private InscricoesAnuncioAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AsyncTaskListaInscricao mAsyncTaskListaInscricao;
    private AsyncTaskContratacao mAsyncTaskContratacao;

    private List<DtoInscricao> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricoes_anuncio);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getSerializable("anuncio") != null) {
                anuncio = (DtoAnuncio) getIntent().getExtras().getSerializable("anuncio");
            } else {
                Toast.makeText(this, "Falha ao carregar as inscrições", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Falha ao carregar as inscrições", Toast.LENGTH_SHORT).show();
            finish();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Inscrições do Anúncio");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_inscricoes);
        mSwipeRefreshLayout.setBackgroundResource(android.R.color.transparent);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mList.size() > 0) {
                    updateInscricoesList(0, mList.size(), mList.get(0).getId());
                } else {
                    updateInscricoesList(10, 0, 0);
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_inscricoes);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                    updateInscricoesList(10, mList.size(), 0);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateInscricoesList(10, 0, 0);
    }

    @Override
    public void onClickListener(View view, int position) {
        /* Não faz nada ao clicar no cardview, apenas nos botões (lógica no Adapter) */
    }

    public void updateInscricoesList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista) {
        mAsyncTaskListaInscricao = new AsyncTaskListaInscricao();
        mAsyncTaskListaInscricao.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public <T> void onComplete(T obj) {
                if (mList == null) {
                    mList = new ArrayList<>();
                    mList.addAll((List<DtoInscricao>) obj);
                    mAdapter = new InscricoesAnuncioAdapter(InscricoesAnuncioActivity.this, mList);
                    mAdapter.setRecyclerViewOnClickListenerHack(mRecyclerViewOnClickListenerHack);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    List<DtoInscricao> listAux = (List<DtoInscricao>) obj;
                    InscricoesAnuncioAdapter adapter = (InscricoesAnuncioAdapter) mRecyclerView.getAdapter();
                    int totalInscricoesRetornadas = listAux.size();
                    for (int i = 0; i < totalInscricoesRetornadas; i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                        if (idPrimeiroLista != 0) {
                            mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
                        }
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(InscricoesAnuncioActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        Map<String, Integer> filtro = new HashMap<>();
        filtro.put("qtdRetorno", qtdRetorno);
        filtro.put("qtdExibida", qtdExibida);
        filtro.put("idPrimeiroLista", idPrimeiroLista);
        filtro.put("idAnuncio", anuncio.getId());
        mAsyncTaskListaInscricao.execute(filtro);
    }

    public void contratar(DtoFreelancer dtoFreelancer) {
        mAsyncTaskContratacao = new AsyncTaskContratacao();
        mAsyncTaskContratacao.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                progress = new ProgressDialog(InscricoesAnuncioActivity.this);
                progress.setMessage("Registrando Contratação...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
            }

            @Override
            public <T> void onComplete(T obj) {
                progress.dismiss();
                abrirMinhasContratacoes();
            }

            @Override
            public void onError(String errorMsg) {
                progress.dismiss();
                Log.e("FreelaSearch", errorMsg);
            }
        });

        DtoContratacao dto = new DtoContratacao();

        dto.setAnuncio(anuncio);
        dto.setFreelancer(dtoFreelancer);

        mAsyncTaskContratacao.execute(dto);
    }

    private void abrirMinhasContratacoes() {
        Intent intent = new Intent(InscricoesAnuncioActivity.this, MainActivity.class);
        intent.putExtra("idNavigationItem", R.id.nav_contratacoes);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaInscricao != null) {
            mAsyncTaskListaInscricao.cancel(true);
        }
    }
}
