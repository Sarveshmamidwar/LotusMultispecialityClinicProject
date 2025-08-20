package com.Hospital.Controller;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Hospital.entity.Patient;
import com.Hospital.entity.Reports;
import com.Hospital.entity.appointmentform;
import com.Hospital.repositories.appointmentrepository;
import com.Hospital.repositories.patientrepository;
import com.Hospital.repositories.reportsrepository;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private patientrepository patientrepository;
	
	@Autowired
	private appointmentrepository appointmentrepository;
	
	@Autowired
	private reportsrepository reportsrepository;
	
	@GetMapping("/patDashboard")
	public String patientDashboard(Principal principal,Model model ) {
		
		Patient patient = patientrepository.findByemail(principal.getName());
		
		List<appointmentform> appointmentsbypatientid = appointmentrepository.findAppointmentsbypatientid(patient.getId());
		
		model.addAttribute("userdetails",patient);
		model.addAttribute("appointmentist",appointmentsbypatientid);
		 model.addAttribute("PatientName",patient.getName());
		
		return "Patient/patdashboard";
	}
	
	@PostMapping("/BookAppointment")
	public String bookAppointment(@ModelAttribute appointmentform appointmentform,Principal principal) {
		Patient patient = patientrepository.findByemail(principal.getName());
		
		appointmentform.setPatientid(patient.getId());
		appointmentform.setAppointmentStatus("Pending");
		appointmentrepository.save(appointmentform);
		return "redirect:/patient/patDashboard";
	}
	
	@GetMapping("/getreports")
	public String GetReports(Model model, Principal principal) {
	    Patient patient = patientrepository.findByemail(principal.getName());
	    
	    List<Reports> reports = reportsrepository.findreportsbypatientid(patient.getId());
	    
	    // Convert byte[] to Base64
	    List<String> reportBase64List = reports.stream()
	        .map(report -> Base64.getEncoder().encodeToString(report.getReport())) // Convert byte[] to Base64 String
	        .collect(Collectors.toList());

	    model.addAttribute("userdetails",patient);
	    model.addAttribute("reports", reportBase64List);
	    model.addAttribute("PatientName",patient.getName());
	    return "Patient/Reports"; // Ensure this matches your template filename
	}
	
	
}
