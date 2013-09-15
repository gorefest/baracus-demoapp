package org.wonderapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;
import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.orm.ObjectReference;
import net.mantucon.baracus.signalling.DataChangeAwareComponent;
import net.mantucon.baracus.util.Logger;
import org.wonderapp.application.ApplicationContext;
import org.wonderapp.dao.AccountDao;
import org.wonderapp.dao.CustomerDao;
import org.wonderapp.model.Account;
import org.wonderapp.model.Customer;
import org.wonderapp.service.ConfigurationService;

import java.util.ArrayList;
import java.util.List;

public class HelloAndroidActivity extends Activity implements DataChangeAwareComponent<Customer> {

    private final static Logger logger = new Logger(HelloAndroidActivity.class);

    @Bean
    AccountDao accountDao;

    @Bean
    CustomerDao customerDao;

    @Bean
    ConfigurationService configurationService;

    ExpandableListView expandableListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (configurationService.isAppBaseInitializationRequired()) {
            initData();
            configurationService.setAppBaseInitializationDone();
        }

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        fillTable();
    }

    private void initData() {
        Customer c = new Customer();
        c.setLastName("BAR");
        c.setFirstName("FOO");

        customerDao.save(c);

        Account a = new Account();
        a.setAccountName("FOO's account");
        a.setAccountNumber("IBAN4711GOHOME");
        a.setCustomerReference(new ObjectReference<Customer>(c));

        accountDao.save(a);

        for (Customer customer : customerDao.loadAll()) {
            logger.info("Customer $1 $2", customer.getFirstName(), customer.getLastName());
            for (Account account : customer.getAccounts()) {
                logger.info("Account $1 number $2", account.getAccountName(), account.getAccountNumber());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(org.wonderapp.R.menu.main, menu);
	return true;
    }



    private void fillTable() {

        List<Customer> customers = customerDao.loadAll();
        AccountExpandListAdapter adapter = new AccountExpandListAdapter(this, new ArrayList<Customer>(customers));

        expandableListView.setAdapter(adapter);

        expandableListView.setLongClickable(true);

        expandableListView.setClickable(true);
    }

    @Override
    public void onChange(Customer customer) {
     logger.info("CHANGE!");
    }
}

