package com.joanmanera.gmaildos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.joanmanera.gmaildos.R;

public class FragmentSendMail extends Fragment implements View.OnClickListener {

    private EditText etEmail;
    private EditText etAsunto;
    private EditText etMensaje;
    private Button bEnviar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_mail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etEmail = getActivity().findViewById(R.id.etMail);
        etAsunto = getActivity().findViewById(R.id.etAsunto);
        etMensaje = getActivity().findViewById(R.id.etCuerpo);
        bEnviar = getActivity().findViewById(R.id.bEnviar);
        bEnviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(etEmail.getText().toString().equals("") || etMensaje.getText().toString().equals("") || etAsunto.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Los campos no pueden estar vacíos", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Mensaje enviado.", Toast.LENGTH_LONG).show();
        }
    }
}
