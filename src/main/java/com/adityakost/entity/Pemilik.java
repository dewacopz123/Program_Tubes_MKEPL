package com.adityakost.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "tbl_pemilik")

public class Pemilik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPemilik;

    @Column(length = 10, nullable = false)
    private String usernamePemilik;

    @Column(nullable = false)
    private String passwordPemilik;

    @Column(nullable = false)
    private String emailPemilik;

    @Column(length = 10)
    private String jenisKelaminPemilik;

    @Column(length = 100)
    private String alamatPemilik;

    @Column(length = 15)
    private String phoneNumberPemilik;

    // Getter and Setter for each field

    public Long getIdPemilik() {
        return getIdPemilik();
    }

    public void setIdPemilik(Long idPemilik) {
        this.idPemilik = idPemilik;
    }

    public String getUsername() {
        return usernamePemilik;
    }

    public void setUsernamePemilik(String usernamePemilik) {
        this.usernamePemilik = usernamePemilik;
    }

    public String getPasswordPemilik() {
        return passwordPemilik;
    }

    public void setPasswordPemilik(String passwordPemilik) {
        this.passwordPemilik = passwordPemilik;
    }

    public String getEmailPemilik() {
        return emailPemilik;
    }

    public void setEmailPemilik(String emailPemilik) {
        this.emailPemilik = emailPemilik;
    }

    public String getJenisKelaminPemilik() {
        return jenisKelaminPemilik;
    }

    public void setJenisKelaminPemilik(String jenisKelaminPemilik) {
        this.jenisKelaminPemilik = jenisKelaminPemilik;
    }

    public String getAlamatPemilik() {
        return alamatPemilik;
    }

    public void setAlamatPemilik(String alamatPemilik) {
        this.alamatPemilik = alamatPemilik;
    }

    public String getPhoneNumberPemilik() {
        return phoneNumberPemilik;
    }

    public void setPhoneNumberPemilik(String phoneNumberPemilik) {
        this.phoneNumberPemilik = phoneNumberPemilik;
    }


}
