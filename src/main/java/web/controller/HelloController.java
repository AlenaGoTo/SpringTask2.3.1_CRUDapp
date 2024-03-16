package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping(value = "/users")
public class HelloController {

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "index";
	}

	@Autowired
	private UserService userService;
	private User user;

	//страничка со всеми юзерами из БД
	@GetMapping(value = "/index")
	public String printUsers(@RequestParam(required=false) Integer count, ModelMap model){
		model.addAttribute("messages", userService.getAllUsers()); // передача данных в html
		return "index";
	}

	//страничка с одним юзером
	@GetMapping(value = "/{id}")
	public String getUser(@PathVariable("id") long id, ModelMap model){
		model.addAttribute("user", userService.getUserById(id)); // передача данных в html
		return "show";
	}

	// удаляем юзера на страничке show
	@DeleteMapping()
	public String deleteUser(@PathVariable("id") long id){
		userService.removeUserById(id);
		return "redirect:/users";
	}

	//страница для правки юзера
	@GetMapping(value = "/{id}/edit")
	public String editUser(@PathVariable("id") long id, ModelMap model){
		model.addAttribute("user", userService.getUserById(id)); // передача данных в html
		return "edit";
	}

	// Действие по кнопке правки юзера на странице edit
	@PatchMapping()
	public String editUser(@RequestParam("name") String name,
						   @RequestParam("lastname") String lastname,
						   @RequestParam("age") byte age,
						   @RequestParam("id") long id )
	{
		userService.updateUser(id, name, lastname, age);
		return "redirect:/users";
	}

	//страничка с добавлением юзера
	@GetMapping(value = "/new")
	public String newUser(@ModelAttribute("user") User user) {
		return "new";
	}

	// Действие по кнопке добавления юзера на страничке new
	@PostMapping()
	public String addUser(@RequestParam("name") String name,
						 @RequestParam("lastname") String lastname,
						 @RequestParam("age") byte age)
	{
		user = new User(name, lastname, age);
		userService.saveUser(user);
		return "redirect:/users";
	}
	
}