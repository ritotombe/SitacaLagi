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
 * Created by AYU ARYSTYANA on 11/04/2015.
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

public class BukuDAO {

    public class BukuRating
    {
        Buku buku;
        float rating;
        public BukuRating(Buku buku, float rating)
        {
            this.buku = buku;
            this.rating = rating;

        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public Buku getBuku() {
            return buku;
        }

        public void setBuku(Buku buku) {
            this.buku = buku;
        }
    }

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {Buku.KEY_ID_buku,
                                    Buku.KEY_ID_kategori,
                                    Buku.KEY_ID_donatur,
                                    Buku.KEY_ID_tamanBaca,
                                    Buku.KEY_judulBuku,
                                    Buku.KEY_pengarang,
                                    Buku.KEY_penerbit,
                                    Buku.KEY_tahunTerbit,
                                    Buku.KEY_edisi,
                                    Buku.KEY_kodeISBN,
                                    Buku.KEY_kodeISBN13,
                                    Buku.KEY_poin,
                                    Buku.KEY_status};
    public BukuDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("Buku", "Exception : " + e.getMessage());
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

    public void createBuku(int id_kategori, int id_donatur, int id_tamanBaca, String judul_buku, String pengarang,
                           String penerbit, String tahun_terbit, String edisi, String ISBN, String ISBN13, int poin, String status){
        ContentValues values = new ContentValues();
        values.put(Buku.KEY_ID_kategori, id_kategori);
        values.put(Buku.KEY_ID_donatur, id_donatur);
        values.put(Buku.KEY_ID_tamanBaca, id_tamanBaca);
        values.put(Buku.KEY_judulBuku, judul_buku);
        values.put(Buku.KEY_pengarang, pengarang);
        values.put(Buku.KEY_penerbit, penerbit);
        values.put(Buku.KEY_tahunTerbit, tahun_terbit);
        values.put(Buku.KEY_edisi, edisi);
        values.put(Buku.KEY_kodeISBN, ISBN);
        values.put(Buku.KEY_kodeISBN13, ISBN13);
        values.put(Buku.KEY_poin, poin);
        values.put(Buku.KEY_status, status);
        //long insertId =
        mDatabase.insert(Buku.TABLE, null, values);
        //long insertId = mDatabase.insert(Anggota.TABLE, null, values);
        //Cursor cursor = mDatabase.query(Buku.TABLE,
                //mAllColumns, Buku.KEY_ID_buku + " = " + insertId, null, null, null, null);
        //cursor.moveToFirst();
        //Buku newBuku = cursorToBuku(cursor);
        //cursor.close();
    }

    public void deleteBuku(Buku buku){
        long id = buku.getId_buku();
        mDatabase.delete(Buku.TABLE, Buku.KEY_ID_buku +" = "+ id, null);
        System.out.println("Buku dengan id = "+id+" telah dihapus");
    }

    public List<Buku> getAllBuku() {
        List<Buku> listBuku = new ArrayList<Buku>();

        Cursor cursor = mDatabase.query(Buku.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Buku buku = cursorToBuku(cursor);
            listBuku.add(buku);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listBuku;
    }

    public List<BukuRating> getAllBukuWithRating() {
        List<BukuRating> listBukuRating = new ArrayList<>();
        String query = "SELECT " +
                "B.id_buku, " +
                "id_kategori, " +
                "id_donatur, " +
                "id_tamanBaca, " +
                "judul_buku, " +
                "pengarang, " +
                "penerbit, " +
                "tahun_terbit, " +
                "edisi, " +
                "ISBN, " +
                "ISBN13, " +
                "poin, " +
                "B.status, " +
                "avg(rating_buku) " +
                "FROM Buku B, Peminjaman P WHERE B.id_buku = P.id_buku GROUP BY B.id_buku";
        Cursor cursor = mDatabase.rawQuery(query, null);
        //Log.d("cek", cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Buku buku = cursorToBuku(cursor);
            //Log.d("cek", ""+cursor.getFloat(13));
            BukuRating bukuRating = new BukuRating(buku, cursor.getFloat(13));
            listBukuRating.add(bukuRating);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listBukuRating;
    }


    public Buku getBuku(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Buku.TABLE, new String[]
                { Buku.KEY_ID_buku, Buku.KEY_ID_kategori, Buku.KEY_ID_donatur, Buku.KEY_ID_tamanBaca, Buku.KEY_judulBuku, Buku.KEY_pengarang,
                  Buku.KEY_penerbit, Buku.KEY_tahunTerbit, Buku.KEY_edisi, Buku.KEY_kodeISBN, Buku.KEY_kodeISBN13, Buku.KEY_poin,
                  Buku.KEY_status}, Buku.KEY_ID_buku + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();
       // Log.d("tes",""+);
        Buku buku = new Buku(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                Integer.parseInt(cursor.getString(11)),
                cursor.getString(12));
        //db.close();
        cursor.close();
        return buku;
    }

    public int updateBuku(Buku buku) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Buku.KEY_ID_buku, buku.getId_buku());
        values.put(Buku.KEY_ID_kategori, buku.getId_kategori());
        values.put(Buku.KEY_ID_donatur, buku.getId_donatur());
        values.put(Buku.KEY_ID_tamanBaca, buku.getId_tamanBaca());
        values.put(Buku.KEY_judulBuku, buku.getJudul_buku());
        values.put(Buku.KEY_pengarang, buku.getPengarang());
        values.put(Buku.KEY_penerbit, buku.getPenerbit());
        values.put(Buku.KEY_tahunTerbit, buku.getTahun_terbit());
        values.put(Buku.KEY_edisi, buku.getEdisi());
        values.put(Buku.KEY_kodeISBN, buku.getISBN());
        values.put(Buku.KEY_kodeISBN13, buku.getISBN13());
        values.put(Buku.KEY_poin, buku.getPoin());
        values.put(Buku.KEY_status, buku.getStatus());

        int rowsAffected = db.update(buku.TABLE, values, buku.KEY_ID_buku + "=?", new String[] { String.valueOf(buku.getId_buku()) });
        //db.close();

        return rowsAffected;
    }

   private Buku cursorToBuku(Cursor cursor) {
        Buku buku = new Buku( (int)cursor.getLong(0),
                (int)cursor.getLong(1),
                (int)cursor.getLong(2),
                (int)cursor.getLong(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                (int)cursor.getLong(11),
                cursor.getString(12));


        // get Buku or Anggota by id
        /*long PeminjamanId = cursor.getLong(7);
        CompanyDAO dao = new CompanyDAO(mContext);
        Company company = dao.getCompanyById(companyId);
        if(company != null)
            employee.setCompany(company);*/

        return buku;
    }
}