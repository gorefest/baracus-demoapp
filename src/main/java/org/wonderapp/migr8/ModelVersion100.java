package org.wonderapp.migr8;

import android.database.sqlite.SQLiteDatabase;
import net.mantucon.baracus.migr8.MigrationStep;
import net.mantucon.baracus.util.Logger;
import org.wonderapp.model.Account;
import org.wonderapp.model.Customer;

/**
 * Model version 100
 */
public class ModelVersion100 implements MigrationStep{

    private static final Logger logger = new Logger(ModelVersion100.class);


    @Override
    public void applyVersion(SQLiteDatabase db) {

        // Use the field identifiers from Your entity to avoid name clashes
        String stmt  = "CREATE TABLE " + Customer.TABLE_NAME                 // Table clause
                + "( "+ Customer.idCol.fieldName+" INTEGER PRIMARY KEY"      // inherited Long ID - every derivate of ModelBase needs this!
                + ", "+ Customer.lastNameCol.fieldName+ " TEXT"              // name cols
                + ", "+ Customer.firstNameCol.fieldName+ " TEXT)";

        logger.info(stmt);
        db.execSQL(stmt);


        // Use the field identifiers from Your entity to avoid name clashes
        stmt  = "CREATE TABLE " + Account.TABLE_NAME                 // Table clause
                + "( "+ Account.idCol.fieldName+" INTEGER PRIMARY KEY"      // inherited Long ID - every derivate of ModelBase needs this!
                + ", "+ Account.accountNumberCol.fieldName+ " TEXT"         // account number
                + ", "+ Account.accountNameCol.fieldName+ " TEXT"           // account name
                + ", "+ Account.customerIdCol.fieldName+ " TEXT)";          // customer ID

        logger.info(stmt);
        db.execSQL(stmt);

    }

    @Override
    public int getModelVersionNumber() {
        return 100;
    }
}
