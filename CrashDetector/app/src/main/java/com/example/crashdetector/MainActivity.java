package com.example.crashdetector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.crashdetector.ui.main.SectionsPagerAdapter;
import com.example.crashdetector.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public final ContactsDB contactsDatabase = new ContactsDB(MainActivity.this);
    public final CrashesDB crashesDatabase = new CrashesDB(MainActivity.this);
    public SectionsPagerAdapter sectionsPagerAdapter;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkSMSPermissions();

        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                openAlert();
            }
        });
    }

    public void checkSMSPermissions() {
        if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            Log.d("permission", "permission denied to SEND_SMS - requesting it");
            String[] permissions = {android.Manifest.permission.SEND_SMS};
            requestPermissions(permissions, 123);
        }
    }

    private void openAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new contact:");
        LinearLayout contactInfoLayout = createContactInfoLayout();
        builder.setView(contactInfoLayout);
        handleContactAlert(builder, contactInfoLayout);
        builder.show();
    }

    private LinearLayout createContactInfoLayout() {
        LinearLayout contactInfoLayout = new LinearLayout(MainActivity.this);
        contactInfoLayout.setOrientation(LinearLayout.VERTICAL);
        contactInfoLayout.addView(createEditTextForAlertInput("First Name"));
        contactInfoLayout.addView(createEditTextForAlertInput("Last Name"));
        contactInfoLayout.addView(createEditTextForAlertInput("Phone Number"));
        contactInfoLayout.addView(createEditTextForAlertInput("Relation"));
        return contactInfoLayout;
    }

    private EditText createEditTextForAlertInput(String hint) {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(hint);
        return input;
    }

    private void handleContactAlert(AlertDialog.Builder builder, LinearLayout contactInfoLayout) {
        handleContactAlertPositive(builder, contactInfoLayout);
        handleContactAlertNegative(builder);
    }

    private void handleContactAlertPositive(AlertDialog.Builder builder, LinearLayout contactInfoLayout) {
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Contact newContact = getNewContactFromInput(contactInfoLayout);
                contactsDatabase.addNewContact(newContact);
                sectionsPagerAdapter.updateListAdapters();
            }
        });
    }

    private Contact getNewContactFromInput(LinearLayout contactInfoLayout) {
        EditText firstNameBox = (EditText) contactInfoLayout.getChildAt(0);
        EditText lastNameBox = (EditText) contactInfoLayout.getChildAt(1);
        EditText phoneNumberBox = (EditText) contactInfoLayout.getChildAt(2);
        EditText relationBox = (EditText) contactInfoLayout.getChildAt(3);
        String firstName = firstNameBox.getText().toString();
        String lastName = lastNameBox.getText().toString();
        String phoneNumber = phoneNumberBox.getText().toString();
        String relation = relationBox.getText().toString();
        return new Contact(firstName, lastName, phoneNumber, relation);
    }

    private void handleContactAlertNegative(AlertDialog.Builder builder) {
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }


//    private void sendSMS(Contact contact) {
//        String firstName = contact.firstName;
//        String lastName = contact.lastName;
//        String phoneNumber = contact.phoneNumber;
//        String relation = contact.relation;
//        SmsManager smsManager = SmsManager.getDefault();
//        String message = getString(R.string.sms_message, firstName, lastName, relation);
//        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//    }
}