package com.example.rito.sitaca;

/**
 * Created by AYU ARYSTYANA on 11/04/2015.
 */
public class Kategori {
    // Labels table name
    public static final String TABLE = "Kategori";

    // Labels Table Columns names
    public static final String KEY_ID_kategori = "id_kategori";
    public static final String KEY_nama = "nama";
    public static final String KEY_deskripsi = "deskripsi";

    // property help us to keep data
    public int id_kategori;
    public String nama;
    public String deskripsi;

    public Kategori(int id_kategori, String nama, String deskripsi){
        this.id_kategori = id_kategori;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
