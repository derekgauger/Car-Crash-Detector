package com.example.crashdetector;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsPage extends Fragment {
    public ContactsDB database;
    ContactListAdapter adapter;
    List<Contact> contacts;

    RecyclerView contactUIList;
    public ContactsPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ContactsPage.
     */
    public static ContactsPage newInstance() {
        return new ContactsPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new ContactsDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_page, container, false);
        contactUIList = view.findViewById(R.id.contactRecycler);
        contactUIList.setLayoutManager(new LinearLayoutManager(getActivity()));
        contacts = database.getContacts();
        adapter = new ContactListAdapter(requireActivity().getApplicationContext(), contacts);
        contactUIList.setAdapter(adapter);
        return view;
    }

    public void updateList() {
        System.out.println("UPDATING");
        contactUIList.setLayoutManager(new LinearLayoutManager(getActivity()));
        contacts = database.getContacts();
        adapter = new ContactListAdapter(requireActivity().getApplicationContext(), contacts);
        contactUIList.setAdapter(adapter);
    }
}