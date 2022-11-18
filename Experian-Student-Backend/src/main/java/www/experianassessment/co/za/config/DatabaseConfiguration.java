package www.experianassessment.co.za.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "experianStudentApplicationEntityManagerFactory", transactionManagerRef = "experianStudentApplicationTransactionManager", basePackages = {
		"www.experianassessment.co.za.repository" })
public class DatabaseConfiguration {

	@Autowired
	Environment env;

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties experianStudentApplicationProperties() {
		return new DataSourceProperties();
	}
	
	@Primary
	@Bean
	public DataSource experianStudentApplicationDataSource() {
		DataSourceProperties experianInfo = experianStudentApplicationProperties();
		return DataSourceBuilder.create().driverClassName(experianInfo.getDriverClassName()).url(experianInfo.getUrl())
				.username(experianInfo.getUsername()).password(experianInfo.getPassword()).build();
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager experianStudentApplicationTransactionManager() {
		EntityManagerFactory factory = experianStudentApplicationEntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}
	
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean experianStudentApplicationEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(experianStudentApplicationDataSource());
		factory.setPackagesToScan(new String[] { "www.experianassessment.co.za.model" });
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put("spring.jpa.properties.hibernate.dialect",env.getProperty("spring.jpa.properties.hibernate.dialect"));
		jpaProperties.put("spring.jpa.hibernate.ddl-auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("spring.jpa.properties.hibernate.id.new_generator_mappings",env.getProperty("spring.jpa.properties.hibernate.id.new_generator_mappings"));
		jpaProperties.put("spring.jpa.hibernate.naming.implicit-strategy",env.getProperty("spring.jpa.hibernate.naming.implicit-strategy"));
		jpaProperties.put("spring.jpa.hibernate.naming.physical-strategy",env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaProperties.put("spring.jpa.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);
		return factory;
	}
	

}
