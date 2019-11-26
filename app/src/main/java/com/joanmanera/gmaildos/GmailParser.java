package com.joanmanera.gmaildos;

import android.content.Context;

import com.joanmanera.gmaildos.models.Account;
import com.joanmanera.gmaildos.models.Contact;
import com.joanmanera.gmaildos.models.Mail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GmailParser {
    private Account account;
    private ArrayList<Contact> contacts;
    private ArrayList<Mail> mails;
    private InputStream mailsFile;

    public GmailParser(Context context){
        this.mailsFile = context.getResources().openRawResource(R.raw.correos);
        contacts = new ArrayList<>();
        mails = new ArrayList<>();
    }

    public boolean parse(){
        boolean parsed;
        String jsonMails;

        try {
            byte[] buffer = new byte[mailsFile.available()];
            mailsFile.read(buffer);
            mailsFile.close();
            jsonMails = new String(buffer, "UTF-8");

            JSONTokener tokener = new JSONTokener(jsonMails);
            JSONArray array = new JSONArray(tokener);
            JSONObject object = array.getJSONObject(0);

            JSONObject objectAccount = object.getJSONObject("myAccount");
            int id = objectAccount.getInt("id");
            String name = objectAccount.getString("name");
            String firstSurname = objectAccount.getString("firstSurname");
            String email = objectAccount.getString("email");

            account = new Account(id, name, firstSurname, email);
            contacts.add(new Contact(id, name, firstSurname, email));

            //CONTACTOS
            JSONArray arrayContacts = object.getJSONArray("contacts");
            for (int i = 0 ; i < arrayContacts.length() ; i++){
                JSONObject contactObject = arrayContacts.getJSONObject(i);
                int idContact;
                String nameContact;
                String firstSurnameContact;
                String secondSurnameContact;
                String birth;
                int photo;
                String emailContact;
                String phone1;
                String phone2;
                String address;
                idContact = contactObject.getInt("id");
                nameContact = contactObject.getString("name");
                firstSurnameContact = contactObject.getString("firstSurname");
                secondSurnameContact = contactObject.getString("secondSurname");
                birth = contactObject.getString("birth");
                photo = contactObject.getInt("foto");
                emailContact = contactObject.getString("email");
                phone1 = contactObject.getString("phone1");
                phone2 = contactObject.getString("phone2");
                address = contactObject.getString("address");

                contacts.add(new Contact(idContact, nameContact, firstSurnameContact, secondSurnameContact, birth, photo, emailContact, phone1, phone2, address));
            }

            //MAILS
            JSONArray arrayMail = object.getJSONArray("mails");
            for (int i = 0 ; i < arrayMail.length() ; i++){
                JSONObject mailObject = arrayMail.getJSONObject(i);
                String from;
                String to;
                String subject;
                String body;
                String sentOn;
                boolean readed;
                boolean deleted;
                boolean spam;
                from = mailObject.getString("from");
                to = mailObject.getString("to");
                subject = mailObject.getString("subject");
                body = mailObject.getString("body");
                sentOn = mailObject.getString("sentOn");
                readed = mailObject.getBoolean("readed");
                deleted = mailObject.getBoolean("deleted");
                spam = mailObject.getBoolean("spam");

                Contact fromContact = null;
                Contact toContact = null;
                for (Contact c: contacts) {
                    if(c.getEmail().equals(from)){
                        fromContact = c;
                    }
                    if(c.getEmail().equals(to)){
                        toContact = c;
                    }
                }

                mails.add(new Mail(fromContact, toContact, subject, body, sentOn, readed, deleted, spam));
            }
            parsed = true;
        } catch (IOException e) {
            e.printStackTrace();
            parsed = false;
        } catch (JSONException e) {
            e.printStackTrace();
            parsed = false;
        }

        return parsed;
    }

    public Account getAccount() {
        return account;
    }

    public ArrayList<Mail> getMails() {
        return mails;
    }
}
