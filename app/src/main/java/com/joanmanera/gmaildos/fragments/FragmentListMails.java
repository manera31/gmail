package com.joanmanera.gmaildos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.gmaildos.AdapterMails;
import com.joanmanera.gmaildos.GmailParser;
import com.joanmanera.gmaildos.IMailListener;
import com.joanmanera.gmaildos.MainActivity;
import com.joanmanera.gmaildos.R;
import com.joanmanera.gmaildos.models.Account;
import com.joanmanera.gmaildos.models.Contact;
import com.joanmanera.gmaildos.models.Mail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FragmentListMails extends Fragment {
    private Account account;
    private ArrayList<Mail> mails;
    private RecyclerView recyclerView;
    private IMailListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mails, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            account = (Account) getArguments().getSerializable("ACCOUNT");
            mails = (ArrayList<Mail>) getArguments().getSerializable("MAILS");
        }
        recyclerView = getView().findViewById(R.id.rvMails);
        recyclerView.setAdapter(new AdapterMails(listener, account, mails, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public void setMailsListener(IMailListener listener){
        this.listener = listener;
    }

}
