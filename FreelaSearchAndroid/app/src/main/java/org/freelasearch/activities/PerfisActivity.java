package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.freelasearch.R;


public class PerfisActivity extends AppCompatActivity {

    private static final String PREF_NAME = "SignupActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfis);
    }

    public void selecionarPerfil(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        Class classe = null;
        if (view.getId() == R.id.anunciantePerfil) {
            editor.putString("perfil", "anunciante");
            classe = MainActivity.class;
        } else if (view.getId() == R.id.freelancerPerfil) {
            editor.putString("perfil", "freelancer");
            classe = ListaCategoriaActivity.class;
        }

        if (classe != null) {
            editor.commit();
            Intent activity = new Intent(this, classe);
            startActivity(activity);
            this.finish();
        }
    }

}