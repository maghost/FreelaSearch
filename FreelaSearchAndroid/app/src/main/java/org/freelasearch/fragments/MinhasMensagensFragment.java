package org.freelasearch.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.activities.ContratacaoDetalharActivity;
import org.freelasearch.activities.MainActivity;
import org.freelasearch.activities.MensagemDetalharActivity;
import org.freelasearch.adapters.MensagemAdapter;
import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskListaMensagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MinhasMensagensFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private SharedPreferences sharedpreferences;

    private RecyclerView mRecyclerView;
    private MensagemAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack = this;
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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_minhas_mensagens);
        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateMinhasMensagensList(0, 0, 0);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), MensagemDetalharActivity.class);
        intent.putExtra("mensagem", mList.get(position));
        startActivity(intent);
    }

    public void updateMinhasMensagensList(int qtdRetorno, int qtdExibida, final int idPrimeiroLista) {
        mAsyncTaskListaMensagem = new AsyncTaskListaMensagem();
        mAsyncTaskListaMensagem.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                mList = new ArrayList<>();
                mList.addAll(separarMensagens((List<DtoMensagem>) obj));
                mAdapter = new MensagemAdapter(getActivity(), mList);
                mAdapter.setRecyclerViewOnClickListenerHack(mRecyclerViewOnClickListenerHack);
                mRecyclerView.setAdapter(mAdapter);

                if (mList.isEmpty()) {
                    ivSemMensagem.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Nenhum item novo encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    ivSemMensagem.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        sharedpreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Map<String, String> filtro = new HashMap<>();
        filtro.put("qtdRetorno", String.valueOf(qtdRetorno));
        filtro.put("qtdExibida", String.valueOf(qtdExibida));
        filtro.put("idPrimeiroLista", String.valueOf(idPrimeiroLista));
        filtro.put("idUsuario", String.valueOf(sharedpreferences.getInt("id", 0)));
        mAsyncTaskListaMensagem.execute(filtro);
    }

    private List<DtoMensagem> separarMensagens(List<DtoMensagem> dtoMensagemList) {
        List<DtoMensagem> dtoMensagemListSeparada = new ArrayList<>();
        Map<Integer, DtoMensagem> dtoMensagemMap = new HashMap<>();

        Integer idUsuario = sharedpreferences.getInt("id", 0);
        Integer idContraparte = null;
        for (DtoMensagem dtoMensagem : dtoMensagemList) {
            if (dtoMensagem.getUsuarioDestinatario() == null) {
                idContraparte = null;
            } else if (dtoMensagem.getUsuarioDestinatario().getId().equals(idUsuario)) {
                idContraparte = dtoMensagem.getUsuarioRemetente().getId();
            } else if (dtoMensagem.getUsuarioRemetente().getId().equals(idUsuario)) {
                idContraparte = dtoMensagem.getUsuarioDestinatario().getId();
                // Deixa setado o usuário da contraparte
                dtoMensagem.setUsuarioRemetente(dtoMensagem.getUsuarioDestinatario());
            } else {
                continue;
            }

            if (!dtoMensagemMap.containsKey(idContraparte)) {
                dtoMensagemMap.put(idContraparte, dtoMensagem);
            }
        }

        Iterator entries = dtoMensagemMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            //Integer key = (Integer) thisEntry.getKey();
            DtoMensagem value = (DtoMensagem) thisEntry.getValue();

            dtoMensagemListSeparada.add(value);
        }

        // Inverte porque ao passar do map para lista acaba invertendo a ordem das mensagens
        Collections.reverse(dtoMensagemListSeparada);
        return dtoMensagemListSeparada;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaMensagem != null) {
            mAsyncTaskListaMensagem.cancel(true);
        }
    }

}