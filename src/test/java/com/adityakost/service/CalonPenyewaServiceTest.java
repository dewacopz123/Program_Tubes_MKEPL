package com.adityakost.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Pemesanan;
import com.adityakost.repo.CalonPenyewaRepo;
import com.adityakost.repo.PemesananRepo;

@ExtendWith(MockitoExtension.class)
class CalonPenyewaServiceTest {

    @Mock
    private CalonPenyewaRepo calonPenyewaRepo;

    @Mock
    private PemesananRepo pemesananRepo;

    @InjectMocks
    private CalonPenyewaService service;

    private CalonPenyewa calonPenyewa;

    @BeforeEach
    void setUp() {
        calonPenyewa = new CalonPenyewa();
        calonPenyewa.setIdCalonPenyewa(1L);
    }

    @Test
    void saveCalonPenyewaShouldCallRepository() {
        service.saveCalonPenyewa(calonPenyewa);

        verify(calonPenyewaRepo).save(calonPenyewa);
    }

    @Test
    void usernameExistsShouldReturnTrue() {
        when(calonPenyewaRepo.existsByUsername("Fulan"))
                .thenReturn(true);

        assertTrue(service.usernameExists("Fulan"));
    }

    @Test
    void usernameExistsShouldReturnFalse() {
        when(calonPenyewaRepo.existsByUsername("Fulan"))
                .thenReturn(false);

        assertFalse(service.usernameExists("Fulan"));
    }

    @Test
    void emailExistsShouldReturnTrue() {
        when(calonPenyewaRepo.existsByEmail("a@mail.com"))
                .thenReturn(true);

        assertTrue(service.emailExists("a@mail.com"));
    }

    @Test
    void phoneNumberExistsShouldReturnTrue() {
        when(calonPenyewaRepo.existsByPhoneNumber("08123"))
                .thenReturn(true);

        assertTrue(service.phoneNumberExists("08123"));
    }

    @Test
    void getAllCalonPenyewaShouldReturnList() {

        List<CalonPenyewa> expected =
                Arrays.asList(new CalonPenyewa(), new CalonPenyewa());

        when(calonPenyewaRepo.findAll())
                .thenReturn(expected);

        List<CalonPenyewa> actual =
                service.getAllCalonPenyewa();

        assertEquals(2, actual.size());
    }

    @Test
    void getCalonPenyewaByIdShouldReturnUser() {

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.of(calonPenyewa));

        CalonPenyewa result =
                service.getCalonPenyewaById(1L);

        assertNotNull(result);
    }

    @Test
    void getCalonPenyewaByIdShouldThrowException() {

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> service.getCalonPenyewaById(1L));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void updateCalonPenyewaShouldSaveUpdatedUser() {

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.of(calonPenyewa));

        service.updateCalonPenyewa(calonPenyewa);

        verify(calonPenyewaRepo).save(calonPenyewa);
    }

    @Test
    void updateCalonPenyewaShouldThrowExceptionWhenUserNotFound() {

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.updateCalonPenyewa(calonPenyewa));
    }

    @Test
    void getCalonPenyewaByEmailAndPasswordShouldReturnUser() {

        when(calonPenyewaRepo.findByEmailAndPassword(
                "mail",
                "123"))
                .thenReturn(calonPenyewa);

        assertNotNull(
                service.getCalonPenyewaByEmailAndPassword(
                        "mail",
                        "123"));
    }

    @Test
    void saveComplainShouldThrowExceptionWhenEmpty() {

        assertThrows(
                IllegalArgumentException.class,
                () -> service.saveComplain(""));
    }

    @Test
    void saveComplainShouldNotThrowException() {

        assertDoesNotThrow(
                () -> service.saveComplain("AC rusak"));
    }

    @Test
    void getPenghuniDenganKamarShouldReturnAllBookings() {

        List<Pemesanan> expected =
                Arrays.asList(new Pemesanan(), new Pemesanan());

        when(pemesananRepo.findAll())
                .thenReturn(expected);

        List<Pemesanan> result =
                service.getPenghuniDenganKamar();

        assertEquals(2, result.size());
    }

    @Test
    void deleteCalonPenyewaByIdShouldDeleteUser() {

        when(calonPenyewaRepo.existsById(1L))
                .thenReturn(true);

        service.deleteCalonPenyewaById(1L);

        verify(calonPenyewaRepo).deleteById(1L);
    }

    @Test
    void deleteCalonPenyewaByIdShouldThrowException() {

        when(calonPenyewaRepo.existsById(1L))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> service.deleteCalonPenyewaById(1L));
    }
}