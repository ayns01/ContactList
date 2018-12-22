package com.derrick.park.assignment3_contacts.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private final ArrayList<Contact> mContactList;

    public ContactListAdapter(ArrayList<Contact> contactList) {
        this.mContactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        View mItemView = mInflater.inflate(
                R.layout.row_contact, viewGroup, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Contact mCurrent = mContactList.get(i);
        viewHolder.contactCellView.setText(mCurrent.getCell());
        viewHolder.contactNameView.setText(mCurrent.getName().toString());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView contactNameView, contactCellView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameView = itemView.findViewById(R.id.txt_name);
            contactCellView = itemView.findViewById(R.id.txt_cell);
        }
    }
}
