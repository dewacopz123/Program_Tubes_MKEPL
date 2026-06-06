package com.adityakost.service;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Kamar;
import com.adityakost.entity.Pemesanan;
import com.adityakost.repo.CalonPenyewaRepo;
import com.adityakost.repo.KamarRepo;
import com.adityakost.repo.PemesananRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PemesananService {

    private final PemesananRepo pemesananRepo;
    private final KamarRepo kamarRepo;
    private final CalonPenyewaRepo calonPenyewaRepo;

    public PemesananService(
            PemesananRepo pemesananRepo,
            KamarRepo kamarRepo,
            CalonPenyewaRepo calonPenyewaRepo) {

        this.pemesananRepo = pemesananRepo;
        this.kamarRepo = kamarRepo;
        this.calonPenyewaRepo = calonPenyewaRepo;
    }

    /**
     * Simpan pemesanan baru
     */
    public Pemesanan savePemesanan(
            Pemesanan pemesanan,
            Long idCalonPenyewa,
            Long idKamar) {

        if (pemesanan == null) {
            throw new IllegalArgumentException("Data pemesanan tidak boleh kosong");
        }

        CalonPenyewa calonPenyewa = calonPenyewaRepo.findById(idCalonPenyewa)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Calon Penyewa tidak ditemukan dengan ID: "
                                        + idCalonPenyewa));

        Kamar kamar = kamarRepo.findById(idKamar)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Kamar tidak ditemukan dengan ID: "
                                        + idKamar));

        if (pemesanan.getDurasi() <= 0) {
            throw new IllegalArgumentException(
                    "Durasi harus lebih besar dari 0");
        }

        pemesanan.setCalonPenyewa(calonPenyewa);
        pemesanan.setKamar(kamar);

        if (pemesanan.getTotalBiaya() <= 0) {
            pemesanan.setTotalBiaya(
                    calculateTotalBiaya(
                            kamar,
                            pemesanan.getDurasi()));
        }

        return pemesananRepo.save(pemesanan);
    }

    /**
     * Ambil semua pemesanan
     */
    public List<Pemesanan> getAllPemesanan() {
        return pemesananRepo.findAll();
    }

    /**
     * Ambil pemesanan berdasarkan ID
     */
    public Pemesanan getPemesananById(Long id) {

        return pemesananRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pemesanan tidak ditemukan dengan ID: "
                                        + id));
    }

    /**
     * Update pemesanan
     */
    public Pemesanan updatePemesanan(Long id, Pemesanan dataBaru) {

        Pemesanan existing = getPemesananById(id);

        existing.setDurasi(dataBaru.getDurasi());

        if (existing.getKamar() != null) {
            existing.setTotalBiaya(
                    calculateTotalBiaya(
                            existing.getKamar(),
                            dataBaru.getDurasi()));
        }

        return pemesananRepo.save(existing);
    }

    /**
     * Hapus pemesanan
     */
    public void deletePemesananById(Long id) {

        Pemesanan pemesanan = getPemesananById(id);

        pemesananRepo.delete(pemesanan);
    }

    /**
     * Ambil pemesanan berdasarkan kamar
     */
    public List<Pemesanan> getPemesananByKamarId(Long idKamar) {

        return pemesananRepo.findAll()
                .stream()
                .filter(p ->
                        p.getKamar() != null &&
                        p.getKamar().getId().equals(idKamar))
                .toList();
    }

    /**
     * Hitung total biaya
     */
    public float calculateTotalBiaya(
            Kamar kamar,
            int durasi) {

        if (kamar == null) {
            throw new IllegalArgumentException(
                    "Kamar tidak boleh null");
        }

        if (durasi <= 0) {
            throw new IllegalArgumentException(
                    "Durasi harus lebih besar dari 0");
        }

        return kamar.getHarga() * durasi;
    }
}