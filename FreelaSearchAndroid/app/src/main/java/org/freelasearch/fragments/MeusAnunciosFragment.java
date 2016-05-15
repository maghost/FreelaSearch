package org.freelasearch.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import org.freelasearch.activities.AnuncioActivity;
import org.freelasearch.activities.AnuncioDetalharActivity;
import org.freelasearch.adapters.AnuncioAdapter;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaAnuncio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeusAnunciosFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private RecyclerView mRecyclerView;
    private AnuncioAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton fab;
    private ImageView ivSemAnuncio;
    private AsyncTaskListaAnuncio mAsyncTaskListaAnuncio;

    private List<DtoAnuncio> mList;

    public MeusAnunciosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meus_anuncios, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnuncioActivity.class);
                startActivity(intent);
            }
        });

        ivSemAnuncio = (ImageView) view.findViewById(R.id.iv_sem_anuncio);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_meus_anuncios);
        mSwipeRefreshLayout.setBackgroundResource(android.R.color.transparent);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mList.size() > 0) {
                    updateMeusAnunciosList(0, mList.size(), mList.get(0).getId());
                } else {
                    updateMeusAnunciosList(10, 0, 0);
                }
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_meus_anuncios);
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
                    updateMeusAnunciosList(10, mList.size(), 0);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateMeusAnunciosList(10, 0, 0);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), AnuncioDetalharActivity.class);
        intent.putExtra("anuncio", mList.get(position));
        startActivity(intent);
    }

    public void updateMeusAnunciosList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista) {
        mAsyncTaskListaAnuncio = new AsyncTaskListaAnuncio();
        mAsyncTaskListaAnuncio.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public <T> void onComplete(T obj) {
                if (mList == null) {
                    mList = new ArrayList<>();
                    mList.addAll((List<DtoAnuncio>) obj);
                    mAdapter = new AnuncioAdapter(getActivity(), mList);
                    mAdapter.setRecyclerViewOnClickListenerHack(mRecyclerViewOnClickListenerHack);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    List<DtoAnuncio> listAux = (List<DtoAnuncio>) obj;
                    AnuncioAdapter adapter = (AnuncioAdapter) mRecyclerView.getAdapter();
                    int totalAnunciosRetornados = listAux.size();
                    for (int i = 0; i < totalAnunciosRetornados; i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                        if (idPrimeiroLista != 0) {
                            mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
                        }
                    }
                }
                if (mList.isEmpty()) {
                    ivSemAnuncio.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Nenhum item novo encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    ivSemAnuncio.setVisibility(View.GONE);
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
        mAsyncTaskListaAnuncio.execute(filtro);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaAnuncio != null) {
            mAsyncTaskListaAnuncio.cancel(true);
        }
    }

}
