package com.joanmanera.gmaildos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.joanmanera.gmaildos.fragments.FragmentListMails;
import com.joanmanera.gmaildos.fragments.FragmentSendMail;
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

        }
        mails = account.getMails();
        loadMails();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSendMail fragmentSendMail = new FragmentSendMail();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentSendMail).commit();
                setTitle("Send");
            }
        });

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Drawable originalDrawable;
        Bitmap originalBitmap;
        RoundedBitmapDrawable roundedBitmapDrawable;
        int resId;

        // Inflamos el menú de la ActionBar
        //getMenuInflater().inflate(R.menu.main, menu);

        LinearLayout llUser = findViewById(R.id.llUser);
        ImageView ivPhoto = findViewById(R.id.ivPhotoUser);
        TextView tvNameUser = findViewById(R.id.tvNameUser);
        TextView tvEmailUser = findViewById(R.id.tvEmailUser);

        resId = this.getResources().getIdentifier("fondo", "drawable", this.getPackageName());
        originalDrawable = this.getResources().getDrawable(resId);

        llUser.setBackground(originalDrawable);

        String nombre = "c"+account.getId();
        resId = this.getResources().getIdentifier(nombre, "drawable", this.getPackageName());
        originalDrawable = this.getResources().getDrawable(resId);
        originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(this.getResources(), originalBitmap);
        roundedBitmapDrawable.setCornerRadius(originalBitmap.getHeight());

        ivPhoto.setImageDrawable(roundedBitmapDrawable);
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

    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentListMails f;
        // Se ha hecho click en algún item del NavigationView
        int id = item.getItemId();

        if (id == R.id.nav_recived) {
            f = new FragmentListMails(mailsRecived, account);
            f.setMailsListener(this);
            mails = mailsRecived;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Recived");
        } else if (id == R.id.nav_sent) {
            f = new FragmentListMails(mailsSent, account);
            f.setMailsListener(this);
            mails = mailsSent;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Sent");
        } else if (id == R.id.nav_unread) {
            f = new FragmentListMails(mailsUnread, account);
            f.setMailsListener(this);
            mails = mailsUnread;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Unread");
        } else if (id == R.id.nav_spam) {
            f = new FragmentListMails(mailsSpam, account);
            f.setMailsListener(this);
            mails = mailsSpam;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Spam");
        } else if (id == R.id.nav_trash) {
            f = new FragmentListMails(mailsTrash, account);
            f.setMailsListener(this);
            mails = mailsTrash;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
            setTitle("Trash");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
   @Override
   public boolean onNavigationItemSelected(MenuItem item) {
       FragmentListMails f = new FragmentListMails();
       f.setMailsListener(this);

       int id = item.getItemId();
       if (id == R.id.nav_recived) {
           account.setMails(mailsRecived);
           setTitle("Recibidos");
       } else if (id == R.id.nav_sent) {
           account.setMails(mailsSent);
           setTitle("Enviados");
       } else if (id == R.id.nav_unread) {
           account.setMails(mailsUnread);
           setTitle("No leidos");
       } else if (id == R.id.nav_spam) {
           account.setMails(mailsSpam);
           setTitle("Spam");
       } else if (id == R.id.nav_trash) {
           account.setMails(mailsTrash);
           setTitle("Papelera");
       }

       Bundle bundleAccount = new Bundle();
       bundleAccount.putSerializable("ACCOUNT", account);

       f.setArguments(bundleAccount);
       getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();

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
        Collections.sort(account.getMails());
        mails = account.getMails();
        mailsRecived = new ArrayList<>();
        mailsSent = new ArrayList<>();
        mailsSpam = new ArrayList<>();
        mailsTrash = new ArrayList<>();
        mailsUnread = new ArrayList<>();
        for (Mail m: mails){
            if (!m.isSpam() && !m.isDeleted() && !m.getFrom().getEmail().equals(account.getEmail()))
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
