package com.adityakost.repo;

import com.adityakost.entity.CalonPenyewa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalonPenyewaRepo extends JpaRepository<CalonPenyewa, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    CalonPenyewa findByEmailAndPassword(String email, String password);
    @Query("SELECT p.calonPenyewa FROM Pemesanan p WHERE p.calonPenyewa IS NOT NULL")
    List<CalonPenyewa> findCalonPenyewaWithPemesanan();
    @Query("DELETE FROM Pemesanan p WHERE p.calonPenyewa.idCalonPenyewa = :calonPenyewaId")
    void deleteByCalonPenyewaId(Long calonPenyewaId);


    
}
