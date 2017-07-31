package com.pisto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources({
    @PropertySource("file:${app_properties_file_location}"),
    @PropertySource(value = "classpath:app_versions.properties", ignoreResourceNotFound = true)
})
public class AppProperties {

    /**
     * Property key which defines the JNDI name of the datasource to use e.g. "java:/appds" 
     **/
    private static final String DATASOURCE_JNDI_NAME_PROPERTY = "app.datasource.jndi.name";
    /**
     * Property key which defines the databse vendor. Value must match the enum values defined in 
     * org.springframework.orm.jpa.vendor.Database. E.g. POSTGRESQL for postgreSQL, ORACLE for Oracle. 
     **/
    private static final String DATABASE_VENDOR_PROPERTY = "app.database.vendor";

    /** Property key which defines the application version number. */
    private static final String APPLICATION_VERSION_PROPERTY = "app.version";
    /** Property key which defines the application build date. */
    private static final String APPLICATION_DATE_PROPERTY = "app.date";
    /** Property key which defines the application build number. */
    private static final String APPLICATION_BUILD_PROPERTY = "app.build";
    
    /** Default string returned when a version property is not found. */
    private static final String DEFAULT_NA = "NA";

    /**Property key which defines the running environment**/
    private static final String ENVIRONMENT = "rangde.env";
    
    /**
     * Value of a system property used to indicate that the environment is Production/Local Dev. Typically passed as the value 
     * of the "rangde.env" property:
     *  -Drangde.env=PROD
     *  or -Drangde.env=DEV
     */
    public static final String ENV_PROD = "PROD";
    
    private static final String ENV_DEV = "DEV";
    
    /**
	 * One can also use Spring's PropertySourcesPlaceholderConfigurer to read
	 * these properties. Apparently you can either use PropertySourcesPlaceholderConfigurer, or autowire Environment.
	 * Both don't work. We went with the Environment approach instead of PropertySourcesPlaceholderConfigurer, as that was 
	 * the approach used in the legacy codebase from which this class was borrowed.
     */
    @Autowired
    private Environment env;

    /**
     * Returns the JNDI name of the datasource to be used by the application.
     * @see appInfraDatabaseConfig
     */
    public String datasourceJndiName() {
        return env.getProperty(DATASOURCE_JNDI_NAME_PROPERTY);
    }
    
    /**
     * Returns the database vendor identifier to be used by the JPA configuration.
     * @see appInfraDatabaseConfig
     */
    public String databaseVendor() {
        return env.getProperty(DATABASE_VENDOR_PROPERTY);
    }

    /**
     * Returns the version of the application. 
     */
    public String applicationVersion() {
        return env.getProperty(APPLICATION_VERSION_PROPERTY, DEFAULT_NA);
    }

    /**
     * Returns the build date of the application EAR. 
     */
    public String applicationDate() {
        return env.getProperty(APPLICATION_DATE_PROPERTY, DEFAULT_NA);
    }

    /**
     * Returns the build number of the application EAR. 
     */
    public String applicationBuildNo() {
        return env.getProperty(APPLICATION_BUILD_PROPERTY, DEFAULT_NA);
    }

    /**
     * Returns the value of the requested property key, or null if not found. 
     */
    public String getProperty(String propertyKey) {
        return env.getProperty(propertyKey);
    }

    /**
     * Method to check if the Environment is DEV i.e: local
     * @return true if Local false otherwise
     */
    public Boolean isEnvDev() {
        return (env.getProperty(ENVIRONMENT).equals(ENV_DEV));
    }
    
    /**
     * Method to check if the Environment is Production
     * @return true if production environment false otherwise
     */
    public Boolean isEnvProd() {
        return (env.getProperty(ENVIRONMENT).equals(ENV_PROD));
    }
}
