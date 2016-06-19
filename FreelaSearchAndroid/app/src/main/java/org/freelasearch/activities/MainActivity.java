package org.freelasearch.activities;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.fragments.MainFragment;
import org.freelasearch.fragments.MeusAnunciosFragment;
import org.freelasearch.fragments.MinhasContratacoesFragment;
import org.freelasearch.fragments.MinhasInscricoesFragment;
import org.freelasearch.fragments.MinhasMensagensFragment;
import org.freelasearch.fragments.TabAnunciosFragment;

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
        boolean isLogged = sharedpreferences.getInt("id", 0) != 0;

        if (!isLogged) {
            logout(this);
            return;
        }

        // Verifica se selecionou o Perfil
        if (sharedpreferences.getInt("freelancer", 0) == 0 && sharedpreferences.getInt("anunciante", 0) == 0) {
            startActivity(PerfisActivity.class);
            finish();
            return;
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

        if (!sharedpreferences.getString("profile_pic", "").trim().equals("")) {
            ImageView nhmProfileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nhm_profile_image);
            Picasso.with(this).load(sharedpreferences.getString("profile_pic", "")).placeholder(R.drawable.default_profile).error(R.drawable.default_profile).into(nhmProfileImage);
        }

        TextView tvPerfilLogado = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_perfil_logado);
        String perfil = sharedpreferences.getInt("freelancer", 0) == 0 ? "Anunciante" : "Freelancer";
        tvPerfilLogado.setText("Você está logado com o perfil de " + perfil);

        navigationView.setNavigationItemSelectedListener(this);
        hideItem();
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView tvPerfilLogado = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_perfil_logado);
        String perfil = sharedpreferences.getInt("freelancer", 0) == 0 ? "Anunciante" : "Freelancer";
        tvPerfilLogado.setText("Você está logado com o perfil de " + perfil);

        hideItem();

        // Algumas Activities podem enviar dados para a MainActivity como mensagem e aviso para redirecionar,
        // precisa ir até a MainActivity pois é ela quem é responsável pelos Fragments que a utilizam.
        Bundle b = getIntent().getExtras();
        if (b != null) {
            int idNavigationItem = b.getInt("idNavigationItem");
            if (idNavigationItem != 0) {
                getIntent().removeExtra("idNavigationItem");
                navigationView.setCheckedItem(idNavigationItem);
                openFragmentByNavigationItemId(idNavigationItem);
            }
            if (b.getString("msgExtras") != null) {
                getIntent().removeExtra("msgExtras");
                Toast.makeText(this, b.getString("msgExtras"), Toast.LENGTH_LONG).show();
            }
            b.clear();
        }
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
        return openFragmentByNavigationItemId(id);
    }

    private boolean openFragmentByNavigationItemId(int id) {
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
        } else if (id == R.id.nav_minhas_inscricoes) {
            setToolbarTitle(R.string.minhas_inscricoes);
            fragment = new MinhasInscricoesFragment();
        } else if (id == R.id.nav_contratacoes) {
            setToolbarTitle(R.string.contratacoes);
            fragment = new MinhasContratacoesFragment();
        } else if (id == R.id.nav_mensagens) {
            setToolbarTitle(R.string.mensagens);
            fragment = new MinhasMensagensFragment();
        } else if (id == R.id.nav_settings) {
            startActivity(SettingsActivity.class);
            return false;
        } else if (id == R.id.nav_logout) {
            logout(this);
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

    /**
     * Método que apaga os dados de login do usuário no SharedPrefences.
     * Static para ser usado em outras Classes
     *
     * @param activity
     */
    public static void logout(Activity activity) {
        SharedPreferences mSharedpreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (mSharedpreferences != null) {
            // Limpa o SharedPreferences referente a login
            SharedPreferences.Editor editor = mSharedpreferences.edit();
            editor.clear();
            editor.commit();
        }

        // Desloga do Facebook
        LoginManager.getInstance().logOut();

        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void startActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void setToolbarTitle(int idString) {
        toolbar.setTitle(this.getString(idString));
    }

    private void hideItem() {
        if (navigationView != null && sharedpreferences != null) {
            Menu navMenu = navigationView.getMenu();
            if (sharedpreferences.getInt("freelancer", 0) != 0) {
                navMenu.findItem(R.id.nav_minhas_inscricoes).setVisible(true);
                navMenu.findItem(R.id.nav_meus_anuncios).setVisible(false);
            } else if (sharedpreferences.getInt("anunciante", 0) != 0) {
                navMenu.findItem(R.id.nav_meus_anuncios).setVisible(true);
                navMenu.findItem(R.id.nav_minhas_inscricoes).setVisible(false);
            }
        }
    }
}
