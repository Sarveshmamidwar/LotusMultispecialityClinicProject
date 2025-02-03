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
}
