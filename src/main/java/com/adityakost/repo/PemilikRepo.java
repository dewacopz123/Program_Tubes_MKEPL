package com.adityakost.repo;

import com.adityakost.entity.Pemilik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PemilikRepo extends JpaRepository<Pemilik, Long> {
    boolean existsByUsernamePemilik(String usernamePemilik);
    boolean existsByEmailPemilik(String emailPemilik);
    boolean existsByPhoneNumberPemilik(String phoneNumberPemilik);
    Pemilik findByEmailPemilikAndPasswordPemilik(String emailPemilik, String passwordPemilik);
}
