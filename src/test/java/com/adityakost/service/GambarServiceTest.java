package com.adityakost.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.adityakost.entity.Gambar;
import com.adityakost.entity.Kamar;
import com.adityakost.repo.GambarRepo;

@ExtendWith(MockitoExtension.class)
class GambarServiceTest {

    @Mock
    private GambarRepo gambarRepo;

    @InjectMocks
    private GambarService gambarService;

    @Test
    void getGambarByKamarIdShouldReturnGambar() {

        Gambar gambar = new Gambar();

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(gambar);

        assertEquals(
                gambar,
                gambarService.getGambarByKamarId(1L));
    }

    @Test
    void getGambarBase64ShouldReturnBase64String() {

        Gambar gambar = new Gambar();

        byte[] imageBytes = "gambar".getBytes();

        gambar.setDataGambar(imageBytes);

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(gambar);

        String expected =
                Base64.getEncoder()
                        .encodeToString(imageBytes);

        assertEquals(
                expected,
                gambarService.getGambarBase64(1L));
    }

    @Test
    void getGambarBase64ShouldReturnNull() {

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(null);

        assertNull(
                gambarService.getGambarBase64(1L));
    }

    @Test
    void saveGambarWithKamarShouldCreateNewImage()
            throws IOException {

        Kamar kamar = new Kamar();
        kamar.setId(1L);

        MultipartFile file =
                new MockMultipartFile(
                        "gambar",
                        "test.jpg",
                        "image/jpeg",
                        "data".getBytes());

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(null);

        gambarService.saveGambarWithKamar(
                kamar,
                file);

        verify(gambarRepo)
                .save(any(Gambar.class));
    }

    @Test
    void saveGambarWithKamarShouldUpdateExistingImage()
            throws IOException {

        Kamar kamar = new Kamar();
        kamar.setId(1L);

        Gambar gambar = new Gambar();

        MultipartFile file =
                new MockMultipartFile(
                        "gambar",
                        "test.jpg",
                        "image/jpeg",
                        "data".getBytes());

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(gambar);

        gambarService.saveGambarWithKamar(
                kamar,
                file);

        verify(gambarRepo)
                .save(gambar);
    }

    @Test
    void saveGambarWithKamarShouldThrowExceptionForNonImage() {

        Kamar kamar = new Kamar();
        kamar.setId(1L);

        MultipartFile file =
                new MockMultipartFile(
                        "file",
                        "doc.pdf",
                        "application/pdf",
                        "test".getBytes());

        assertThrows(
                IllegalArgumentException.class,
                () -> gambarService.saveGambarWithKamar(
                        kamar,
                        file));
    }

    @Test
    void saveGambarWithKamarShouldThrowExceptionForNullContentType() {

        Kamar kamar = new Kamar();
        kamar.setId(1L);

        MultipartFile file =
                new MockMultipartFile(
                        "file",
                        "abc",
                        null,
                        "test".getBytes());

        assertThrows(
                IllegalArgumentException.class,
                () -> gambarService.saveGambarWithKamar(
                        kamar,
                        file));
    }

    @Test
    void getGambarMapForKamarShouldReturnMap() {

        Kamar kamar1 = new Kamar();
        kamar1.setId(1L);

        Kamar kamar2 = new Kamar();
        kamar2.setId(2L);

        Gambar gambar1 = new Gambar();
        gambar1.setDataGambar("a".getBytes());

        Gambar gambar2 = new Gambar();
        gambar2.setDataGambar("b".getBytes());

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(gambar1);

        when(gambarRepo.findByKamarId(2L))
                .thenReturn(gambar2);

        List<Kamar> kamarList =
                Arrays.asList(kamar1, kamar2);

        Map<Long, String> result =
                gambarService.getGambarMapForKamar(
                        kamarList);

        assertEquals(2, result.size());
    }

    @Test
    void getBase64ImageShouldReturnBase64() {

        Gambar gambar = new Gambar();

        gambar.setDataGambar(
                "test".getBytes());

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(gambar);

        assertNotNull(
                gambarService.getBase64Image(1L));
    }

    @Test
    void getBase64ImageShouldReturnNull() {

        when(gambarRepo.findByKamarId(1L))
                .thenReturn(null);

        assertNull(
                gambarService.getBase64Image(1L));
    }
}