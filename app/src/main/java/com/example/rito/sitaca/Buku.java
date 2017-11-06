package com.example.rito.sitaca;

/**
 * Created by AYU ARYSTYANA on 10/04/2015.
 */
public class Buku {
    // Labels table name
    public static final String TABLE = "Buku";

    // Labels Table Columns names
    public static final String KEY_ID_buku = "id_buku";
    public static final String KEY_ID_kategori = "id_kategori";
    public static final String KEY_ID_donatur = "id_donatur";
    public static final String KEY_ID_tamanBaca = "id_tamanBaca";
    public static final String KEY_judulBuku = "judul_buku";
    public static final String KEY_pengarang = "pengarang";
    public static final String KEY_penerbit = "penerbit";
    public static final String KEY_tahunTerbit = "tahun_terbit";
    public static final String KEY_edisi = "edisi";
    public static final String KEY_kodeISBN = "ISBN";
    public static final String KEY_kodeISBN13 = "ISBN13";
    public static final String KEY_poin = "poin";
    public static final String KEY_status = "status";

    // property help us to keep data
    public int id_buku;
    public int id_kategori;
    public int id_donatur;
    public int id_tamanBaca;
    public String judul_buku;
    public String pengarang;
    public String penerbit;
    public String tahun_terbit ;
    public String edisi;
    public String ISBN;
    public String ISBN13;
    public int poin;
    public String status;

    public Buku(int id_buku, int id_kategori, int id_donatur, int id_tamanBaca, String judul_buku,
                String pengarang, String penerbit, String tahun_terbit, String edisi, String ISBN,
                String ISBN13, int poin, String status){
        this.id_buku = id_buku;
        this.id_kategori = id_kategori;
        this.id_donatur = id_donatur;
        this.id_tamanBaca = id_tamanBaca;
        this.judul_buku = judul_buku;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahun_terbit = tahun_terbit;
        this.edisi = edisi;
        this.ISBN = ISBN;
        this.ISBN13 = ISBN13;
        this.poin = poin;
        this.status = status;
    }
    public int getId_buku() {
        return id_buku;
    }

    public void setId_buku(int id_buku) {
        this.id_buku = id_buku;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public int getId_donatur() {
        return id_donatur;
    }

    public void setId_donatur(int id_donatur) {
        this.id_donatur = id_donatur;
    }

    public int getId_tamanBaca() {
        return id_tamanBaca;
    }

    public void setId_tamanBaca(int id_tamanBaca) {
        this.id_tamanBaca = id_tamanBaca;
    }

    public String getJudul_buku() {
        return judul_buku;
    }

    public void setJudul_buku(String judul_buku) {
        this.judul_buku = judul_buku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTahun_terbit() {
        return tahun_terbit;
    }

    public void setTahun_terbit(String tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }

    public String getEdisi() {
        return edisi;
    }

    public void setEdisi(String edisi) {
        this.edisi = edisi;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getISBN13() {
        return ISBN13;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
