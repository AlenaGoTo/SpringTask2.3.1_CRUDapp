package web;

import web.config.AppConfig;
import web.model.User;
import web.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      //userService.createUsersTable();
      //userService.saveUser(new User("User1", "Lastname1", (byte) 22));
      //userService.saveUser(new User("User2", "Lastname2", (byte) 32));
      //userService.saveUser(new User("User3", "Lastname3", (byte) 44));
      userService.saveUser(new User("User4", "Lastname4", (byte) 18));
      //System.out.println(userService.getUserById(1).toString());
      //userService.removeUserById(4);
      //userService.getAllUsers();
      //userService.cleanUsersTable();
      //userService.dropUsersTable();

      /*List<User> users = userService.getAllUsers();
      for (User user : users) {
         System.out.println(user.toString());
      }*/

   }
}
