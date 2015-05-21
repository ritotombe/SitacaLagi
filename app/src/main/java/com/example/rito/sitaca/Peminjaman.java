package com.example.rito.sitaca;

/**
 * Created by rito on 4/10/2015.
 */
public class Peminjaman {
    // Labels table name
    public static final String TABLE = "Peminjaman";

    // Labels Table Columns names
    public static final String KEY_ID_peminjaman = "id_peminjaman";
    public static final String KEY_ID_anggota = "id_anggota";
    public static final String KEY_ID_buku = "id_buku";
    public static final String KEY_tanggalPinjam = "tanggal_pinjam";
    public static final String KEY_tanggalKembali = "tanggal_kembali";
    public static final String KEY_ratingBuku = "rating_buku";
    public static final String KEY_status = "status";


    // property help us to keep data
    public int id_peminjaman;
    public int id_anggota;
    public int id_buku;
    public String tanggal_pinjam;
    public String tanggal_kembali;
    public int rating;
    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Peminjaman(int id_peminjaman, int id_buku, int id_anggota,
                      String tanggal_pinjam, String tanggal_kembali,
                      int rating, int status){
        this.id_peminjaman = id_peminjaman;
        this.id_buku = id_buku;
        this.id_anggota = id_anggota;
        this.tanggal_kembali = tanggal_kembali;
        this.tanggal_pinjam = tanggal_pinjam;
        this.rating = rating;
        this.status = status;

    }

    public int getId_peminjaman() {
        return id_peminjaman;
    }

    public void setId_peminjaman(int id_peminjaman) {
        this.id_peminjaman = id_peminjaman;
    }

    public int getId_anggota() {
        return id_anggota;
    }

    public void setId_anggota(int id_anggota) {
        this.id_anggota = id_anggota;
    }

    public int getId_buku() {
        return id_buku;
    }

    public void setId_buku(int id_buku) {
        this.id_buku = id_buku;
    }

    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam(String tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
