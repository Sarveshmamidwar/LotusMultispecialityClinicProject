package com.Hospital.ivr;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.entity.Doctors;
import com.Hospital.repositories.DoctorsRepositories;

import jakarta.servlet.http.HttpServlet;

@RestController
@RequestMapping(value = "/voice", produces = "application/xml")
public class VoiceController {
    private final CallSessionRepository sessionRepo;
    private final AppointmentIvrService appointmentService;
    private final DoctorsRepositories doctorsRepo;

    public VoiceController(CallSessionRepository sessionRepo, AppointmentIvrService appointmentService, DoctorsRepositories doctorsRepo) {
        this.sessionRepo = sessionRepo;
        this.appointmentService = appointmentService;
        this.doctorsRepo = doctorsRepo;
    }

    @PostMapping("/incoming")
    public String incoming(@RequestParam("CallSid") String callSid) {
        CallSession s = sessionRepo.findById(callSid).orElseGet(() -> {
            CallSession ns = new CallSession();
            ns.setCallSid(callSid);
            return ns;
        });
        
        System.out.println("✅ Incoming call received from Twilio");
       
        s.setStep(CallStep.CONFIRM_BOOKING);
        sessionRepo.save(s);

        return """
        <Response>
          <Say>Welcome to Lotus Hospital.</Say>
          <Pause length=\"1\"/>
          <Say>Do you want to book an appointment? Press 1 for Yes. Press 2 for No.</Say>
          <Gather input=\"dtmf\" timeout=\"6\" numDigits=\"1\" action=\"/voice/confirmBooking\"/>
        </Response>
        """;
    }

    @PostMapping("/confirmBooking")
    public String confirmBooking(@RequestParam("CallSid") String callSid,
                                 @RequestParam(value="Digits", required=false) String digits) {
        Optional<CallSession> opt = sessionRepo.findById(callSid);
        if (opt.isEmpty()) return hangup("Session not found. Goodbye.");
        if (!"1".equals(digits)) {
            return """
            <Response>
              <Say>Okay, if you need anything else, please call us again. Goodbye.</Say>
              <Hangup/>
            </Response>
            """;
        }
        CallSession s = opt.get();
        s.setStep(CallStep.ASK_NAME);
        sessionRepo.save(s);

        return """
        <Response>
          <Say>Please say your full name after the beep.</Say>
          <Gather input=\"speech dtmf\" speechTimeout=\"auto\" action=\"/voice/saveName\" timeout=\"6\"/>
        </Response>
        """;
    }

    @PostMapping("/saveName")
    public String saveName(@RequestParam("CallSid") String callSid,
                           @RequestParam(value="SpeechResult", required=false) String speech) {
        CallSession s = sessionRepo.findById(callSid).orElse(null);
        if (s == null) return hangup("Session not found. Goodbye.");
        s.setFullName(speech != null ? speech : "Unknown");
        s.setStep(CallStep.ASK_PHONE);
        sessionRepo.save(s);

        return """
        <Response>
          <Say>Thank you.</Say>
          <Say>Please enter your 10 digit phone number now, then press hash.</Say>
          <Gather input=\"dtmf\" finishOnKey=\"#\" timeout=\"12\" action=\"/voice/savePhone\"/>
        </Response>
        """;
    }

    @PostMapping("/savePhone")
    public String savePhone(@RequestParam("CallSid") String callSid,
                            @RequestParam(value="Digits", required=false) String digits) {
        CallSession s = sessionRepo.findById(callSid).orElse(null);
        if (s == null) return hangup("Session not found. Goodbye.");
        if (digits == null || digits.length() < 10) {
            return """
            <Response>
              <Say>Invalid phone number. Please try again.</Say>
              <Redirect method=\"POST\">/voice/reaskPhone</Redirect>
            </Response>
            """;
        }
        s.setPhone(digits);
        s.setStep(CallStep.ASK_EMAIL);
        sessionRepo.save(s);

        return """
        <Response>
          <Say>Please say your email address after the beep.</Say>
          <Gather input=\"speech dtmf\" speechTimeout=\"auto\" action=\"/voice/saveEmail\" timeout=\"8\"/>
        </Response>
        """;
    }

    @PostMapping("/reaskPhone")
    public String reaskPhone(@RequestParam("CallSid") String callSid) {
        return """
        <Response>
          <Say>Please enter your 10 digit phone number now, then press hash.</Say>
          <Gather input=\"dtmf\" finishOnKey=\"#\" timeout=\"12\" action=\"/voice/savePhone\"/>
        </Response>
        """;
    }

    @PostMapping("/saveEmail")
    public String saveEmail(@RequestParam("CallSid") String callSid,
                            @RequestParam(value="SpeechResult", required=false) String speech) {
        CallSession s = sessionRepo.findById(callSid).orElse(null);
        if (s == null) return hangup("Session not found. Goodbye.");
        s.setEmail(speech != null ? speech : "unknown@example.com");
        s.setStep(CallStep.ASK_ADDRESS);
        sessionRepo.save(s);

        return """
        <Response>
          <Say>Please say your address after the beep.</Say>
          <Gather input=\"speech\" speechTimeout=\"auto\" action=\"/voice/saveAddress\" timeout=\"8\"/>
        </Response>
        """;
    }

    @PostMapping("/saveAddress")
    public String saveAddress(@RequestParam("CallSid") String callSid,
                              @RequestParam(value="SpeechResult", required=false) String speech) {
        CallSession s = sessionRepo.findById(callSid).orElse(null);
        if (s == null) return hangup("Session not found. Goodbye.");
        s.setAddress(speech != null ? speech : "Not provided");
        s.setStep(CallStep.ASK_DOCTOR);
        sessionRepo.save(s);

        return buildDoctorMenu();
    }

    @PostMapping("/saveDoctor")
    public String saveDoctor(@RequestParam("CallSid") String callSid,
                             @RequestParam(value="Digits", required=false) String digits) {
        CallSession s = sessionRepo.findById(callSid).orElse(null);
        if (s == null) return hangup("Session not found. Goodbye.");
        int choice = -1;
        try { choice = Integer.parseInt(digits); } catch(Exception e) { choice = -1; }

        List<Doctors> docs = doctorsRepo.findAll();
        if (choice < 1 || choice > docs.size()) {
            return buildDoctorMenu("Invalid option. Please try again.");
        }

        Doctors d = docs.get(choice - 1);
        String doctorName = d.getName() != null ? d.getName() : ("Doctor " + choice);
        s.setDoctor(doctorName);
        s.setStep(CallStep.ASK_TIME);
        sessionRepo.save(s);

        return """
        <Response>
          <Say>Please enter your preferred time in 24 hour format H H M M. For example, for 2 30 PM press 1 4 3 0, then hash.</Say>
          <Gather input=\"dtmf\" finishOnKey=\"#\" timeout=\"12\" action=\"/voice/saveTime\"/>
        </Response>
        """;
    }

    @PostMapping("/saveTime")
    public String saveTime(@RequestParam("CallSid") String callSid,
                           @RequestParam(value="Digits", required=false) String digits) {
        CallSession s = sessionRepo.findById(callSid).orElse(null);
        if (s == null) return hangup("Session not found. Goodbye.");
        if (digits == null || digits.length() != 4) {
            return """
            <Response>
              <Say>Invalid time. Please try again.</Say>
              <Redirect method=\"POST\">/voice/reaskTime</Redirect>
            </Response>
            """;
        }
        String hh = digits.substring(0, 2);
        String mm = digits.substring(2, 4);
        s.setTimePref(hh + ":" + mm);
        s.setStep(CallStep.CONFIRM_AND_SAVE);
        sessionRepo.save(s);

        // Save appointment into your existing appointmentform table
        appointmentService.saveFromSession(s);
        s.setStep(CallStep.DONE);
        sessionRepo.save(s);

        return """
        <Response>
          <Say>Your appointment is booked. Please come on time. Thank you for calling Lotus Hospital.</Say>
          <Hangup/>
        </Response>
        """;
    }

    @PostMapping("/reaskTime")
    public String reaskTime(@RequestParam("CallSid") String callSid) {
        return """
        <Response>
          <Say>Please enter your preferred time in 24 hour format H H M M, then hash.</Say>
          <Gather input=\"dtmf\" finishOnKey=\"#\" timeout=\"12\" action=\"/voice/saveTime\"/>
        </Response>
        """;
    }

    // Utilities

    private String buildDoctorMenu() {
        return buildDoctorMenu(null);
    }

    private String buildDoctorMenu(String prefix) {
        List<Doctors> docs = doctorsRepo.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<Response>");
        if (prefix != null && !prefix.isBlank()) {
            sb.append("<Say>").append(escape(prefix)).append("</Say>");
        }
        if (docs.isEmpty()) {
            sb.append("<Say>No doctors found. Please try again later.</Say><Hangup/></Response>");
            return sb.toString();
        }
        sb.append("<Say>Choose your doctor.</Say>");
        for (int i = 0; i < docs.size(); i++) {
            Doctors d = docs.get(i);
            String name = d.getName() != null ? d.getName() : ("Doctor " + (i+1));
            sb.append("<Say>Press ").append(i+1).append(" for ").append(escape(name)).append(".</Say>");
        }
        sb.append("<Gather input=\"dtmf\" timeout=\"8\" numDigits=\"1\" action=\"/voice/saveDoctor\"/>");
        sb.append("</Response>");
        return sb.toString();
    }

    private String hangup(String msg) {
        return "<Response><Say>" + escape(msg) + "</Say><Hangup/></Response>";
    }

    private String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
    
    
  
}