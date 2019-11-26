package com.joanmanera.gmaildos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.joanmanera.gmaildos.fragments.FragmentListMails;
import com.joanmanera.gmaildos.models.Account;
import com.joanmanera.gmaildos.models.Mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMailListener {

    private Account account;
    private ArrayList<Mail> mails;
    private ArrayList<Mail> mailsRecived;
    private ArrayList<Mail> mailsSent;
    private ArrayList<Mail> mailsSpam;
    private ArrayList<Mail> mailsTrash;
    private ArrayList<Mail> mailsUnread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GmailParser parser = new GmailParser(this);
        if (parser.parse()) {
            account = parser.getAccount();
            mails = parser.getMails();

        }
        loadMails();

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
        TextView tvNameUser = findViewById(R.id.tvNameUser);
        TextView tvEmailUser = findViewById(R.id.tvEmailUser);
        tvNameUser.setText(account.getName());
        tvEmailUser.setText(account.getEmail());
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
        FragmentListMails f;
        // Se ha hecho click en algún item del NavigationView
        int id = item.getItemId();

        if (id == R.id.nav_recived) {
            f = new FragmentListMails(mailsRecived);
            f.setMailsListener(this);
            mails = mailsRecived;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Recived");
        } else if (id == R.id.nav_sent) {
            f = new FragmentListMails(mailsSent);
            f.setMailsListener(this);
            mails = mailsSent;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Sent");
        } else if (id == R.id.nav_unread) {
            f = new FragmentListMails(mailsUnread);
            f.setMailsListener(this);
            mails = mailsUnread;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Unread");
        } else if (id == R.id.nav_send) {
            f = new FragmentListMails(mails);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Send");
        } else if (id == R.id.nav_spam) {
            f = new FragmentListMails(mailsSpam);
            f.setMailsListener(this);
            mails = mailsSpam;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Spam");
        } else if (id == R.id.nav_trash) {
            f = new FragmentListMails(mailsTrash);
            f.setMailsListener(this);
            mails = mailsTrash;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Trash");
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

    private void loadMails(){
        Collections.sort(mails);
        mailsRecived = new ArrayList<>();
        mailsSent = new ArrayList<>();
        mailsSpam = new ArrayList<>();
        mailsTrash = new ArrayList<>();
        mailsUnread = new ArrayList<>();
        for (Mail m: mails){
            if (!m.isSpam() && !m.isDeleted())
                mailsRecived.add(m);
            if (m.getFrom() != null && !m.getTo().getEmail().equals(account.getEmail()))
                mailsSent.add(m);
            if (!m.isReaded())
                mailsUnread.add(m);
            if (m.isSpam())
                mailsSpam.add(m);
            if (m.isDeleted())
                mailsTrash.add(m);
        }
    }
}
