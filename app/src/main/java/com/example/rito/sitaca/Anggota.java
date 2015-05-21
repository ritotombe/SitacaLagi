package com.example.rito.sitaca;

/**
 * Created by theo on 4/10/2015.
 */
public class Anggota {
    // Labels table name
    public static final String TABLE = "Anggota";

    // Labels Table Columns names
    public static final String KEY_ID_anggota = "id_anggota";
    public static final String KEY_ID_tamanBaca = "id_tamanBaca";
    public static final String KEY_namaLengkap = "nama_lengkap";
    public static final String KEY_namaPanggilan = "nama_panggilan";
    public static final String KEY_tanggalLahir = "tanggal_lahir";
    public static final String KEY_alamat = "alamat";
    public static final String KEY_jenisKelamin = "jenis_kelamin";
    public static final String KEY_noTelp = "no_telp";
    public static final String KEY_sekolah = "sekolah";
    public static final String KEY_kelas = "kelas";
    public static final String KEY_jumlahPoin = "jumlahPoin";
    public static final String KEY_status = "status";

    // property help us to keep data
    public int id_anggota;
    public int id_tamanBaca;
    public String nama_lengkap;
    public String nama_panggilan;
    public String tanggal_lahir;
    public String alamat;
    public String jenis_kelamin;
    public String no_telp;
    public String sekolah;
    public String kelas;
    public int jumlahPoin;
    public String status;

    public Anggota(int id_anggota, int id_tamanBaca, String nama_lengkap, String nama_panggilan, String tanggal_lahir, String alamat,
                   String jenis_kelamin, String no_telp, String sekolah, String kelas, int jumlahPoin, String status) {
        this.id_anggota = id_anggota;
        this.id_tamanBaca = id_tamanBaca;
        this.nama_lengkap = nama_lengkap;
        this.nama_panggilan = nama_panggilan;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.no_telp = no_telp;
        this.sekolah = sekolah;
        this.kelas = kelas;
        this.jumlahPoin = jumlahPoin;
        this.status = status;
    }

    public int getId_tamanBaca() {
        return id_tamanBaca;
    }

    public void setId_tamanBaca(int id_tamanBaca) {
        this.id_tamanBaca = id_tamanBaca;
    }

    public int getJumlahPoin() {
        return jumlahPoin;
    }

    public void setJumlahPoin(int jumlahPoin) {
        this.jumlahPoin = jumlahPoin;
    }

    public int getId_anggota() {
        return id_anggota;
    }

    public void setId_anggota(int id_anggota) {
        this.id_anggota = id_anggota;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getNama_panggilan() {
        return nama_panggilan;
    }

    public void setNama_panggilan(String nama_panggilan) {
        this.nama_panggilan = nama_panggilan;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getSekolah() {
        return sekolah;
    }

    public void setSekolah(String sekolah) {
        this.sekolah = sekolah;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

