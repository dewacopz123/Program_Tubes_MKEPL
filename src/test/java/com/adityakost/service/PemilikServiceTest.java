package com.adityakost.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adityakost.entity.Pemilik;
import com.adityakost.repo.PemilikRepo;

@ExtendWith(MockitoExtension.class)
class PemilikServiceTest {

    @Mock
    private PemilikRepo pemilikRepo;

    @InjectMocks
    private PemilikService pemilikService;

    @Test
    void savePemilikShouldCallRepository() {

        Pemilik pemilik = new Pemilik();

        pemilikService.savePemilik(pemilik);

        verify(pemilikRepo).save(pemilik);
    }

    @Test
    void usernameExistsShouldReturnTrue() {

        when(
                pemilikRepo.existsByUsernamePemilik("admin"))
                .thenReturn(true);

        assertTrue(
                pemilikService.usernameExists("admin"));
    }

    @Test
    void usernameExistsShouldReturnFalse() {

        when(
                pemilikRepo.existsByUsernamePemilik("admin"))
                .thenReturn(false);

        assertFalse(
                pemilikService.usernameExists("admin"));
    }

    @Test
    void emailExistsShouldReturnTrue() {

        when(
                pemilikRepo.existsByEmailPemilik("a@mail.com"))
                .thenReturn(true);

        assertTrue(
                pemilikService.emailExists("a@mail.com"));
    }

    @Test
    void phoneNumberExistsShouldReturnTrue() {

        when(
                pemilikRepo.existsByPhoneNumberPemilik("08123"))
                .thenReturn(true);

        assertTrue(
                pemilikService.phoneNumberExists("08123"));
    }

    @Test
    void findByEmailAndPasswordShouldReturnPemilik() {

        Pemilik pemilik = new Pemilik();

        when(
                pemilikRepo.findByEmailPemilikAndPasswordPemilik(
                        "owner@mail.com",
                        "123"))
                .thenReturn(pemilik);

        Pemilik result =
                pemilikService
                        .findByEmailPemilikAndPasswordPemilik(
                                "owner@mail.com",
                                "123");

        assertNotNull(result);
    }
}