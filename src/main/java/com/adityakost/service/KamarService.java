package com.adityakost.service;

import com.adityakost.entity.Kamar;
import com.adityakost.repo.KamarRepo;
import com.adityakost.repo.PemesananRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class KamarService {

    private final KamarRepo kamarRepo;
    private final GambarService gambarService;
    private final PemesananRepo pemesananRepo;

    @Autowired
    public KamarService(
            KamarRepo kamarRepo,
            GambarService gambarService,
            PemesananRepo pemesananRepo) {

        this.kamarRepo = kamarRepo;
        this.gambarService = gambarService;
        this.pemesananRepo = pemesananRepo;
    }

    public List<Kamar> getAllKamar() {
        return kamarRepo.findAll();
    }

    public Kamar getKamarById(Long id) {
        return kamarRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Kamar tidak ditemukan dengan ID " + id));
    }

    public Kamar saveKamar(Kamar kamar) {
        return kamarRepo.save(kamar);
    }

    public Kamar saveKamarWithGambar(
            String nomorKamar,
            String type,
            float harga,
            MultipartFile gambarFile) throws IOException {

        if (nomorKamar == null || nomorKamar.isBlank()) {
            throw new IllegalArgumentException("Nomor kamar wajib diisi");
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type kamar wajib diisi");
        }

        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih besar dari 0");
        }

        Kamar kamar = new Kamar();
        kamar.setNomorKamar(nomorKamar);
        kamar.setType(type);
        kamar.setHarga(harga);

        kamar = kamarRepo.save(kamar);

        if (gambarFile != null && !gambarFile.isEmpty()) {
            gambarService.saveGambarWithKamar(kamar, gambarFile);
        }

        return kamar;
    }

    public void deleteKamar(Long id) {

        Kamar kamar = getKamarById(id);

        pemesananRepo.deleteByKamarId(id);

        kamarRepo.delete(kamar);
    }
}