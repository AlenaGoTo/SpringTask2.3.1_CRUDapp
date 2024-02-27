package web.service;

import web.dao.UserDao;
import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void saveUser(User user) {
      userDao.saveUser(user);
   }

   @Transactional
   @Override
   public void removeUserById(long id) {
      userDao.removeUserById(id);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> getAllUsers() {
      return userDao.getAllUsers();
   }

   @Transactional
   @Override
   public void updateUser(long id, String name, String lastname, byte age) {
      userDao.updateUser(id, name, lastname, age);
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserById(long id) {
      return userDao.getUserById(id);
   }

   @Transactional
   @Override
   public void createUsersTable() {
      userDao.createUsersTable();
   }

   @Transactional
   @Override
   public void dropUsersTable() {
      userDao.dropUsersTable();
   }

   @Transactional
   @Override
   public void cleanUsersTable() {
      userDao.cleanUsersTable();
   }
}
