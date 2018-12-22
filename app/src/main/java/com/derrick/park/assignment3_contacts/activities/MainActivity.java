package com.derrick.park.assignment3_contacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.derrick.park.assignment3_contacts.Adapter.ContactListAdapter;
import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;
import com.derrick.park.assignment3_contacts.models.ContactList;
import com.derrick.park.assignment3_contacts.network.ContactClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> mContactList;
    RecyclerView mRecyclerView;
    ContactListAdapter mAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<String> newGetContact = new ArrayList<>();
    private String first;
    private String last;
    private String cell;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivityForResult(intent, 1);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Call<ContactList> call = ContactClient.getContacts(10);
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ContactList>() {
            @Override
            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                if (response.isSuccessful()) {
                    mContactList = response.body().getContactList();

                    generateContactList(mContactList);
                    for (Contact contact : mContactList) {
                        Log.d(TAG, "onResponse: " + mContactList.size());
                        Log.d(TAG, "onResponse: " + contact);
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void generateContactList(ArrayList<Contact> contactDataList) {

        Collections.sort(contactDataList, new Comparator<Contact>() {
            public int compare(Contact obj1, Contact obj2) {
                return obj1.getName().getFirst()
                        .compareToIgnoreCase(obj2.getName().getFirst());
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new ContactListAdapter(contactDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        mRecyclerView.setLayoutManager(layoutManager);

        // Create Divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 1) {
            newGetContact = resultData.getStringArrayListExtra("ADD_NEW_CONTACT");
            for (String nc:newGetContact) {
                Log.d(TAG, "onActivityResult: " + nc);
            }

            for (int i=0; i<newGetContact.size(); i++) {
                first = newGetContact.get(0);
                last = newGetContact.get(1);
                cell = newGetContact.get(2);
            }

            mContactList.add(new Contact(new Contact.Name(first, last), cell));

            generateContactList(mContactList);
        }
    }
}
