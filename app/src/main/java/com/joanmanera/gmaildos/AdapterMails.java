package com.joanmanera.gmaildos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.gmaildos.models.Account;
import com.joanmanera.gmaildos.models.Mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterMails extends RecyclerView.Adapter<AdapterMails.MailsViewHolder> {

    private IMailListener listener;
    private ArrayList<Mail> mails;
    private Account account;
    private Context context;

    public AdapterMails(IMailListener listener, Account account, ArrayList<Mail> mails, Context context) {
        this.listener = listener;
        this.mails = mails;
        this.account = account;
        this.context = context;
    }

    @NonNull
    @Override
    public MailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mail, parent, false);
        return new MailsViewHolder(view, listener, account, context);
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
        private Account account;


        public MailsViewHolder(@NonNull View itemView, IMailListener listener, Account account, Context context) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvName = itemView.findViewById(R.id.tvName);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHour = itemView.findViewById(R.id.tvHour);
            this.listener = listener;
            this.context = context;
            this.account = account;
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        public void bindMail(Mail mail){
            Drawable originalDrawable;
            Bitmap originalBitmap;
            RoundedBitmapDrawable roundedBitmapDrawable;

            if (!mail.isReaded()){
                tvName.setTypeface(null, Typeface.BOLD);
                tvSubject.setTypeface(null, Typeface.BOLD);
                tvDate.setTypeface(null, Typeface.BOLD);
                tvDate.setTextColor(Color.BLUE);
                tvHour.setTypeface(null, Typeface.BOLD);
                tvHour.setTextColor(Color.BLUE);
            }

            if (mail.getFrom() != null) {
                 String nombre;
                 if(mail.getFrom().getEmail().equals(account.getEmail())){
                     tvName.setText(mail.getTo().getName());
                     nombre = "c" + mail.getTo().getPhoto();
                 }else {
                     tvName.setText(mail.getFrom().getName());
                     nombre = "c" + mail.getFrom().getPhoto();
                 }

                 int resId = context.getResources().getIdentifier(nombre, "drawable", context.getPackageName());

                 originalDrawable = context.getResources().getDrawable(resId);
                 originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                 roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap);
                 roundedBitmapDrawable.setCornerRadius(originalBitmap.getHeight());
            } else {
                 tvName.setText(mail.getUknowMail());

                 originalDrawable = context.getResources().getDrawable(R.drawable.unknown);
                 originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                 roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap);
                 roundedBitmapDrawable.setCornerRadius(originalBitmap.getHeight());

            }

            ivPhoto.setImageDrawable(roundedBitmapDrawable);
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
