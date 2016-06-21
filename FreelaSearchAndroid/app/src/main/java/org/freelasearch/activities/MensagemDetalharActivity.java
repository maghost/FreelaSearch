package org.freelasearch.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.adapters.MensagemChatAdapter;
import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaMensagem;
import org.freelasearch.tasks.impl.AsyncTaskMensagem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MensagemDetalharActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack, View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private SharedPreferences sharedpreferences;

    private DtoMensagem mensagem;
    private DtoUsuario usuarioContraparte;
    private Integer idUsuario;

    private EditText etMensagem;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private MensagemChatAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;

    private AsyncTaskListaMensagem mAsyncTaskListaMensagem;
    private AsyncTaskMensagem mAsyncTaskMensagem;

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
        idUsuario = sharedpreferences.getInt("id", 0);

        if (mensagem.getUsuarioRemetente() != null && mensagem.getUsuarioRemetente().getId() != null) {
            usuarioContraparte = mensagem.getUsuarioRemetente().getId().equals(idUsuario) ? mensagem.getUsuarioDestinatario() : mensagem.getUsuarioRemetente();
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

        etMensagem = (EditText) findViewById(R.id.et_mensagem);
        ImageButton ibEnviarMensagem = (ImageButton) findViewById(R.id.ib_enviar_mensagem);
        ibEnviarMensagem.setOnClickListener(this);

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
                    updateMensagensList(10, mList.size(), 0, false);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateMensagensList(10, 0, 0, false);
    }

    @Override
    public void onClickListener(View view, int position) {
    }

    public void updateMensagensList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista, final boolean adicionaInicio) {
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
                    mAdapter.setIdUsuario(idUsuario);
                    mAdapter.setRecyclerViewOnClickListenerHack(mRecyclerViewOnClickListenerHack);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.scrollToPosition(mList.size() - 1);
                } else {
                    List<DtoMensagem> listAux = (List<DtoMensagem>) obj;
                    MensagemChatAdapter adapter = (MensagemChatAdapter) mRecyclerView.getAdapter();
                    int totalMensagensRetornadas = listAux.size();
                    for (int i = 0; i < totalMensagensRetornadas; i++) {
                        adapter.addListItem(listAux.get(i), mList.size(), adicionaInicio);
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
        filtro.put("idUsuario", String.valueOf(idUsuario));
        if (usuarioContraparte.getId() != null) {
            filtro.put("idContraparte", String.valueOf(usuarioContraparte.getId()));
        } else {
            filtro.put("idContraparte", "0"); // Sistema
        }
        mAsyncTaskListaMensagem.execute(filtro);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_enviar_mensagem:
                enviarMensagem();
                break;
        }
    }

    private void enviarMensagem() {
        if (etMensagem == null || etMensagem.getText().toString().isEmpty()) {
            Toast.makeText(MensagemDetalharActivity.this, "Preencha a mensagem antes de enviá-la", Toast.LENGTH_SHORT).show();
            return;
        }

        mAsyncTaskMensagem = new AsyncTaskMensagem();
        mAsyncTaskMensagem.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public <T> void onComplete(T obj) {
                // Trazer todas as mensagens a partir da última exibida
                updateMensagensList(0, mList.size(), getIdUltimoLista(), true);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(MensagemDetalharActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                Log.e("FreelaSearch", errorMsg);
            }
        });

        DtoMensagem dto = new DtoMensagem();

        DtoUsuario dtoUsuario = new DtoUsuario();
        dtoUsuario.setId(idUsuario);
        dtoUsuario.setNome(sharedpreferences.getString("nome", ""));
        dtoUsuario.setUrlFoto(sharedpreferences.getString("profile_pic", ""));
        dto.setUsuarioRemetente(dtoUsuario);

        dto.setUsuarioDestinatario(usuarioContraparte);

        dto.setDataEnvio(new Date());

        dto.setConteudo(etMensagem.getText().toString());

        etMensagem.setText("");

        mAsyncTaskMensagem.execute(dto);
    }

    private Integer getIdUltimoLista() {
        if (mList != null && mList.get(0) != null) {
            return mList.get(0).getId();
        } else {
            return 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaMensagem != null) {
            mAsyncTaskListaMensagem.cancel(true);
        }
        if (mAsyncTaskMensagem != null) {
            mAsyncTaskMensagem.cancel(true);
        }
    }

}
