package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public void saveUser(String name, String lastName, byte age) {
      Transaction transaction = null;
      try (Session session = sessionFactory.openSession()) {
         transaction = session.beginTransaction();
         session.createSQLQuery("INSERT INTO USERS_WEB (NAME,LASTNAME,AGE) "
                         + "VALUES ('" +name+ "','" +lastName+ "','" +age+ "')")
                 .executeUpdate();
         transaction.commit();
      } catch (Exception ex) {
         if (transaction != null) transaction.rollback();
         ex.printStackTrace();
      }
      System.out.printf("User с именем – %s добавлен в базу данных\n", name);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> getAllUsers() {
      Transaction transaction = null;
      List<User> usersList = new ArrayList<>();
      try (Session session = sessionFactory.openSession()) {
         transaction = session.beginTransaction();
         usersList = session.createSQLQuery("SELECT ID, NAME, LASTNAME, AGE  FROM USERS_WEB").addEntity(User.class).list();
         transaction.commit();
         for (User user : usersList) {
            System.out.println(user.toString());
         }
      } catch (Exception ex) {
         if (transaction != null && transaction.isActive()) transaction.rollback();
         ex.printStackTrace();
      }
      return usersList;
   }

   @Override
   public void createUsersTable() {
      Transaction transaction = null;
      try (Session session = sessionFactory.openSession()) {
         transaction = session.beginTransaction();
         session.createSQLQuery("CREATE TABLE IF NOT EXISTS USERS_WEB " +
                         "(ID            SERIAL PRIMARY KEY     NOT NULL," +
                         " NAME          VARCHAR(255)    NOT NULL, " +
                         " LASTNAME      VARCHAR(255)    NOT NULL, " +
                         " AGE           INT     NOT NULL)")
                 .executeUpdate();
         transaction.commit();
      } catch (Exception ex) {
         if (transaction != null) transaction.rollback();
         ex.printStackTrace();
      }
   }

   @Override
   public void dropUsersTable() {
      Transaction transaction = null;
      try (Session session = sessionFactory.openSession()) {
         transaction = session.beginTransaction();
         session.createSQLQuery("DROP TABLE IF EXISTS USERS_WEB ")
                 .executeUpdate();
         transaction.commit();
      } catch (Exception ex) {
         if (transaction != null) transaction.rollback();
         ex.printStackTrace();
      }
   }


   @Override
   public void removeUserById(long id) {
      Transaction transaction = null;
      try (Session session = sessionFactory.openSession()) {
         transaction = session.beginTransaction();
         Query<User> query = session.createSQLQuery("DELETE from USERS_WEB where ID = '" +id+ "';");
         System.out.println("Создан commit");
         if (query.executeUpdate() == 0) {
            System.out.printf("User с id – %s не найден\n", id);
         }
         transaction.commit();
      } catch (Exception ex) {
         if (transaction != null) transaction.rollback();
         ex.printStackTrace();
      }
   }

   @Override
   public void cleanUsersTable() {
      Transaction transaction = null;
      try (Session session = sessionFactory.openSession()) {
         transaction = session.beginTransaction();
         session.createSQLQuery("DELETE from USERS_WEB").executeUpdate();
         transaction.commit();
      } catch (Exception ex) {
         if (transaction != null) transaction.rollback();
         ex.printStackTrace();
      }
   }

}
