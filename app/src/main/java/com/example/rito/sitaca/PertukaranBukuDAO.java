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
 * Created by theo on 4/16/2015.
 */
public class PertukaranBukuDAO {
    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {PertukaranBuku.KEY_ID_pertukaranBuku,
            PertukaranBuku.KEY_ID_TBSekitar,
            PertukaranBuku.KEY_ID_buku,
            PertukaranBuku.KEY_tanggalPinjam,
            PertukaranBuku.KEY_tanggalKembali,
            PertukaranBuku.KEY_status};

    public PertukaranBukuDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("PertukaranBuku", "Exception : " + e.getMessage());
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

    public void createPertukaranBuku(int id_TBSekitar, int id_buku,String tanggal_pinjam, String tanggal_kembali){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PertukaranBuku.KEY_ID_buku, id_buku);
        values.put(PertukaranBuku.KEY_ID_TBSekitar, id_TBSekitar);
        values.put(PertukaranBuku.KEY_tanggalPinjam, tanggal_pinjam);
        values.put(PertukaranBuku.KEY_tanggalKembali, tanggal_kembali);
        values.put(PertukaranBuku.KEY_status,1);
        mDatabase.insert(PertukaranBuku.TABLE, null, values);
    }

    public void deletePertukaranBuku(PertukaranBuku pertukaranBuku){
        long id = pertukaranBuku.getId_pertukaranBuku();
        mDatabase.delete(pertukaranBuku.TABLE, pertukaranBuku.KEY_ID_pertukaranBuku +" = "+ id, null);
        System.out.println("Pertukaran Buku dengan id = "+id+" telah dihapus");
    }

    public List<PertukaranBuku> getAllPertukaranBuku() {
        List<PertukaranBuku> listPertukaranBuku= new ArrayList<PertukaranBuku>();

        Cursor cursor = mDatabase.query(PertukaranBuku.TABLE,
                mAllColumns, PertukaranBuku.KEY_status + "= 1", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PertukaranBuku pertukaranBuku = cursorToPertukaranBuku(cursor);
            listPertukaranBuku.add(pertukaranBuku);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listPertukaranBuku;
    }

    public PertukaranBuku getPertukaranBuku(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(PertukaranBuku.TABLE, new String[]
                { PertukaranBuku.KEY_ID_pertukaranBuku, PertukaranBuku.KEY_ID_TBSekitar, PertukaranBuku.KEY_ID_buku,
                        PertukaranBuku.KEY_tanggalPinjam, PertukaranBuku.KEY_tanggalKembali, PertukaranBuku.KEY_status}, PertukaranBuku.KEY_ID_pertukaranBuku + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        PertukaranBuku pertukaranBuku = new PertukaranBuku(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5));
        //db.close();
        cursor.close();
        return pertukaranBuku;
    }

    public int updatePertukaranBuku(PertukaranBuku pertukaranBuku) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PertukaranBuku.KEY_ID_pertukaranBuku, pertukaranBuku.getId_pertukaranBuku());
        values.put(PertukaranBuku.KEY_ID_TBSekitar, pertukaranBuku.getId_TBSekitar());
        values.put(PertukaranBuku.KEY_ID_buku, pertukaranBuku.getId_buku());
        values.put(PertukaranBuku.KEY_tanggalPinjam, pertukaranBuku.getTanggal_pinjam());
        values.put(PertukaranBuku.KEY_tanggalKembali, pertukaranBuku.getTanggal_kembali());
        values.put(PertukaranBuku.KEY_status, pertukaranBuku.getStatus());

        int rowsAffected = db.update(pertukaranBuku.TABLE, values, pertukaranBuku.KEY_ID_pertukaranBuku + "=?", new String[] { String.valueOf(pertukaranBuku.getId_pertukaranBuku()) });
        //db.close();

        return rowsAffected;
    }


    private PertukaranBuku cursorToPertukaranBuku(Cursor cursor) {
        PertukaranBuku pertukaranBuku =
                new PertukaranBuku((int)cursor.getLong(0),
                        (int)cursor.getLong(1),
                        (int)cursor.getLong(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5));
        return pertukaranBuku;
    }

    public Boolean isMeminjam(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(PertukaranBuku.TABLE, new String[]
                    { PertukaranBuku.KEY_ID_pertukaranBuku, PertukaranBuku.KEY_ID_TBSekitar, PertukaranBuku.KEY_ID_buku,
                        PertukaranBuku.KEY_tanggalPinjam, PertukaranBuku.KEY_tanggalKembali, PertukaranBuku.KEY_status}, PertukaranBuku.KEY_ID_TBSekitar+ "=?  AND " + PertukaranBuku.KEY_status +" = 1", new String[] { String.valueOf(id) }, null, null,null,"1");

        if (cursor.getCount()!=0)
            return true;
        else

            return false;
    }
}
