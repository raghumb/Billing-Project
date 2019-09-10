package snmaddula.random.poc.app.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "snmaddula.random.poc.app.repo", transactionManagerRef = "tm", entityManagerFactoryRef = "em")
public class DataSourceContext {

	@Bean
	public DataSource ds() throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass("org.postgresql.Driver");
		ds.setUser("admin");
		ds.setPassword("admin");
		ds.setJdbcUrl("jdbc:postgresql://localhost/alpha");
		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean em(DataSource ds) throws PropertyVetoException {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ds);
		em.setPackagesToScan("snmaddula.random.poc.app.entity");
		em.setPersistenceProvider(new HibernatePersistenceProvider());
		em.setPersistenceUnitName("alpha");
		em.setJpaDialect(new HibernateJpaDialect());
		return em;
	}

	@Bean
	public JpaTransactionManager tm(LocalContainerEntityManagerFactoryBean em) throws PropertyVetoException {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(em.getObject());
		return transactionManager;
	}

}
