package org.wonderapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import org.wonderapp.model.Account;
import org.wonderapp.model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<Customer> groups;

    public ExpandListAdapter(Activity context, List<Customer> groups) {
        this.context = context;
        this.groups = groups;
    }

    public Object getChild(int groupPosition, int childPosition) {
        List<Account> entries = groups.get(groupPosition).getAccounts();
        ArrayList<Account> chList = new ArrayList<Account>(entries);
        return chList.get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        final Date today = new Date();

        Account child = (Account) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.entries_list_child_item, null);
        }
        TextView entryName = (TextView) view.findViewById(R.id.entryName);
        entryName.setText(child.getAccountName().toString());

        TextView entryValue = (TextView) view.findViewById(R.id.entryAccount);
        entryValue.setText(child.getAccountNumber());

        return view;
    }

    public int getChildrenCount(int groupPosition) {
        ArrayList<Account> chList = new ArrayList<Account>(groups.get(groupPosition).getAccounts());

        return chList.size();

    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        Customer group = (Customer) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.entries_list_group_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.tvGroup);
        tv.setText(group.getLastName()+", "+group.getFirstName());
        return view;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}



