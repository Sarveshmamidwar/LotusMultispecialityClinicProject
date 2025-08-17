package com.Hospital.ivr;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.Hospital.entity.appointmentform;
import com.Hospital.repositories.appointmentrepository;

@Service
public class AppointmentIvrService {
    private final appointmentrepository repo;

    public AppointmentIvrService(appointmentrepository repo) {
        this.repo = repo;
    }

    public appointmentform saveFromSession(CallSession s) {
        appointmentform a = new appointmentform();
        a.setName(s.getFullName() != null ? s.getFullName() : "Unknown");
        a.setPhone(s.getPhone());
        a.setEmail(s.getEmail());
        a.setDoctor(s.getDoctor());
        // Default values
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        a.setAppointmentDate(today);
        a.setAppointmentTime(s.getTimePref() != null ? s.getTimePref() : "00:00");
        a.setAppointmentStatus("Pending");
        a.setPaymentStatus("Unpaid");
        a.setAppointmentType("IVR");
        // Optional free text
        a.setMessage(s.getAddress());
        return repo.save(a);
    }
}