package com.joanmanera.gmaildos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.joanmanera.gmaildos.R;
import com.joanmanera.gmaildos.models.Mail;

public class FragmentMail extends Fragment {
    private TextView tvFrom;
    private TextView tvTo;
    private TextView tvSubject;
    private TextView tvBody;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        tvFrom = view.findViewById(R.id.tvFrom);
        tvTo = view.findViewById(R.id.tvTo);
        tvSubject = view.findViewById(R.id.tvSubjectMail);
        tvBody = view.findViewById(R.id.tvBodyMail);

        return view;
    }

    public void mostrarDetalle(Mail mail){

        tvFrom.setText(mail.getFrom().getEmail());
        tvTo.setText(mail.getTo().getEmail());
        tvSubject.setText(mail.getSubject());
        tvBody.setText(mail.getBody());
    }
}
