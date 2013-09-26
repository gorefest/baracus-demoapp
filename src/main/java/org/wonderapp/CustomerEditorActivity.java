package org.wonderapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.context.ManagedActivity;
import net.mantucon.baracus.errorhandling.ErrorSeverity;
import net.mantucon.baracus.util.Logger;
import org.wonderapp.application.ApplicationContext;
import org.wonderapp.dao.AccountDao;
import org.wonderapp.dao.CustomerDao;
import org.wonderapp.model.Account;
import org.wonderapp.model.Customer;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * Date: 30.08.13
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
@Bean
public class CustomerEditorActivity extends ManagedActivity {

    private final static Logger logger = new Logger(CustomerEditorActivity.class);

    @Bean
    CustomerDao customerDao;

    Customer currentCustomer;

    EditText firstName;
    EditText lastName;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_editor);

        Long customerId= this.getIntent().getExtras().getLong("customerId");
        if (customerId!= null) {

            for (Customer customer : customerDao.loadAll()) {
                logger.info("Customer found $1",customer.toString());
            }
            currentCustomer = customerDao.getById(customerId);

            firstName = (EditText) findViewById(R.id.txtCustomerFirstName);
            firstName.setText(currentCustomer.getFirstName());

            lastName= (EditText) findViewById(R.id.txtCustomerLastName);
            lastName.setText(currentCustomer.getLastName());
        } else {
            currentCustomer = new Customer();
        }
    }

    public void btnSaveClicked(View v){

        // clear all error notifications, we are going to validate here
        ApplicationContext.resetErrors(underlyingView);

        /* implicit form validation
         * The validation wiring is done on the validatedBy-Property inside of
         * the xml-declaration of the components
         * The routing is determined by the use of special error views (case 1)
         * or a mappeable component (TextViews are processed by @see TextEditErrorHandlers) */
        ApplicationContext.validateView(underlyingView);

        if (!ApplicationContext.viewHasErrors(underlyingView)) {
            currentCustomer.setFirstName(firstName.getText().toString());
            currentCustomer.setLastName(lastName.getText().toString());
            customerDao.save(currentCustomer);
            onBackPressed();
        }

    }

}