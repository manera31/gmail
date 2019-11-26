package com.joanmanera.gmaildos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.gmaildos.AdapterMails;
import com.joanmanera.gmaildos.GmailParser;
import com.joanmanera.gmaildos.IMailListener;
import com.joanmanera.gmaildos.R;
import com.joanmanera.gmaildos.models.Account;
import com.joanmanera.gmaildos.models.Mail;

import java.util.ArrayList;

public class FragmentSent extends Fragment {
    private ArrayList<Mail> mails;
    private Account account;
    private RecyclerView recyclerView;
    private IMailListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        GmailParser parser = new GmailParser(getActivity());
        if (parser.parse()){
            this.mails = parser.getMails();
            this.account = parser.getAccount();
        }
        return inflater.inflate(R.layout.fragment_mails, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.rvMails);
        ArrayList<Mail> mailsRecived = new ArrayList<>();
        for(Mail m: mails){
            if(m.getTo() !=  null && !m.getTo().getEmail().equals(account.getEmail())){
                mailsRecived.add(m);
            }
        }
        recyclerView.setAdapter(new AdapterMails(listener, mailsRecived));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public void setMailsListener(IMailListener listener){
        this.listener = listener;
    }

    public ArrayList<Mail> getMails(){
        return mails;
    }
}
