package com.adityakost.repo;

import com.adityakost.entity.Pemesanan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PemesananRepo extends JpaRepository<Pemesanan, Long> {
    void deleteByKamarId(Long idKamar);
}
