package com.adityakost.repo;

import com.adityakost.entity.Penjaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenjagaRepo extends JpaRepository<Penjaga, Long> {
    boolean existsByUsernamePenjaga(String usernamePenjaga);
    boolean existsByEmailPenjaga(String emailPenjaga);
    boolean existsByPhoneNumberPenjaga(String phoneNumberPenjaga);
    Penjaga findByEmailPenjagaAndPasswordPenjaga(String emailPenjaga, String passwordPenjaga);
}
