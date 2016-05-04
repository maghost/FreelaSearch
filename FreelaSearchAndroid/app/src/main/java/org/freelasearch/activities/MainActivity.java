package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.fragments.MainFragment;
import org.freelasearch.fragments.TabAnunciosFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private NavigationView navigationView = null;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //Verifica se está logado
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean loggedByApplication = !sharedpreferences.getString("email", "").equals("");

        if (!loggedByApplication) {
            logout();
        }

        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbarTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        TextView nhm_username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nhm_username);
        nhm_username.setText(sharedpreferences.getString("nome", ""));

        TextView nhm_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nhm_email);
        nhm_email.setText(sharedpreferences.getString("email", ""));

        if (!sharedpreferences.getString("profile_pic", "").equals("")) {
            ImageView nhmProfileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nhm_profile_image);
            Picasso.with(this).load(sharedpreferences.getString("profile_pic", "")).placeholder(R.drawable.default_profile).error(R.drawable.default_profile).into(nhmProfileImage);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(PerfisActivity.class);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        if (id == R.id.nav_dashboard) {
            setToolbarTitle(R.string.app_name);
            fragment = new MainFragment();
        } else if (id == R.id.nav_escolher_perfil) {
            startActivity(PerfisActivity.class);
            return false;
        } else if (id == R.id.nav_anuncios) {
            setToolbarTitle(R.string.title_tab_fragment);
            fragment = new TabAnunciosFragment();
        } else if (id == R.id.nav_settings) {
            setToolbarTitle(R.string.title_tab_fragment);
            fragment = new TabAnunciosFragment();
        } else if (id == R.id.nav_logout) {
            logout();
            return false;
        } else {
            setToolbarTitle(R.string.title_main_fragment);
            fragment = new MainFragment();
        }

        if (fragment instanceof MainFragment) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();

        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        LoginManager.getInstance().logOut();
    }

    private void startActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void setToolbarTitle(int idString) {
        toolbar.setTitle(this.getString(idString));
    }

    public void buscar(View view) {
        Intent activity = new Intent(this, MainActivity.class);
        startActivity(activity);
    }

    public List<DtoAnuncio> getAnunciosList(int qtd) {

        List<DtoAnuncio> anuncios = new ArrayList<>();

        for (int i = 1; i <= qtd; i++) {
            DtoAnuncio anuncio = new DtoAnuncio();
            anuncio.id = i;
            anuncio.ativo = true;
            anuncio.titulo = "Anúncio nº" + i;
            anuncio.descricao = "Lorem Ipsum Dolor Amet";
//            if (i % 2 == 0) {
//                anuncio.imagem = "https://s-media-cache-ak0.pinimg.com/originals/cb/f8/41/cbf841229a4ae14dbb7629f524627083.jpg";
//            } else {
            anuncio.imagem = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
//            }
            anuncios.add(anuncio);
        }

        return anuncios;
    }

}
