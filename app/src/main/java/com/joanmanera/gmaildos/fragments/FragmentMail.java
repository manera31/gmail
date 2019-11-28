package com.joanmanera.gmaildos.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.joanmanera.gmaildos.R;
import com.joanmanera.gmaildos.models.Mail;

public class FragmentMail extends Fragment {

    private ImageView ivFrom;
    private ImageView ivTo;
    private TextView tvFromName;
    private TextView tvToName;
    private TextView tvSubject;
    private TextView tvBody;
    private TextView tvFromMail;
    private TextView tvToMail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        ivFrom = view.findViewById(R.id.ivFrom);
        ivTo = view.findViewById(R.id.ivTo);
        tvFromName = view.findViewById(R.id.tvFromName);
        tvToName = view.findViewById(R.id.tvToName);
        tvSubject = view.findViewById(R.id.tvSubjectMail);
        tvBody = view.findViewById(R.id.tvBodyMail);
        tvFromMail = view.findViewById(R.id.tvFromMail);
        tvToMail = view.findViewById(R.id.tvToMail);

        return view;
    }

    public void mostrarDetalle(Mail mail){
        String nombre;
        int resId;


        if (mail.getFrom() != null){
            nombre = "c"+ mail.getFrom().getPhoto();
            resId = getActivity().getResources().getIdentifier(nombre, "drawable", getActivity().getPackageName());
            ivFrom.setImageResource(resId);
            tvFromMail.setText(mail.getFrom().getEmail());
            tvFromName.setText(mail.getFrom().getName() + " " + mail.getFrom().getFirstSurname() + " " + mail.getFrom().getSecondSurname());

            nombre = "c"+mail.getTo().getPhoto();
            resId = getActivity().getResources().getIdentifier(nombre, "drawable", getActivity().getPackageName());
            ivTo.setImageResource(resId);
            tvToMail.setText(mail.getTo().getEmail());
            tvToName.setText(mail.getTo().getName() + " " + mail.getTo().getFirstSurname() + " " + mail.getTo().getSecondSurname());
        } else {
            ivFrom.setImageResource(R.drawable.unknown);
            tvFromName.setText("unknown");
            tvFromMail.setText(mail.getUknowMail());

            ivTo.setImageResource(R.drawable.c0);
            tvToName.setText("Pepito Palotes");
            tvToMail.setText("pepitopalotes@gmail.com");
        }
        tvSubject.setText(mail.getSubject());
        tvBody.setText(mail.getBody());
    }
}
