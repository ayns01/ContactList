package com.derrick.park.assignment3_contacts.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.validation.Validator;

public class AddContactActivity extends AppCompatActivity {
    private static final String TAG = AddContactActivity.class.getSimpleName();
    private EditText mNewName;
    private EditText mNewCell;
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

        if (isValidName(mNewName) && isValidCell(mNewCell) ) {

            String name = mNewName.getText().toString();
            String cell = mNewCell.getText().toString();
            String[] result = name.split(" ");
            String first = "";
            String last = "";
            for (int x = 0; x < result.length; x++) {
                first = result[0];
                last = result[1];
            }

            newContactStringArrayList.add(first);
            newContactStringArrayList.add(last);
            newContactStringArrayList.add(cell);

            if (!(first == null || last == null || cell == null)) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("ADD_NEW_CONTACT", newContactStringArrayList);
                setResult(1, intent);
                finish();
            }else {
                Toast.makeText(AddContactActivity.this, "Please fill every category", Toast.LENGTH_SHORT).show();
            }
            
        }else {
            Toast.makeText(this, "Please fill in valid name or cell number", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isValidName(EditText nameText) {
        CharSequence name = nameText.getText().toString();
        return (!TextUtils.isEmpty(name) && Pattern.matches("[a-zA-Z]*[ ][a-zA-Z]*", name));
    }

    boolean isValidCell(EditText cellText) {
        CharSequence cell = cellText.getText().toString();
        return (!TextUtils.isEmpty(cell) && Pattern.matches("[0-9]{10}", cell));
    }
}
