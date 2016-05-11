package org.freelasearch.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.freelasearch.R;

public class SearchActivity extends AppCompatActivity {

    private static final String PREF_NAME = "SignupActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(this.getString(R.string.search_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Dependendo do perfil o item exibido é diferente
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.getString("perfil", "").equals("anunciante")) {
            findViewById(R.id.txt_anuncio).setVisibility(View.GONE);
        } else {
            findViewById(R.id.txt_freelancer).setVisibility(View.GONE);
        }

        // Colocando e pintando os icones (drawable) do EditText (por tag não funcionou)
        Drawable drawableAnuncio = getResources().getDrawable(R.drawable.ic_flag_24dp);
        drawableAnuncio = DrawableCompat.wrap(drawableAnuncio);
        DrawableCompat.setTint(drawableAnuncio, getResources().getColor(R.color.accent));
        DrawableCompat.setTintMode(drawableAnuncio, PorterDuff.Mode.SRC_IN);
        ((EditText) findViewById(R.id.txt_anuncio)).setCompoundDrawablesWithIntrinsicBounds(drawableAnuncio, null, null, null);

        Drawable drawableFreelancer = getResources().getDrawable(R.drawable.ic_assignment_ind_24dp);
        drawableFreelancer = DrawableCompat.wrap(drawableFreelancer);
        DrawableCompat.setTint(drawableFreelancer, getResources().getColor(R.color.accent));
        DrawableCompat.setTintMode(drawableFreelancer, PorterDuff.Mode.SRC_IN);
        ((EditText) findViewById(R.id.txt_freelancer)).setCompoundDrawablesWithIntrinsicBounds(drawableFreelancer, null, null, null);

        Drawable drawableLocal = getResources().getDrawable(R.drawable.ic_map_24dp);
        drawableLocal = DrawableCompat.wrap(drawableLocal);
        DrawableCompat.setTint(drawableLocal, getResources().getColor(R.color.accent));
        DrawableCompat.setTintMode(drawableLocal, PorterDuff.Mode.SRC_IN);
        ((EditText) findViewById(R.id.txt_local)).setCompoundDrawablesWithIntrinsicBounds(drawableLocal, null, null, null);


    }

    public void buscar(View view) {
        Toast.makeText(this, "Busca por algo...", Toast.LENGTH_SHORT);
    }

}
