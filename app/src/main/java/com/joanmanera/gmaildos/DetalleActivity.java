package com.joanmanera.gmaildos;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.gmaildos.fragments.FragmentMail;
import com.joanmanera.gmaildos.models.Mail;

public class DetalleActivity extends AppCompatActivity {
    public static final String EXTRA_TEXTO = "com.joanmanera.gmail";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        FragmentMail detalle = (FragmentMail)getSupportFragmentManager().findFragmentById(R.id.FragmentDetalle);
        detalle.mostrarDetalle((Mail)getIntent().getSerializableExtra(EXTRA_TEXTO));
    }
}
