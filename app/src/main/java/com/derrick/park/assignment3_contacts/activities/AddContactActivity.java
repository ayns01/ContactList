package com.derrick.park.assignment3_contacts.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {
    private static final String TAG = AddContactActivity.class.getSimpleName();
    private TextView mNewName;
    private TextView mNewCell;
    ArrayList<Contact> newContact;
    ArrayList<String> newContactStringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Intent intent = getIntent();

        mNewName = findViewById(R.id.txt_add_name);
        mNewCell = findViewById(R.id.txt_add_cell);
    }

    public void submit_newContact(View view) {
        String name = mNewName.getText().toString();
        String cell = mNewCell.getText().toString();
        String[] result = name.split(" ");
        String first = "";
        String last = "";
        for (int x=0; x<result.length; x++) {
            first = result[0];
            last = result[result.length - 1];
        }
        Log.d(TAG, "submit_newContact: " + cell);
        Log.d(TAG, "submit_newContact: " + first);
        Log.d(TAG, "submit_newContact: " + last);
//        newContact.add(new Contact(new Contact.Name(first, last), cell));

        newContactStringArrayList.add(first);
        newContactStringArrayList.add(last);
        newContactStringArrayList.add(cell);

        if (!(first == null || last == null || cell == null)) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("ADD_NEW_CONTACT", newContactStringArrayList);
            setResult(1, intent);
            finish();
        }else {
            Toast.makeText(AddContactActivity.this,"Please fill every category", Toast.LENGTH_SHORT ).show();
        }
    }

    private boolean isPasswordValid(@Nullable TextView text) {
        return text != null && text.length() == 10;
    }
}
