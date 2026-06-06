package com.adityakost.repo;

import com.adityakost.entity.Kamar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KamarRepo extends JpaRepository<Kamar, Long> {
    
}
