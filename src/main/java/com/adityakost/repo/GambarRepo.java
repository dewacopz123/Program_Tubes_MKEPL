package com.adityakost.repo;

import com.adityakost.entity.Gambar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GambarRepo extends JpaRepository<Gambar, Long> {
    
    // Menambahkan metode untuk menemukan gambar berdasarkan ID kamar
    Gambar findByKamarId(Long kamarId);
}
