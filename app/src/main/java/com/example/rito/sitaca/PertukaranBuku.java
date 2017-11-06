package com.example.rito.sitaca;

/**
 * Created by theo on 4/16/2015.
 */
public class PertukaranBuku {
    // Labels table name
    public static final String TABLE = "PertukaranBuku";

    // Labels Table Columns names
    public static final String KEY_ID_pertukaranBuku = "id_pertukaranBuku";
    public static final String KEY_ID_TBSekitar = "id_TBSekitar";
    public static final String KEY_ID_buku = "id_buku";
    public static final String KEY_tanggalPinjam = "tanggal_pinjam";
    public static final String KEY_tanggalKembali = "tanggal_kembali";
    public static final String KEY_status = "status";

    // property help us to keep data
    public int id_pertukaranBuku;
    public int id_TBSekitar;
    public int id_buku;
    public String tanggal_pinjam;
    public String tanggal_kembali;
    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PertukaranBuku(int id_pertukaranBuku, int id_TBSekitar, int id_buku,
                      String tanggal_pinjam, String tanggal_kembali, int status){
        this.id_pertukaranBuku = id_pertukaranBuku;
        this.id_buku = id_buku;
        this.id_TBSekitar = id_TBSekitar;
        this.tanggal_kembali = tanggal_kembali;
        this.tanggal_pinjam = tanggal_pinjam;
        this.status=status;
    }

    public int getId_pertukaranBuku() {
        return id_pertukaranBuku;
    }

    public void setId_pertukaranBuku(int id_pertukaranBuku) {
        this.id_pertukaranBuku = id_pertukaranBuku;
    }

    public int getId_TBSekitar() {
        return id_TBSekitar;
    }

    public void setId_TBSekitar(int id_TBSekitar) {
        this.id_TBSekitar = id_TBSekitar;
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
}
