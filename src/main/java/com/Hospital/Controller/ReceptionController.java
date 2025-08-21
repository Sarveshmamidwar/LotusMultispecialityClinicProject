package com.Hospital.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.Hospital.entity.Doctors;
import com.Hospital.entity.MedicalBills;
import com.Hospital.entity.Patient;
import com.Hospital.entity.Reports;
import com.Hospital.entity.appointmentform;
import com.Hospital.entity.tablets;
import com.Hospital.repositories.DoctorsRepositories;
import com.Hospital.repositories.MedicalBillrepository;
import com.Hospital.repositories.appointmentrepository;
import com.Hospital.repositories.patientrepository;
import com.Hospital.repositories.reportsrepository;
import com.Hospital.repositories.tabletsrepositories;


@Controller
@RequestMapping("/recption")
public class ReceptionController {

	@Autowired
	private appointmentrepository appointmentrepository;
	
	@Autowired
	private tabletsrepositories medicinesrepositories;
	
	@Autowired
	private reportsrepository reportsrepository;
	
	@Autowired
	private patientrepository patientrepository;
	
	@Autowired
	private DoctorsRepositories doctorsRepositories;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private MedicalBillrepository medicalBillrepository;
	
	@GetMapping("/recptionDashboard")
	public String recptionDashboard(Model model,Principal principal) {
		
int findtotalcount = appointmentrepository.findtotalcount();
    	
    	int todayAppointmentsCount = appointmentrepository.findTodayAppointmentsCount();
    	int cancleAppointmentsCount = appointmentrepository.findCancleAppointmentsCount();
    	int tomorrowAppointmentsCount = appointmentrepository.findTomorrowAppointmentsCount();
    	int rutineApointmentCount = appointmentrepository.findRutineAppointmentsCount();
    	int DoneAppointmentCount = appointmentrepository.findDoneAppointmentsCount();
    	int OnlinePaymentCount = appointmentrepository.findOnlinePaymentCount();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("totalCount",findtotalcount);
    	model.addAttribute("todayAppointmentsCount",todayAppointmentsCount);
    	model.addAttribute("cancleAppointmentsCount",cancleAppointmentsCount);
    	model.addAttribute("tomorrowAppointmentsCount",tomorrowAppointmentsCount);
    	model.addAttribute("rutineApointmentCount",rutineApointmentCount);
    	model.addAttribute("DoneAppointmentCount",DoneAppointmentCount);
    	model.addAttribute("OnlinePaymentCount",OnlinePaymentCount);
		
		return "Recption/recptionDashboard";
	}
	
	 @GetMapping("/totalAppointment")
	    public String totalAppointment(Model model,Principal principal ) {
	    	
	    	List<appointmentform> all = appointmentrepository.findAll();
	    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	int count = all.size();
	    	Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	
	    	model.addAttribute("userdetails",userdetail);
	    	
	    	model.addAttribute("username",userdetails.getName());
	    	model.addAttribute("all",all);
	        return "Recption/totalappointment"; // Ensure the view name matches your Thymeleaf template location
	    }
	    
	    @GetMapping("/todyasAppointment")
	    public String todaysAppointment(Model model,Principal principal ) {
	    	
	    	List<appointmentform> todayAppointments = appointmentrepository.findTodayAppointments();
	    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	
	    	model.addAttribute("userdetails",userdetail);
	    	model.addAttribute("username",userdetails.getName());
	    	model.addAttribute("todayAppointments",todayAppointments);
	        return "Recption/todyasAppointment"; // Ensure the view name matches your Thymeleaf template location
	    }
	    
	    @GetMapping("/cancleAppointment")
	    public String cancleAppointment(Model model ,Principal principal) {
	    	
	    	List<appointmentform> cancleAppointments = appointmentrepository.findCancleAppointments();
	    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	
	    	model.addAttribute("userdetails",userdetail);
	    	model.addAttribute("username",userdetails.getName());
	    	model.addAttribute("cancleAppointments",cancleAppointments);
	        return "Recption/cancleAppointment"; // Ensure the view name matches your Thymeleaf template location
	    }
	    
	    @GetMapping("/tommorowAppointment")
	    public String tommorowAppointment(Model model,Principal principal ) {
	    	
	    	List<appointmentform> tommorowAppointments = appointmentrepository.findTomorrowAppointments();
	    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
	    	
	    	
	    	model.addAttribute("userdetails",userdetail);
	    	model.addAttribute("username",userdetails.getName());
	    	model.addAttribute("tommorowAppointments",tommorowAppointments);
	        return "Recption/tommorowAppointment"; // Ensure the view name matches your Thymeleaf template location
	    }

	@GetMapping("/listAppointment")
	public String BookAppointment(Model model,Principal principal) {
		
        List<appointmentform> todayAppointments = appointmentrepository.findTodayAppointments();
        Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
        
        Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
    	
    	
    	model.addAttribute("userdetails",userdetail);
    	model.addAttribute("username",userdetails.getName());
		model.addAttribute("todayAppointments",todayAppointments);
		
		return "Recption/BookApointment";
	}
	
	@PostMapping("/BookAppointment")
	public String postMethodName(@ModelAttribute appointmentform appointmentform) {
	    try {
	        appointmentform.setAppointmentStatus("Pending");
	        appointmentrepository.save(appointmentform);
	        return "redirect:/recption/listAppointment";
	    } catch (Exception e) {
	        // Log the error
	        e.printStackTrace();
	        // Redirect to an error page or fallback
	        return "redirect:/recption/listAppointment";
	    }
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
    public String medicinesList(Model model,Principal principal) {
    	 
    	List<tablets> medicineslist = medicinesrepositories.findAll();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
        Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
    	
    	
    	model.addAttribute("userdetails",userdetail);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("medicineslist",medicineslist);
    	return "Recption/medicinesList";
    }
    
	@PostMapping("/addmedicines")
	public String addmedicines(@ModelAttribute tablets tablets) {
	    try {
	        if (tablets == null) {
	            throw new IllegalArgumentException("Medicine data cannot be null!");
	        }

	        medicinesrepositories.save(tablets);
	        return "redirect:/recption/medicinesLists";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/recption/medicinesListsr"; // fallback page
	    }
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
    
    @GetMapping("/getInvoice/{name}")
    public String GetBill(@PathVariable("name") String name,Model model,Principal principal) {
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
        Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
    	
    	
    	model.addAttribute("userdetails",userdetail);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("name",name);
    	return "Recption/GenrateMedicalBill";
    }
    
    @PostMapping("/{id}/UpdatePaymentStatus")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable("id") int id, @RequestParam("status") String status) {
        try {
            Optional<appointmentform> appointmentOpt = appointmentrepository.findById(id);
            if (appointmentOpt.isPresent()) {
                appointmentform appointment = appointmentOpt.get();
                appointment.setPaymentStatus(status); // Assuming a paymentStatus field
                appointmentrepository.save(appointment);
                return ResponseEntity.ok(status); // Return the updated status
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
            }
        } catch (Exception e) {
            // Log the exception (optional)
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating payment status");
        }
    }

    
    @PostMapping("/addReports/{id}")
    public String addReports(@RequestParam("report") MultipartFile file,@PathVariable("id") int id ) {
    	
    	try {
            Reports report = new Reports();
            System.out.println("data : " + file.getBytes());
            report.setReport(file.getBytes());
            System.out.println("id ;' " + id);// Convert file to byte array
            report.setPatientid(id);
            reportsrepository.save(report);  // Save to DB
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return "redirect:/recption/patientlist";
    }
    
    @GetMapping("/patientlist")
    public String patientlist(Model model,Principal principal) {
    	
       List<Patient> allpatient = patientrepository.findAll();
       Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
       
       Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
   	
   	
   	model.addAttribute("userdetails",userdetail);
   	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("allpatient",allpatient);
    	return "Recption/patientlist";
    }
    
    @PostMapping("/AddPatient")
    public String AddPatient(@ModelAttribute Patient patient, Principal principal) {
        try {
            Doctors user = doctorsRepositories.findByEmail(principal.getName());
            String encodedPassword = bCryptPasswordEncoder.encode(patient.getBirthdate());

            Doctors doctors = new Doctors();
            doctors.setName(patient.getName());
            doctors.setEmail(patient.getEmail());
            doctors.setPassword(encodedPassword);
            doctors.setRole("PATIENT");
            doctors.setMobileNumber(patient.getNumber());
            doctors.setAddress(patient.getAddress());
            doctors.setDoctorId(user.getId());

            doctorsRepositories.save(doctors);
            patientrepository.save(patient);
            
            return "redirect:/recption/patientlist";
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return "error"; // You can return an error page or message
        }
    }

    @GetMapping("/search-tablets")
    @ResponseBody
    public List<tablets> searchTablets(@RequestParam String term) {
    	
    	List<tablets> findtabletsbyname = medicinesrepositories.findtabletsbyname(term);
        return findtabletsbyname;
    }

    
    @PostMapping("/addbill")
    public String Genratemedicalbill(@ModelAttribute MedicalBills medicalBills) {
    	
    	medicalBillrepository.save(medicalBills);
    	
    	return "redirect:/recption/getInvoice/" +medicalBills.getPatientName();
    }
    
    @PutMapping("/update-quantity")
    public ResponseEntity<?> updateQuantity(
            @RequestParam String drugName, 
            @RequestParam int qty) {

        tablets tablet = medicinesrepositories.findByTabletName(drugName);

        if (tablet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Tablet not found");
        }

        // Reduce stock
        int newQty = tablet.getQuantity() - qty;
        if (newQty < 0) newQty = 0; // prevent negative stock
        tablet.setQuantity(newQty);

        medicinesrepositories.save(tablet);
        return ResponseEntity.ok("Stock updated successfully for " + drugName);
    }
    
    @GetMapping("/searchPatient")
    @ResponseBody
    public ResponseEntity<?> searchepatients(@RequestParam String name){
    	
    	List<Patient> patient = patientrepository.findByNameContainingIgnoreCase(name);
    	 if (patient == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                  .body("Patient not found");
         }
    	 
    	
    	return ResponseEntity.ok(patient);
    	
    }
    
    @GetMapping("/searchTommAppointment")
    @ResponseBody
    public ResponseEntity<?> searcheTommAppointment(@RequestParam String name){
    	
    	List<appointmentform> TommappointmentsByNaame = appointmentrepository.findTommAppointmentsByNaame(name);
    	 if (TommappointmentsByNaame == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                  .body("Patient not found");
         }
    	return ResponseEntity.ok(TommappointmentsByNaame);
    	
    }
    @GetMapping("/searchCancleAppointment")
    @ResponseBody
    public ResponseEntity<?> searcheCancleAppointment(@RequestParam String name){
    	
    	List<appointmentform> CancleappointmentsByNaame = appointmentrepository.findCancleAppointmentsByNaame(name);
    	 if (CancleappointmentsByNaame == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                  .body("Patient not found");
         }
    	return ResponseEntity.ok(CancleappointmentsByNaame);
    	
    }
    @GetMapping("/searchAllAppointment")
    @ResponseBody
    public ResponseEntity<?> searcheAllAppointment(@RequestParam String name){
    	
    	List<appointmentform> AllappointmentsByNaame = appointmentrepository.findAllAppointmentsByNaame(name);
    	 if (AllappointmentsByNaame == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                  .body("Patient not found");
         }
    	return ResponseEntity.ok(AllappointmentsByNaame);
    	
    }
    @GetMapping("/searchAppointment")
    @ResponseBody
    public ResponseEntity<?> searcheAppointment(@RequestParam String name){
    	
    	List<appointmentform> appointmentsByNaame = appointmentrepository.findAppointmentsByNaame(name);
    	 if (appointmentsByNaame == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                  .body("Patient not found");
         }
    	 
    	
    	return ResponseEntity.ok(appointmentsByNaame);
    	
    }

    @GetMapping("/getreports/{id}")
	public String GetReports(Model model, Principal principal, @PathVariable("id") int id) {
	    Patient patient = patientrepository.findByemail(principal.getName());
	    
	    Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	model.addAttribute("username",userdetails.getName());
	    
        Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
    	
    	
    	model.addAttribute("userdetails",userdetail);
	    List<Reports> reports = reportsrepository.findreportsbypatientid(id);
	    
	    // Convert byte[] to Base64
	    List<String> reportBase64List = reports.stream()
	        .map(report -> Base64.getEncoder().encodeToString(report.getReport())) // Convert byte[] to Base64 String
	        .collect(Collectors.toList());

	    model.addAttribute("reports", reportBase64List);
	   
	    return "Recption/Reports"; // Ensure this matches your template filename
	}

}
