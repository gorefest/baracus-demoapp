package org.wonderapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import net.mantucon.baracus.signalling.DataChangeAwareComponent;
import org.wonderapp.application.ApplicationContext;
import org.wonderapp.model.Account;
import org.wonderapp.model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountExpandListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<Customer> groups;

    public AccountExpandListAdapter(Activity context, List<Customer> groups) {
        this.context = context;
        this.groups = groups;
        ApplicationContext.freeDataChangeListeners(Account.class);
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

        final Account child = (Account) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.entries_list_child_item, null);
        }
        final TextView entryName = (TextView) view.findViewById(R.id.entryName);
        entryName.setText(child.getAccountName().toString());


        final TextView entryValue = (TextView) view.findViewById(R.id.entryAccount);
        entryValue.setText(child.getAccountNumber());

        // Define a change listener in order to refresh the data automatically
        DataChangeAwareComponent<Account> dataChangeAwareComponent = new DataChangeAwareComponent<Account>() {
            @Override
            public void onChange(Account changedInstance) {
                if (changedInstance.getId().equals(child.getId())) { // Only react on the account handled here
                    // Because the data is not reloaded, but the child object is reused
                    // we simply merge the to model objects. The layout()-Event will
                    // reuse the child data object and fill in the correct values
                    child.setAccountName(changedInstance.getAccountName());
                    child.setAccountNumber(changedInstance.getAccountNumber());
                    entryName.setText(child.getAccountName());
                    entryValue.setText(child.getAccountNumber());
                }
            }
        };

        // any data change on accounts done through the DAO layer will run through the change listener
        ApplicationContext.registerDataChangeListener(Account.class, dataChangeAwareComponent);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AccountEditorActivity.class);
                intent.putExtra("accountId", child.getId());
                context.startActivity(intent);
            }
        };

        entryName.setOnClickListener(onClickListener);
        entryValue.setOnClickListener(onClickListener);

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



