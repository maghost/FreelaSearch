package org.freelasearch.fragments;

import android.content.Context;
import android.content.Intent;
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
import org.freelasearch.activities.InscricaoDetalharActivity;
import org.freelasearch.adapters.InscricaoAdapter;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaInscricao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinhasInscricoesFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private RecyclerView mRecyclerView;
    private InscricaoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView ivSemInscricao;
    private AsyncTaskListaInscricao mAsyncTaskListaInscricao;

    private List<DtoInscricao> mList;

    public MinhasInscricoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_minhas_inscricoes, container, false);

        ivSemInscricao = (ImageView) view.findViewById(R.id.iv_sem_inscricao);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_minhas_inscricoes);
        mSwipeRefreshLayout.setBackgroundResource(android.R.color.transparent);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mList.size() > 0) {
                    updateMinhasInscricoesList(0, mList.size(), mList.get(0).getId());
                } else {
                    updateMinhasInscricoesList(10, 0, 0);
                }
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_minhas_inscricoes);
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
                    updateMinhasInscricoesList(10, mList.size(), 0);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateMinhasInscricoesList(10, 0, 0);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), InscricaoDetalharActivity.class);
        intent.putExtra("inscricao", mList.get(position));
        startActivity(intent);
    }

    public void updateMinhasInscricoesList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista) {
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
                    mAdapter = new InscricaoAdapter(getActivity(), mList);
                    mAdapter.setRecyclerViewOnClickListenerHack(mRecyclerViewOnClickListenerHack);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    List<DtoInscricao> listAux = (List<DtoInscricao>) obj;
                    InscricaoAdapter adapter = (InscricaoAdapter) mRecyclerView.getAdapter();
                    int totalInscricoesRetornadas = listAux.size();
                    for (int i = 0; i < totalInscricoesRetornadas; i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                        if (idPrimeiroLista != 0) {
                            mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
                        }
                    }
                }
                if (mList.isEmpty()) {
                    ivSemInscricao.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Nenhum item novo encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    ivSemInscricao.setVisibility(View.GONE);
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

        Map<String, Integer> filtro = new HashMap<>();
        filtro.put("qtdRetorno", qtdRetorno);
        filtro.put("qtdExibida", qtdExibida);
        filtro.put("idPrimeiroLista", idPrimeiroLista);
        filtro.put("idUsuario", sharedpreferences.getInt("id", 0));
        filtro.put("tipoBusca", 1);
        mAsyncTaskListaInscricao.execute(filtro);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaInscricao != null) {
            mAsyncTaskListaInscricao.cancel(true);
        }
    }

}