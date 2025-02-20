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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Hospital.entity.appointmentform;
import com.Hospital.entity.tablets;
import com.Hospital.repositories.appointmentrepository;
import com.Hospital.repositories.tabletsrepositories;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {
	
	@Autowired
	private appointmentrepository appointmentrepository;
	
	@Autowired
	private tabletsrepositories medicinesrepositories;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	
    	int findtotalcount = appointmentrepository.findtotalcount();
    	
    	int todayAppointmentsCount = appointmentrepository.findTodayAppointmentsCount();
    	int cancleAppointmentsCount = appointmentrepository.findCancleAppointmentsCount();
    	int tomorrowAppointmentsCount = appointmentrepository.findTomorrowAppointmentsCount();
    	
    	model.addAttribute("totalCount",findtotalcount);
    	model.addAttribute("todayAppointmentsCount",todayAppointmentsCount);
    	model.addAttribute("cancleAppointmentsCount",cancleAppointmentsCount);
    	model.addAttribute("tomorrowAppointmentsCount",tomorrowAppointmentsCount);
        return "Doctors/dashboard"; // Ensure the view name matches your Thymeleaf template location
    }
    
    @GetMapping("/totalAppointment")
    public String totalAppointment(Model model ) {
    	
    	List<appointmentform> all = appointmentrepository.findAll();
    	
    	
    	int count = all.size();
    	
    	
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
    
    @GetMapping("/tommorowAppointment")
    public String tommorowAppointment(Model model ) {
    	
    	List<appointmentform> tommorowAppointments = appointmentrepository.findTomorrowAppointments();
    	
    	model.addAttribute("tommorowAppointments",tommorowAppointments);
        return "Doctors/tommorowAppointment"; // Ensure the view name matches your Thymeleaf template location
    }
    
    @GetMapping("/appointmentalert")
    @ResponseBody
    public List<appointmentform> todaysAppointmentAlert() {
        List<appointmentform> todayAppointmentswithstatus = appointmentrepository.findTodayAppointmentswithstatus();
        //System.out.println("Total appointments today: " + todayAppointmentswithstatus.size());
        return todayAppointmentswithstatus;
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


    @GetMapping("/Prescription")
    public String Prescription () {
    	
    	return"Doctors/prescription";
    }

    @GetMapping("/search-tablets")
    @ResponseBody
    public List<tablets> searchTablets(@RequestParam String term) {
    	
    	List<tablets> findtabletsbyname = medicinesrepositories.findtabletsbyname(term);
        return findtabletsbyname;
    }
    
    @GetMapping("/medicinesLists")
    public String medicinesList(Model model) {
    	 
    	List<tablets> medicineslist = medicinesrepositories.findAll();
    	
    	model.addAttribute("medicineslist",medicineslist);
    	return "Doctors/medicinesList";
    }
    
    @PostMapping("/addmedicines")
    public String addmedicines(@ModelAttribute tablets tablets) {
    	
    	medicinesrepositories.save(tablets);
    	
    	return "redirect:/doctors/medicinesLists";
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
        return "redirect:/doctors/medicinesLists";
    }
    
    
    @DeleteMapping("/{id}/deletemedicines")
    public ResponseEntity<Void> deletemedicine(@PathVariable("id") int id) {
        // Delete medicine using the repository (assuming deleteById is available)
        medicinesrepositories.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
