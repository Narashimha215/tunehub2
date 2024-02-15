package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Song;
import com.example.demo.entities.Users;
import com.example.demo.services.SongService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UsersController {
	
	@Autowired
	UsersService service;
	@Autowired
	SongService songSerivice;
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user){

		boolean userStatus = service.emailExists(user.getEmail());
		if(userStatus == false) {
			service.addUser(user);
			System.out.println("User Added");
		}
		else
		{
			System.out.println("User already exists");
		}
		return "home";
	}
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
		//@RequestBody  LoginData data
//		
//		System.out.println("call received");
//		
//		String email = data.getEmail();
//		String password = data.getPassword();
		
		if(service.validateUser(email, password)==true) {
			String role = service.getRole(email);
			session.setAttribute("email", email);
			if(role.equals("admin")) {
				return "adminHome";
			}
			else
			{
				Users user = service.getUser(email);
				boolean userStatus = user.isPremium();
				List<Song> songsList = songSerivice.fetctAllSongs();
				model.addAttribute("songs",songsList);
				
				model.addAttribute("isPremium",userStatus);
				return "customerHome";
			}
		}
		else
		{
			return "login";
		}
	}	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}