package org.freelasearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.freelasearch.R;
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

    private RecyclerView mRecyclerView;
    private AnuncioAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;

    private List<DtoAnuncio> mList;

    public MeusAnunciosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meus_anuncios, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_meus_anuncios);
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
                    updateMeusAnunciosList(10, mList.size());
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateMeusAnunciosList(10, 0);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onClickListener(): " + position, Toast.LENGTH_SHORT).show();
    }

    public void updateMeusAnunciosList(int qtdRetorno, int qtdExibida) {
        AsyncTaskListaAnuncio mAsyncTaskListaAnuncio = new AsyncTaskListaAnuncio();
        mAsyncTaskListaAnuncio.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
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
                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        Map<String, Integer> filtro = new HashMap<>();
        filtro.put("qtdRetorno", qtdRetorno);
        filtro.put("qtdExibida", qtdExibida);
        filtro.put("tipoBusca", 1);
        mAsyncTaskListaAnuncio.execute(filtro);
    }

}
