package com.example.crashdetector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListViewHolder> {

    Context context;
    List<Contact> contacts;

    public ContactListAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactListViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {
        holder.firstNameView.setText(contacts.get(position).firstName);
        holder.lastNameView.setText(contacts.get(position).lastName);
        holder.phoneNumberView.setText(contacts.get(position).phoneNumber);
        holder.relationView.setText(contacts.get(position).relation);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
