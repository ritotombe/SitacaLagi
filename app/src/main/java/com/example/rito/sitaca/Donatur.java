package com.example.rito.sitaca;

/**
 * Created by AYU ARYSTYANA on 11/04/2015.
 */
public class Donatur {
    // Labels table name
    public static final String TABLE = "Donatur";

    // Labels Table Columns names
    public static final String KEY_ID_donatur = "id_donatur";
    public static final String KEY_nama = "nama";
    public static final String KEY_alamat = "alamat";
    public static final String KEY_jenisDonatur = "jenisDonatur";
    public static final String KEY_namaKontak = "namaKontak";
    public static final String KEY_noTelp = "noTelp";

    // property help us to keep data
    public int id_donatur;
    public String nama;
    public String alamat;
    public String jenisDonatur;
    public String namaKontak;
    public String noTelp;

    public Donatur(int id_donatur, String nama, String alamat, String jenisDonatur,
                   String namaKontak, String noTelp){
        this.id_donatur = id_donatur;
        this.nama = nama;
        this.alamat = alamat;
        this.jenisDonatur = jenisDonatur;
        this.namaKontak = namaKontak;
        this.noTelp = noTelp;
    }

    public int getId_donatur() {
        return id_donatur;
    }

    public void setId_donatur(int id_donatur) {
        this.id_donatur = id_donatur;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenisDonatur() {
        return jenisDonatur;
    }

    public void setJenisDonatur(String jenisDonatur) {
        this.jenisDonatur = jenisDonatur;
    }

    public String getNamaKontak() {
        return namaKontak;
    }

    public void setNamaKontak(String namaKontak) { this.namaKontak = namaKontak; }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
