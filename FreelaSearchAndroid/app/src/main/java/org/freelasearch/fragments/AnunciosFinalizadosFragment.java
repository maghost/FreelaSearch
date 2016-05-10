package org.freelasearch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.activities.AnuncioActivity;
import org.freelasearch.adapters.AnuncioAdapter;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaAnuncio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnunciosFinalizadosFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private AnuncioAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<DtoAnuncio> mList;

    public AnunciosFinalizadosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anuncios, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_anuncios);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAnunciosList(0, mList.size(), mList.get(0).getId());
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_anuncios);
        mRecyclerView.setHasFixedSize(true);
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
                    updateAnunciosList(10, mList.size(), 0);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateAnunciosList(10, 0, 0);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), AnuncioActivity.class);
        intent.putExtra("anuncio", mList.get(position));
        startActivity(intent);
        //Toast.makeText(getActivity(), "onClickListener(): " + position, Toast.LENGTH_SHORT).show();
    }

    public void updateAnunciosList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista) {
        AsyncTaskListaAnuncio mAsyncTaskListaAnuncio = new AsyncTaskListaAnuncio();
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
                        if (idPrimeiroLista != 0) {
                            adapter.addListItem(listAux.get(i), 0);
                            mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
                        } else {
                            adapter.addListItem(listAux.get(i), mList.size());
                        }
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        Map<String, Integer> filtro = new HashMap<>();
        filtro.put("qtdRetorno", qtdRetorno);
        filtro.put("qtdExibida", qtdExibida);
        filtro.put("idPrimeiroLista", idPrimeiroLista);
        filtro.put("tipoBusca", 3);
        mAsyncTaskListaAnuncio.execute(filtro);
    }

}
