package com.adityakost.service;


import com.adityakost.entity.Penjaga;

import com.adityakost.repo.PenjagaRepo;

import org.springframework.stereotype.Service;


@Service
public class PenjagaService {

    private final PenjagaRepo penjagaRepo;

    public PenjagaService(PenjagaRepo penjagaRepo) {
        this.penjagaRepo = penjagaRepo;
    }

    public void savePenjaga(Penjaga penjaga) {
        penjagaRepo.save(penjaga);
    }

    public boolean usernameExists(String usernamePenjaga) {
        return penjagaRepo.existsByUsernamePenjaga(usernamePenjaga); // Nama metode diperbaiki
    }

    public boolean emailExists(String emailPenjaga) {
        return penjagaRepo.existsByEmailPenjaga(emailPenjaga); // Nama metode diperbaiki
    }

    public boolean phoneNumberExists(String phoneNumberPenjaga) {
        return penjagaRepo.existsByPhoneNumberPenjaga(phoneNumberPenjaga); // Nama metode diperbaiki
    }

    public Penjaga findByEmailPenjagaAndPasswordPenjaga(String emailPenjaga, String passwordPenjaga) {
        return penjagaRepo.findByEmailPenjagaAndPasswordPenjaga(emailPenjaga, passwordPenjaga);
    }
}
