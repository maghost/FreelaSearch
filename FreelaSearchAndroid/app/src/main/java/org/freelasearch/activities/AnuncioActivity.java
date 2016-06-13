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
import org.freelasearch.tasks.impl.AsyncTaskListaCategoria;
import org.freelasearch.utils.CategoriaUtils;
import org.freelasearch.utils.EstadoUtils;

import java.util.ArrayList;
import java.util.List;

public class AnuncioActivity extends AppCompatActivity implements LabelledSpinner.OnItemChosenListener, View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";

    private Toolbar mToolbar;

    private ProgressDialog progress;

    private List<EstadoUtils> estadoUtils = new EstadoUtils().preencherEstados();
    private String ufSelecionada;

    private List<DtoCategoria> listDtoCategoria;
    private DtoCategoria categoriaSelecionada;
    private LabelledSpinner categoriaSpinner;
    private AsyncTaskListaCategoria mAsyncTaskListaCategoria;

    private DtoAnuncio dtoAnuncioOriginal;
    private DtoAnuncio dtoAnuncioModificado;
    private AsyncTaskAnuncio mAsyncTaskAnuncio;

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Bundle b = getIntent().getExtras();
        dtoAnuncioOriginal = b != null ? (DtoAnuncio) b.getSerializable("anuncio") : null;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(dtoAnuncioOriginal == null ? "Criar Anúncio" : "Editar Anúncio");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        categoriaSpinner = (LabelledSpinner) findViewById(R.id.categoria_anuncio);
        categoriaSpinner.setOnItemChosenListener(this);
        buscarCategorias();

        LabelledSpinner estadoSpinner = (LabelledSpinner) findViewById(R.id.estado_anuncio);
        estadoSpinner.setItemsArray(estadoUtils);
        estadoSpinner.setOnItemChosenListener(this);

        AppCompatButton btnCadastrarAnuncio = (AppCompatButton) findViewById(R.id.btn_cadastrar_anuncio);
        btnCadastrarAnuncio.setOnClickListener(this);

        AppCompatButton btnEditarAnuncio = (AppCompatButton) findViewById(R.id.btn_editar_anuncio);
        btnEditarAnuncio.setOnClickListener(this);

        if (dtoAnuncioOriginal != null) {
            btnEditarAnuncio.setVisibility(View.VISIBLE);

            // Pegando os campos da tela
            TextInputEditText tituloAnuncio = (TextInputEditText) findViewById(R.id.titulo_anuncio);
            TextInputEditText cidadeAnuncio = (TextInputEditText) findViewById(R.id.cidade_anuncio);
            LabelledSpinner estadoAnuncio = (LabelledSpinner) findViewById(R.id.estado_anuncio);
            TextInputEditText descricaoAnuncio = (TextInputEditText) findViewById(R.id.descricao_anuncio);

            // Preenchendo os dados conforme dto (Edição)
            // Categoria não pode preencher aqui pois pode não ter terminado a thread de buscar a lista

            tituloAnuncio.setText(dtoAnuncioOriginal.getTitulo());

            cidadeAnuncio.setText(dtoAnuncioOriginal.getLocalizacao().getCidade());

            int posicaoEstado = new EstadoUtils().getPositionByUf(dtoAnuncioOriginal.getLocalizacao().getEstado());
            estadoAnuncio.setSelection(posicaoEstado);
            ufSelecionada = estadoUtils.get(posicaoEstado).getUf();

            descricaoAnuncio.setText(dtoAnuncioOriginal.getDescricao());
        } else {
            btnCadastrarAnuncio.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
        switch (labelledSpinner.getId()) {
            case R.id.estado_anuncio:
                ufSelecionada = estadoUtils.get(position).getUf();
                break;
            case R.id.categoria_anuncio:
                categoriaSelecionada = listDtoCategoria.get(position);
                break;
        }

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
            case R.id.btn_editar_anuncio:
                editarAnuncio();
                break;
        }
    }

    public void buscarCategorias() {
        mAsyncTaskListaCategoria = new AsyncTaskListaCategoria();
        mAsyncTaskListaCategoria.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                listDtoCategoria = new ArrayList<>();
                listDtoCategoria.addAll((List<DtoCategoria>) obj);

                if (categoriaSpinner == null) {
                    categoriaSpinner = (LabelledSpinner) AnuncioActivity.this.findViewById(R.id.categoria_anuncio);
                }
                categoriaSpinner.setItemsArray(CategoriaUtils.getNamesByList(listDtoCategoria));

                if (dtoAnuncioOriginal != null && dtoAnuncioOriginal.getCategoria() != null) {
                    categoriaSpinner.setSelection(CategoriaUtils.getPositionById(listDtoCategoria, dtoAnuncioOriginal.getCategoria().getId()));
                    categoriaSelecionada = dtoAnuncioOriginal.getCategoria();
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getApplicationContext(), "Erro ao buscar categorias", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        mAsyncTaskListaCategoria.execute((Void) null);
    }

    private void cadastrarAnuncio() {
        if (!isValid()) {
            Toast.makeText(getApplicationContext(), R.string.required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        preencheDto();

        mAsyncTaskAnuncio = new AsyncTaskAnuncio();
        mAsyncTaskAnuncio.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                progress = new ProgressDialog(AnuncioActivity.this);
                progress.setMessage("Cadastrando anúncio, aguarde...");
                progress.show();
            }

            @Override
            public <T> void onComplete(T obj) {
                abrirMeusAnuncios((DtoAnuncio) obj, "cadastrado");
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(AnuncioActivity.this, "Falha ao cadastrar o anúncio.", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });

        mAsyncTaskAnuncio.execute(dtoAnuncioModificado);
    }

    private void editarAnuncio() {
        if (!isValid()) {
            Toast.makeText(getApplicationContext(), R.string.required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        preencheDto();

        mAsyncTaskAnuncio = new AsyncTaskAnuncio();
        mAsyncTaskAnuncio.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
                progress = new ProgressDialog(AnuncioActivity.this);
                progress.setMessage("Editando anúncio, aguarde...");
                progress.show();
            }

            @Override
            public <T> void onComplete(T obj) {
                abrirMeusAnuncios((DtoAnuncio) obj, "editado");
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(AnuncioActivity.this, "Falha ao editar o anúncio.", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });

        mAsyncTaskAnuncio.execute(dtoAnuncioModificado);
    }

    private boolean isValid() {
        if (((TextInputEditText) findViewById(R.id.titulo_anuncio)).getText().toString().trim().isEmpty()
                || categoriaSelecionada == null
                || ((TextInputEditText) findViewById(R.id.cidade_anuncio)).getText().toString().trim().isEmpty()
                || ufSelecionada == null
                || ((TextInputEditText) findViewById(R.id.descricao_anuncio)).getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private void preencheDto() {
        if (dtoAnuncioOriginal != null) {
            dtoAnuncioModificado = dtoAnuncioOriginal;
        } else {
            dtoAnuncioModificado = new DtoAnuncio();

            // Só precisa setar o usuário quando é uma inclusão, caso contrário já estará preenchido no dtoAnuncioOriginal
            DtoAnunciante dtoAnunciante = new DtoAnunciante();
            dtoAnunciante.setId(sharedpreferences.getInt("anunciante", 0));

            DtoUsuario dtoUsuario = new DtoUsuario();
            dtoUsuario.setId(sharedpreferences.getInt("id", 0));
            dtoAnunciante.setUsuario(dtoUsuario);

            dtoAnuncioModificado.setAnunciante(dtoAnunciante);
        }

        dtoAnuncioModificado.setTitulo(((TextInputEditText) findViewById(R.id.titulo_anuncio)).getText().toString().trim());

        dtoAnuncioModificado.setCategoria(categoriaSelecionada);

        DtoLocalizacao dtoLocalizacao = new DtoLocalizacao();
        dtoLocalizacao.setCidade(((TextInputEditText) findViewById(R.id.cidade_anuncio)).getText().toString().trim());
        dtoLocalizacao.setEstado(ufSelecionada);
        dtoAnuncioModificado.setLocalizacao(dtoLocalizacao);

        dtoAnuncioModificado.setDescricao(((TextInputEditText) findViewById(R.id.descricao_anuncio)).getText().toString().trim());
    }

    private void abrirMeusAnuncios(DtoAnuncio anuncio, String acao) {
        progress.dismiss();
        dtoAnuncioModificado = anuncio;

        Intent intent = new Intent(AnuncioActivity.this, MainActivity.class);
        intent.putExtra("idNavigationItem", R.id.nav_meus_anuncios);
        intent.putExtra("msgExtras", "Anúncio " + acao + " com sucesso.");
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskAnuncio != null) {
            mAsyncTaskAnuncio.cancel(true);
        }
        if (mAsyncTaskListaCategoria != null) {
            mAsyncTaskListaCategoria.cancel(true);
        }
    }

}
