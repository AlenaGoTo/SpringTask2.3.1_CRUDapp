package hiber;

import hiber.config.AppConfig;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", (byte) 22));
      userService.add(new User("User2", "Lastname2", (byte) 32));
      userService.add(new User("User3", "Lastname3", (byte) 44));
      userService.add(new User("User4", "Lastname4", (byte) 18));

      //userService.createUsersTable();
      userService.saveUser("Lous","Black", (byte) 31);
      userService.saveUser("Mini","Mouse", (byte) 7);
      userService.getAllUsers();
      userService.removeUserById(1);
      //userService.getAllUsers();
      //userService.cleanUsersTable();
      //userService.dropUsersTable();

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println(user.toString());
      }

      context.close();
   }
}
