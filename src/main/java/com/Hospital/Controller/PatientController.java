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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String patientDashboard(Principal principal, Model model) {
	    try {
	        if (principal == null) {
	            return "redirect:/login"; 
	        }	       
	        Patient patient = patientrepository.findByemail(principal.getName());
	        if (patient == null) {
	            model.addAttribute("errorMessage", "Patient record not found.");
	            return "error";  
	        }	     
	        List<appointmentform> appointments = appointmentrepository.findAppointmentsbypatientid(patient.getId());
	        model.addAttribute("userdetails", patient);
	        model.addAttribute("appointmentlist", appointments);
	        model.addAttribute("PatientName", patient.getName());
	        return "Patient/patdashboard";

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("errorMessage", "Something went wrong while loading dashboard.");
	        return "error"; 
	    }
	}

	
	@PostMapping("/BookAppointment")
	public String bookAppointment(@ModelAttribute appointmentform appointmentform, Principal principal, RedirectAttributes redirectAttributes) {
	    try {
	        Patient patient = patientrepository.findByemail(principal.getName());
	        if (patient == null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Patient not found. Please log in again.");
	            return "redirect:/login";
	        }
	        if (appointmentform == null || appointmentform.getDoctor() == null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Invalid appointment details.");
	            return "redirect:/patient/patDashboard";
	        }
	        appointmentform.setPatientid(patient.getId());
	        appointmentform.setAppointmentStatus("Pending");
	        appointmentrepository.save(appointmentform);
	        redirectAttributes.addFlashAttribute("successMessage", "Appointment booked successfully!");
	        return "redirect:/patient/patDashboard";
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong while booking your appointment. Please try again.");
	        return "redirect:/patient/patDashboard";
	    }
	}

	
	@GetMapping("/getreports")
	public String GetReports(Model model, Principal principal) {
	    try {
	        if (principal == null || principal.getName() == null) {
	            model.addAttribute("errorMessage", "User is not logged in.");
	            return "error"; 
	        }
	        Patient patient = patientrepository.findByemail(principal.getName());
	        if (patient == null) {
	            model.addAttribute("errorMessage", "Patient not found.");
	            return "error";
	        }
	        List<Reports> reports = reportsrepository.findreportsbypatientid(patient.getId());
	        if (reports == null || reports.isEmpty()) {
	            model.addAttribute("message", "No reports found.");
	            model.addAttribute("userdetails", patient);
	            model.addAttribute("PatientName", patient.getName());
	            return "Patient/Reports"; 
	        }
	        List<String> reportBase64List = reports.stream()
	            .map(report -> Base64.getEncoder().encodeToString(report.getReport()))
	            .collect(Collectors.toList());

	        model.addAttribute("userdetails", patient);
	        model.addAttribute("reports", reportBase64List);
	        model.addAttribute("PatientName", patient.getName());
	        return "Patient/Reports";

	    } catch (Exception e) {
	        e.printStackTrace(); 
	        model.addAttribute("errorMessage", "Something went wrong while fetching reports.");
	        return "error"; 
	    }
	}

	
	
}
