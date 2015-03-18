package org.wonderapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.baracus.annotations.Bean;
import org.baracus.context.ManagedActivity;
import org.baracus.errorhandling.ErrorSeverity;
import org.wonderapp.application.ApplicationContext;
import org.wonderapp.dao.AccountDao;
import org.wonderapp.model.Account;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * Date: 30.08.13
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
@Bean
public class AccountEditorActivity extends ManagedActivity {

    @Bean
    AccountDao accountDao;

    Account currentAccount;

    EditText accountNumber;
    EditText accoutName;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_editor);

        Long accountId = this.getIntent().getExtras().getLong("accountId");
        if (accountId != null) {
            currentAccount = accountDao.getById(accountId);

            accountNumber = (EditText) findViewById(R.id.txtAccountNumber);
            accountNumber.setText(currentAccount.getAccountNumber());

            accoutName = (EditText) findViewById(R.id.txtAccountName);
            accoutName.setText(currentAccount.getAccountName());
        }
    }

    public void btnSaveClicked(View v){

        // clear all error notifications, we are going to validate here
        ApplicationContext.resetErrors(underlyingView);

        // explicit form validation
        if (accountNumber.getText() == null || accountNumber.getText().toString().trim().length() ==0) {
            ApplicationContext.addErrorToView(underlyingView, R.id.txtAccountNumber, R.string.notNullAccountNumber, ErrorSeverity.ERROR);
            // ... here could be more validations
            // ... and here we route them to the form :
            ApplicationContext.applyErrorsOnView(underlyingView);
        } else {
            currentAccount.setAccountNumber(accountNumber.getText().toString());
            currentAccount.setAccountName(accoutName.getText().toString());
            accountDao.save(currentAccount); // This will fire the DataChanged Event on the MainActivity
            onBackPressed();
        }
    }

}