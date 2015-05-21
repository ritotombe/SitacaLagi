package com.example.rito.sitaca;

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
import java.util.List;
import java.util.StringTokenizer;


public class UbahPertukaranBukuActivity extends ActionBarActivity implements DatePickerFragment.TheListener {

    String date;
    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pertukaran_buku);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_pertukaran_buku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void returnDate(String date) {
        this.date = date;
        if(fragment.tglKembali)
            fragment.tanggalKembali.setText(this.date);
        else if(fragment.tglPinjam)
            fragment.tanggalPinjam.setText(this.date);

    }
    public PlaceholderFragment getFragment()
    {
        return fragment;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private TbSekitarDAO TBSekitarDao;
        private BukuDAO bukuDao;
        private PertukaranBukuDAO pertukaranBukuDAO;
        List<Buku> listBuku = new ArrayList<Buku>();
        List<TbSekitar> listTBSekitar = new ArrayList<TbSekitar>();
        private int idBuku,idTBSekitar;
        private String judul, nama;
        private View rootView;
        private AutoCompleteTextView spinnerBuku, spinnerTBSekitar;
        private EditText tanggalPinjam, tanggalKembali;
        private boolean tglPinjam = false, tglKembali =false;
        String day, month, year;
        int hariPinjam, bulanPinjam, tahunPinjam, hariKembali, bulanKembali, tahunKembali;
        PertukaranBuku pertukaranBuku;
        Buku bukuLama, bukuBaru;

        public PlaceholderFragment() {
        }

        //cari TBSekitar
        public Boolean cekTBSekitar(int id)
        {
            Boolean bool= false;
            if(listTBSekitar.size()>0)
            {
                for(int i = 0; i < listTBSekitar.size();i++)
                {
                    if(listTBSekitar.get(i).getId_tb_sekitar() == id)
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
            rootView = inflater.inflate(R.layout.fragment_ubah_pertukaran_buku, container, false);

            // Layout
            spinnerBuku = (AutoCompleteTextView) rootView.findViewById(R.id.buku);
            spinnerTBSekitar = (AutoCompleteTextView) rootView.findViewById(R.id.tamanBaca);
            tanggalPinjam = (EditText) rootView.findViewById(R.id.tanggalPinjam);
            tanggalKembali =  (EditText) rootView.findViewById(R.id.tanggalKembali);
            TBSekitarDao = new TbSekitarDAO(rootView.getContext());
            bukuDao = new BukuDAO(rootView.getContext());
            pertukaranBukuDAO = new PertukaranBukuDAO(rootView.getContext());
            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            //Log.d("tet", "" + id);
            //Mengisi field secara otomatis
            pertukaranBuku = pertukaranBukuDAO.getPertukaranBuku(id);
            //Menyimpan buku lama
            bukuLama = bukuDao.getBuku(pertukaranBuku.getId_buku());

            //Log.d("Tet2", ""+TBSekitarDao.getTbSekitar(
            //        (pertukaranBuku.getId_TBSekitar())).getNama());

            spinnerTBSekitar.setText(pertukaranBuku.getId_TBSekitar()+" - "+
                    TBSekitarDao.getTbSekitar(
                            (pertukaranBuku.getId_TBSekitar())).getNama());
            spinnerBuku.setText(pertukaranBuku.getId_buku()+" - "+
                    bukuDao.getBuku
                            (pertukaranBuku.getId_buku())
                            .getJudul_buku());
            tanggalPinjam.setText(pertukaranBuku.getTanggal_pinjam());
            tanggalKembali.setText(pertukaranBuku.getTanggal_kembali());

            //DatePicker
            tanggalPinjam.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DialogFragment picker = new DatePickerFragment();
                    Bundle args = new Bundle();
                    if (!String.valueOf(tanggalPinjam.getText()).equalsIgnoreCase("")) {

                        StringTokenizer st = new StringTokenizer(String.valueOf(tanggalPinjam.getText()), "/");
                        while (st.hasMoreTokens()) {
                            day = st.nextToken();
                            month = st.nextToken();
                            year = st.nextToken();
                        }

                        args.putString("day", day);
                        args.putString("month", month);
                        args.putString("year", year);

                    } else {
                        args = null;
                    }
                    picker.setArguments(args);
                    picker.show(getActivity().getFragmentManager(), "Tanggal Pinjam");
                    tglPinjam = true;
                    tglKembali = false;
                }
            });

            tanggalKembali.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick (View v){
                    DialogFragment picker = new DatePickerFragment();
                    Bundle args = new Bundle();
                    if (!String.valueOf(tanggalKembali.getText()).equalsIgnoreCase("")) {

                        StringTokenizer st = new StringTokenizer(String.valueOf(tanggalKembali.getText()), "/");
                        while (st.hasMoreTokens()) {
                            day = st.nextToken();
                            month = st.nextToken();
                            year = st.nextToken();
                        }

                        args.putString("day", day);
                        args.putString("month", month);
                        args.putString("year", year);

                    } else {
                        args = null;
                    }
                    picker.setArguments(args);
                    picker.show(getActivity().getFragmentManager(), "Tanggal Kembali");
                    tglPinjam = false;
                    tglKembali = true;
                }
            });


            listBuku = bukuDao.getAllBuku();
            List<String> listBukuString = new ArrayList<String>();
            for(int i = 0; i < listBuku.size(); i++)
            {
                if(listBuku.get(i).getId_buku()!=1) {
                    listBukuString.add(listBuku.get(i).getId_buku() + " - " + listBuku.get(i).getJudul_buku());
                }
            }
            listTBSekitar = TBSekitarDao.getAllTbSekitar();
            List<String> listTBSekitarString = new ArrayList<String>();
            for(int i = 0; i < listTBSekitar.size(); i++)
            {
                if(listTBSekitar.get(i).getId_tb_sekitar()!=1) {
                    listTBSekitarString.add(listTBSekitar.get(i).getId_tb_sekitar() + " - " + listTBSekitar.get(i).getNama());
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

            //Here comes the TBsekitar
            ArrayAdapter<String> TBSekitarAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            listTBSekitarString
                    );
            TBSekitarAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            spinnerTBSekitar.setAdapter(TBSekitarAdapter);
            spinnerTBSekitar.setThreshold(1);
            spinnerTBSekitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    spinnerTBSekitar.showDropDown();
                }
            });

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
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
                        tahunPinjam = Integer.parseInt(year);}


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
                        tahunKembali = Integer.parseInt(year);}



                    boolean cek = true;//baru mulai sini
                    try {
                        String[] splitString = String.valueOf(spinnerTBSekitar.getText()).split(" - ");
                        idTBSekitar = Integer.parseInt(splitString[0]);
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
                    else if(String.valueOf(spinnerBuku.getText()).equalsIgnoreCase("")){
                        cek = false;
                        errorToast("Kesalahan : Buku belum terisi.");
                    }
                    else if (!cekBuku(idBuku)&&!String.valueOf(spinnerBuku.getText()).equalsIgnoreCase("")) {
                        cek = false;
                        errorToast("Kesalahan : Buku "+String.valueOf(spinnerBuku.getText())+" tidak terdaftar.");
                    }


                    if (TBSekitarDao.getAllTbSekitar().size() == 0){
                        cek = false;
                        errorToast("Peringatan : Belum ada taman baca, silahkan masukkan data taman baca baru.");
                    }
                    else if(String.valueOf(spinnerTBSekitar.getText()).equalsIgnoreCase("")){
                        cek = false;
                        errorToast("Kesalahan : Taman Baca belum terisi.");
                    }
                    else if (!cekTBSekitar(idTBSekitar)&&!String.valueOf(spinnerTBSekitar.getText()).equalsIgnoreCase("")) {
                        cek = false;
                        errorToast("Kesalahan : Taman Baca "+spinnerTBSekitar.getText()+" tidak terdaftar.");
                    }

                    if(String.valueOf(tanggalPinjam.getText()).equalsIgnoreCase("")){
                        cek = false;
                        errorToast("Kesalahan : Tanggal pinjam belum terisi.");
                    }
                    if(String.valueOf(tanggalKembali.getText()).equalsIgnoreCase("")){
                        cek = false;
                        errorToast("Kesalahan : Tanggal kembali belum terisi.");
                    }
                    if(!String.valueOf(tanggalPinjam.getText()).equalsIgnoreCase("") &&
                            !String.valueOf(tanggalKembali.getText()).equalsIgnoreCase("")){
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
                    }
                    if (cek) {
                        Buku buku = bukuDao.getBuku(idBuku);
                        TbSekitar TBSekitar= TBSekitarDao.getTbSekitar(idTBSekitar);
                        if (!buku.getStatus().equalsIgnoreCase("Tersedia") && bukuLama.getId_buku()!=idBuku) {
                            errorToast("Kesalahan : Buku tidak tersedia.");
                        }
                        else {
                            //Set data lama
                            bukuLama.setStatus("Tersedia");
                            bukuDao.updateBuku(bukuLama);
                            //set data baru
                            bukuBaru = bukuDao.getBuku(idBuku);
                            bukuBaru.setStatus("Dipinjam");
                            bukuDao.updateBuku(bukuBaru);

                            pertukaranBuku.setId_buku(idBuku);
                            pertukaranBuku.setId_TBSekitar(idTBSekitar);
                            pertukaranBuku.setTanggal_pinjam(String.valueOf(tanggalPinjam.getText()));
                            pertukaranBuku.setTanggal_kembali(String.valueOf(tanggalKembali.getText()));
                            pertukaranBukuDAO.updatePertukaranBuku(pertukaranBuku);

                            Toast.makeText(
                                    rootView.getContext(),
                                    "Pertukaran buku telah diubah.",
                                    Toast.LENGTH_SHORT
                            ).show();
                            TBSekitarDao.close();
                            pertukaranBukuDAO.close();
                            bukuDao.close();
                            Intent intent = new Intent(rootView.getContext(), KelolaPertukaranBukuActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                    }
                }}
            );
            return rootView;
        }


    }
}
