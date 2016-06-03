package org.freelasearch.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskAnunciante;
import org.freelasearch.tasks.impl.AsyncTaskFreelancer;
import org.freelasearch.tasks.impl.AsyncTaskListaAnunciante;
import org.freelasearch.tasks.impl.AsyncTaskListaFreelancer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PerfisActivity extends AppCompatActivity {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private SharedPreferences sharedpreferences;
    private AsyncTaskListaAnunciante mAsyncTaskListaAnunciante;
    private AsyncTaskListaFreelancer mAsyncTaskListaFreelancer;
    private AsyncTaskAnunciante mAsyncTaskAnunciante;
    private AsyncTaskFreelancer mAsyncTaskFreelancer;

    private ProgressDialog progress;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfis);

        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        email = sharedpreferences.getString("email", null);
        if (email == null) {
            MainActivity.logout(this);
            return;
        }

        ImageView ivAnunciante = (ImageView) findViewById(R.id.iv_perfil_anunciante);
        Picasso.with(this).load(R.drawable.anunciante_perfil).into(ivAnunciante);

        ImageView ivFreelancer = (ImageView) findViewById(R.id.iv_perfil_freelancer);
        Picasso.with(this).load(R.drawable.freelancer_perfil).into(ivFreelancer);
    }

    public void selecionarPerfil(View view) {
        if (view.getId() == R.id.anunciante_perfil) {
            mAsyncTaskListaAnunciante = new AsyncTaskListaAnunciante();
            mAsyncTaskListaAnunciante.setAsyncTaskListener(new AsyncTaskListener() {
                @Override
                public void onPreExecute() {
                    progress = new ProgressDialog(PerfisActivity.this);
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

                            acessarPerfil();
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
            filtro.put("email", email);
            mAsyncTaskListaAnunciante.execute(filtro);
        } else if (view.getId() == R.id.freelancer_perfil) {
            mAsyncTaskListaFreelancer = new AsyncTaskListaFreelancer();
            mAsyncTaskListaFreelancer.setAsyncTaskListener(new AsyncTaskListener() {
                @Override
                public void onPreExecute() {
                    progress = new ProgressDialog(PerfisActivity.this);
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

                            acessarPerfil();
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
            filtro.put("email", email);
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
            //Caso não tenha setado o id ainda, usará o email para identificar o usuário
            dtoUsuario.setEmail(email);
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
            //Caso não tenha setado o id ainda, usará o email para identificar o usuário
            dtoUsuario.setEmail(email);
            dtoFreelancer.setUsuario(dtoUsuario);
            mAsyncTaskFreelancer.execute(dtoFreelancer);
        } else {
            return;
        }
    }

    private void acessarPerfil() {
        Intent intent = new Intent(PerfisActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAsyncTaskListaAnunciante != null) {
            mAsyncTaskListaAnunciante.cancel(true);
        }
        if (mAsyncTaskListaFreelancer != null) {
            mAsyncTaskListaFreelancer.cancel(true);
        }
    }

}