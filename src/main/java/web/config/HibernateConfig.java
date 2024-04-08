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
@EnableTransactionManagement // Включает возможность управления транзакциями Spring на основе аннотаций
@ComponentScan(value = "web")
public class HibernateConfig {

   @Autowired
   private Environment env; // интерфейс для работы с Property

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
      emfb.setPackagesToScan("web.model"); // сканирование для поиска Entity
      emfb.setDataSource(dataSource());
      emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); // поставщик реализации JPA
      emfb.setJpaProperties(hibernateProperties()); // специфичные для JpaVendor-а свойства
      return emfb;
   }

   @Bean
   // управление транзакциями (перехватывает методы @Transactional)
   // можете изменить способ управления транзакциями, просто изменив конфигурацию
   public JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
      return transactionManager;
   }

   @Bean
   // настройка ExceptionTranslation (не обязательна т.к. LocalContainerEntityManagerFactoryBean его реализует)
   // PersistenceExceptionTranslationPostProcessor используется для преобразования ошибок @Repository
   // в объекты Spring DataAccessException (не зависимо от поставщика Jpa)
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
      return new PersistenceExceptionTranslationPostProcessor();
   }

}
