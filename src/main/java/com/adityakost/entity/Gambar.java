package com.adityakost.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_gambar")
public class Gambar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Lob
    @Column(name = "data_gambar", columnDefinition = "LONGBLOB")
    private byte[] dataGambar;

    @OneToOne
    @JoinColumn(name = "id_kamar")
    private Kamar kamar;

    // Getters and Setters

    public byte[] getDataGambar() {
        return dataGambar;
    }

    public void setDataGambar(byte[] dataGambar) {
        this.dataGambar = dataGambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public void setKamar(Kamar kamar) {
        this.kamar = kamar;
    }
    
}
