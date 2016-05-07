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
import org.freelasearch.activities.MainActivity;
import org.freelasearch.adapters.AnuncioAdapter;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;

public class MeusAnunciosFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private AnuncioAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

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
                AnuncioAdapter adapter = (AnuncioAdapter) mRecyclerView.getAdapter();

                if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                    List<DtoAnuncio> listAux = ((MainActivity) getActivity()).getMeusAnunciosList(10, mList.size());

                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mList = ((MainActivity) getActivity()).getMeusAnunciosList(10, mList.size());
        mAdapter = new AnuncioAdapter(getActivity(), mList);
        mAdapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onClickListener(): " + position, Toast.LENGTH_SHORT).show();
    }

}
