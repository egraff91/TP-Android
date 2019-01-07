package com.example.formation4.superquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.formation4.superquizz.R;
import com.example.formation4.superquizz.api.APIClient;
import com.example.formation4.superquizz.database.QuestionDatabaseHelper;
import com.example.formation4.superquizz.model.Question;
import com.example.formation4.superquizz.ui.fragments.CreationFragment;
import com.example.formation4.superquizz.ui.fragments.QuestionListFragment;
import com.example.formation4.superquizz.ui.fragments.ScoreFragment;
import com.example.formation4.superquizz.ui.fragments.SettingsFragment;

import java.io.IOException;
import java.util.List;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.event.BreadcrumbBuilder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestionListFragment.OnListFragmentInteractionListener, CreationFragment.OnFragmentInteractionListener, CreationFragment.OnCreationFragmentListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            playDisplay(fragmentTransaction);
        }

        android.content.Context ctx = this.getApplicationContext();

        String sentryDsn = "https://dc5c805c231a4ebf8c676aaa7d966ee6@sentry.io/1365602";
        Sentry.init(sentryDsn, new AndroidSentryClientFactory(ctx));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final QuestionDatabaseHelper helper = QuestionDatabaseHelper.getInstance(this);

        APIClient apiClient = APIClient.getInstance();
        apiClient.getQuestions(new APIClient.APIResult<List<Question>>() {
            @Override
            public void onFailure(IOException e) {
                Log.e("DEBUG", e.toString());
                FragmentManager fragmentManager= getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                playDisplay(fragmentTransaction);

            }

            @Override
            public void OnSuccess(List<Question> object) throws IOException {
                Log.d("DEBUG", object.toString());


                helper.synchroniseDatabaseQuestions(object);

                FragmentManager fragmentManager= getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                playDisplay(fragmentTransaction);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (id == R.id.item_play) {
            playDisplay(fragmentTransaction);
        } else if (id == R.id.item_score) {
            ScoreFragment scoreFragment = new ScoreFragment();
            fragmentTransaction.replace(R.id.main_container, scoreFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.item_infos) {
            Intent intent = new Intent(this, InfosActivity.class);
            startActivity(intent);

        } else if (id == R.id.item_settings){
            SettingsFragment settingsFragment = new SettingsFragment();
            fragmentTransaction.replace(R.id.main_container, settingsFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.item_creation){
            CreationFragment creationFragment = new CreationFragment();
            creationFragment.listener = this;
            fragmentTransaction.replace(R.id.main_container, creationFragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void playDisplay(FragmentTransaction fragmentTransaction){
        QuestionListFragment questionListFragment = new QuestionListFragment();
        fragmentTransaction.replace(R.id.main_container, questionListFragment);
        fragmentTransaction.commit();
    }



    @Override
    public void onListFragmentInteraction(Question item) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("QUESTION", item);
        startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Question q) {

    }



    @Override
    public void createQuestion(Question q) {
        QuestionDatabaseHelper.getInstance(this).addQuestion(q);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        playDisplay(fragmentTransaction);
    }
}
