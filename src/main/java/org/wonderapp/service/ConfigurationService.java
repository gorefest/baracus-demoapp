package org.wonderapp.service;

import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.dao.ConfigurationDao;
import net.mantucon.baracus.model.ConfigurationParameter;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * Date: 15.05.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@Bean
public class ConfigurationService {

    @Bean
    ConfigurationDao configurationDao;
    private static final String NO = "No";
    private static final String APP_BASE_INIT_REQUIRED="BaseInitRequired";

    public boolean isAppBaseInitializationRequired() {
        ConfigurationParameter p = configurationDao.getByName(APP_BASE_INIT_REQUIRED);
        if (p != null) {
            return !NO.equals(p.getConfigParameterValue());
        } else {
            return true;
        }
    }

    public void setAppBaseInitializationDone() {
        ConfigurationParameter p = configurationDao.getByName(APP_BASE_INIT_REQUIRED);
        if (p != null) {
            p.setConfigParameterValue(NO);
            configurationDao.save(p);
        } else {
            ConfigurationParameter parm = new ConfigurationParameter();
            parm.setConfigParameter(APP_BASE_INIT_REQUIRED);
            parm.setConfigParameterValue(NO);
            configurationDao.save(parm);
        }
    }
}
