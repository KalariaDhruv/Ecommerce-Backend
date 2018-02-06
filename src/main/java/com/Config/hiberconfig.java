package com.Config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.model.Authorities;
import com.model.BillingAddress;
import com.model.Cart;
import com.model.CartItem;
import com.model.Customer;
import com.model.CustomerOrder;
import com.model.Product;
import com.model.ShippingAddress;
import com.model.User;

@Configuration
@EnableTransactionManagement
@ComponentScan("com")
public class hiberconfig {

	@Autowired
	@Bean(name = "dataSource")
	public DataSource geth2Data()
	{
		DriverManagerDataSource dsource = new DriverManagerDataSource();
		dsource.setDriverClassName("org.h2.Driver");
		dsource.setUrl("jdbc:h2:tcp://localhost/~/dtproject1");
		dsource.setUsername("sa");
		dsource.setPassword("");
		System.out.println("H2 Connected");
		return dsource;
	}
	
	private Properties getHiber()
	{
		Properties p = new Properties();
		p.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		p.put("hibernate.hbm2ddl.auto", "update");
		p.put("hibernate.show_sql", "true");
	
		System.out.println(" Data Tables created in H2");
		return p;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getHiberSession(DataSource dataSource)
	{	
		System.out.println("in session factory");
		
		LocalSessionFactoryBuilder localSessionFacBuilder = new LocalSessionFactoryBuilder(dataSource);
		localSessionFacBuilder.addProperties(getHiber());
		localSessionFacBuilder.addAnnotatedClass(User.class);
		localSessionFacBuilder.addAnnotatedClass(Customer.class);
		localSessionFacBuilder.addAnnotatedClass(Authorities.class);
		localSessionFacBuilder.addAnnotatedClass(Product.class);
		localSessionFacBuilder.addAnnotatedClass(Cart.class);
		localSessionFacBuilder.addAnnotatedClass(CartItem.class);
		localSessionFacBuilder.addAnnotatedClass(ShippingAddress.class);
		localSessionFacBuilder.addAnnotatedClass(BillingAddress.class);
		localSessionFacBuilder.addAnnotatedClass(CustomerOrder.class);
		
		
		System.out.println("Session Factory Object Created");
		return localSessionFacBuilder.buildSessionFactory();
    }
	
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getHibernateTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager hibernateTranMgr = new HibernateTransactionManager(sessionFactory);
		return hibernateTranMgr;
	
	}

}
