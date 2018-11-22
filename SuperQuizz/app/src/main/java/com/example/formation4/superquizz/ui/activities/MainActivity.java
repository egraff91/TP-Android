package com.example.formation4.superquizz.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.formation4.superquizz.database.QuestionDatabaseHelper;
import com.example.formation4.superquizz.model.Question;
import com.example.formation4.superquizz.ui.fragments.CreationFragment;
import com.example.formation4.superquizz.ui.fragments.PlayFragment;
import com.example.formation4.superquizz.R;
import com.example.formation4.superquizz.ui.fragments.QuestionListFragment;
import com.example.formation4.superquizz.ui.fragments.ScoreFragment;
import com.example.formation4.superquizz.ui.fragments.SettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PlayFragment.OnFragmentInteractionListener, ScoreFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, QuestionListFragment.OnListFragmentInteractionListener, CreationFragment.OnFragmentInteractionListener, CreationFragment.OnCreationFragmentListener {



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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        QuestionDatabaseHelper helper = QuestionDatabaseHelper.getInstance(this);
        helper.deleteAllQuestions();

        Question question1 = new Question("Quelle est la capitale de la France ?", "Paris");
        question1.addProposition("Madrid");
        question1.addProposition("Versailles");
        question1.addProposition("Paris");
        question1.addProposition("Londres");

        Question question2 = new Question("En quelle année l'Homme a t-il posé pour la première fois le pied sur la Lune ?", "1969");
        question2.addProposition("1968");
        question2.addProposition("1969");
        question2.addProposition("1970");
        question2.addProposition("1971");

        helper.addQuestion(question1);
        helper.addQuestion(question2);

        //questions.add(question1);
        //questions.add(question2);

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
    public void onFragmentInteraction(Uri uri) {

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
