package com.Hospital.Controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Hospital.entity.appointmentform;
import com.Hospital.repositories.appointmentrepository;


@Controller
public class HomerController {
	
	@Autowired
	private appointmentrepository  appointmentrepository;

	
	@GetMapping("/")
	public String Home() {
		
		return "Home/Home";
	}
	
	@GetMapping("/about")
	public String about() {
		
		return "Home/about";
	}
	
	@GetMapping("/ouserService")
	public String ouerService() {
		
		return "Home/services";
	}
	
	
	@GetMapping("/ouserDoctors")
	public String ouerSDoctors() {
		
		return "Home/ourDoctors";
	}
	
	@GetMapping("/contacts")
	public String contact() {
		
		return "Home/contact";
	}
	
	@GetMapping("/appointment")
	public String appointment() {
		
		return "Home/appointment";
	}
	
	@PostMapping("/bookAppointment")
	public String BookAppointment(@ModelAttribute appointmentform appointmentform) {
		
		
		appointmentrepository.save(appointmentform);
		
		return "redirect:/appointment";
	}
	
}
