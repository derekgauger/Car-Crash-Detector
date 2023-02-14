package com.example.crashdetector;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactListViewHolder extends RecyclerView.ViewHolder {

    TextView firstNameView;
    TextView lastNameView;
    TextView phoneNumberView;
    TextView relationView;

    public ContactListViewHolder(@NonNull View itemView) {
        super(itemView);
        firstNameView = itemView.findViewById(R.id.firstName);
        lastNameView = itemView.findViewById(R.id.lastName);
        phoneNumberView = itemView.findViewById(R.id.phoneNumber);
        relationView = itemView.findViewById(R.id.relation);
    }
}
