package com.joanmanera.gmaildos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.joanmanera.gmaildos.fragments.FragmentMail;
import com.joanmanera.gmaildos.fragments.FragmentRecived;
import com.joanmanera.gmaildos.fragments.FragmentSent;
import com.joanmanera.gmaildos.fragments.FragmentSend;
import com.joanmanera.gmaildos.fragments.FragmentSpam;
import com.joanmanera.gmaildos.fragments.FragmentUnread;
import com.joanmanera.gmaildos.models.Account;
import com.joanmanera.gmaildos.models.Contact;
import com.joanmanera.gmaildos.models.Mail;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMailListener {

    private Account account;
    private ArrayList<Mail> mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GmailParser parser = new GmailParser(this);
        if (parser.parse()) {
            account = parser.getAccount();
            mails = parser.getMails();

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        /**
         * Si el usuario pulsa el botón atrás mientras está mostrándose el menú del NavigationView,
         * hacemos que se cierre dicho menú, ya que el comportamiento por defecto es cerrar la
         * Activity.
         */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menú de la ActionBar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Se ha hecho click en algún item del menú de la ActionBar
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment f;
        // Se ha hecho click en algún item del NavigationView
        int id = item.getItemId();

        if (id == R.id.nav_recived) {
            FragmentRecived f1 = new FragmentRecived();
            f1.setMailsListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f1).commit();
            setTitle("Recived");
        } else if (id == R.id.nav_sent) {
            FragmentSent f2 = new FragmentSent();
            f2.setMailsListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f2).commit();
            setTitle("Sent");
        } else if (id == R.id.nav_unread) {
            FragmentUnread f3 = new FragmentUnread();
            f3.setMailsListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f3).commit();
            setTitle("Presentación");
        } else if (id == R.id.nav_send) {
            f = new FragmentSend();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Herramientas");
        } else if (id == R.id.nav_spam) {
            Bundle b = new Bundle();
            f = new FragmentSpam();
            b.putString("SHARE", "Mi texto");
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).addToBackStack(null).commit();
            setTitle("Compartir");
        } else if (id == R.id.nav_trash) {

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMailSelected(int position) {
        Mail m = mails.get(position);
        Intent i = new Intent(this, DetalleActivity.class);
        i.putExtra(DetalleActivity.EXTRA_TEXTO, m);
        startActivity(i);
    }
}
