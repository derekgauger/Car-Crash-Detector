package com.example.crashdetector;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrashesPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrashesPage extends Fragment {
    public CrashesDB database;
    CrashesListAdapter adapter;
    List<Crash> crashes;
    private FusedLocationProviderClient fusedLocationClient;

    private Location lastLocation;

    public CrashesPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CrashesPage.
     */
    public static CrashesPage newInstance() {
        return new CrashesPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new CrashesDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crashes_page, container, false);
//        contactUIList.setLayoutManager(new LinearLayoutManager(getActivity()));
        crashes = database.getCrashes();
        adapter = new CrashesListAdapter(requireActivity().getApplicationContext(), crashes);
//        contactUIList.setAdapter(adapter);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Button button = view.findViewById(R.id.crashButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsDB contactsDatabase = new ContactsDB(getActivity());
                List<Contact> contacts = contactsDatabase.getContacts();
                Random rand = new Random();
                Contact randomContact = contacts.get(rand.nextInt(contacts.size()));
                sendLocationSMS(randomContact);
            }
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList() {
        crashes = database.getCrashes();
        adapter.notifyDataSetChanged();
    }

    private void sendLocationSMS(Contact contact) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            sendSMS(location);
                            // Logic to handle location object
                        }
                    }

                    public void sendSMS(Location location) {
                        String firstName = contact.firstName;
                        String lastName = contact.lastName;
                        String phoneNumber = contact.phoneNumber;
                        String relation = contact.relation;
                        SmsManager smsManager = SmsManager.getDefault();
                        String message = getString(R.string.sms_message, firstName, lastName, relation, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                        System.out.println(message);
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null);

                    }
                });
    }

}