package com.example.rito.sitaca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theo on 4/11/2015.
 */
public class AnggotaDAO {

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {Anggota.KEY_ID_anggota,
            Anggota.KEY_ID_tamanBaca,
            Anggota.KEY_namaLengkap,
            Anggota.KEY_namaPanggilan,
            Anggota.KEY_tanggalLahir,
            Anggota.KEY_alamat,
            Anggota.KEY_jenisKelamin,
            Anggota.KEY_noTelp,
            Anggota.KEY_sekolah,
            Anggota.KEY_kelas,
            Anggota.KEY_jumlahPoin,
            Anggota.KEY_status};

    public AnggotaDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();

        }
        catch (SQLException e) {
            Log.e("Anggota", "Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.execSQL("PRAGMA foreign_keys = ON;");
    }

    public void close(){
        mDbHelper.close();
    }

     public void createAnggota(int id_tamanBaca, String nama_lengkap, String nama_panggilan, String tanggal_lahir,
                                 String alamat, String jenis_kelamin, String no_telp, String sekolah, String kelas){
        ContentValues values = new ContentValues();
        values.put(Anggota.KEY_namaLengkap, nama_lengkap);
        values.put(Anggota.KEY_namaPanggilan, nama_panggilan);
         values.put(Anggota.KEY_tanggalLahir, tanggal_lahir);
         values.put(Anggota.KEY_alamat, alamat);
         values.put(Anggota.KEY_jenisKelamin, jenis_kelamin);
         values.put(Anggota.KEY_noTelp, no_telp);
         values.put(Anggota.KEY_sekolah, sekolah);
         values.put(Anggota.KEY_kelas, kelas);
         values.put(Anggota.KEY_jumlahPoin, 0);
         values.put(Anggota.KEY_status, "Aktif");
         values.put(Anggota.KEY_ID_tamanBaca, id_tamanBaca);
        //long insertId =
                mDatabase.insert(Anggota.TABLE, null, values);
        /*Cursor cursor = mDatabase.query(Anggota.TABLE,
                mAllColumns, Anggota.KEY_ID_anggota + " = " + insertId, null, null, null, null, null);
        cursor.moveToFirst();
        Anggota newAnggota = cursorToAnggota(cursor);
        cursor.close();*/
    }

    public void deleteAnggota(Anggota anggota){
        long id = anggota.getId_anggota();
        mDatabase.delete(anggota.TABLE, anggota.KEY_ID_anggota +" = "+ id, null);
        //System.out.println("Anggota dengan id = "+id+" telah dihapus");
    }

    public List<Anggota> getAllAnggota() {
        List<Anggota> listAnggota = new ArrayList<Anggota>();

        Cursor cursor = mDatabase.query(Anggota.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Anggota anggota = cursorToAnggota(cursor);
            listAnggota.add(anggota);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listAnggota;
    }

    public Anggota getAnggota(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Anggota.TABLE, new String[]
                {Anggota.KEY_ID_anggota, Anggota.KEY_ID_tamanBaca, Anggota.KEY_namaLengkap, Anggota.KEY_namaPanggilan, Anggota.KEY_tanggalLahir, Anggota.KEY_alamat,
                        Anggota.KEY_jenisKelamin, Anggota.KEY_noTelp, Anggota.KEY_sekolah, Anggota.KEY_kelas, Anggota.KEY_jumlahPoin, Anggota.KEY_status}, Anggota.KEY_ID_anggota + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

            Anggota anggota = new Anggota(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    Integer.parseInt(cursor.getString(10)),
                    cursor.getString(11));

            //db.close();
            cursor.close();
        return anggota;
    }

    public int updateAnggota(Anggota anggota) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Anggota.KEY_namaLengkap, anggota.getNama_lengkap());
        values.put(Anggota.KEY_namaPanggilan, anggota.getNama_panggilan());
        values.put(Anggota.KEY_tanggalLahir, anggota.getTanggal_lahir());
        values.put(Anggota.KEY_alamat, anggota.getAlamat());
        values.put(Anggota.KEY_jenisKelamin, anggota.getJenis_kelamin());
        values.put(Anggota.KEY_noTelp, anggota.getNo_telp());
        values.put(Anggota.KEY_sekolah, anggota.getSekolah());
        values.put(Anggota.KEY_kelas, anggota.getKelas());
        values.put(Anggota.KEY_jumlahPoin, anggota.getJumlahPoin());
        values.put(Anggota.KEY_status, anggota.getStatus());
        values.put(Anggota.KEY_ID_tamanBaca, anggota.getId_tamanBaca());

        int rowsAffected = db.update(anggota.TABLE, values, anggota.KEY_ID_anggota + "=?", new String[] { String.valueOf(anggota.getId_anggota()) });
        db.close();

        return rowsAffected;
    }

    private Anggota cursorToAnggota(Cursor cursor) {
        Anggota anggota = new Anggota((int)cursor.getLong(0),
                (int) cursor.getLong(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                (int)cursor.getLong(10),
                cursor.getString(11));

        return anggota;
    }
}
