package org.freelasearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskAnunciante;
import org.freelasearch.tasks.impl.AsyncTaskFreelancer;
import org.freelasearch.tasks.impl.AsyncTaskListaAnunciante;
import org.freelasearch.tasks.impl.AsyncTaskListaAnuncio;
import org.freelasearch.tasks.impl.AsyncTaskListaFreelancer;
import org.freelasearch.utils.EstadoUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnuncioDetalharActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private SharedPreferences sharedpreferences;

    private DtoAnuncio anuncio;
    private Toolbar mToolbar;
    private Context context = this;
    private AsyncTaskListaAnuncio mAsyncTaskListaAnuncio;

    private AsyncTaskListaAnunciante mAsyncTaskListaAnunciante;
    private AsyncTaskListaFreelancer mAsyncTaskListaFreelancer;
    private AsyncTaskAnunciante mAsyncTaskAnunciante;
    private AsyncTaskFreelancer mAsyncTaskFreelancer;

    private ProgressDialog progress;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_detalhar);

        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getSerializable("anuncio") != null) {
                anuncio = (DtoAnuncio) getIntent().getExtras().getSerializable("anuncio");
            } else if (getIntent().getExtras().getInt("id") != 0) {
                buscarAnuncioPorId();
            } else {
                Toast.makeText(this, "Falha ao carregar o anúncio", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Falha ao carregar o anúncio", Toast.LENGTH_SHORT).show();
            finish();
        }

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        email = sharedpreferences.getString("email", null);

        preencherTela();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_inscrever_se:
                AlertDialog.Builder alertDialogInscricao = new AlertDialog.Builder(this);
                alertDialogInscricao.setTitle("Deseja inscrever-se?");
                alertDialogInscricao.setMessage("Ao inscrever-se para a vaga será enviada uma notificação para o anunciante informando-o do seu interesse pelo anúncio.");
                alertDialogInscricao.setPositiveButton("Ok", null);
                alertDialogInscricao.setNegativeButton("Cancelar", null);
                alertDialogInscricao.show();
                break;
            case R.id.btn_logar_freelancer:
                selecionarPerfil("freelancer");
                break;
            case R.id.btn_logar_anunciante:
                selecionarPerfil("anunciante");
                break;
        }
    }

    public void buscarAnuncioPorId() {
        mAsyncTaskListaAnuncio = new AsyncTaskListaAnuncio();
        mAsyncTaskListaAnuncio.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                List<DtoAnuncio> listAux = (List<DtoAnuncio>) obj;
                anuncio = listAux.get(0);
                preencherTela();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getApplicationContext(), "Erro ao buscar o anúncio", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        mAsyncTaskListaAnuncio.execute(Collections.singletonMap("id", getIntent().getExtras().getInt("id")));
    }

    private void preencherTela() {
        ImageView nhmAnunciante = (ImageView) findViewById(R.id.nhm_anunciante);
        TextView tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        TextView tvAnunciante = (TextView) findViewById(R.id.tv_anunciante);
        TextView tvLocalizacao = (TextView) findViewById(R.id.tv_localizacao);
        TextView tvDescricao = (TextView) findViewById(R.id.tv_descricao);
        AppCompatButton btnInscrever = (AppCompatButton) findViewById(R.id.btn_inscrever_se);
        LinearLayout llLogarFreelancer = (LinearLayout) findViewById(R.id.ll_logar_freelancer);
        AppCompatButton btnLogarFreelancer = (AppCompatButton) findViewById(R.id.btn_logar_freelancer);
        AppCompatButton btnLogarAnunciante = (AppCompatButton) findViewById(R.id.btn_logar_anunciante);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (anuncio != null) {
            if (anuncio.getAnunciante().getUsuario().getUrlFoto() != null && !anuncio.getAnunciante().getUsuario().getUrlFoto().trim().isEmpty()) {
                Picasso.with(this).load(anuncio.getAnunciante().getUsuario().getUrlFoto())
                        .placeholder(R.drawable.default_profile).error(R.drawable.default_profile).fit().into(nhmAnunciante);
            }

            tvTitulo.setText(anuncio.getTitulo());

            tvAnunciante.setText(anuncio.getAnunciante().getUsuario().getNome());

            tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new EstadoUtils().getDescriptionByUf(anuncio.getLocalizacao().getEstado()));

            tvDescricao.setText(anuncio.getDescricao());

            // Se anúncio não for inativo (status != 1) deve mostrar algum botão de ação
            if (anuncio.getStatus() != 1) {
                btnInscrever.setOnClickListener(this);

                btnLogarFreelancer.setOnClickListener(this);

                btnLogarAnunciante.setOnClickListener(this);

                // Lógicas para exibir ou ocultar botões/funcionaldades
                if (sharedpreferences.getInt("id", 0) != anuncio.getAnunciante().getUsuario().getId()) {
                    if (sharedpreferences.getInt("freelancer", 0) != 0) {
                        btnInscrever.setVisibility(View.VISIBLE);
                    } else {
                        llLogarFreelancer.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (sharedpreferences.getInt("anunciante", 0) == 0) {
                        btnLogarAnunciante.setVisibility(View.VISIBLE);

                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Apenas com o perfil de anunciante é possível criar/editar anúncios", Snackbar.LENGTH_LONG).setAction("Ação não permitida", null).show();
                            }
                        });
                    } else {
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, AnuncioActivity.class);
                                intent.putExtra("anuncio", anuncio);
                                startActivity(intent);
                            }
                        });
                    }
                    fab.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //TODO: Tentar arrumar o texto "logado como ..." (NavigationView) quando faz a troca de perfil por essa tela
    private void selecionarPerfil(String perfil) {
        if (perfil.equals("anunciante")) {
            mAsyncTaskListaAnunciante = new AsyncTaskListaAnunciante();
            mAsyncTaskListaAnunciante.setAsyncTaskListener(new AsyncTaskListener() {
                @Override
                public void onPreExecute() {
                    progress = new ProgressDialog(AnuncioDetalharActivity.this);
                    progress.setMessage("Buscando perfil...");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                }

                @Override
                public <T> void onComplete(T obj) {
                    if (obj != null) {
                        List<DtoAnunciante> dtoAnunciante = (List<DtoAnunciante>) obj;

                        if (dtoAnunciante.size() == 1) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("anunciante", dtoAnunciante.get(0).getId());
                            editor.remove("freelancer");
                            editor.commit();

                            AnuncioDetalharActivity.this.recreate();
                            progress.dismiss();
                        } else {
                            progress.setMessage("Você ainda não possui um perfil de Anunciante, criando perfil...");
                            criarPerfil("anunciante");
                        }
                    } else {
                        Log.e("FreelaSearch", "Falha ao selecionar o perfil do Anunciante");
                        return;
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    Log.e("FreelaSearch", errorMsg);
                }
            });

            Map<String, String> filtro = new HashMap<>();
            filtro.put("email", sharedpreferences.getString("email", ""));
            mAsyncTaskListaAnunciante.execute(filtro);
        } else if (perfil.equals("freelancer")) {
            mAsyncTaskListaFreelancer = new AsyncTaskListaFreelancer();
            mAsyncTaskListaFreelancer.setAsyncTaskListener(new AsyncTaskListener() {
                @Override
                public void onPreExecute() {
                    progress = new ProgressDialog(AnuncioDetalharActivity.this);
                    progress.setMessage("Buscando perfil...");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                }

                @Override
                public <T> void onComplete(T obj) {
                    if (obj != null) {
                        List<DtoFreelancer> dtoFreelancer = (List<DtoFreelancer>) obj;

                        if (dtoFreelancer.size() == 1) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("freelancer", dtoFreelancer.get(0).getId());
                            editor.remove("anunciante");
                            editor.commit();

                            AnuncioDetalharActivity.this.recreate();
                            progress.dismiss();
                        } else {
                            progress.setMessage("Você ainda não possui um perfil de Freelancer, criando perfil...");
                            criarPerfil("freelancer");
                        }
                    } else {
                        Log.e("FreelaSearch", "Falha ao selecionar o perfil do Freelancer");
                        return;
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    Log.e("FreelaSearch", errorMsg);
                }
            });

            Map<String, String> filtro = new HashMap<>();
            filtro.put("email", sharedpreferences.getString("email", ""));
            mAsyncTaskListaFreelancer.execute(filtro);
        } else {
            return;
        }
    }

    private void criarPerfil(String perfil) {
        if (perfil.equals("anunciante")) {
            mAsyncTaskAnunciante = new AsyncTaskAnunciante();
            mAsyncTaskAnunciante.setAsyncTaskListener(new AsyncTaskListener() {
                @Override
                public void onPreExecute() {
                }

                @Override
                public <T> void onComplete(T obj) {
                    progress.dismiss();
                }

                @Override
                public void onError(String errorMsg) {
                    Log.e("FreelaSearch", errorMsg);
                }
            });

            DtoAnunciante dtoAnunciante = new DtoAnunciante();
            DtoUsuario dtoUsuario = new DtoUsuario();
            dtoUsuario.setId(sharedpreferences.getInt("id", 0));
            dtoAnunciante.setUsuario(dtoUsuario);
            mAsyncTaskAnunciante.execute(dtoAnunciante);
        } else if (perfil.equals("freelancer")) {
            mAsyncTaskFreelancer = new AsyncTaskFreelancer();
            mAsyncTaskFreelancer.setAsyncTaskListener(new AsyncTaskListener() {
                @Override
                public void onPreExecute() {
                }

                @Override
                public <T> void onComplete(T obj) {
                    progress.dismiss();
                }

                @Override
                public void onError(String errorMsg) {
                    Log.e("FreelaSearch", errorMsg);
                }
            });

            DtoFreelancer dtoFreelancer = new DtoFreelancer();
            DtoUsuario dtoUsuario = new DtoUsuario();
            dtoUsuario.setId(sharedpreferences.getInt("id", 0));
            dtoFreelancer.setUsuario(dtoUsuario);
            mAsyncTaskFreelancer.execute(dtoFreelancer);
        } else {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getBoolean("backMeusAnuncios")) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("idNavigationItem", R.id.nav_meus_anuncios);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaAnuncio != null) {
            mAsyncTaskListaAnuncio.cancel(true);
        }
    }
}
