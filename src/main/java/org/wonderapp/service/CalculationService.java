package org.wonderapp.service;

import org.baracus.annotations.Bean;
import org.baracus.lifecycle.Initializeable;
import org.baracus.util.Logger;
import org.wonderapp.R;
import org.wonderapp.application.ApplicationContext;
import org.wonderapp.dao.AccountDao;

/**
 * Sample Service Bean utilizing Dependency Injection
 */
@Bean
public class CalculationService implements Initializeable{

    private static final Logger logger = new Logger(CalculationService.class);

    private String label;

    @Bean
    AccountDao accountDao;

    public void doSomething() {

    }

    @Override
    public void postConstruct() {
       label = ApplicationContext.resolveString(R.string.hello);
    }
}
