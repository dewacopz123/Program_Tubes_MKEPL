package com.adityakost.service;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Kamar;
import com.adityakost.entity.Pemesanan;
import com.adityakost.repo.CalonPenyewaRepo;
import com.adityakost.repo.KamarRepo;
import com.adityakost.repo.PemesananRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PemesananServiceTest {

    @Mock
    private PemesananRepo pemesananRepo;

    @Mock
    private KamarRepo kamarRepo;

    @Mock
    private CalonPenyewaRepo calonPenyewaRepo;

    @InjectMocks
    private PemesananService service;

    @Test
    void savePemesananShouldThrowWhenNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> service.savePemesanan(
                        null,
                        1L,
                        1L));
    }

    @Test
    void savePemesananShouldThrowWhenUserNotFound() {

        Pemesanan p = new Pemesanan();

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.savePemesanan(
                        p,
                        1L,
                        1L));
    }

    @Test
    void savePemesananShouldThrowWhenRoomNotFound() {

        Pemesanan p = new Pemesanan();

        CalonPenyewa user = new CalonPenyewa();

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.of(user));

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.savePemesanan(
                        p,
                        1L,
                        1L));
    }

    @Test
    void savePemesananShouldThrowWhenDurationInvalid() {

        Pemesanan p = new Pemesanan();
        p.setDurasi(0);

        CalonPenyewa user = new CalonPenyewa();
        Kamar kamar = new Kamar();

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.of(user));

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.of(kamar));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.savePemesanan(
                        p,
                        1L,
                        1L));
    }

    @Test
    void savePemesananShouldCalculateTotalBiayaAutomatically() {

        Pemesanan p = new Pemesanan();
        p.setDurasi(3);
        p.setTotalBiaya(0);

        CalonPenyewa user = new CalonPenyewa();

        Kamar kamar = new Kamar();
        kamar.setHarga(500000);

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.of(user));

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.of(kamar));

        when(pemesananRepo.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        Pemesanan result =
                service.savePemesanan(
                        p,
                        1L,
                        1L);

        assertEquals(
                1500000,
                result.getTotalBiaya());
    }

    @Test
    void savePemesananShouldSaveSuccessfully() {

        Pemesanan p = new Pemesanan();
        p.setDurasi(2);
        p.setTotalBiaya(100000);

        CalonPenyewa user = new CalonPenyewa();

        Kamar kamar = new Kamar();
        kamar.setHarga(50000);

        when(calonPenyewaRepo.findById(1L))
                .thenReturn(Optional.of(user));

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.of(kamar));

        service.savePemesanan(
                p,
                1L,
                1L);

        verify(pemesananRepo)
                .save(p);
    }

    @Test
    void getAllPemesananShouldReturnList() {

        when(pemesananRepo.findAll())
                .thenReturn(
                        Arrays.asList(
                                new Pemesanan(),
                                new Pemesanan()));

        assertEquals(
                2,
                service.getAllPemesanan().size());
    }

    @Test
    void getPemesananByIdShouldReturnData() {

        Pemesanan p = new Pemesanan();

        when(pemesananRepo.findById(1L))
                .thenReturn(Optional.of(p));

        assertNotNull(
                service.getPemesananById(1L));
    }

    @Test
    void getPemesananByIdShouldThrowException() {

        when(pemesananRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.getPemesananById(1L));
    }

    @Test
    void updatePemesananShouldUpdateDurationAndPrice() {

        Kamar kamar = new Kamar();
        kamar.setHarga(100000);

        Pemesanan existing = new Pemesanan();
        existing.setKamar(kamar);

        Pemesanan baru = new Pemesanan();
        baru.setDurasi(4);

        when(pemesananRepo.findById(1L))
                .thenReturn(Optional.of(existing));

        service.updatePemesanan(
                1L,
                baru);

        verify(pemesananRepo)
                .save(existing);
    }

    @Test
    void deletePemesananShouldDeleteData() {

        Pemesanan p = new Pemesanan();

        when(pemesananRepo.findById(1L))
                .thenReturn(Optional.of(p));

        service.deletePemesananById(1L);

        verify(pemesananRepo)
                .delete(p);
    }

    @Test
    void getPemesananByKamarIdShouldFilterCorrectly() {

        Kamar k1 = new Kamar();
        k1.setId(1L);

        Kamar k2 = new Kamar();
        k2.setId(2L);

        Pemesanan p1 = new Pemesanan();
        p1.setKamar(k1);

        Pemesanan p2 = new Pemesanan();
        p2.setKamar(k2);

        when(pemesananRepo.findAll())
                .thenReturn(
                        Arrays.asList(p1, p2));

        List<Pemesanan> result =
                service.getPemesananByKamarId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void calculateTotalBiayaShouldReturnCorrectValue() {

        Kamar kamar = new Kamar();
        kamar.setHarga(200000);

        float total =
                service.calculateTotalBiaya(
                        kamar,
                        5);

        assertEquals(
                1000000,
                total);
    }

    @Test
    void calculateTotalBiayaShouldThrowWhenRoomNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateTotalBiaya(
                        null,
                        5));
    }

    @Test
    void calculateTotalBiayaShouldThrowWhenDurationInvalid() {

        Kamar kamar = new Kamar();
        kamar.setHarga(100000);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateTotalBiaya(
                        kamar,
                        0));
    }
}