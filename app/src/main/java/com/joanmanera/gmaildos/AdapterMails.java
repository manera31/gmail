package com.joanmanera.gmaildos;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.gmaildos.models.Account;
import com.joanmanera.gmaildos.models.Mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterMails extends RecyclerView.Adapter<AdapterMails.MailsViewHolder> {

    private IMailListener listener;
    private ArrayList<Mail> mails;

    public AdapterMails(IMailListener listener, ArrayList<Mail> mails) {
        this.listener = listener;
        this.mails = mails;
    }

    @NonNull
    @Override
    public MailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mail, parent, false);
        return new MailsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MailsViewHolder holder, int position) {
        Mail mail = mails.get(position);
        holder.bindMail(mail);
    }

    @Override
    public int getItemCount() {
        return mails.size();
    }

    public static class MailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvSubject;
        private TextView tvBody;
        private TextView tvDate;
        private TextView tvHour;
        private IMailListener listener;


        public MailsViewHolder(@NonNull View itemView, IMailListener listener) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvName = itemView.findViewById(R.id.tvName);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHour = itemView.findViewById(R.id.tvHour);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public void bindMail(Mail mail){
            if(mail.getFrom() != null){
                tvName.setText(mail.getFrom().getName());
            } else {
                tvName.setText(mail.getUknowMail());
            }
            tvSubject.setText(mail.getSubject());
            tvBody.setText(mail.getBody());
            tvDate.setText(mail.getDate());
            tvHour.setText(mail.getHour());
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onMailSelected(getAdapterPosition());
            }
        }
    }
}
