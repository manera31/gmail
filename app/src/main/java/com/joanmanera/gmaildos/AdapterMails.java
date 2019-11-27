package com.joanmanera.gmaildos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
    private Context context;

    public AdapterMails(IMailListener listener, ArrayList<Mail> mails, Context context) {
        this.listener = listener;
        this.mails = mails;
        this.context = context;
    }

    @NonNull
    @Override
    public MailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mail, parent, false);
        return new MailsViewHolder(view, listener, context);
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
        private Context context;


        public MailsViewHolder(@NonNull View itemView, IMailListener listener, Context context) {
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

        @SuppressLint("ResourceAsColor")
        public void bindMail(Mail mail){
            if (!mail.isReaded()){
                tvName.setTypeface(null, Typeface.BOLD);
                tvSubject.setTypeface(null, Typeface.BOLD);
                tvDate.setTypeface(null, Typeface.BOLD);
                tvDate.setTextColor(Color.BLUE);
                tvHour.setTypeface(null, Typeface.BOLD);
                tvHour.setTextColor(Color.BLUE);
            }
            if(mail.getFrom() != null){
                tvName.setText(mail.getFrom().getName());
                String nombre = "c"+mail.getFrom().getPhoto();
                int resId = context.getResources().getIdentifier(nombre, "drawable", context.getPackageName());
                ivPhoto.setImageResource(resId);
            } else {
                tvName.setText(mail.getUknowMail());
                ivPhoto.setImageResource(R.drawable.unknown);
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
