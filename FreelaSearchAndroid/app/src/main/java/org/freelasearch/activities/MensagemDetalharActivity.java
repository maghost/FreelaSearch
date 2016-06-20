package org.freelasearch.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.adapters.MensagemChatAdapter;
import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaMensagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MensagemDetalharActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private SharedPreferences sharedpreferences;

    private DtoMensagem mensagem;
    private DtoUsuario usuarioContraparte;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private MensagemChatAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;

    private AsyncTaskListaMensagem mAsyncTaskListaMensagem;

    private List<DtoMensagem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem_detalhar);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getSerializable("mensagem") != null) {
                mensagem = (DtoMensagem) getIntent().getExtras().getSerializable("mensagem");
            } else {
                Toast.makeText(this, "Falha ao carregar a mensagem", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } else {
            Toast.makeText(this, "Falha ao carregar a mensagem", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (mensagem.getUsuarioRemetente() != null && mensagem.getUsuarioRemetente().getId() != null) {
            usuarioContraparte = mensagem.getUsuarioRemetente().getId().equals(sharedpreferences.getInt("id", 0)) ? mensagem.getUsuarioDestinatario() : mensagem.getUsuarioRemetente();
        } else {
            DtoUsuario usuarioSistema = new DtoUsuario();
            usuarioSistema.setNome("SISTEMA");
            usuarioContraparte = usuarioSistema;
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(usuarioContraparte.getNome());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mensagens);
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
                    updateMensagensList(5, mList.size(), 0);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateMensagensList(5, 0, 0);
    }

    @Override
    public void onClickListener(View view, int position) {
    }

    public void updateMensagensList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista) {
        mAsyncTaskListaMensagem = new AsyncTaskListaMensagem();
        mAsyncTaskListaMensagem.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                if (mList == null) {
                    mList = new ArrayList<>();
                    mList.addAll((List<DtoMensagem>) obj);
                    mAdapter = new MensagemChatAdapter(MensagemDetalharActivity.this, mList);
                    mAdapter.setIdUsuario(sharedpreferences.getInt("id", 0));
                    mAdapter.setRecyclerViewOnClickListenerHack(mRecyclerViewOnClickListenerHack);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.scrollToPosition(mList.size() - 1);
                } else {
                    List<DtoMensagem> listAux = (List<DtoMensagem>) obj;
                    MensagemChatAdapter adapter = (MensagemChatAdapter) mRecyclerView.getAdapter();
                    int totalMensagensRetornadas = listAux.size();
                    for (int i = 0; i < totalMensagensRetornadas; i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                        if (idPrimeiroLista != 0) {
                            mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(MensagemDetalharActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        Map<String, String> filtro = new HashMap<>();
        filtro.put("qtdRetorno", String.valueOf(qtdRetorno));
        filtro.put("qtdExibida", String.valueOf(qtdExibida));
        filtro.put("idPrimeiroLista", String.valueOf(idPrimeiroLista));
        filtro.put("idUsuario", String.valueOf(sharedpreferences.getInt("id", 0)));
        if (usuarioContraparte.getId() != null) {
            filtro.put("idContraparte", String.valueOf(usuarioContraparte.getId()));
        } else {
            filtro.put("idContraparte", "0"); // Sistema
        }
        mAsyncTaskListaMensagem.execute(filtro);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaMensagem != null) {
            mAsyncTaskListaMensagem.cancel(true);
        }
    }

}
