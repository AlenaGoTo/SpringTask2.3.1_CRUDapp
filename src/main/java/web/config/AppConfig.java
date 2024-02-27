package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class AppConfig {

   @Autowired
   private Environment env; // представляет окружение, в котором приложение запущено (интерфейс для работы с properties)

   @Bean
   public DataSource dataSource(){
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("hibernate.connection.driver_class"));
      dataSource.setUrl(env.getProperty("hibernate.connection.url"));
      dataSource.setUsername(env.getProperty("hibernate.connection.username"));
      dataSource.setPassword(env.getProperty("hibernate.connection.password"));
      return dataSource;
   }

   @Bean
   public Properties hibernateProperties() {
      Properties props = new Properties();
      props.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
      return props;
   }

   @Bean
   // настройка EntityManagerFactory указываем источник данных и свойства JPA
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
      emfb.setPackagesToScan("web.model");
      emfb.setDataSource(dataSource());
      emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      emfb.setJpaProperties(hibernateProperties());
      return emfb;
   }

   @Bean
   // настройка TransactionManager
   public JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
      return transactionManager;
   }

   @Bean
   // настройка ExceptionTranslation
   // чтобы Spring при инициализации контекста приложения осуществил автосвязывание полей бинов с менеджером сущностей
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
      return new PersistenceExceptionTranslationPostProcessor();
   }

}
