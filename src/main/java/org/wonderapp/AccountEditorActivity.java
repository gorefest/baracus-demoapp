package org.wonderapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import net.mantucon.baracus.annotations.Bean;
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
public class AccountEditorActivity extends Activity {

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
        currentAccount.setAccountNumber(accountNumber.getText().toString());
        currentAccount.setAccountName(accoutName.getText().toString());
        accountDao.save(currentAccount); // This will fire the DataChanged Event on the MainActivity
        onBackPressed();

    }

}