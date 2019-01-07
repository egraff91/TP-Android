package com.example.formation4.superquizz.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.formation4.superquizz.R;

import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;

public class InfosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder().setMessage("User start Infos activity").build()
        );

        Sentry.getContext().setUser(
                new UserBuilder().setEmail("lemaildelutilisateur@gmail.com").build()
        );

        Sentry.capture("This is a test");

    }

}
