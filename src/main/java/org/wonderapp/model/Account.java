package org.wonderapp.model;

import net.mantucon.baracus.orm.*;

/**
 * Sample class for demonstrating simple persistence. This table is created by a migration step through the openHelper.
 */
public class Account extends AbstractModelBase {

    /* Table metadata */
    public static int columnIndex= AbstractModelBase.fieldList.size();

    public static final String TABLE_NAME="account";    // needed for constructor and table creation

    public static final FieldList fieldList = new FieldList(TABLE_NAME);        // The field list needs to know about it's table
    public static final Field accountNumberCol = new Field("account_number", columnIndex++);   // the metadata of the table
    public static final Field accountNameCol = new Field("account_name", columnIndex++);
    public static final Field customerIdCol = new Field("customer_id", columnIndex++);   // this is going to be the N:1 relationship

    static {
        fieldList.add(AbstractModelBase.fieldList);     // You must have a local field list in each class
        fieldList.add(accountNumberCol);                // there you add all columns
        fieldList.add(accountNameCol);
        fieldList.add(customerIdCol);
    }

    /* End metadata */

    /* instance fields */
    private String accountNumber;   // Account number
    private String accountName;     // some account name

    // enables You to have LazyLoading capabilities on object level
    // the access to the customer iself becomes encapsulated by two transient functions
    private Reference<Customer> customerReference;

    public Account() {
        super(TABLE_NAME);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Reference<Customer> getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(Reference<Customer> customerReference) {
        this.customerReference = customerReference;
    }

    public void setCustomer(Customer c){
        setCustomerReference(new ObjectReference<Customer>(c));
    }

    public Customer getCustomer() {
        return getCustomerReference().getObject();
    }
}
