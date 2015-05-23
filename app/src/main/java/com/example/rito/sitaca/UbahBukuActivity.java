package com.example.rito.sitaca;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UbahBukuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_buku);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_buku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private BukuDAO bukuDAO;
        private KategoriDAO kategoriDAO;
        private DonaturDAO donaturDAO;
        private TamanBacaDAO tamanBacaDAO;
        private Kategori kategori;
        private Donatur donatur;
        private TamanBaca tamanBaca;
        private int poinBaru = 0;
        List<Kategori> listKategori = new ArrayList<Kategori>();
        List<Donatur> listDonatur = new ArrayList<Donatur>();
        private int idKategori,idDonatur, idTamanBaca;
        private String namaKategori, namaDonatur;
        private View rootView;
        private AutoCompleteTextView spinnerKategori, spinnerDonatur;
        private EditText judulBuku, pengarang, penerbit, tahun_terbit, edisi, ISBN, ISBN13, poin;
        private Spinner status;

        public PlaceholderFragment() {
        }

        //Untuk cari kategori
        public Boolean cekKategori(int id)
        {
            Boolean bool= false;
            if(listKategori.size()>0)
            {
                for(int i = 0; i < listKategori.size();i++)
                {
                    if(listKategori.get(i).getId_kategori() == id)
                    {
                        bool = true;
                    }
                }
            }
            return bool;
        }

        //Untuk cari donatur
        public Boolean cekDonatur(int id)
        {
            Boolean bool= false;
            if(listDonatur.size()>0)
            {
                for(int i = 0; i < listDonatur.size();i++)
                {
                    if(listDonatur.get(i).getId_donatur() == id)
                    {
                        bool = true;
                    }
                }
            }
            return bool;
        }
        void errorToastKategori(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : " + String.valueOf(spinnerKategori.getText()) + "kategori tidak ditemukan.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        void errorToastDonatur(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : " +String.valueOf(spinnerDonatur.getText()) + "donatur tidak ditemukan.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        void errorToastJudulBuku(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Judul buku belum terisi.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        void errorToastStatus(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Status belum dipilih.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        void errorToastKategoriKosong(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Kategori belum terisi.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        void errorToastDonaturKosong(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Donatur belum terisi.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
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
            rootView = inflater.inflate(R.layout.fragment_ubah_buku, container, false);

            spinnerKategori = (AutoCompleteTextView) rootView.findViewById(R.id.tambahBukuNamaKategori);
            spinnerDonatur = (AutoCompleteTextView) rootView.findViewById(R.id.tambahBukuNamaDonatur);
            judulBuku = (EditText) rootView.findViewById(R.id.tambahBukuJudulBuku);
            pengarang = (EditText) rootView.findViewById(R.id.tambahBukuPengarang);
            penerbit = (EditText) rootView.findViewById(R.id.tambahBukuPenerbit);
            tahun_terbit = (EditText) rootView.findViewById(R.id.tambahBukuTahunTerbit);
            edisi = (EditText) rootView.findViewById(R.id.tambahBukuEdisi);
            ISBN = (EditText) rootView.findViewById(R.id.tambahBukuISBN);
            ISBN13 = (EditText) rootView.findViewById(R.id.tambahBukuISBN13);
            poin = (EditText) rootView.findViewById(R.id.tambahBukuPoin);
            status = (Spinner) rootView.findViewById(R.id.tambahBukuStatus);
            final int tersedia = 0;
            final int hilang = 1;
            final int lainnya = 2;
            int ubahStatus=2;


            //Here comes the status
            String[] statusArray = {"Tersedia","Hilang","Lainnya"};
            List<String> listStatus = new ArrayList<String>
                    (Arrays.asList(statusArray));
            listStatus.add("Status*");
            ArrayAdapter<String> ratingSpinnerAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            //R.id.emptySpinnerValue,
                            listStatus
                    );
            ratingSpinnerAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            status.setAdapter(ratingSpinnerAdapter);
            status.setSelection(listStatus.size()-1, true);

            bukuDAO = new BukuDAO(rootView.getContext());
            kategoriDAO = new KategoriDAO(rootView.getContext());
            donaturDAO = new DonaturDAO(rootView.getContext());
            tamanBacaDAO = new TamanBacaDAO(rootView.getContext());

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            Log.d("tesIdIntent", "" + id);
            //Mengisi field secara otomatis
            final Buku buku = bukuDAO.getBuku(id);
            switch (buku.getStatus()){
                case "Tersedia":
                    ubahStatus = tersedia;
                    break;
                case "Hilang":
                    ubahStatus = hilang;
                    break;
                case "Lainnya":
                    ubahStatus = lainnya;
                    break;
            }

            spinnerDonatur.setText(buku.getId_donatur() + " - " + donaturDAO.getDonatur
                            (buku.getId_donatur())
                            .getNama()
            );
            spinnerKategori.setText(buku.getId_kategori() + " - " +
                    kategoriDAO.getKategori
                            (buku.getId_kategori())
                            .getNama());
            judulBuku.setText(buku.getJudul_buku());
            pengarang.setText(buku.getPengarang());
            penerbit.setText(buku.getPenerbit());
            tahun_terbit.setText(buku.getTahun_terbit());
            edisi.setText(buku.getEdisi());
            ISBN.setText(buku.getISBN());
            ISBN13.setText(buku.getISBN13());
            poin.setText("" + buku.getPoin());
            status.setSelection(ubahStatus);

            tamanBaca = tamanBacaDAO.getAllTamanBaca().get(0);

            listKategori = kategoriDAO.getAllKategori();
            List<String> listKategoriString = new ArrayList<String>();
            for (int i = 0; i < listKategori.size(); i++) {
                if(listKategori.get(i).getId_kategori()!=1) {
                    listKategoriString.add(listKategori.get(i).getId_kategori() + " - " + listKategori.get(i).getNama());
                }
            }
            listDonatur = donaturDAO.getAllDonatur();
            List<String> listDonaturString = new ArrayList<String>();
            for (int i = 0; i < listDonatur.size(); i++) {
                if(listDonatur.get(i).getId_donatur()!=1) {
                    listDonaturString.add(listDonatur.get(i).getId_donatur() + " - " + listDonatur.get(i).getNama());
                }
            }

            //Here comes the Kategori;
            ArrayAdapter<String> kategoriAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            listKategoriString
                    );
            kategoriAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            spinnerKategori.setAdapter(kategoriAdapter);
            spinnerKategori.setThreshold(1);

            spinnerKategori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    spinnerKategori.showDropDown();
                }
            });

            //Here comes the Donatur;
            ArrayAdapter<String> donaturAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            listDonaturString
                    );
            donaturAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            spinnerDonatur.setAdapter(donaturAdapter);
            spinnerDonatur.setThreshold(1);
            spinnerDonatur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    spinnerDonatur.showDropDown();
                }
            });


            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean tes = true;
                    if (String.valueOf(judulBuku.getText()).equalsIgnoreCase("")) {
                        tes = false;
                        errorToastJudulBuku();
                    }

                    if (String.valueOf(spinnerKategori.getText()).equalsIgnoreCase("")) {
                        tes = false;
                        errorToastKategoriKosong();
                    }

                    if (String.valueOf(spinnerDonatur.getText()).equalsIgnoreCase("")) {
                        tes = false;
                        errorToastDonaturKosong();
                    }
                    if (!(String.valueOf(spinnerKategori.getText()).equalsIgnoreCase(""))) {
                        try {
                            String[] splitString = String.valueOf(spinnerKategori.getText()).split(" - ");
                            idKategori = Integer.parseInt(splitString[0]);
                        } catch (NumberFormatException e) {
                            tes = false;
                            // errorToastKategori();
                        }
                        if (idKategori >= 0 || idKategori < 0) {
                            if (!cekKategori(idKategori)) {
                                tes = false;
                                errorToastKategori();
                            }
                        }
                    }
                    if (!String.valueOf(poin.getText()).equalsIgnoreCase("")) {
                        poinBaru = Integer.parseInt(String.valueOf(poin.getText()));
                    }
                    if (!(String.valueOf(spinnerDonatur.getText()).equalsIgnoreCase(""))) {
                        try {
                            String[] splitString1 = String.valueOf(spinnerDonatur.getText()).split(" - ");
                            idDonatur = Integer.parseInt(splitString1[0]);
                        } catch (NumberFormatException e) {
                            tes = false;
                            //errorToastDonatur();
                        }
                        if (idDonatur >= 0 || idDonatur < 0) {
                            if (!cekDonatur(idDonatur)) {
                                tes = false;
                                errorToastDonatur();
                            }
                        }
                    }

                    if (status.getSelectedItem().toString().equalsIgnoreCase("status*")) {
                        tes = false;
                        errorToastStatus();
                    }
                    if (tes) {
                        buku.setId_kategori(idKategori);
                        buku.setId_donatur(idDonatur);
                        buku.setJudul_buku(String.valueOf(judulBuku.getText()));
                        buku.setPengarang(String.valueOf(pengarang.getText()));
                        buku.setPenerbit(String.valueOf(penerbit.getText()));
                        buku.setTahun_terbit(String.valueOf(tahun_terbit.getText()));
                        buku.setEdisi(String.valueOf(edisi.getText()));
                        buku.setISBN(String.valueOf(ISBN.getText()));
                        buku.setISBN13(String.valueOf(ISBN13.getText()));
                        buku.setPoin(poinBaru);
                        buku.setStatus(status.getSelectedItem().toString());

                        bukuDAO.updateBuku(buku);
                        Toast.makeText(
                                rootView.getContext(),
                                "Buku telah diubah.",
                                Toast.LENGTH_SHORT
                        ).show();
                        bukuDAO.close();
                        kategoriDAO.close();
                        donaturDAO.close();
                        tamanBacaDAO.close();
                        Intent intent = new Intent(rootView.getContext(), BukuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });
            return rootView;
        }
    }
}
