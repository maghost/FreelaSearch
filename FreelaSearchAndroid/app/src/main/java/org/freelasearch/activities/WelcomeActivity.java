package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.tasks.impl.TarefaCadastroUsuarioFacebook;
import org.freelasearch.tasks.TarefaInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements TarefaInterface {

    private static final String PREF_NAME = "SignupActivityPreferences";
    CallbackManager callbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_welcome);

        //Verifica se está logado pelo facebook
        callbackManager = CallbackManager.Factory.create();
        boolean loggedByFacebook = AccessToken.getCurrentAccessToken() != null;

        //Verifica se está logado pela aplicação
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean loggedByApplication = !sharedpreferences.getString("email", "").equals("");

        if (loggedByFacebook || loggedByApplication) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //Se não estiver logado, monta a tela de Welcome com o botão do fb para login/cadastro
        LoginButton loginButton = (LoginButton) findViewById(R.id.lbFacebook);
        if (loginButton == null) {
            return;
        }
        List<String> permissionNeeds = Arrays.asList("public_profile", "email");
        loginButton.setReadPermissions(permissionNeeds);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String profile_pic = "https://graph.facebook.com/" + id + "/picture?type=large";

                                    // TODO: Arrumar essa lógica, o usuário está logando mesmo antes de se cadastrar
                                    SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("nome", name);
                                    editor.putString("email", email);
                                    editor.putString("profile_pic", profile_pic);
                                    editor.commit();

                                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);

                                    intent.putExtra("email", email);
                                    intent.putExtra("nome", name);
                                    intent.putExtra("profile_pic", profile_pic);

                                    cadastroUsuarioFacebook(email, name, profile_pic);

                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace(); //something's seriously wrong here
                                }
                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("WelcomeActivity", exception.getCause().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void abrirLoginActivity(View view) {
        Intent activity = new Intent(this, LoginActivity.class);
        startActivity(activity);
    }

    public void abrirSignupActivity(View view) {
        Intent activity = new Intent(this, SignupActivity.class);
        startActivity(activity);
    }

    public void abrirResetPasswordActivity(View view) {
        Intent activity = new Intent(this, ResetPasswordActivity.class);
        startActivity(activity);
    }

    private void cadastroUsuarioFacebook(String email, String nome, String urlFoto) {
        DtoUsuario dto = new DtoUsuario();
        dto.setEmail(email);
        dto.setNome(nome);
        dto.setUrlFoto(urlFoto);

        TarefaCadastroUsuarioFacebook tarefa = new TarefaCadastroUsuarioFacebook(this, this);
        tarefa.execute(dto);
    }

    @Override
    public void retorno(Object obj) {
        if (obj instanceof String) {
            Toast.makeText(getApplicationContext(), (String) obj, Toast.LENGTH_LONG).show();
        }

        if (obj instanceof DtoUsuario) {
            SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("id", (((DtoUsuario) obj).getId()).toString());
            editor.putString("nome", ((DtoUsuario) obj).getNome());
            editor.putString("email", ((DtoUsuario) obj).getEmail());
            editor.commit();

            Intent activity = new Intent(this, MainActivity.class);
            startActivity(activity);
            finish();
        }
    }

}
