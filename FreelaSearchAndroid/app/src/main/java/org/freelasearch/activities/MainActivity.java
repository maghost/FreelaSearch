package org.freelasearch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.fragments.MainFragment;
import org.freelasearch.fragments.MeusAnunciosFragment;
import org.freelasearch.fragments.TabAnunciosFragment;
import org.freelasearch.tasks.AsyncTaskListener;
import org.freelasearch.tasks.impl.AsyncTaskUsuario;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PREF_NAME = "SignupActivityPreferences";
    private NavigationView navigationView = null;
    private Toolbar toolbar;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //Verifica se está logado
        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean loggedByApplication = !sharedpreferences.getString("email", "").equals("");

        if (!loggedByApplication) {
            logout();
        }

        if (sharedpreferences.getString("perfil", "").equals("")) {
            startActivity(PerfisActivity.class);
            finish();
        }

        if (sharedpreferences.getInt("id", 0) == 0) {
            // Para o caso de estar logado via facebook e não tenha id nas etapas anteriores
            // Futuramente deverá ser alterada a lógica do login pelo facebook para logar apenas depois
            // de cadastrar online
            setUserIdByEmail(sharedpreferences.getString("email", ""));
        }

        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbarTitle(R.string.app_name);
        setSupportActionBar(toolbar);

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
        hideItem();
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
            startActivity(SearchActivity.class);
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
        } else if (id == R.id.nav_meus_anuncios) {
            setToolbarTitle(R.string.meus_anuncios);
            fragment = new MeusAnunciosFragment();
        } else if (id == R.id.nav_settings) {
            startActivity(SettingsActivity.class);
            return false;
        } else if (id == R.id.nav_logout) {
            logout();
            return false;
        } else {
            setToolbarTitle(R.string.title_main_fragment);
            fragment = new MainFragment();
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

        if (sharedpreferences != null) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
        }

        LoginManager.getInstance().logOut();
    }

    private void startActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void setToolbarTitle(int idString) {
        toolbar.setTitle(this.getString(idString));
    }

    public void buscar(View view) {
        startActivity(SearchActivity.class);
    }

    private void hideItem() {
        if (navigationView != null && sharedpreferences != null) {
            Menu navMenu = navigationView.getMenu();
            if (sharedpreferences.getString("perfil", "").equals("anunciante")) {
                navMenu.findItem(R.id.nav_minhas_inscricoes).setVisible(false);
            } else {
                navMenu.findItem(R.id.nav_meus_anuncios).setVisible(false);
            }
        }
    }

    private void setUserIdByEmail(String email) {

        AsyncTaskUsuario mAsyncTaskUsuario = new AsyncTaskUsuario();
        mAsyncTaskUsuario.setAsyncTaskListener(new AsyncTaskListener() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public <T> void onComplete(T obj) {
                if (obj != null) {
                    DtoUsuario dtoUsuario = (DtoUsuario) obj;

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("id", dtoUsuario.getId());
                    editor.putString("nome", dtoUsuario.getNome());
                    editor.putString("email", dtoUsuario.getEmail());
                    editor.putString("profile_pic", dtoUsuario.getUrlFoto());
                    editor.commit();
                } else {
                    Log.e("ERRO", "Retorno nulo.");
                }
            }

            @Override
            public void onError(String errorMsg) {
                Log.e("ERRO", errorMsg);
            }
        });

        Map<String, String> filtro = new HashMap<>();
        filtro.put("email", email);
        mAsyncTaskUsuario.execute(filtro);
    }

}
