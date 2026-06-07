package com.adityakost.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.adityakost.entity.Kamar;
import com.adityakost.repo.KamarRepo;
import com.adityakost.repo.PemesananRepo;

@ExtendWith(MockitoExtension.class)
class KamarServiceTest {

    @Mock
    private KamarRepo kamarRepo;

    @Mock
    private GambarService gambarService;

    @Mock
    private PemesananRepo pemesananRepo;

    @InjectMocks
    private KamarService kamarService;

    @Test
    void getAllKamarShouldReturnList() {

        List<Kamar> kamarList =
                Arrays.asList(
                        new Kamar(),
                        new Kamar());

        when(kamarRepo.findAll())
                .thenReturn(kamarList);

        List<Kamar> result =
                kamarService.getAllKamar();

        assertEquals(2, result.size());
    }

    @Test
    void getKamarByIdShouldReturnKamar() {

        Kamar kamar = new Kamar();
        kamar.setId(1L);

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.of(kamar));

        Kamar result =
                kamarService.getKamarById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getKamarByIdShouldThrowException() {

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> kamarService.getKamarById(1L));

        assertTrue(
                ex.getMessage()
                        .contains("Kamar tidak ditemukan"));
    }

    @Test
    void saveKamarShouldCallRepository() {

        Kamar kamar = new Kamar();

        kamarService.saveKamar(kamar);

        verify(kamarRepo).save(kamar);
    }

    @Test
    void saveKamarWithGambarShouldThrowIfNomorKamarNull() {

        MultipartFile file =
                new MockMultipartFile(
                        "gambar",
                        "a.jpg",
                        "image/jpeg",
                        "data".getBytes());

        assertThrows(
                IllegalArgumentException.class,
                () -> kamarService.saveKamarWithGambar(
                        null,
                        "VIP",
                        100000,
                        file));
    }

    @Test
    void saveKamarWithGambarShouldThrowIfNomorKamarBlank() {

        assertThrows(
                IllegalArgumentException.class,
                () -> kamarService.saveKamarWithGambar(
                        "",
                        "VIP",
                        100000,
                        null));
    }

    @Test
    void saveKamarWithGambarShouldThrowIfTypeNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> kamarService.saveKamarWithGambar(
                        "A1",
                        null,
                        100000,
                        null));
    }

    @Test
    void saveKamarWithGambarShouldThrowIfTypeBlank() {

        assertThrows(
                IllegalArgumentException.class,
                () -> kamarService.saveKamarWithGambar(
                        "A1",
                        "",
                        100000,
                        null));
    }

    @Test
    void saveKamarWithGambarShouldThrowIfHargaZero() {

        assertThrows(
                IllegalArgumentException.class,
                () -> kamarService.saveKamarWithGambar(
                        "A1",
                        "VIP",
                        0,
                        null));
    }

    @Test
    void saveKamarWithGambarShouldThrowIfHargaNegative() {

        assertThrows(
                IllegalArgumentException.class,
                () -> kamarService.saveKamarWithGambar(
                        "A1",
                        "VIP",
                        -1,
                        null));
    }

    @Test
    void saveKamarWithGambarWithoutImage() throws IOException {

        Kamar saved = new Kamar();
        saved.setId(1L);

        when(kamarRepo.save(any(Kamar.class)))
                .thenReturn(saved);

        Kamar result =
                kamarService.saveKamarWithGambar(
                        "A1",
                        "VIP",
                        100000,
                        null);

        assertNotNull(result);

        verify(kamarRepo).save(any(Kamar.class));

        verify(gambarService, never())
                .saveGambarWithKamar(any(), any());
    }

    @Test
    void saveKamarWithGambarWithImage() throws IOException {

        Kamar saved = new Kamar();
        saved.setId(1L);

        when(kamarRepo.save(any(Kamar.class)))
                .thenReturn(saved);

        MultipartFile file =
                new MockMultipartFile(
                        "gambar",
                        "gambar.jpg",
                        "image/jpeg",
                        "abc".getBytes());

        kamarService.saveKamarWithGambar(
                "A1",
                "VIP",
                100000,
                file);

        verify(gambarService)
                .saveGambarWithKamar(saved, file);
    }

    @Test
    void deleteKamarShouldDeleteBookingAndKamar() {

        Kamar kamar = new Kamar();
        kamar.setId(1L);

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.of(kamar));

        kamarService.deleteKamar(1L);

        verify(pemesananRepo)
                .deleteByKamarId(1L);

        verify(kamarRepo)
                .delete(kamar);
    }

    @Test
    void deleteKamarShouldThrowIfNotFound() {

        when(kamarRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> kamarService.deleteKamar(1L));
    }
}