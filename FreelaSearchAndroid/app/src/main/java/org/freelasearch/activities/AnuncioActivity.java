package org.freelasearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.satsuware.usefulviews.LabelledSpinner;

import org.freelasearch.R;

public class AnuncioActivity extends AppCompatActivity implements LabelledSpinner.OnItemChosenListener {

    private Toolbar mToolbar;
    private Spinner mSpinner;
    private String[] estados = new String[]{
            "",
            "Acre",
            "Alagoas",
            "Amapá",
            "Amazonas",
            "Bahia",
            "Ceará",
            "Distrito Federal",
            "Espírito Santo",
            "Goiás",
            "Maranhão",
            "Mato Grosso",
            "Mato Grosso do Sul",
            "Minas Gerais",
            "Pará",
            "Paraíba",
            "Paraná",
            "Pernambuco",
            "Piauí",
            "Rio de Janeiro",
            "Rio Grande do Norte",
            "Rio Grande do Sul",
            "Rondônia",
            "Roraima",
            "Santa Catarina",
            "São Paulo",
            "Sergipe",
            "Tocantins"};

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
    }

    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {

    }

    @Override
    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

    }
}
