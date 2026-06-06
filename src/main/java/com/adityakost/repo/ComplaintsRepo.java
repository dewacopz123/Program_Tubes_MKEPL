package com.adityakost.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Complaint;

@Repository
public interface ComplaintsRepo extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser(CalonPenyewa user);
}
