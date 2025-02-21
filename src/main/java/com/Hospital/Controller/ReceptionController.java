package com.Hospital.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Hospital.entity.appointmentform;
import com.Hospital.entity.tablets;
import com.Hospital.repositories.appointmentrepository;
import com.Hospital.repositories.tabletsrepositories;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/recption")
public class ReceptionController {

	@Autowired
	private appointmentrepository appointmentrepository;
	
	@Autowired
	private tabletsrepositories medicinesrepositories;
	
	@GetMapping("/recptionDashboard")
	public String recptionDashboard() {
		
		return "Recption/recptionDashboard";
	}

	@GetMapping("/listAppointment")
	public String BookAppointment(Model model) {
		
        List<appointmentform> todayAppointments = appointmentrepository.findTodayAppointments();
		
		model.addAttribute("todayAppointments",todayAppointments);
		
		return "Recption/BookApointment";
	}
	
	@PostMapping("/BookAppointment")
	public String postMethodName(@ModelAttribute appointmentform appointmentform) {
		appointmentform.setAppointmentStatus("Pending");
		appointmentrepository.save(appointmentform);
		return "redirect:/recption/listAppointment";
	}
	
	@PostMapping("/update-status")
    public ResponseEntity<?> updateAppointmentStatus(@RequestBody Map<String, String> requestData) {
        String status = requestData.get("status");
        int appointmentId = Integer.parseInt(requestData.get("appointmentId"));
        
        System.out.println("appointmentId : " + appointmentId);
        System.out.println("status : " + status);
        
        // Use the dynamic appointmentId
        appointmentform appointment = appointmentrepository.findbyappointmentid(appointmentId);
        appointment.setAppointmentStatus(status);
        appointmentrepository.save(appointment);
        
        return ResponseEntity.ok("Status updated successfully");
    }
	
	
	@GetMapping("/medicinesLists")
    public String medicinesList(Model model) {
    	 
    	List<tablets> medicineslist = medicinesrepositories.findAll();
    	
    	model.addAttribute("medicineslist",medicineslist);
    	return "Recption/medicinesList";
    }
    
    @PostMapping("/addmedicines")
    public String addmedicines(@ModelAttribute tablets tablets) {
    	
    	medicinesrepositories.save(tablets);
    	
    	return "redirect:/recption/medicinesLists";
    }
    
    @PostMapping("/{id}/editmedicines")
    public String editMedicines(@ModelAttribute tablets updatedTablet, @RequestParam("id") int id) {
        // Find the existing tablet by id
        List<tablets> foundTablets = medicinesrepositories.findtabletsbyid(id);
        
        if (!foundTablets.isEmpty()) {
            tablets existingTablet = foundTablets.get(0);
            // Update the fields
            existingTablet.setTabletName(updatedTablet.getTabletName());
            existingTablet.setMedicineCompanyName(updatedTablet.getMedicineCompanyName());
            existingTablet.setMedecineType(updatedTablet.getMedecineType());
            
            // Save the updated tablet
            medicinesrepositories.save(existingTablet);
        }
        
        // Redirect to the medicines list page after update
        return "redirect:/recption/medicinesLists";
    }
    
    
    @DeleteMapping("/{id}/deletemedicines")
    public ResponseEntity<Void> deletemedicine(@PathVariable("id") int id) {
        // Delete medicine using the repository (assuming deleteById is available)
        medicinesrepositories.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/getInvoice")
    public String GetBill() {
    	
    	return "Recption/GenrateMedicalBill";
    }
}
