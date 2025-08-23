package com.Hospital.Controller;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.Hospital.entity.StockInvoice;
import com.Hospital.entity.appointmentform;
import com.Hospital.entity.staff;
import com.Hospital.entity.tablets;
import com.Hospital.repositories.DoctorsRepositories;
import com.Hospital.repositories.HositalStaffrepository;
import com.Hospital.repositories.MedicalBillrepository;
import com.Hospital.repositories.MedicalStockInvoiceRepository;
import com.Hospital.repositories.appointmentrepository;
import com.Hospital.repositories.patientrepository;
import com.Hospital.repositories.reportsrepository;
import com.Hospital.repositories.tabletsrepositories;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {
	
	@Autowired
	private appointmentrepository appointmentrepository;
	
	@Autowired
	private tabletsrepositories medicinesrepositories;
	
	@Autowired
	private DoctorsRepositories  doctorsRepositories;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private patientrepository patientrepository;
	
	@Autowired
	private MedicalBillrepository medicalBillrepository;
	
	@Autowired
	private HositalStaffrepository hospitalStaffrepository;
	
	@Autowired
	private reportsrepository reportsrepository;
	
	@Autowired
	private MedicalStockInvoiceRepository medicalStockInvoiceRepository;
	
	@Autowired
	private MedicalStockInvoiceRepository stockInvoiceRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model,Principal principal) {
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	int findtotalcount = appointmentrepository.findtotalcount();   	
    	int todayAppointmentsCount = appointmentrepository.findTodayAppointmentsCount();
    	int cancleAppointmentsCount = appointmentrepository.findCancleAppointmentsCount();
    	int tomorrowAppointmentsCount = appointmentrepository.findTomorrowAppointmentsCount();
    	int rutineApointmentCount = appointmentrepository.findRutineAppointmentsCount();
    	int DoneAppointmentCount = appointmentrepository.findDoneAppointmentsCount();
    	int OnlinePaymentCount = appointmentrepository.findOnlinePaymentCount();
    	Integer totaRevenue = medicalBillrepository.getTodayTotalBilling();
    	Integer todayTotalFee = appointmentrepository.findTodayTotalFee();
    	
    	model.addAttribute("todayFee", todayTotalFee == null ? 0 : todayTotalFee);
    	model.addAttribute("totalrevenue",totaRevenue == null ? 0 : totaRevenue);
    	model.addAttribute("totalCount",findtotalcount);
    	model.addAttribute("todayAppointmentsCount",todayAppointmentsCount);
    	model.addAttribute("cancleAppointmentsCount",cancleAppointmentsCount);
    	model.addAttribute("tomorrowAppointmentsCount",tomorrowAppointmentsCount);
    	model.addAttribute("rutineApointmentCount",rutineApointmentCount);
    	model.addAttribute("DoneAppointmentCount",DoneAppointmentCount);
    	model.addAttribute("OnlinePaymentCount",OnlinePaymentCount);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("userdetails",userdetails);
        return "Doctors/dashboard"; 
    }
    
    @GetMapping("/totalAppointment")
    public String totalAppointment(Model model,Principal principal ) {   	
    	List<appointmentform> all = appointmentrepository.findAll();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
    	int count = all.size();
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("all",all);
        return "Doctors/totalappointment"; 
    }
    
    @GetMapping("/todyasAppointment")
    public String todaysAppointment(Model model,Principal principal ) {    	
    	List<appointmentform> todayAppointments = appointmentrepository.findTodayAppointments();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("todayAppointments",todayAppointments);
        return "Doctors/todyasAppointment"; 
    }
    
    @GetMapping("/cancleAppointment")
    public String cancleAppointment(Model model ,Principal principal) {   	
    	List<appointmentform> cancleAppointments = appointmentrepository.findCancleAppointments();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("cancleAppointments",cancleAppointments);
        return "Doctors/cancleAppointment";
    }
    
    @GetMapping("/tommorowAppointment")
    public String tommorowAppointment(Model model,Principal principal ) {   	
    	List<appointmentform> tommorowAppointments = appointmentrepository.findTomorrowAppointments();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("tommorowAppointments",tommorowAppointments);
        return "Doctors/tommorowAppointment"; 
    }
    
    @GetMapping("/appointmentalert")
    @ResponseBody
    public List<appointmentform> todaysAppointmentAlert() {
        List<appointmentform> todayAppointmentswithstatus = appointmentrepository.findTodayAppointmentswithstatus();
        
        return todayAppointmentswithstatus;
    }


    @PostMapping("/update-status")
    public ResponseEntity<?> updateAppointmentStatus(@RequestBody Map<String, String> requestData) {
        String status = requestData.get("status");
        int appointmentId = Integer.parseInt(requestData.get("appointmentId"));
        
        System.out.println("appointmentId : " + appointmentId);
        System.out.println("status : " + status);

        appointmentform appointment = appointmentrepository.findbyappointmentid(appointmentId);
        appointment.setAppointmentStatus(status);
        appointmentrepository.save(appointment);
        
        return ResponseEntity.ok("Status updated successfully");
    }


    @GetMapping("/Prescription/{name}/{gender}/{id}")
    public String Prescription (@PathVariable("name") String name ,@PathVariable("gender") String gender,
    		@PathVariable("id") int id ,
    		Model model,Principal principal) {
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	Patient patient = patientrepository.findByemail(principal.getName());
    	
    	model.addAttribute("patientdetails",patient);
    	model.addAttribute("username",userdetails.getName());
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("patientname",name);
    	model.addAttribute("patientgender",gender);
    	model.addAttribute("patientid",id);
    	return"Doctors/prescription";
    }

    @GetMapping("/search-tablets")
    @ResponseBody
    public List<tablets> searchTablets(@RequestParam String term) {    	
    	List<tablets> findtabletsbyname = medicinesrepositories.findtabletsbyname(term);
        return findtabletsbyname;
    }
    
    @GetMapping("/medicinesLists")
    public String medicinesList(Model model,Principal principal) {   	 
    	List<tablets> medicineslist = medicinesrepositories.findAll();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("medicineslist",medicineslist);
    	return "Doctors/medicinesList";
    }
    
    @PostMapping("/addmedicines")
    public String addmedicines(@ModelAttribute tablets tablets) {
        try {
            if (tablets == null) {
                throw new IllegalArgumentException("Medicine data cannot be null!");
            }

            medicinesrepositories.save(tablets);
            return "redirect:/doctors/medicinesLists";

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return "redirect:/doctors/error"; 
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return "redirect:/doctors/medicinesLists";  
        }
    }

    
    @PostMapping("/{id}/editmedicines")
    public String editMedicines(@ModelAttribute tablets updatedTablet, @PathVariable("id") String idParam) {
        try {
            int id = Integer.parseInt(idParam);

            List<tablets> foundTablets = medicinesrepositories.findtabletsbyid(id);

            if (foundTablets == null || foundTablets.isEmpty()) {
                throw new IllegalArgumentException("No tablet found with ID: " + id);
            }
            tablets existingTablet = foundTablets.get(0);

            existingTablet.setTabletName(updatedTablet.getTabletName());
            existingTablet.setMedicineCompanyName(updatedTablet.getMedicineCompanyName());
            existingTablet.setMedecineType(updatedTablet.getMedecineType());

            medicinesrepositories.save(existingTablet);
            return "redirect:/doctors/medicinesLists";

        } catch (NumberFormatException e) {
          e.printStackTrace();
            return "redirect:/doctors/medicinesLists";
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
            return "redirect:/doctors/medicinesLists?error=not_found";
        } catch (Exception e) {
          e.printStackTrace();
            return "redirect:/doctors/medicinesLists";
        }
    }

    
    
    @DeleteMapping("/{id}/deletemedicines")
    public ResponseEntity<Void> deletemedicine(@PathVariable("id") int id) {       
        medicinesrepositories.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/UpdatePaymentStatus")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable("id") int id, @RequestParam("status") String status) {
        try {
            Optional<appointmentform> appointmentOpt = appointmentrepository.findById(id);
            if (appointmentOpt.isPresent()) {
                appointmentform appointment = appointmentOpt.get();
                appointment.setPaymentStatus(status); 
                appointmentrepository.save(appointment);
                return ResponseEntity.ok(status); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating payment status");
        }
    }
    
    @GetMapping("/GetAddEmployee")
    public String Employee(Principal principal,Model model) {
    	System.out.println("name  : "+ principal.getName());
        Doctors user = doctorsRepositories.findByEmail(principal.getName());        
        List<Doctors> findemployeebydoctors = doctorsRepositories.findemployeebydoctors(user.getId());
        Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
        
        model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
        model.addAttribute("employee",findemployeebydoctors);
        System.out.println(user);
        
    	return "Doctors/AddEmployee";
    }
    
    @PostMapping("/Addemployee")
    public String AddEmployee(@ModelAttribute Doctors doctors, Principal principal) {
        try {
            if (principal == null) {
                throw new IllegalArgumentException("Principal is null, user not authenticated!");
            }
            Doctors user = doctorsRepositories.findByEmail(principal.getName());
            if (user == null) {
                throw new IllegalArgumentException("Logged-in doctor not found in DB!");
            }
            if (doctors.getPassword() == null || doctors.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty!");
            }
            String encodedPassword = bCryptPasswordEncoder.encode(doctors.getPassword());

            doctors.setPassword(encodedPassword);
            doctors.setDoctorId(user.getId());
            doctors.setRole("RECPTION");
            doctors.setEmployeetype("Reception");
            staff hospitaStaff = new staff();
            hospitaStaff.setName(doctors.getName());
            hospitaStaff.setEmail(doctors.getEmail());
            hospitaStaff.setNumber(doctors.getMobileNumber());
            hospitaStaff.setGender(doctors.getGender());
            hospitaStaff.setAddress(doctors.getAddress());
            hospitaStaff.setEmpType("Reception");
            hospitaStaff.setDocid(user.getId());

            hospitalStaffrepository.save(hospitaStaff);
            doctorsRepositories.save(doctors);
            return "redirect:/doctors/GetAddEmployee";

        } catch (IllegalArgumentException e) {
        	e.printStackTrace();
            return "redirect:/doctors/GetAddEmployee";  // you can show error.html page
        } catch (Exception e) {
        	e.printStackTrace();
            return "redirect:/doctors/GetAddEmployee"; 
        }
    }

    
    
    @GetMapping("/patient")
    public String patient(Model model,Principal principal) {    	
    	List<Patient> allpatient = patientrepository.findAll();
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	
    	model.addAttribute("userdetails",userdetails);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("allpatient",allpatient);
    	return "Doctors/Patient";
    }
    
    @PostMapping("/AddPatient")
    public String AddPatient(@ModelAttribute Patient patient, Principal principal, Model model) {
        try {
            Doctors user = doctorsRepositories.findByEmail(principal.getName());
            if (user == null) {
                throw new RuntimeException("Logged-in doctor not found.");
            }

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
            return "redirect:/doctors/patient";
        } catch (DataIntegrityViolationException e) {
        	e.printStackTrace();
        	return "redirect:/doctors/patient"; 
        } catch (Exception e) {
        	e.printStackTrace();
        	return "redirect:/doctors/patient"; 
        }
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
    
    @GetMapping("/searchEmployee")
    @ResponseBody
    public ResponseEntity<?> searchemployee(@RequestParam("name") String name,Principal principal){ 	
    	 Doctors user = doctorsRepositories.findByEmail(principal.getName());    	
    	List<Doctors> findemployeebyname = doctorsRepositories.findemployeebyname(user.getId(), name);   	
    	if (findemployeebyname == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Employee not found");
        }   	
    	return ResponseEntity.ok(findemployeebyname);
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
    
    @GetMapping("/getreports/{id}")
	public String GetReports(Model model, Principal principal, @PathVariable("id") int id) {
	    Patient patient = patientrepository.findByemail(principal.getName());	  
	    Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
    	model.addAttribute("username",userdetails.getName());
	    
	    List<Reports> reports = reportsrepository.findreportsbypatientid(id);

	    List<String> reportBase64List = reports.stream()
	        .map(report -> Base64.getEncoder().encodeToString(report.getReport())) 
	        .collect(Collectors.toList());

	    model.addAttribute("userdetails",userdetails);
	    model.addAttribute("reports", reportBase64List);
	   
	    return "Doctors/Reports"; 
	}
    
    @PostMapping("/addReports/{id}")
    public String addReports(@RequestParam("report") MultipartFile file,
                             @RequestParam("reportName") String reportName,
                             @PathVariable("id") int id) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is missing or empty");
            }

            if (id <= 0) {
                throw new IllegalArgumentException("Invalid patient ID: " + id);
            }

            Reports report = new Reports();
            report.setReport(file.getBytes());
            report.setPatientid(id);
            report.setReportName(reportName); // ✅ save report name

            reportsrepository.save(report);

        } catch (IllegalArgumentException | IOException e) {
            throw new RuntimeException("Error saving report: " + e.getMessage(), e);
        }

        return "redirect:/doctors/patient";
    }
    
    @PostMapping("/BookAppointment")
	public String postMethodName(@ModelAttribute appointmentform appointmentform,@RequestParam(value = "patientId", required = false, defaultValue = "0") int patientId) {
	    try {
	        appointmentform.setAppointmentStatus("Pending");
            appointmentform.setPaymentStatus("Pending");
	        appointmentform.setPatientid(patientId);
	        appointmentrepository.save(appointmentform);
	        return "redirect:/doctors/todyasAppointment";
	    } catch (Exception e) {
	        // Log the error
	        e.printStackTrace();
	        // Redirect to an error page or fallback
	        return "redirect:/doctors/todyasAppointment";
	    }
	}
    
    @GetMapping("/getInvoice/{id}")
    public String GetBill(@PathVariable("id") int id,Model model,Principal principal) {
    	Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
        Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
        
       Patient patientdetails = patientrepository.findByid(id);
    	
    	
    	model.addAttribute("userdetails",userdetail);
    	model.addAttribute("username",userdetails.getName());
    	model.addAttribute("patientdetails",patientdetails);
    	return "Doctors/GenrateMedicalBill";
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
    
    @PutMapping("/increase-quantity")
    public ResponseEntity<?> increaseQuantity(
            @RequestParam String drugName, 
            @RequestParam int qty) {
        tablets tablet = medicinesrepositories.findByTabletName(drugName);
        if (tablet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tablet not found");
        }

        int newQty = tablet.getQuantity() + qty; // ✅ increase instead of reduce
        tablet.setQuantity(newQty);

        medicinesrepositories.save(tablet);
        return ResponseEntity.ok("Stock increased successfully for " + drugName);
    }

    
    @PostMapping("/addbill")
    public String Genratemedicalbill(@ModelAttribute MedicalBills medicalBills) {
    	
    	
    	medicalBillrepository.save(medicalBills);
    	
    	return "redirect:/doctors/patient";
    }

    @GetMapping("/getStockInvoice")
    public String getStockInvoice(Model model, Principal principal) {
    	Doctors userdetail = doctorsRepositories.findByEmail(principal.getName());
    	
    	model.addAttribute("userdetails",userdetail);
    	return "Doctors/StockUpdateInvoice";
    }
    
    @PostMapping("/genrateStockInvoice")
    public String genrateStockInvoice(@ModelAttribute StockInvoice stockInvoicedata,
                                      @RequestParam("file") MultipartFile file) {
        try {
            stockInvoicedata.setReport(file.getBytes()); // assuming report is byte[] in entity
            medicalStockInvoiceRepository.save(stockInvoicedata);
            return "redirect:/doctors/getStockInvoice";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/doctors/getStockInvoice";
        }
    }
    
    @GetMapping("/getstockinovicce")
	public String GetReports(Model model, Principal principal) {
	    try {
	        if (principal == null || principal.getName() == null) {
	            model.addAttribute("errorMessage", "User is not logged in.");
	            return "error"; 
	        }
	        Doctors userdetails = doctorsRepositories.findByEmail(principal.getName());
	       
	        List<StockInvoice> reports = stockInvoiceRepository.findAll();
	        if (reports == null || reports.isEmpty()) {
	            model.addAttribute("message", "No reports found.");
	            model.addAttribute("userdetails", userdetails);
	            //model.addAttribute("PatientName", patient.getName());
	            return "Doctors/AllStockInvoice"; 
	        }
	        List<String> reportBase64List = reports.stream()
	            .map(report -> Base64.getEncoder().encodeToString(report.getReport()))
	            .collect(Collectors.toList());

	        model.addAttribute("userdetails", userdetails);
	        model.addAttribute("reports", reportBase64List);
	        model.addAttribute("PatientName", userdetails);
	        return "Doctors/AllStockInvoice";

	    } catch (Exception e) {
	        e.printStackTrace(); 
	        model.addAttribute("errorMessage", "Something went wrong while fetching reports.");
	        return "Doctors/AllStockInvoice"; 
	    }
	}

   
}
