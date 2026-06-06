package com.adityakost.service;

import com.adityakost.entity.Gambar;
import com.adityakost.entity.Kamar;
import com.adityakost.repo.GambarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GambarService {

    @Autowired
    private GambarRepo gambarRepo;

    public Gambar getGambarByKamarId(Long kamarId) {
        return gambarRepo.findByKamarId(kamarId);
    }

    public String getGambarBase64(Long kamarId) {
        Gambar gambar = gambarRepo.findByKamarId(kamarId);
        return gambar != null ? Base64.getEncoder().encodeToString(gambar.getDataGambar()) : null;
    }

    public void saveGambarWithKamar(Kamar kamar,
            MultipartFile gambarFile)
            throws IOException {

        String fileType = gambarFile.getContentType();

        if (fileType == null || !fileType.startsWith("image")) {
            throw new IllegalArgumentException(
                    "Hanya file gambar yang diperbolehkan");
        }

        Gambar gambar = gambarRepo.findByKamarId(kamar.getId());

        if (gambar == null) {
            gambar = new Gambar();
            gambar.setKamar(kamar);
        }

        gambar.setNama(gambarFile.getOriginalFilename());
        gambar.setDataGambar(gambarFile.getBytes());

        gambarRepo.save(gambar);
    }

    // Method baru untuk mendapatkan gambar semua kamar dalam bentuk map
    public Map<Long, String> getGambarMapForKamar(List<Kamar> kamarList) {
        Map<Long, String> gambarMap = new HashMap<>();

        for (Kamar kamar : kamarList) {
            String base64Image = getGambarBase64(kamar.getId()); // Ambil gambar base64 untuk setiap kamar
            gambarMap.put(kamar.getId(), base64Image); // Tambahkan ke map
        }

        return gambarMap;
    }

    public String getBase64Image(Long kamarId) {
        Gambar gambar = gambarRepo.findByKamarId(kamarId);
        return gambar != null ? Base64.getEncoder().encodeToString(gambar.getDataGambar()) : null;
    }

}
