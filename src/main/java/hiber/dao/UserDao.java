package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
   void add(User user);
   List<User> listUsers();

   void createUsersTable();

   void dropUsersTable();

   void saveUser(String name, String lastName, byte age);

   void removeUserById(long id);

   List<User> getAllUsers();

   void cleanUsersTable();

}
