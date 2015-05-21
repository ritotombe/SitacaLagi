package com.example.rito.sitaca;

import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rito on 5/19/2015.
 */
public class BuatLaporan {
    private int id_tb;
    Context context;
    public BuatLaporan(int id_tb, Context context)
    {
        this.id_tb = id_tb;
        this.context = context;
    }

    public String ratingBuku(){
        BukuDAO bukuDao =  new BukuDAO(context);
        List<BukuDAO.BukuRating>  listBukuRating = bukuDao.getAllBukuWithRating();
        String theQuery="";
        if(listBukuRating.size()>0) {
            for (int i = 0 ; i < listBukuRating.size();i++){
                theQuery += "(" +
                        listBukuRating.get(i).getBuku().getId_buku() + ", " +
                        listBukuRating.get(i).getBuku().getId_kategori() +", " +
                        id_tb + ", '" +
                        listBukuRating.get(i).getBuku().getJudul_buku() +"', '" +
                        listBukuRating.get(i).getBuku().getPengarang() + "', '" +
                        listBukuRating.get(i).getBuku().getTahun_terbit() +"', '" +
                        listBukuRating.get(i).getBuku().getPenerbit() +"', " +
                        listBukuRating.get(i).getBuku().getPoin()+", '" +
                        listBukuRating.get(i).getBuku().getStatus()+ "', " +
                        listBukuRating.get(i).getRating()+")"  ;
                if(i<listBukuRating.size()-1)
                {
                    theQuery += ",";
                }
            }
        }
        bukuDao.close();
        return theQuery;
    }

    public String kategori(){
        KategoriDAO kategoriDao =  new KategoriDAO(context);
        List<Kategori>  listKategori = kategoriDao.getAllKategori();
        String theQuery="";
        if(listKategori.size()>0) {
            for (int i = 0 ; i < listKategori.size();i++){
                theQuery += "(" +
                        listKategori.get(i).getId_kategori() +", " +
                        id_tb +", '" +
                        listKategori.get(i).getNama()+ "', '" +
                        listKategori.get(i).getDeskripsi()+"')"  ;
                if(i<listKategori.size()-1)
                {
                    theQuery += ",";
                }
            }
        }
        kategoriDao.close();
        return theQuery;
    }

    public String kegiatan(){
        KegiatanDAO kegiatanDao =  new KegiatanDAO(context);
        List<Kegiatan>  listKegiatan = kegiatanDao.getAllKegiatan();
        String theQuery="";
        if(listKegiatan.size()>0) {
            for (int i = 0 ; i < listKegiatan.size();i++){
                theQuery += "(" +
                        listKegiatan.get(i).getId_kegiatan() +", " +
                        id_tb +", '" +
                        listKegiatan.get(i).getNama()+ "', '" +
                        listKegiatan.get(i).getTanggal()+ "', '" +
                        listKegiatan.get(i).getDeskripsi()+"')"  ;
                if(i<listKegiatan.size()-1)
                {
                    theQuery += ",";
                }
            }
        }
        kegiatanDao.close();
        return theQuery;
    }
    public String logHarian(){
        LogHarianDAO LogHarianDao =  new LogHarianDAO(context);
        List<LogHarian>  listLogHarian = LogHarianDao.getAllLogHarian();
        String theQuery="";
        if(listLogHarian.size()>0) {
            for (int i = 0 ; i < listLogHarian.size();i++){
                theQuery += "(" +
                        listLogHarian.get(i).getId_logHarian() +", " +
                        id_tb +", '" +
                        listLogHarian.get(i).getRealisasi_jamBuka()+ "', '" +
                        listLogHarian.get(i).getRealisasi_jamTutup()+ "', '" +
                        listLogHarian.get(i).getTanggal()+ "', " +
                        listLogHarian.get(i).getJumlah_kehadiran()+")"  ;
                if(i<listLogHarian.size()-1)
                {
                    theQuery += ",";
                }
            }
        }
        LogHarianDao.close();
        return theQuery;
    }

    public String summary(){
        DonaturDAO donaturDAO = new DonaturDAO(context);
        DonaturDAO.SummaryBuku summaryBuku = donaturDAO.summaryBuku();
        String theQuery="";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        if(!summaryBuku.equals(null)) {
            theQuery += "(" +
                    id_tb + ", " +
                    summaryBuku.getIndividu() + ", " +
                    summaryBuku.getOrganisasi() + ", " +
                    summaryBuku.getBeli_sendiri() + ", " +
                    summaryBuku.getYayasan() + ", '" +
                    dateFormat.format(date).toString() + "')";
        }
        donaturDAO.close();
        return theQuery;
    }


}
