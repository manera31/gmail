package com.joanmanera.gmaildos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.gmaildos.models.Mail;

import java.util.ArrayList;

public class AdapterMails extends RecyclerView.Adapter<AdapterMails.MailsViewHolder> {

    private IMailListener listener;
    private ArrayList<Mail> mails;

    @NonNull
    @Override
    public MailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mail, parent, false);
        return new MailsViewHolder(parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mails.size();
    }

    public static class MailsViewHolder extends RecyclerView.ViewHolder implements IMailListener{
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvSubject;
        private TextView tvBody;
        private TextView tvDate;
        private TextView tvHour;
        private IMailListener listener;


        public MailsViewHolder(@NonNull View itemView, IMailListener listener) {
            super(itemView);

        }

        @Override
        public void onMailSelected(int position) {

        }
    }
}
