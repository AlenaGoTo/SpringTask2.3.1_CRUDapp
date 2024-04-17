package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/users_s")
public class OneFormController {
	@Autowired
	private UserService userService;
	private User user;

	//страничка со всеми юзерами из БД
	@GetMapping()
	public String printUsers(ModelMap model, @ModelAttribute("user") User user){
		model.addAttribute("users", userService.getAllUsers()); // передача данных в html
		return "indexOnly";
	}

	// удаляем юзера на страничке
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable("id") long id){
		userService.removeUserById(id);
		return "redirect:/users_s";
	}

	// Действие по кнопке добавления юзера
	@PostMapping()
	public String addUser(@RequestParam("firstName") String name,
						  @RequestParam("lastName") String lastname,
						  @RequestParam("age") byte age)
	{
		user = new User(name, lastname, age);
		userService.saveUser(user);
		return "redirect:/users_s";
	}

	// Действие по кнопке правки юзера на странице edit
	@PatchMapping()
	public String messageCenterHome(@RequestParam("firstName") String name,
									@RequestParam("lastName") String lastname,
									@RequestParam("age") byte age,
									@RequestParam(value = "id") long id)
	{
		userService.updateUser(id, name, lastname, age);
		return "redirect:/users_s";
	}


	/*//страничка с одним юзером
	@GetMapping(value = "/{id}")
	public String getUser(@PathVariable("id") long id, ModelMap model){
		model.addAttribute("user", userService.getUserById(id)); // передача данных в html
		return "show";
	}*/



	/*//страница для правки юзера
	@GetMapping(value = "/{id}/edit")
	public String editUser(@PathVariable("id") long id, ModelMap model){
		model.addAttribute("user", userService.getUserById(id)); // передача данных в html
		return "edit";
	}*/



	/*//страничка с добавлением юзера
	@GetMapping(value = "/new")
	public String newUser(@ModelAttribute("user") User user) {
		return "new";
	}*/

	
}