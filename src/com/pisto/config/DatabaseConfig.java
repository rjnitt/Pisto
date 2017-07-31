package com.pisto.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    
    @Autowired
    private AppProperties appProperties;

    /**
     * The datasource can be @Autowired by classes which need it, using a setDataSource() method. E.g. see class UserDAO.
     */
    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndi = new JndiDataSourceLookup();
        DataSource ds = jndi.getDataSource(appProperties.datasourceJndiName());
        LOG.debug("dataSource: {}", ds);
        return ds;
    }

    @Bean
    public AbstractJpaVendorAdapter jpaVendorAdapter() {
        AbstractJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setGenerateDdl(false);
        jpaAdapter.setShowSql(false);
        jpaAdapter.setDatabase(Database.valueOf(appProperties.databaseVendor())); 
        LOG.debug("jpaVendorAdapter: {}", jpaAdapter);
        return jpaAdapter;
    
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setValidationMode(ValidationMode.CALLBACK);
        emf.setPackagesToScan("org.habba.domain.entity");
        
        Map<String, String> jpaProperties = new HashMap<String, String>();
        jpaProperties.put("hibernate.dialect", "org.habba.infra.util.json.JsonPostgreSQLDialect");
//        jpaProperties.put("hibernate.cache.use_second_level_cache", "true");
//        jpaProperties.put("hibernate.cache.use_query_cache", "true");
//        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.infinispan.InfinispanRegionFactory");
//        jpaProperties.put("hibernate.cache.default_cache_concurrency_strategy", "nonstrict-read-write");
        emf.setJpaPropertyMap(jpaProperties);
        
        LOG.debug("entityManagerFactory: {}", emf);
        return emf;
    }

    /**
     * We are only using a single database, so local (i.e. native) transactions is sufficient, no JTA required.
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        LOG.debug("transactionManager: {}", tm);
        return tm;
    }

}

