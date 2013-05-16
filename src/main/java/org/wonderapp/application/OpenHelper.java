package org.wonderapp.application;

import android.content.Context;
import net.mantucon.baracus.dao.BaracusOpenHelper;
import net.mantucon.baracus.migr8.ModelVersion000;
import org.wonderapp.migr8.ModelVersion100;

/**
 * Open Helper. Because baracus brings You already a preconfigured
 * DAO for retrieving and storing key-value pairs in the database,
 * You must inherit the BaracusOpenHelper, register it to the app
 * and provide a proper constructor public, carrying only the
 * database version.
 */
public class OpenHelper extends BaracusOpenHelper {

    public static final String DATABASE_NAME="wonderapp";    // Your database here
    public static final int TARGET_DB_VERSION= 100;          // Do not set the version == 0!

    static {
        addMigrationStep(new ModelVersion100());
    }

    public OpenHelper(Context mContext) {
        super(mContext, DATABASE_NAME, TARGET_DB_VERSION);
    }
}
