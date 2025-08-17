package com.Hospital.ivr;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class CallSession {
    @Id
    private String callSid; // Twilio CallSid

    @Enumerated(EnumType.STRING)
    private CallStep step = CallStep.START;

    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String doctor;
    private String timePref;

    public String getCallSid() { return callSid; }
    public void setCallSid(String callSid) { this.callSid = callSid; }
    public CallStep getStep() { return step; }
    public void setStep(CallStep step) { this.step = step; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }
    public String getTimePref() { return timePref; }
    public void setTimePref(String timePref) { this.timePref = timePref; }
}