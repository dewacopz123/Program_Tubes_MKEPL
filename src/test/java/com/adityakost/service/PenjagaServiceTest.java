package com.adityakost.service;

import com.adityakost.entity.Penjaga;
import com.adityakost.repo.PenjagaRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PenjagaServiceTest {

    @Mock
    private PenjagaRepo penjagaRepo;

    @InjectMocks
    private PenjagaService penjagaService;

    @Test
    void savePenjagaShouldCallRepository() {

        Penjaga penjaga = new Penjaga();

        penjagaService.savePenjaga(penjaga);

        verify(penjagaRepo).save(penjaga);
    }

    @Test
    void usernameExistsShouldReturnTrue() {

        when(
                penjagaRepo.existsByUsernamePenjaga("penjaga"))
                .thenReturn(true);

        assertTrue(
                penjagaService.usernameExists("penjaga"));
    }

    @Test
    void usernameExistsShouldReturnFalse() {

        when(
                penjagaRepo.existsByUsernamePenjaga("penjaga"))
                .thenReturn(false);

        assertFalse(
                penjagaService.usernameExists("penjaga"));
    }

    @Test
    void emailExistsShouldReturnTrue() {

        when(
                penjagaRepo.existsByEmailPenjaga("a@mail.com"))
                .thenReturn(true);

        assertTrue(
                penjagaService.emailExists("a@mail.com"));
    }

    @Test
    void phoneNumberExistsShouldReturnTrue() {

        when(
                penjagaRepo.existsByPhoneNumberPenjaga("08123"))
                .thenReturn(true);

        assertTrue(
                penjagaService.phoneNumberExists("08123"));
    }

    @Test
    void findByEmailAndPasswordShouldReturnPenjaga() {

        Penjaga penjaga = new Penjaga();

        when(
                penjagaRepo.findByEmailPenjagaAndPasswordPenjaga(
                        "penjaga@mail.com",
                        "123"))
                .thenReturn(penjaga);

        Penjaga result =
                penjagaService
                        .findByEmailPenjagaAndPasswordPenjaga(
                                "penjaga@mail.com",
                                "123");

        assertNotNull(result);
    }

    @Test
    void findByEmailAndPasswordShouldReturnNull() {

        when(
                penjagaRepo.findByEmailPenjagaAndPasswordPenjaga(
                        "salah@mail.com",
                        "salah"))
                .thenReturn(null);

        Penjaga result =
                penjagaService
                        .findByEmailPenjagaAndPasswordPenjaga(
                                "salah@mail.com",
                                "salah");

        assertNull(result);
    }
}