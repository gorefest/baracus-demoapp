package org.wonderapp.model;

import org.baracus.orm.AbstractModelBase;
import org.baracus.orm.Field;
import org.baracus.orm.FieldList;
import org.baracus.orm.ModelBase;

import java.util.Collection;
import java.util.List;

/**
 * Sample class for demonstrating persistence with relations
 */
public class Customer extends ModelBase {

    /* Table metadata */

    public static final String TABLE_NAME="customer";    // needed for constructor
    public static int columnIndex = ModelBase.fieldList.size();

    public static final FieldList fieldList = new FieldList(TABLE_NAME);    // The field list needs to know about it's table
    public static final Field lastNameCol= new Field("last_name", columnIndex++);
    public static final Field firstNameCol= new Field("first_name", columnIndex++);

    static {
        fieldList.add(ModelBase.fieldList);
        fieldList.add(lastNameCol);
        fieldList.add(firstNameCol);
    }

    /* End metadata */

    /* instance fields */
    private String lastName;
    private String firstName;
    public List<Account> accounts; // Simply a collection for the details

    public Customer() {
        super(TABLE_NAME);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
