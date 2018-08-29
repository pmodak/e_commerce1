package com.evolutionco.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.*;

@Configuration
@EnableWebMvc
@ComponentScan({"com.evolutionco.*"})
@EnableTransactionManagement
//load environment
/*@PropertySource(value="classpath:ds-hibernate-cfg.properties")*/
public class JavaConfig {
	@Autowired
	// The Environment class serves as the property holder
    // and stores all the properties loaded by the @PropertySource
	//private Environment env;
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		System.out.println("messagesource");
		ResourceBundleMessageSource source=new ResourceBundleMessageSource();
		source.setBasenames(new String[] {"messages/validator"});
		source.setDefaultEncoding("UTF-8");
		return source;
	}
	@Bean
	public InternalResourceViewResolver viewResolver() {
		System.out.println("viewresolver");
		InternalResourceViewResolver resolver=new InternalResourceViewResolver();
		/*resolver.setViewClass(JstlView.class);*/
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	@Bean(name="dataSource")
	public DataSource getDataSource() {
		System.out.println("datasource");
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		//see:hibernate-cfg.properties
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
		dataSource.setUsername("root");
		dataSource.setPassword("evolutionco");
		System.out.println("getDataSource:"+dataSource);
		return dataSource;
	}
	@Bean
	public SessionFactory factoryBean(DataSource ds) throws Exception {
		System.out.println("session");
		//see hibernate-cfg.properties
		Properties prop=new Properties();
		prop.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
		prop.put("hibernate.show_sql","true");
		prop.put("hibernate.hbm2ddl.auto","update");
		prop.put("current_session_context_class","thread");
		LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
		sessionFactory.setDataSource(ds);
		//package contain entity class
		sessionFactory.setPackagesToScan(new String[] {"com.evolutionco.entity"});
		sessionFactory.setHibernateProperties(prop);
		sessionFactory.afterPropertiesSet();
		SessionFactory sf=sessionFactory.getObject();
		System.out.println("sessionfactory:"+sf);
		return sf;
	}
	@Bean
	public HibernateTemplate hibernateTemplate(SessionFactory sf) {
		System.out.println("template");
		HibernateTemplate template=new HibernateTemplate(sf);
		return template;
	}
	@Bean
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sf) {
		System.out.println("manager");
		HibernateTransactionManager manager=new HibernateTransactionManager(sf);
		return manager;
	}

	//Config for upload
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		System.out.println("resolver");
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
		//set max size for upload
		commonsMultipartResolver.setMaxUploadSize(400250);
		return commonsMultipartResolver;
}
	}
