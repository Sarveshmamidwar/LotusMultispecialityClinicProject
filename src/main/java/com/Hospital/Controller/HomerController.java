package com.Hospital.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomerController {

	
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
}
