package org.wonderapp.application;

import net.mantucon.baracus.context.BaracusApplicationContext;
import net.mantucon.baracus.util.Logger;
import org.wonderapp.dao.AccountDao;
import org.wonderapp.dao.CustomerDao;
import org.wonderapp.service.CalculationService;
import org.wonderapp.service.ConfigurationService;

/**
 * Simple Application Context for demo purpose
 */
public class ApplicationContext extends BaracusApplicationContext {

    static {
        Logger.setTag("WONDERAPP");

        registerBeanClass(OpenHelper.class);    // This ALWAYS should be Your first bean!
        registerBeanClass(AccountDao.class);    // The DAO for managing account accesses
        registerBeanClass(ConfigurationService.class); // Config services easying access to conf
        registerBeanClass(CustomerDao.class);    // The DAO for managing account accesses
        registerBeanClass(CalculationService.class);    // Some services
    }

}
