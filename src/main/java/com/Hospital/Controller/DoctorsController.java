package com.Hospital.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Hospital.entity.appointmentform;
import com.Hospital.repositories.appointmentrepository;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {
	
	@Autowired
	private appointmentrepository appointmentrepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	
    	
        return "Doctors/dashboard"; // Ensure the view name matches your Thymeleaf template location
    }
    
    @GetMapping("/totalAppointment")
    public String totalAppointment(Model model ) {
    	
    	List<appointmentform> all = appointmentrepository.findAll();
    	
    	model.addAttribute("all",all);
        return "Doctors/totalappointment"; // Ensure the view name matches your Thymeleaf template location
    }
    
    @GetMapping("/todyasAppointment")
    public String todaysAppointment(Model model ) {
    	
    	List<appointmentform> todayAppointments = appointmentrepository.findTodayAppointments();
    	
    	model.addAttribute("todayAppointments",todayAppointments);
        return "Doctors/todyasAppointment"; // Ensure the view name matches your Thymeleaf template location
    }
    
    @GetMapping("/cancleAppointment")
    public String cancleAppointment(Model model ) {
    	
    	List<appointmentform> cancleAppointments = appointmentrepository.findCancleAppointments();
    	
    	model.addAttribute("cancleAppointments",cancleAppointments);
        return "Doctors/cancleAppointment"; // Ensure the view name matches your Thymeleaf template location
    }
}
