package com.adityakost.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_pemesanan")
public class Pemesanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pemesanan")
    private Long idPemesanan;

    @ManyToOne
    @JoinColumn(name = "id_kamar", nullable = false)
    private Kamar kamar;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_calon_penyewa", nullable = false)
    private CalonPenyewa calonPenyewa;

    @Column(name = "durasi", nullable = false, columnDefinition = "INT DEFAULT 1")
    private int durasi;
    

    @Column(name = "jumlah_biaya", nullable = false)
    private float totalBiaya;
    public Pemesanan() {
        this.durasi = 1; 
    }
    
    // Getters dan Setters
    public Long getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(Long idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public void setKamar(Kamar kamar) {
        this.kamar = kamar;
    }

    public CalonPenyewa getCalonPenyewa() {
        return calonPenyewa;
    }

    public void setCalonPenyewa(CalonPenyewa calonPenyewa) {
        this.calonPenyewa = calonPenyewa;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public float getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(float totalBiaya) {
        this.totalBiaya = totalBiaya;
    }
}
