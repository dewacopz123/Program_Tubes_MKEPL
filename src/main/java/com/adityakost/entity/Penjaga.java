package com.adityakost.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "tbl_penjaga")

public class Penjaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPenjaga;

    @Column(length = 10, nullable = false)
    private String usernamePenjaga;

    @Column(nullable = false)
    private String passwordPenjaga;

    @Column(nullable = false)
    private String emailPenjaga;

    @Column(length = 10)
    private String jenisKelaminPenjaga;

    @Column(length = 100)
    private String alamatPenjaga;

    @Column(length = 15)
    private String phoneNumberPenjaga;

    // Getter and Setter for each field

    public Long getIdPenjaga() {
        return getIdPenjaga();
    }

    public void setIdPenjaga(Long idPenjaga) {
        this.idPenjaga = idPenjaga;
    }

    public String getUsername() {
        return usernamePenjaga;
    }

    public void setUsernamePenjaga(String usernamePenjaga) {
        this.usernamePenjaga = usernamePenjaga;
    }

    public String getPasswordPenjaga() {
        return passwordPenjaga;
    }

    public void setPasswordPenjaga(String passwordPenjaga) {
        this.passwordPenjaga = passwordPenjaga;
    }

    public String getEmailPenjaga() {
        return emailPenjaga;
    }

    public void setEmailPenjaga(String emailPenjaga) {
        this.emailPenjaga = emailPenjaga;
    }

    public String getJenisKelaminPenjaga() {
        return jenisKelaminPenjaga;
    }

    public void setJenisKelaminPenjaga(String jenisKelaminPenjaga) {
        this.jenisKelaminPenjaga = jenisKelaminPenjaga;
    }

    public String getAlamatPenjaga() {
        return alamatPenjaga;
    }

    public void setAlamatPenjaga(String alamatPenjaga) {
        this.alamatPenjaga = alamatPenjaga;
    }

    public String getPhoneNumberPenjaga() {
        return phoneNumberPenjaga;
    }

    public void setPhoneNumberPenjaga(String phoneNumberPenjaga) {
        this.phoneNumberPenjaga = phoneNumberPenjaga;
    }


}
