package com.example.rito.sitaca;

/**
 * Created by rito on 4/11/2015.
 */

//Here comes the CRUDs
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PeminjamanDAO {

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {Peminjaman.KEY_ID_peminjaman,
                                    Peminjaman.KEY_ID_buku,
                                    Peminjaman.KEY_ID_anggota,
                                    Peminjaman.KEY_tanggalPinjam,
                                    Peminjaman.KEY_tanggalKembali,
                                    Peminjaman.KEY_ratingBuku,
                                    Peminjaman.KEY_status};
    public PeminjamanDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("Peminjaman","Exception : " + e.getMessage() );
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

    public void createPeminjaman(int id_buku, int id_anggota, String tanggalKembali, String tanggalPinjam, int ratingBuku){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Peminjaman.KEY_ID_buku, id_buku);
        values.put(Peminjaman.KEY_ID_anggota, id_anggota);
        values.put(Peminjaman.KEY_tanggalPinjam, tanggalPinjam);
        values.put(Peminjaman.KEY_tanggalKembali, tanggalKembali);
        values.put(Peminjaman.KEY_ratingBuku,ratingBuku);
        values.put(Peminjaman.KEY_status,1);
       // long insertId =
       mDatabase.insert(Peminjaman.TABLE, null, values);
        //Cursor cursor = mDatabase.query(Peminjaman.TABLE,
               // mAllColumns, Peminjaman.KEY_ID_peminjaman + " = " + insertId, null, null, null, null);
        //cursor.moveToFirst();
        //Peminjaman newPeminjaman = cursorToPeminjaman(cursor);
        //cursor.close();
    }

    public void deletePeminjaman(Peminjaman peminjaman){
        long id = peminjaman.getId_peminjaman();
        mDatabase.delete(peminjaman.TABLE, peminjaman.KEY_ID_peminjaman +" = "+ id, null);
        System.out.println("Peminjaman dengan id = "+id+" telah dihapus");
    }

    public List<Peminjaman> getAllPeminjaman() {
        List<Peminjaman> listPeminjaman = new ArrayList<Peminjaman>();

        Cursor cursor = mDatabase.query(Peminjaman.TABLE,
                mAllColumns, Peminjaman.KEY_status + "= 1", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Peminjaman peminjaman = cursorToPeminjaman(cursor);
            listPeminjaman.add(peminjaman);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listPeminjaman;
    }

    public Peminjaman getPeminjaman(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Peminjaman.TABLE, new String[]
                { Peminjaman.KEY_ID_peminjaman, Peminjaman.KEY_ID_buku, Peminjaman.KEY_ID_anggota,
                  Peminjaman.KEY_tanggalPinjam, Peminjaman.KEY_tanggalKembali, Peminjaman.KEY_ratingBuku, Peminjaman.KEY_status}, Peminjaman.KEY_ID_peminjaman + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Peminjaman peminjaman = new Peminjaman(Integer.parseInt(cursor.getString(0)),
                                               Integer.parseInt(cursor.getString(1)),
                                               Integer.parseInt(cursor.getString(2)),
                                               cursor.getString(3),
                                               cursor.getString(4),
                                               Integer.parseInt(cursor.getString(5)),
                                               cursor.getInt(6));
       // db.close();
        cursor.close();
        return peminjaman;
    }

    public Boolean isMeminjam(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Peminjaman.TABLE, new String[]
                { Peminjaman.KEY_ID_peminjaman, Peminjaman.KEY_ID_buku, Peminjaman.KEY_ID_anggota,
                        Peminjaman.KEY_tanggalPinjam, Peminjaman.KEY_tanggalKembali, Peminjaman.KEY_ratingBuku, Peminjaman.KEY_status}, Peminjaman.KEY_ID_anggota+ "=?  AND " + Peminjaman.KEY_status +" = 1", new String[] { String.valueOf(id) }, null, null,null,"1");

        if (cursor.getCount()!=0)
            return true;
        else

        return false;
    }




    public int updatePeminjaman(Peminjaman peminjaman) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Peminjaman.KEY_ID_peminjaman, peminjaman.getId_peminjaman());
        values.put(Peminjaman.KEY_ID_anggota, peminjaman.getId_anggota());
        values.put(Peminjaman.KEY_ID_buku, peminjaman.getId_buku());
        values.put(Peminjaman.KEY_tanggalPinjam, peminjaman.getTanggal_pinjam());
        values.put(Peminjaman.KEY_tanggalKembali, peminjaman.getTanggal_kembali());
        values.put(Peminjaman.KEY_ratingBuku, peminjaman.getRating());
        values.put(Peminjaman.KEY_status, peminjaman.getStatus());

        int rowsAffected = db.update(peminjaman.TABLE, values, peminjaman.KEY_ID_peminjaman + "=?", new String[] { String.valueOf(peminjaman.getId_peminjaman()) });
       // db.close();

        return rowsAffected;
    }


    private Peminjaman cursorToPeminjaman(Cursor cursor) {
        Peminjaman peminjaman =
                new Peminjaman((int)cursor.getLong(0),
                               (int)cursor.getLong(1),
                               (int)cursor.getLong(2),
                               cursor.getString(3),
                               cursor.getString(4),
                               (int)cursor.getLong(5),
                               cursor.getInt(6));
        return peminjaman;
    }
}
