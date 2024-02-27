package com.example.contactappmanagerapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactappmanagerapp.R;
import com.example.contactappmanagerapp.databinding.ContactListBinding;
import com.example.contactappmanagerapp.db.entity.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private ArrayList<Contact> contacts;

    private Context context;

    public ContactAdapter(ArrayList<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    public ContactAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.contact_list, parent, false);
//        return new MyViewHolder(itemView);

        ContactListBinding contactListBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.contact_list, parent, false);

        return new MyViewHolder(contactListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact contact = contacts.get(position);
//        holder.contact_name.setText(contact.getName());
//        holder.contact_email.setText(contact.getEmail());

        holder.contactListBinding.setContact(contact);
    }

    @Override
    public int getItemCount() {
        if(contacts != null)
            return contacts.size();
        else
            return 0;
    }

    public void setContacts(ArrayList<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        /*private TextView contact_name;
        private TextView contact_email;*/
        private ContactListBinding contactListBinding;


        public MyViewHolder(@NonNull ContactListBinding contactListBinding) {
            super(contactListBinding.getRoot());
            this.contactListBinding = contactListBinding;
            /*contact_email = itemView.findViewById(R.id.contact_email);
            contact_name = itemView.findViewById(R.id.contact_name);*/
        }
    }
}
