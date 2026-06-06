package com.adityakost.service;


import com.adityakost.entity.Pemilik;

import com.adityakost.repo.PemilikRepo;

import org.springframework.stereotype.Service;


@Service
public class PemilikService {

    private final PemilikRepo pemilikRepo;

    public PemilikService(PemilikRepo pemilikRepo) {
        this.pemilikRepo = pemilikRepo;
    }

    public void savePemilik(Pemilik pemilik) {
        pemilikRepo.save(pemilik);
    }

    public boolean usernameExists(String usernamePemilik) {
        return pemilikRepo.existsByUsernamePemilik(usernamePemilik); // Nama metode diperbaiki
    }

    public boolean emailExists(String emailPemilik) {
        return pemilikRepo.existsByEmailPemilik(emailPemilik); // Nama metode diperbaiki
    }

    public boolean phoneNumberExists(String phoneNumberPemilik) {
        return pemilikRepo.existsByPhoneNumberPemilik(phoneNumberPemilik); // Nama metode diperbaiki
    }

    public Pemilik findByEmailPemilikAndPasswordPemilik(String emailPemilik, String passwordPemilik) {
        return pemilikRepo.findByEmailPemilikAndPasswordPemilik(emailPemilik, passwordPemilik);
    }
}
