package com.example.rito.sitaca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TambahBukuActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_buku);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_buku, menu);
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
        List<Kategori> listKategori = new ArrayList<Kategori>();
        List<Donatur> listDonatur = new ArrayList<Donatur>();
        private int idKategori,idDonatur, idTamanBaca;
        private String namaKategori, namaDonatur;
        private View rootView;
        private AutoCompleteTextView spinnerKategori, spinnerDonatur;
        private EditText judulBuku, pengarang, penerbit, tahun_terbit, edisi, ISBN, ISBN13, poin;
        private int poinBaru = 0;
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
                    "Kesalahan : Kategori " + String.valueOf(spinnerKategori.getText()) + " tidak ditemukan.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        void errorToastDonatur(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Donatur " +String.valueOf(spinnerDonatur.getText()) + " tidak ditemukan.",
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            rootView = inflater.inflate(R.layout.fragment_tambah_buku, container, false);

            //Cuman setting layout
            spinnerKategori = (AutoCompleteTextView) rootView.findViewById(R.id.tambahBukuNamaKategori);
            spinnerDonatur = (AutoCompleteTextView) rootView.findViewById(R.id.tambahBukuNamaDonatur);
            judulBuku = (EditText)rootView.findViewById(R.id.tambahBukuJudulBuku);
            pengarang = (EditText)rootView.findViewById(R.id.tambahBukuPengarang);
            penerbit = (EditText)rootView.findViewById(R.id.tambahBukuPenerbit);
            tahun_terbit = (EditText)rootView.findViewById(R.id.tambahBukuTahunTerbit);
            edisi = (EditText)rootView.findViewById(R.id.tambahBukuEdisi);
            ISBN = (EditText)rootView.findViewById(R.id.tambahBukuISBN);
            ISBN13 = (EditText)rootView.findViewById(R.id.tambahBukuISBN13);
            poin = (EditText)rootView.findViewById(R.id.tambahBukuPoin);
            status = (Spinner) rootView.findViewById(R.id.tambahBukuStatus);


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

            tamanBaca = tamanBacaDAO.getAllTamanBaca().get(0);

            listKategori = kategoriDAO.getAllKategori();
            List<String> listKategoriString = new ArrayList<String>();
            for(int i = 0; i < listKategori.size(); i++)
            {
                if(listKategori.get(i).getId_kategori()!=1) {
                    listKategoriString.add(listKategori.get(i).getId_kategori() + " - " + listKategori.get(i).getNama());
                }
            }
            listDonatur = donaturDAO.getAllDonatur();
            List<String> listDonaturString = new ArrayList<String>();
            for(int i = 0; i < listDonatur.size(); i++)
            {
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
            if(!String.valueOf(poin.getText()).equalsIgnoreCase(""))
            {
                poinBaru=Integer.parseInt(String.valueOf(poin.getText()));
            }

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                //@Override

                public void onClick(View view){
                    boolean tes = true;
                    if(String.valueOf(judulBuku.getText()).equalsIgnoreCase("")){
                        tes = false;
                        errorToastJudulBuku();
                    }

                    if(String.valueOf(spinnerKategori.getText()).equalsIgnoreCase("")){
                        tes = false;
                        errorToastKategoriKosong();
                    }

                    if(String.valueOf(spinnerDonatur.getText()).equalsIgnoreCase("")){
                        tes = false;
                        errorToastDonaturKosong();
                    }
                    if(!(String.valueOf(spinnerKategori.getText()).equalsIgnoreCase(""))) {
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
                    if(!(String.valueOf(spinnerDonatur.getText()).equalsIgnoreCase(""))) {
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
                        bukuDAO.createBuku(
                                idKategori,
                                idDonatur,
                                tamanBaca.getId_tamanBaca(),
                                String.valueOf(judulBuku.getText()),
                                String.valueOf(pengarang.getText()),
                                String.valueOf(penerbit.getText()),
                                String.valueOf(tahun_terbit.getText()),
                                String.valueOf(edisi.getText()),
                                String.valueOf(ISBN.getText()),
                                String.valueOf(ISBN13.getText()),
                                poinBaru,
                                status.getSelectedItem().toString());
                        Intent intent = new Intent(rootView.getContext(), BukuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(
                                rootView.getContext(),
                                "Buku telah ditambahkan.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            });
            return rootView;
        }
    }
}