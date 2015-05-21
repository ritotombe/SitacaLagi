package com.example.rito.sitaca;

/**
 * Created by rito on 4/10/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "sitacadb";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_PEMINJAMAN = "CREATE TABLE " + Peminjaman.TABLE  + "("
                + Peminjaman.KEY_ID_peminjaman + " INTEGER PRIMARY KEY autoincrement,"
                + Peminjaman.KEY_ID_buku + " INTEGER DEFAULT 1, "
                + Peminjaman.KEY_ID_anggota  + " INTEGER DEFAULT 1,"
                + Peminjaman.KEY_tanggalPinjam + " TEXT, "
                + Peminjaman.KEY_tanggalKembali + " TEXT, "
                + Peminjaman.KEY_ratingBuku + " INTEGER,"
                + Peminjaman.KEY_status + " INTEGER,"
                + "FOREIGN KEY("+Peminjaman.KEY_ID_anggota +") REFERENCES Anggota("+Anggota.KEY_ID_anggota+") ON DELETE SET DEFAULT,"
                + "FOREIGN KEY("+Peminjaman.KEY_ID_buku +") REFERENCES Buku("+Buku.KEY_ID_buku+") ON DELETE SET DEFAULT)";

        db.execSQL(CREATE_TABLE_PEMINJAMAN);

        String CREATE_TABLE_PERTUKARAN_BUKU = "CREATE TABLE " + PertukaranBuku.TABLE  + "("
                + PertukaranBuku.KEY_ID_pertukaranBuku + " INTEGER PRIMARY KEY autoincrement,"
                + PertukaranBuku.KEY_ID_buku + " INTEGER DEFAULT 1, "
                + PertukaranBuku.KEY_ID_TBSekitar  + " INTEGER DEFAULT 1,"
                + PertukaranBuku.KEY_tanggalPinjam + " TEXT, "
                + PertukaranBuku.KEY_tanggalKembali + " TEXT, "
                + PertukaranBuku.KEY_status + " INTEGER, "
                + "FOREIGN KEY("+PertukaranBuku.KEY_ID_TBSekitar +") REFERENCES TbSekitar("+TbSekitar.KEY_ID_tb_sekitar+") ON DELETE SET DEFAULT,"
                + "FOREIGN KEY("+PertukaranBuku.KEY_ID_buku +") REFERENCES Buku("+Buku.KEY_ID_buku+") ON DELETE SET DEFAULT)";

        db.execSQL(CREATE_TABLE_PERTUKARAN_BUKU);

        String CREATE_TABLE_BUKU = "CREATE TABLE " + Buku.TABLE  + "("
                + Buku.KEY_ID_buku + " INTEGER PRIMARY KEY autoincrement, "
                + Buku.KEY_ID_kategori + " INTEGER DEFAULT 1 , "
                + Buku.KEY_ID_donatur + " INTEGER DEFAULT 1, "
                + Buku.KEY_ID_tamanBaca + " INTEGER, "
                + Buku.KEY_judulBuku  + " TEXT,"
                + Buku.KEY_pengarang + " TEXT, "
                + Buku.KEY_penerbit + " TEXT, "
                + Buku.KEY_tahunTerbit + " TEXT, "
                + Buku.KEY_edisi + " TEXT, "
                + Buku.KEY_kodeISBN + " TEXT, "
                + Buku.KEY_kodeISBN13 + " TEXT, "
                + Buku.KEY_poin + " INTEGRER, "
                + Buku.KEY_status + " TEXT, "
                + "FOREIGN KEY("+Buku.KEY_ID_kategori +") REFERENCES Kategori("+Kategori.KEY_ID_kategori+") ON DELETE SET DEFAULT, "
                + "FOREIGN KEY("+Buku.KEY_ID_donatur +") REFERENCES Donatur("+Donatur.KEY_ID_donatur+") ON DELETE SET DEFAULT, "
                + "FOREIGN KEY("+Buku.KEY_ID_tamanBaca +") REFERENCES TamanBaca("+TamanBaca.KEY_ID_tamanBaca+"))";

        db.execSQL(CREATE_TABLE_BUKU);

        String CREATE_TABLE_KATEGORI = "CREATE TABLE " + Kategori.TABLE  + "("
                + Kategori.KEY_ID_kategori + " INTEGER PRIMARY KEY autoincrement,"
                + Kategori.KEY_nama + " TEXT, "
                + Kategori.KEY_deskripsi + " TEXT " + ")";

        db.execSQL(CREATE_TABLE_KATEGORI);

        String CREATE_TABLE_DONATUR = "CREATE TABLE " + Donatur.TABLE  + "("
                + Donatur.KEY_ID_donatur + " INTEGER PRIMARY KEY autoincrement,"
                + Donatur.KEY_nama + " TEXT, "
                + Donatur.KEY_alamat + " TEXT, "
                + Donatur.KEY_jenisDonatur + " TEXT, "
                + Donatur.KEY_namaKontak + " TEXT, "
                + Donatur.KEY_noTelp + " TEXT " +
                ")";
//
        db.execSQL(CREATE_TABLE_DONATUR);

        String CREATE_TABLE_TAMAN_BACA = "CREATE TABLE " + TamanBaca.TABLE  + "("
                + TamanBaca.KEY_ID_tamanBaca + " INTEGER PRIMARY KEY autoincrement,"
                + TamanBaca.KEY_ID_user + " INTEGER,"
                + TamanBaca.KEY_nama + " TEXT,"
                + TamanBaca.KEY_alamat + " TEXT, "
                + TamanBaca.KEY_twitter + " TEXT, "
                + TamanBaca.KEY_facebook + " TEXT, "
                + "FOREIGN KEY("+TamanBaca.KEY_ID_user +") REFERENCES User("+User.KEY_ID_user+"))";

        db.execSQL(CREATE_TABLE_TAMAN_BACA);

        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_ID_user + " INTEGER PRIMARY KEY autoincrement,"
                + User.KEY_nama + " TEXT,"
                + User.KEY_alamat + " TEXT,"
                + User.KEY_jabatan + " TEXT, "
                + User.KEY_noTelp + " TEXT, "
                + User.KEY_email + " TEXT, "
                + User.KEY_password + " TEXT " + ")";

        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_ANGGOTA = "CREATE TABLE " + Anggota.TABLE  + "("
                + Anggota.KEY_ID_anggota + " INTEGER PRIMARY KEY autoincrement,"
                + Anggota.KEY_ID_tamanBaca + " INTEGER, "
                + Anggota.KEY_namaLengkap + " TEXT, "
                + Anggota.KEY_namaPanggilan + " TEXT, "
                + Anggota.KEY_tanggalLahir + " TEXT, "
                + Anggota.KEY_alamat + " TEXT,"
                + Anggota.KEY_jenisKelamin + " TEXT,"
                + Anggota.KEY_noTelp + " TEXT,"
                + Anggota.KEY_sekolah + " TEXT,"
                + Anggota.KEY_jumlahPoin + " INTEGER,"
                + Anggota.KEY_status + " TEXT, "
                + Anggota.KEY_kelas + " TEXT, "
                + "FOREIGN KEY("+Anggota.KEY_ID_tamanBaca +") REFERENCES TamanBaca("+TamanBaca.KEY_ID_tamanBaca+"))";

        db.execSQL(CREATE_TABLE_ANGGOTA);

        String CREATE_TABLE_LOGHARIAN = "CREATE TABLE " + LogHarian.TABLE  + "("
                + LogHarian.KEY_ID_logHarian + " INTEGER PRIMARY KEY autoincrement,"
                + LogHarian.KEY_ID_tamanBaca + " INTEGER, "
                + LogHarian.KEY_tanggal + " TEXT, "
                + LogHarian.KEY_jumlah_kehadiran + " INTEGER, "
                + LogHarian.KEY_realisasi_jamBuka + " TEXT,"
                + LogHarian.KEY_realisasi_jamTutup + " TEXT,"
                + "FOREIGN KEY("+LogHarian.KEY_ID_tamanBaca +") REFERENCES TamanBaca("+TamanBaca.KEY_ID_tamanBaca+"))";

        db.execSQL(CREATE_TABLE_LOGHARIAN);

        String CREATE_TABLE_TAMAN_BACA_SEKITAR = "CREATE TABLE " + TbSekitar.TABLE  + "("
                + TbSekitar.KEY_ID_tb_sekitar + " INTEGER PRIMARY KEY autoincrement,"
                + TbSekitar.KEY_ID_taman_baca + " INTEGER, "
                + TbSekitar.KEY_nama + " TEXT, "
                + TbSekitar.KEY_alamat + " TEXT, "
                + TbSekitar.KEY_twitter + " TEXT, "
                + TbSekitar.KEY_facebook + " TEXT, "
                + TbSekitar.KEY_no_telepon + " TEXT " + ")";

        db.execSQL(CREATE_TABLE_TAMAN_BACA_SEKITAR);

        String CREATE_TABLE_KEGIATAN = "CREATE TABLE " + Kegiatan.TABLE  + "("
                + Kegiatan.KEY_ID_kegiatan + " INTEGER PRIMARY KEY autoincrement,"
                + Kegiatan.KEY_ID_tamanBaca + " INTEGER, "
                + Kegiatan.KEY_nama + " TEXT, "
                + Kegiatan.KEY_tanggal + " TEXT, "
                + Kegiatan.KEY_deskripsi + " TEXT, "
                + "FOREIGN KEY("+Kegiatan.KEY_ID_tamanBaca +") REFERENCES TamanBaca("+TamanBaca.KEY_ID_tamanBaca+"))";

        db.execSQL(CREATE_TABLE_KEGIATAN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Peminjaman.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Buku.TABLE);
        // Create tables again
        onCreate(db);
    }
    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

}
