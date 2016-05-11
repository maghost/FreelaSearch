package org.freelasearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;

public class AnuncioDetalharActivity extends AppCompatActivity {

    private DtoAnuncio anuncio;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_detalhar);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getSerializable("anuncio") != null) {
            anuncio = (DtoAnuncio) getIntent().getExtras().getSerializable("anuncio");
        } else {
            Toast.makeText(this, "Falha ao carregar anúncio", Toast.LENGTH_SHORT).show();
            finish();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(anuncio.getTitulo());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
