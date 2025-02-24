package com.Hospital.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Hospital.entity.Doctors;
import com.Hospital.repositories.DoctorsRepositories;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

	@Autowired
    private DoctorsRepositories doctorsRepository;  // Assuming you have a repository

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Doctors doctor = doctorsRepository.findByEmail(email);
        if (doctor == null) {
            throw new UsernameNotFoundException("No doctor found with email: " + email);
        }

        return User.builder()
                .username(doctor.getEmail())  // Use email as username
                .password(doctor.getPassword())  // Password from DB (should be encoded)
                .roles(doctor.getRole())  // Assuming role is stored as "DOCTOR" or "RECPTION"
                .build();
    }

}
