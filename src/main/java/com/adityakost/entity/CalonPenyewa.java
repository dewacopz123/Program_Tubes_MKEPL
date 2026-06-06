package com.adityakost.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_calon_penyewa")
public class CalonPenyewa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalonPenyewa;

    @Column(length = 10, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String email;

    @Column(length = 10)
    private String jenisKelamin;

    @Column(length = 100)
    private String alamat;

    @Column(length = 15)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Complaint> complaints;

    @OneToMany(mappedBy = "calonPenyewa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pemesanan> pemesanans;

    // Getters dan Setters
    public Long getIdCalonPenyewa() {
        return idCalonPenyewa;
    }

    public void setIdCalonPenyewa(Long idCalonPenyewa) {
        this.idCalonPenyewa = idCalonPenyewa;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Pemesanan> getPemesanans() {
        return pemesanans;
    }

    public void setPemesanans(List<Pemesanan> pemesanans) {
        this.pemesanans = pemesanans;
    }
}
