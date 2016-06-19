package org.freelasearch.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.adapters.MensagemAdapter;
import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaMensagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinhasMensagensFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private RecyclerView mRecyclerView;
    private MensagemAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView ivSemMensagem;
    private AsyncTaskListaMensagem mAsyncTaskListaMensagem;

    private List<DtoMensagem> mList;

    public MinhasMensagensFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_minhas_mensagens, container, false);

        ivSemMensagem = (ImageView) view.findViewById(R.id.iv_sem_mensagem);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_minhas_mensagens);
        mSwipeRefreshLayout.setBackgroundResource(android.R.color.transparent);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mList.size() > 0) {
                    updateMinhasMensagensList(0, mList.size(), mList.get(0).getId());
                } else {
                    updateMinhasMensagensList(10, 0, 0);
                }
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_minhas_mensagens);
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
                    updateMinhasMensagensList(10, mList.size(), 0);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateMinhasMensagensList(10, 0, 0);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        /*Intent intent = new Intent(getActivity(), MensagemDetalharActivity.class);
        intent.putExtra("mensagem", mList.get(position));
        startActivity(intent);*/
    }

    public void updateMinhasMensagensList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista) {
        mAsyncTaskListaMensagem = new AsyncTaskListaMensagem();
        mAsyncTaskListaMensagem.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public <T> void onComplete(T obj) {
                if (mList == null) {
                    mList = new ArrayList<>();
                    mList.addAll((List<DtoMensagem>) obj);
                    mAdapter = new MensagemAdapter(getActivity(), mList);
                    mAdapter.setRecyclerViewOnClickListenerHack(mRecyclerViewOnClickListenerHack);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    List<DtoMensagem> listAux = (List<DtoMensagem>) obj;
                    MensagemAdapter adapter = (MensagemAdapter) mRecyclerView.getAdapter();
                    int totalMensagensRetornadas = listAux.size();
                    for (int i = 0; i < totalMensagensRetornadas; i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                        if (idPrimeiroLista != 0) {
                            mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
                        }
                    }
                }
                if (mList.isEmpty()) {
                    ivSemMensagem.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Nenhuma mensagem nova encontrada", Toast.LENGTH_SHORT).show();
                } else {
                    ivSemMensagem.setVisibility(View.GONE);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Map<String, String> filtro = new HashMap<>();
        filtro.put("qtdRetorno", String.valueOf(qtdRetorno));
        filtro.put("qtdExibida", String.valueOf(qtdExibida));
        filtro.put("idPrimeiroLista", String.valueOf(idPrimeiroLista));
        if (sharedpreferences.getInt("anunciante", 0) != 0) {
            filtro.put("idAnunciante", String.valueOf(sharedpreferences.getInt("anunciante", 0)));
        } else if (sharedpreferences.getInt("freelancer", 0) != 0) {
            filtro.put("idFreelancer", String.valueOf(sharedpreferences.getInt("freelancer", 0)));
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