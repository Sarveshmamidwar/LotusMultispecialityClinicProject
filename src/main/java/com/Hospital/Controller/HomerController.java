package com.Hospital.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Hospital.entity.Doctors;
import com.Hospital.entity.appointmentform;
import com.Hospital.repositories.DoctorsRepositories;
import com.Hospital.repositories.appointmentrepository;


@Controller
public class HomerController {
	
	@Autowired
	private appointmentrepository  appointmentrepository;

	@Autowired
	private DoctorsRepositories doctorsRepositories;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
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
	public String appointment(Model model) {
		 
		
		return "Home/appointment";
	}
	
	@PostMapping("/bookAppointment")
	public String BookAppointment(@ModelAttribute appointmentform appointmentform) {
		
		appointmentform.setAppointmentStatus("Pending");
		appointmentrepository.save(appointmentform);
		
		return "redirect:/appointment";
	}
	
	@GetMapping("/getsignUp")
	public String getSignUp() {
		
		
		return "Home/SignUp";
	}
	
	@PostMapping("/signUp")
	public String postSignup(@ModelAttribute Doctors doctors) {
		
		try {
			String encodedPassword = bCryptPasswordEncoder.encode(doctors.getPassword());
			System.out.println("xzdfdfsfbgfgb");
		       doctors.setPassword(encodedPassword);
		       doctors.setDoctorId(0);
			    doctorsRepositories.save(doctors);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/getsignUp";
	}
	
	@GetMapping("/getsignin")
	public String getSignin() {

		return "Home/Signin";
	}
	
	
}
