package com.adityakost.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_kamar")
public class Kamar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kamar")
    private Long id;

    @Column(name = "nomor_kamar", nullable = false, unique = true)
    private String nomorKamar;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private float harga;

    @OneToOne(
            mappedBy = "kamar",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Gambar gambar;

    @OneToMany(
            mappedBy = "kamar",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Pemesanan> pemesanans;

    public Kamar() {
    }

    public Kamar(String nomorKamar, String type, float harga) {
        this.nomorKamar = nomorKamar;
        this.type = type;
        this.harga = harga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getHarga() {
        return harga;
    }

    public void setHarga(float harga) {
        this.harga = harga;
    }

    public Gambar getGambar() {
        return gambar;
    }

    public void setGambar(Gambar gambar) {
        this.gambar = gambar;
    }

    public List<Pemesanan> getPemesanans() {
        return pemesanans;
    }

    public void setPemesanans(List<Pemesanan> pemesanans) {
        this.pemesanans = pemesanans;
    }
}