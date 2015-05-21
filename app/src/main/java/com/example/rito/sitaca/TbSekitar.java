package com.example.rito.sitaca;

/**
 * Created by Asus on 4/12/2015.
 */
public class TbSekitar {

    // Labels table name
    public static final String TABLE = "TbSekitar";

    // Labels Table Columns names
    public static final String KEY_ID_tb_sekitar = "id_tb_sekitar";
    public static final String KEY_ID_taman_baca = "id_taman_baca";
    public static final String KEY_nama = "nama";
    public static final String KEY_alamat = "alamat";
    public static final String KEY_twitter = "twitter";
    public static final String KEY_facebook = "facebook";
    public static final String KEY_no_telepon = "no_telepon";

    // property help us to keep data
    public int id_tb_sekitar;
    public int id_taman_baca;
    public String nama;
    public String alamat;
    public String twitter;
    public String facebook;
    public String no_telepon;

    public TbSekitar(int id_tb_sekitar, int id_taman_baca,String nama, String alamat, String twitter, String facebook, String no_telepon){
        this.id_tb_sekitar = id_tb_sekitar;
        this.id_taman_baca =  id_taman_baca;
        this.nama = nama;
        this.alamat = alamat;
        this.twitter = twitter;
        this.facebook = facebook;
        this.no_telepon = no_telepon;
    }

    public int getId_tb_sekitar() {
        return id_tb_sekitar;
    }

    public void setId_tb_sekitar(int id_tb_sekitar) {
        this.id_tb_sekitar = id_tb_sekitar;
    }

    public int getId_taman_baca() {
        return id_taman_baca;
    }

    public void setId_taman_baca(int id_taman_baca) {
        this.id_taman_baca = id_taman_baca;
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

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }
}
//