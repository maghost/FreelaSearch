package org.freelasearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.satsuware.usefulviews.LabelledSpinner;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.dtos.DtoLocalizacao;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskAnuncio;
import org.freelasearch.utils.Estado;

import java.util.List;

public class AnuncioActivity extends AppCompatActivity implements LabelledSpinner.OnItemChosenListener, View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private Toolbar mToolbar;
    private List<Estado> estados = new Estado().preencherEstados();
    private String ufSelecionada = null;

    private AsyncTaskAnuncio mAsyncTaskAnuncio;
    private AnuncioActivity activity = this;
    private ProgressDialog progress;
    private DtoAnuncio dtoAnuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Criar Anúncio");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LabelledSpinner estadoSpinner = (LabelledSpinner) findViewById(R.id.estado_anuncio);
        estadoSpinner.setItemsArray(estados);
        estadoSpinner.setOnItemChosenListener(this);

        AppCompatButton btnCadastrarAnuncio = (AppCompatButton) findViewById(R.id.btn_cadastrar_anuncio);
        btnCadastrarAnuncio.setOnClickListener(this);
    }

    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
        ufSelecionada = estados.get(position).getUf();
    }

    @Override
    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cadastrar_anuncio:
                cadastrarAnuncio();
                break;
        }
    }

    private void cadastrarAnuncio() {
        dtoAnuncio = preencheValidaDto();
        if (dtoAnuncio == null) {
            Toast.makeText(getApplicationContext(), R.string.required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        mAsyncTaskAnuncio = new AsyncTaskAnuncio();
        mAsyncTaskAnuncio.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                progress = new ProgressDialog(activity);
                progress.setMessage("Cadastrando anúncio, aguarde...");
                progress.show();
            }

            @Override
            public <T> void onComplete(T obj) {
                progress.dismiss();

                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("idNavigationItem", R.id.nav_meus_anuncios);
                intent.putExtra("msgExtras", "Anúncio cadastrado com sucesso.");
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(activity, "Falha ao cadastrar anúncio.", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });

        mAsyncTaskAnuncio.execute(dtoAnuncio);
    }

    private DtoAnuncio preencheValidaDto() {
        if (((TextInputEditText) findViewById(R.id.titulo_anuncio)).getText().toString().isEmpty()
                || ((TextInputEditText) findViewById(R.id.descricao_anuncio)).getText().toString().isEmpty()
                || ((TextInputEditText) findViewById(R.id.cidade_anuncio)).getText().toString().isEmpty()
                || ((TextInputEditText) findViewById(R.id.categoria_anuncio)).getText().toString().isEmpty()
                || ufSelecionada.isEmpty()) {
            return null;
        }

        // Preenchendo o DTO do Anúncio para cadastrá-lo ou atualizá-lo
        DtoAnuncio dto = new DtoAnuncio();

        dto.setAtivo(true);

        dto.setTitulo(((TextInputEditText) findViewById(R.id.titulo_anuncio)).getText().toString());

        dto.setDescricao(((TextInputEditText) findViewById(R.id.descricao_anuncio)).getText().toString());

        // TODO: Falta criar uma lista de categoria e passar o id do item selecionado
        DtoCategoria dtoCategoria = new DtoCategoria();
        dtoCategoria.setId(2);
        dto.setCategoria(dtoCategoria);

        DtoLocalizacao dtoLocalizacao = new DtoLocalizacao();
        dtoLocalizacao.setCidade(((TextInputEditText) findViewById(R.id.cidade_anuncio)).getText().toString());
        dtoLocalizacao.setEstado(ufSelecionada);
        dto.setLocalizacao(dtoLocalizacao);

        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        DtoAnunciante dtoAnunciante = new DtoAnunciante();
        dtoAnunciante.setId(1); // TODO: sharedpreferences.getInt("id", 0) = Aqui está pegando o id do Usuário, deve ser do Anunciante
        DtoUsuario dtoUsuario = new DtoUsuario();
        dtoUsuario.setId(1);
        dtoAnunciante.setUsuario(dtoUsuario);
        dto.setAnunciante(dtoAnunciante);

        return dto;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskAnuncio != null) {
            mAsyncTaskAnuncio.cancel(true);
        }
    }

}
