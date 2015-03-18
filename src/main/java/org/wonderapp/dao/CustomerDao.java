package org.wonderapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import org.baracus.annotations.Bean;
import org.baracus.dao.BaseDao;
import org.baracus.orm.AbstractModelBase;
import org.baracus.orm.Field;
import org.baracus.orm.FieldList;
import org.baracus.orm.LazyCollection;
import org.wonderapp.model.Account;
import org.wonderapp.model.Customer;

import java.util.List;

import static org.baracus.orm.ModelBase.idCol;
import static org.wonderapp.model.Customer.firstNameCol;
import static org.wonderapp.model.Customer.lastNameCol;

/**
 * Sample DAO class implementing a rowmapper for an entity with no
 * lazy loading functions.
 */
@Bean
public class CustomerDao extends BaseDao<Customer> {

    @Bean
    AccountDao accountDao;

    public CustomerDao() {
        super(Customer.class);
    }

    /**
     * The rowmapper has two basic tasks : create an object out of one row
     * and to generate a content values object.
     * The decision to make hand-written rowmappers has been done in order
     * to enable the users having smart rowmappers and the freedom to decide
     * how to deal with mapping instead of having generated code,
     *
     */
    private final RowMapper<Customer> customerRowMapper= new RowMapper<Customer>() {
        @Override
        public Customer from(Cursor c) {
            // Map the cursor row data to an appropriate object
            Customer result = new Customer();
            final Long customerId = c.getLong(idCol.fieldIndex);
            result.setId(customerId);
            result.setLastName(c.getString(lastNameCol.fieldIndex));
            result.setFirstName(c.getString(firstNameCol.fieldIndex));

            // Manage the collection. For this, we use the lazy collection carrying a Dao load trigger
            // Lazy collections are somewhat more handy than many-to-ones, because we do not need
            // a lazy pseudo-container (LazyReference) plus transient getter/setters
            LazyCollection<Account> accounts = new LazyCollection<Account>(
                    new LazyCollection.LazyLoader<Account>() {
                        @Override
                        public List<Account> loadReference() {
                            return accountDao.getByCustomerId(customerId);
                        }
                    }
            );
            result.setAccounts(accounts);

            // important, do not forget to mark this result as a already persisted record.
            result.setTransient(false);

            return result;
        }

        @Override
        public String getAffectedTable() {
            return Customer.TABLE_NAME;
        }

        @Override
        public FieldList getFieldList() {
            return Customer.fieldList;
        }

        @Override
        public Field getNameField() {
            return lastNameCol;
        }

        @Override
        public ContentValues getContentValues(Customer item) {
        // instead of dynamic code relation, we make this mapping by hand. done once, used many
        // much faster than any annotation based dynamic generated persistence code
            ContentValues result = new ContentValues();
            if (item.getId() != null) { result.put(idCol.fieldName, item.getId()); }
            if (item.getLastName() != null) { result.put(lastNameCol.fieldName, item.getLastName()); }
            if (item.getFirstName() != null) { result.put(firstNameCol.fieldName, item.getFirstName()); }
            return result;

        }
    };

    @Override
    public RowMapper<Customer> getRowMapper() {
        return customerRowMapper;
    }
}
