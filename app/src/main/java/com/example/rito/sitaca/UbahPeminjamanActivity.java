package com.example.rito.sitaca;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class UbahPeminjamanActivity extends ActionBarActivity implements DatePickerFragment.TheListener {

    String date;
    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peminjaman);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_peminjaman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    //Datessssss
    @Override
    public void returnDate(String date) {
        this.date = date;
        if(fragment.tglKembali)
            fragment.tanggalKembali.setText(this.date);
        else if(fragment.tglPinjam)
            fragment.tanggalPinjam.setText(this.date);
    }
    public String getDate()
    {
        return date;

    }
    public PlaceholderFragment getFragment()
    {
        return fragment;
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {

        private AnggotaDAO anggotaDao;
        private BukuDAO bukuDao;
        private PeminjamanDAO peminjamanDao;
        List<Buku> listBuku = new ArrayList<Buku>();
        List<Anggota> listAnggota = new ArrayList<Anggota>();
        private int idBuku,idAnggota;
        private String judul, nama;
        private View rootView;
        private AutoCompleteTextView spinnerBuku, spinnerPeminjam;
        private Spinner  rating;
        private EditText tanggalPinjam, tanggalKembali;
        private boolean tglPinjam = false, tglKembali =false;
        String day, month, year;
        int hariPinjam, bulanPinjam, tahunPinjam, hariKembali, bulanKembali, tahunKembali;
        int ratingVal;
        Peminjaman peminjaman;
        Buku bukuLama, bukuBaru;
        Anggota peminjamLama, peminjamBaru;

        public PlaceholderFragment() {
        }

        //Untuk cari anggota
        public Boolean cekAnggota(int id)
        {
            Boolean bool= false;
            if(listAnggota.size()>0)
            {
                for(int i = 0; i < listAnggota.size();i++)
                {
                    if(listAnggota.get(i).getId_anggota() == id)
                    {
                        return true;
                    }
                }
            }
            return bool;
        }

        //Untuk cari buku
        public Boolean cekBuku(int id)
        {
            Boolean bool= false;
            if(listBuku.size()>0)
            {
                for(int i = 0; i < listBuku.size();i++)
                {
                    if(listBuku.get(i).getId_buku() == id)
                    {
                        return true;
                    }
                }
            }
            return bool;
        }
        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_ubah_peminjaman, container, false);
            // Layout
            spinnerBuku = (AutoCompleteTextView) rootView.findViewById(R.id.tambahPeminjamanJudulBuku);
            spinnerPeminjam = (AutoCompleteTextView) rootView.findViewById(R.id.tambahPeminjamanNamaPeminjam);
            tanggalPinjam = (EditText) rootView.findViewById(R.id.tambahPeminjamanTanggalPeminjaman);
            tanggalKembali =  (EditText) rootView.findViewById(R.id.tambahPeminjamanTanggalKembali);
            rating = (Spinner) rootView.findViewById(R.id.tambahPeminjamanRating);
            anggotaDao = new AnggotaDAO(rootView.getContext());
            bukuDao = new BukuDAO(rootView.getContext());
            peminjamanDao = new PeminjamanDAO(rootView.getContext());
            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");


            //Mengisi field secara otomatis
            peminjaman = peminjamanDao.getPeminjaman(id);

            spinnerPeminjam.setText(peminjaman.getId_anggota()+" - "+
                    anggotaDao.getAnggota
                            (peminjaman.getId_anggota())
                            .getNama_lengkap());
            spinnerBuku.setText(peminjaman.getId_buku()+" - "+
                    bukuDao.getBuku
                            (peminjaman.getId_buku())
                            .getJudul_buku());
            tanggalPinjam.setText(peminjaman.getTanggal_pinjam());
            tanggalKembali.setText(peminjaman.getTanggal_kembali());
            rating.setSelection(peminjaman.getRating()-1);

            //Mengisi variabel lama
            bukuLama = bukuDao.getBuku(peminjaman.getId_buku());



            //DatePicker
            tanggalPinjam.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DialogFragment picker = new DatePickerFragment();
                    Bundle args = new Bundle();
                    if(!String.valueOf(tanggalPinjam.getText()).equalsIgnoreCase("")) {
                        StringTokenizer st = new StringTokenizer(String.valueOf(tanggalPinjam.getText()), "/");
                        while(st.hasMoreTokens()) {
                            day = st.nextToken();
                            month = st.nextToken();
                            year = st.nextToken();
                        }
                        args.putString("day", day);
                        args.putString("month", month);
                        args.putString("year", year);
                    }
                    else{
                        args=null;
                    }
                    picker.setArguments(args);
                    picker.show(getActivity().getFragmentManager(), "Tanggal Pinjam");
                    tglPinjam = true;
                    tglKembali = false;
                }
            });
            tanggalKembali.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment picker = new DatePickerFragment();
                    Bundle args = new Bundle();
                    if(!String.valueOf(tanggalKembali.getText()).equalsIgnoreCase("")) {
                        StringTokenizer st = new StringTokenizer(String.valueOf(tanggalKembali.getText()), "/");
                        while(st.hasMoreTokens()) {
                            day = st.nextToken();
                            month = st.nextToken();
                            year = st.nextToken();
                        }
                        args.putString("day", day);
                        args.putString("month", month);
                        args.putString("year", year);
                    }
                    else{
                        args=null;
                    }
                    picker.setArguments(args);
                    picker.show(getActivity().getFragmentManager(), "Tanggal Kembali");
                    tglPinjam = false;
                    tglKembali = true;
                }
            });

            //Here comes the ratings
            String[] ratingArray = {"1","2","3","4","5"};
            List<String> listRating = new ArrayList<String>
                    (Arrays.asList(ratingArray));
            listRating.add("Rating");
            ArrayAdapter<String> ratingSpinnerAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            //R.id.emptySpinnerValue,
                            listRating
                    );
            ratingSpinnerAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            rating.setAdapter(ratingSpinnerAdapter);
            rating.setSelection(listRating.size()-1, true);


            listBuku = bukuDao.getAllBuku();
            List<String> listBukuString = new ArrayList<String>();
            for(int i = 0; i < listBuku.size(); i++)
            {
                if(listBuku.get(i).getId_buku()!=1) {
                    listBukuString.add(listBuku.get(i).getId_buku() + " - " + listBuku.get(i).getJudul_buku());
                }
            }
            listAnggota = anggotaDao.getAllAnggota();
            List<String> listAnggotaString = new ArrayList<String>();
            for(int i = 0; i < listAnggota.size(); i++)
            {
                if(listAnggota.get(i).getId_anggota()!=1) {
                    listAnggotaString.add(listAnggota.get(i).getId_anggota() + " - " + listAnggota.get(i).getNama_lengkap());
                }
            }

            //Here comes the Bukus;
            ArrayAdapter<String> bukuAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            listBukuString
                    );
            bukuAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            spinnerBuku.setAdapter(bukuAdapter);
            spinnerBuku.setThreshold(1);

            spinnerBuku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    spinnerBuku.showDropDown();
                }
            });

            //Here comes the Anggotas;
            ArrayAdapter<String> anggotaAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            listAnggotaString
                    );
            anggotaAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            spinnerPeminjam.setAdapter(anggotaAdapter);
            spinnerPeminjam.setThreshold(1);
            spinnerPeminjam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    spinnerPeminjam.showDropDown();
                }
            });

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view){
                                              if(rating.getSelectedItem().toString().equalsIgnoreCase("Rating"))
                                                  ratingVal = 0;
                                              else
                                                  ratingVal = Integer.parseInt(rating.getSelectedItem().toString());

                                              if(!String.valueOf(tanggalPinjam.getText()).equalsIgnoreCase("")) {
                                                  StringTokenizer st = new StringTokenizer(String.valueOf(tanggalPinjam.getText()), "/");
                                                  while (st.hasMoreTokens()) {
                                                      day = st.nextToken();
                                                      month = st.nextToken();
                                                      year = st.nextToken();
                                                  }
                                                  //-----

                                                  hariPinjam = Integer.parseInt(day);
                                                  bulanPinjam = Integer.parseInt(month);
                                                  tahunPinjam = Integer.parseInt(year);
                                                  Log.d("LogPinjam", hariPinjam + " " + bulanPinjam + " " + tahunPinjam);}


                                              if (!String.valueOf(tanggalKembali.getText()).equalsIgnoreCase("")) {
                                                  StringTokenizer st1 = new StringTokenizer(String.valueOf(tanggalKembali.getText()), "/");
                                                  while (st1.hasMoreTokens()) {
                                                      day = st1.nextToken();
                                                      month = st1.nextToken();
                                                      year = st1.nextToken();
                                                  }
                                                  //---

                                                  hariKembali = Integer.parseInt(day);
                                                  bulanKembali = Integer.parseInt(month);
                                                  tahunKembali = Integer.parseInt(year);
                                                  Log.d("LogPinjam", hariKembali + " " + bulanKembali + " " + tahunKembali);}



                                              boolean cek = true;//baru mulai sini
                                              try {
                                                  String[] splitString = String.valueOf(spinnerPeminjam.getText()).split(" - ");
                                                  idAnggota = Integer.parseInt(splitString[0]);
                                                  cek= true;
                                              } catch (NumberFormatException e) {
                                                  cek= false;

                                              }

                                              try {
                                                  String[] splitString1 = String.valueOf(spinnerBuku.getText()).split(" - ");
                                                  idBuku = Integer.parseInt(splitString1[0]);
                                                  cek = true;
                                              } catch (NumberFormatException e) {
                                                  cek = false;

                                              }
                                              if(bukuDao.getAllBuku().size() == 0)
                                              {
                                                  cek = false;
                                                  errorToast("Peringatan : Belum ada buku, silahkan masukkan data buku baru.");
                                              }
                                              if (anggotaDao.getAllAnggota().size() == 0){
                                                  cek = false;
                                                  errorToast("Peringatan : Belum ada anggota, silahkan masukkan data anggota baru.");
                                              }
                                              if(String.valueOf(spinnerPeminjam.getText()).equalsIgnoreCase("")){
                                                  cek = false;
                                                  errorToast("Kesalahan : Peminjam belum terisi.");
                                              }
                                              if(String.valueOf(spinnerBuku.getText()).equalsIgnoreCase("")){
                                                  cek = false;
                                                  errorToast("Kesalahan : Buku belum terisi.");
                                              }
                                              if(String.valueOf(tanggalPinjam.getText()).equalsIgnoreCase("")){
                                                  cek = false;
                                                  errorToast("Kesalahan : Tanggal pinjam belum terisi.");
                                              }
                                              if(String.valueOf(tanggalKembali.getText()).equalsIgnoreCase("")){
                                                  cek = false;
                                                  errorToast("Kesalahan : Tanggal kembali belum terisi.");
                                              }
                                              if (!cekAnggota(idAnggota)&&!String.valueOf(spinnerPeminjam.getText()).equalsIgnoreCase("")) {
                                                  cek = false;
                                                  errorToast("Kesalahan : Anggota tidak terdaftar.");
                                              }
                                              if (!cekBuku(idBuku)&&!String.valueOf(spinnerBuku.getText()).equalsIgnoreCase("")) {
                                                  cek = false;
                                                  errorToast("Kesalahan : Buku tidak terdaftar.");
                                              }

                                              if (tahunKembali < tahunPinjam) {
                                                  errorToast("Kesalahan : Tanggal kembali harus setelah tanggal pinjam.");
                                                  cek = false;
                                              } if(tahunKembali == tahunPinjam && bulanKembali < bulanPinjam){
                                                  errorToast("Kesalahan : Tanggal kembali harus setelah tanggal pinjam.");
                                                  cek = false;
                                              } if(tahunKembali == tahunPinjam && bulanKembali == bulanPinjam && hariKembali <= hariPinjam){
                                                  errorToast("Kesalahan : Tanggal kembali harus setelah tanggal pinjam.");
                                                  cek = false;
                                              }

                                              if(cek) {
                                                  Buku buku = bukuDao.getBuku(idBuku);
                                                  Anggota anggota = anggotaDao.getAnggota(idAnggota);

                                                  if (!buku.getStatus().equalsIgnoreCase("Tersedia")&& idBuku!=peminjaman.getId_buku()) {
                                                      errorToast("Kesalahan : Buku tidak tersedia.");
                                                  } else {
                                                      //Set data lama
                                                      bukuLama.setStatus("Tersedia");
                                                      bukuDao.updateBuku(bukuLama);
                                                      //set data baru
                                                      bukuBaru = bukuDao.getBuku(idBuku);
                                                      bukuBaru.setStatus("Dipinjam");
                                                      bukuDao.updateBuku(bukuBaru);

                                                      peminjaman.setId_buku(idBuku);
                                                      peminjaman.setId_anggota(idAnggota);
                                                      peminjaman.setTanggal_pinjam(String.valueOf(tanggalPinjam.getText()));
                                                      peminjaman.setTanggal_kembali(String.valueOf(tanggalKembali.getText()));
                                                      peminjaman.setRating(ratingVal);
                                                      peminjamanDao.updatePeminjaman(peminjaman);
                                                      Toast.makeText(
                                                              rootView.getContext(),
                                                              "Peminjaman telah diubah.",
                                                              Toast.LENGTH_SHORT
                                                      ).show();
                                                      anggotaDao.close();;
                                                      peminjamanDao.close();
                                                      bukuDao.close();
                                                      Intent intent = new Intent(rootView.getContext(), PeminjamanActivity.class);
                                                      startActivity(intent);
                                                      getActivity().finish();

                                                  }

                                              }
                                          }

                                      }


            );

            return rootView;
        }
    }
}
