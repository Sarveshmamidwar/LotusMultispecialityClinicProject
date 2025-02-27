package com.Hospital.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Hospital.entity.Patient;
import com.Hospital.entity.appointmentform;
import com.Hospital.repositories.appointmentrepository;
import com.Hospital.repositories.patientrepository;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	patientrepository patientrepository;
	
	@Autowired
	private appointmentrepository appointmentrepository;
	
	@GetMapping("/patDashboard")
	public String patientDashboard(Principal principal,Model model ) {
		
		Patient patient = patientrepository.findByemail(principal.getName());
		
		List<appointmentform> appointmentsbypatientid = appointmentrepository.findAppointmentsbypatientid(patient.getId());
		
		model.addAttribute("appointmentist",appointmentsbypatientid);
		 model.addAttribute("PatientName",patient.getName());
		
		return "Patient/patdashboard";
	}
	
	@PostMapping("/BookAppointment")
	public String bookAppointment(@ModelAttribute appointmentform appointmentform,Principal principal) {
		Patient patient = patientrepository.findByemail(principal.getName());
		
		appointmentform.setPatientid(patient.getId());
		appointmentrepository.save(appointmentform);
		return "redirect:/patient/patDashboard";
	}
}
