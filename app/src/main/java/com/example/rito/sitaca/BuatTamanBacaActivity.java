package com.example.rito.sitaca;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BuatTamanBacaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_taman_baca);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        setContentView(R.layout.activity_buat_taman_baca);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buat_taman_baca, menu);
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
        private TamanBacaDAO tamanBacaDAO;
        private EditText nama, alamat, twitter, facebook;
        private View rootView;
        private int id_pengguna;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_buat_taman_baca, container, false);

            /*final EditText buatTamanBacaNama, buatTamanBacaAlamat, buatTamanBacaTwitter, buatTamanBacaFacebook;
            final View rootView = inflater.inflate(R.layout.fragment_buat_taman_baca, container, false);
            final TamanBacaDAO tamanBacaDAO = new TamanBacaDAO(rootView.getContext());
            final UserDAO userDAO = new UserDAO(rootView.getContext());
            final User user = userDAO.getAllUser().get(0);;*/

            //Cuman setting layout
            nama = (EditText) rootView.findViewById(R.id.buatTamanBacaNama);
            alamat = (EditText) rootView.findViewById(R.id.buatTamanBacaAlamat);
            twitter= (EditText) rootView.findViewById(R.id.buatTamanBacaTwitter);
            facebook = (EditText) rootView.findViewById(R.id.buatTamanBacaFacebook);
            tamanBacaDAO = new TamanBacaDAO(rootView.getContext());


            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean cek = true;

                    if(String.valueOf(nama.getText()).equals("")){
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Nama taman baca belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    if(String.valueOf(alamat.getText()).equals("")){
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Alamat taman baca belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    if(cek) {
                        tamanBacaDAO.createTamanBaca(1 , String.valueOf(nama.getText()), String.valueOf(alamat.getText()),
                        String.valueOf(twitter.getText()), String.valueOf(facebook.getText()));
                        DonaturDAO donaturDAO = new DonaturDAO(getActivity());
                        KategoriDAO kategoriDAO = new KategoriDAO(getActivity());
                        AnggotaDAO anggotaDAO = new AnggotaDAO(getActivity());
                        TbSekitarDAO tbSekitarDAO = new TbSekitarDAO(getActivity());
                        BukuDAO bukuDAO = new BukuDAO(getActivity());

                        donaturDAO.createDonatur("Donatur Tidak Terdaftar", "", "Dummy Donatur", null, "0");
                        kategoriDAO.createKategori("Tidak Ada Kategori", "Buku tersebut tidak termasuk kategori apapun");
                        anggotaDAO.createAnggota(1, "Anggota Tidak Ada", "DummyAnggota", "ddmmyyyy", "alamat", "jk", "no_telp", "sekolah", "lagi");
                        tbSekitarDAO.createTbSekitar(1, "TB Tidak Ada", "dummy", "dummy", "dummy", "dummy");
                        bukuDAO.createBuku(1, 1, 1, "Buku Tidak Ada", "dummy", "dummy", "dummy", "dummy", "dummy", "dummy", 0, "dummy");

                        donaturDAO.close();
                        kategoriDAO.close();
                        bukuDAO.close();
                        anggotaDAO.close();
                        tbSekitarDAO.close();

                        createDummy();

                        Toast.makeText(
                                rootView.getContext(),
                                "Taman baca telah dibuat.",
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });

            return rootView;
        }

        public void createDummy()
        {
            DonaturDAO donaturDAO = new DonaturDAO(getActivity());
            KategoriDAO kategoriDAO = new KategoriDAO(getActivity());
            AnggotaDAO anggotaDAO = new AnggotaDAO(getActivity());
            TbSekitarDAO tbSekitarDAO = new TbSekitarDAO(getActivity());
            BukuDAO bukuDAO = new BukuDAO(getActivity());
            PeminjamanDAO peminjamanDAO = new PeminjamanDAO(getActivity());
            PertukaranBukuDAO pertukaranBukuDAO = new PertukaranBukuDAO(getActivity());
            KegiatanDAO kegiatanDAO = new KegiatanDAO(getActivity());
            LogHarianDAO logHarianDAO = new LogHarianDAO(getActivity());

            donaturDAO.createDonatur("Donatur1","Alamat1","Individu","Kontak1","12012012");
            donaturDAO.createDonatur("Donatur2","Alamat2","1001buku","Kontak2","12012012");
            donaturDAO.createDonatur("Donatur3","Alamat3","Organisasi","Kontak3","12012012");
            donaturDAO.createDonatur("Donatur4","Alamat4","Taman Baca","Kontak4","12012012");
            donaturDAO.createDonatur("Donatur5","Alamat5","Individu","Kontak5","12012012");
            donaturDAO.createDonatur("Donatur6","Alamat6","1001buku","Kontak6","12012012");
            donaturDAO.createDonatur("Donatur7","Alamat7","Organisasi","Kontak7","12012012");
            donaturDAO.createDonatur("Donatur8","Alamat8","Taman Baca","Kontak8","12012012");
            donaturDAO.createDonatur("Donatur9","Alamat9","Individu","Kontak9","12012012");
            donaturDAO.createDonatur("Donatur10","Alamat10","1001buku","Kontak10","12012012");
            donaturDAO.createDonatur("Donatur11","Alamat11","Organisasi","Kontak11","12012012");
            donaturDAO.createDonatur("Donatur12","Alamat12","Taman Baca","Kontak12","12012012");
            donaturDAO.createDonatur("Donatur13","Alamat13","Individu","Kontak13","12012012");
            donaturDAO.createDonatur("Donatur14","Alamat14","1001buku","Kontak14","12012012");
            donaturDAO.createDonatur("Donatur15","Alamat15","Organisasi","Kontak15","12012012");
            donaturDAO.createDonatur("Donatur16","Alamat16","Taman Baca","Kontak16","12012012");
            donaturDAO.createDonatur("Donatur17","Alamat17","Individu","Kontak17","12012012");
            donaturDAO.createDonatur("Donatur18","Alamat18","1001buku","Kontak18","12012012");
            donaturDAO.createDonatur("Donatur19","Alamat19","Organisasi","Kontak19","12012012");
            donaturDAO.createDonatur("Donatur20","Alamat20","Taman Baca","Kontak20","12012012");
            donaturDAO.createDonatur("Donatur21","Alamat21","Individu","Kontak21","12012012");
            donaturDAO.createDonatur("Donatur22","Alamat22","1001buku","Kontak22","12012012");
            donaturDAO.createDonatur("Donatur23","Alamat23","Organisasi","Kontak23","12012012");
            donaturDAO.createDonatur("Donatur24","Alamat24","Taman Baca","Kontak24","12012012");
            donaturDAO.createDonatur("Donatur25","Alamat25","Individu","Kontak25","12012012");
            donaturDAO.createDonatur("Donatur26","Alamat26","1001buku","Kontak26","12012012");
            donaturDAO.createDonatur("Donatur27","Alamat27","Organisasi","Kontak27","12012012");
            donaturDAO.createDonatur("Donatur28","Alamat28","Taman Baca","Kontak28","12012012");
            donaturDAO.createDonatur("Donatur29","Alamat29","Individu","Kontak29","12012012");
            donaturDAO.createDonatur("Donatur30","Alamat30","1001buku","Kontak30","12012012");
            donaturDAO.createDonatur("Donatur31","Alamat31","Organisasi","Kontak31","12012012");
            donaturDAO.createDonatur("Donatur32","Alamat32","Taman Baca","Kontak32","12012012");
            donaturDAO.createDonatur("Donatur33","Alamat33","Individu","Kontak33","12012012");
            donaturDAO.createDonatur("Donatur34","Alamat34","1001buku","Kontak34","12012012");
            donaturDAO.createDonatur("Donatur35","Alamat35","Organisasi","Kontak35","12012012");
            donaturDAO.createDonatur("Donatur36","Alamat36","Taman Baca","Kontak36","12012012");
            donaturDAO.createDonatur("Donatur37","Alamat37","Individu","Kontak37","12012012");
            donaturDAO.createDonatur("Donatur38","Alamat38","1001buku","Kontak38","12012012");
            donaturDAO.createDonatur("Donatur39","Alamat39","Organisasi","Kontak39","12012012");
            donaturDAO.createDonatur("Donatur40","Alamat40","Taman Baca","Kontak40","12012012");
            donaturDAO.createDonatur("Donatur41","Alamat41","Individu","Kontak41","12012012");
            donaturDAO.createDonatur("Donatur42","Alamat42","1001buku","Kontak42","12012012");
            donaturDAO.createDonatur("Donatur43","Alamat43","Organisasi","Kontak43","12012012");
            donaturDAO.createDonatur("Donatur44","Alamat44","Taman Baca","Kontak44","12012012");
            donaturDAO.createDonatur("Donatur45","Alamat45","Individu","Kontak45","12012012");
            donaturDAO.createDonatur("Donatur46","Alamat46","1001buku","Kontak46","12012012");
            donaturDAO.createDonatur("Donatur47","Alamat47","Organisasi","Kontak47","12012012");
            donaturDAO.createDonatur("Donatur48","Alamat48","Taman Baca","Kontak48","12012012");
            donaturDAO.createDonatur("Donatur49","Alamat49","Individu","Kontak49","12012012");
            donaturDAO.createDonatur("Donatur50", "Alamat50", "1001buku", "Kontak50", "12012012");

            kategoriDAO.createKategori("Kategori 1", "Deskripsi 1");
            kategoriDAO.createKategori("Kategori 2", "Deskripsi 2");
            kategoriDAO.createKategori("Kategori 3", "Deskripsi 3");
            kategoriDAO.createKategori("Kategori 4", "Deskripsi 4");
            kategoriDAO.createKategori("Kategori 5", "Deskripsi 5");
            kategoriDAO.createKategori("Kategori 6", "Deskripsi 6");
            kategoriDAO.createKategori("Kategori 7", "Deskripsi 7");
            kategoriDAO.createKategori("Kategori 8", "Deskripsi 8");
            kategoriDAO.createKategori("Kategori 9", "Deskripsi 9");
            kategoriDAO.createKategori("Kategori 10","Deskripsi 10");
            kategoriDAO.createKategori("Kategori 11","Deskripsi 11");
            kategoriDAO.createKategori("Kategori 12","Deskripsi 12");
            kategoriDAO.createKategori("Kategori 13","Deskripsi 13");
            kategoriDAO.createKategori("Kategori 14","Deskripsi 14");
            kategoriDAO.createKategori("Kategori 15","Deskripsi 15");
            kategoriDAO.createKategori("Kategori 16","Deskripsi 16");
            kategoriDAO.createKategori("Kategori 17","Deskripsi 17");
            kategoriDAO.createKategori("Kategori 18","Deskripsi 18");
            kategoriDAO.createKategori("Kategori 19","Deskripsi 19");
            kategoriDAO.createKategori("Kategori 20","Deskripsi 20");
            kategoriDAO.createKategori("Kategori 21","Deskripsi 21");
            kategoriDAO.createKategori("Kategori 22","Deskripsi 22");
            kategoriDAO.createKategori("Kategori 23","Deskripsi 23");
            kategoriDAO.createKategori("Kategori 24","Deskripsi 24");
            kategoriDAO.createKategori("Kategori 25", "Deskripsi 25");

            bukuDAO.createBuku(1, 1, 1, "Judul Buku 1 ", "Pengarang 1", "Penerbit 1", "2002", "Edisi 1", "ISBN 1", "ISBN13 ", 30, "Tersedia");
            bukuDAO.createBuku(2,2,1,"Judul Buku 2","Pengarang 2","Penerbit 2","2003","Edisi 2","ISBN 2","ISBN14",30,"Tersedia");
            bukuDAO.createBuku(3,3,1,"Judul Buku 3","Pengarang 3","Penerbit 3","2004","Edisi 3","ISBN 3","ISBN15",30,"Tersedia");
            bukuDAO.createBuku(4,4,1,"Judul Buku 4","Pengarang 4","Penerbit 4","2005","Edisi 4","ISBN 4","ISBN16",30,"Tersedia");
            bukuDAO.createBuku(5,5,1,"Judul Buku 5","Pengarang 5","Penerbit 5","2006","Edisi 5","ISBN 5","ISBN17",30,"Tersedia");
            bukuDAO.createBuku(6,6,1,"Judul Buku 6","Pengarang 6","Penerbit 6","2007","Edisi 6","ISBN 6","ISBN18",30,"Tersedia");
            bukuDAO.createBuku(7,7,1,"Judul Buku 7","Pengarang 7","Penerbit 7","2008","Edisi 7","ISBN 7","ISBN19",30,"Tersedia");
            bukuDAO.createBuku(8,8,1,"Judul Buku 8","Pengarang 8","Penerbit 8","2009","Edisi 8","ISBN 8","ISBN20",30,"Tersedia");
            bukuDAO.createBuku(9,9,1,"Judul Buku 9","Pengarang 9","Penerbit 9","2000","Edisi 9","ISBN 9","ISBN21",30,"Tersedia");
            bukuDAO.createBuku(10,10,1,"Judul Buku 10","Pengarang 10","Penerbit 10","2001","Edisi 10","ISBN 10","ISBN22",30,"Tersedia");
            bukuDAO.createBuku(11,11,1,"Judul Buku 11","Pengarang 11","Penerbit 11","2002","Edisi 11","ISBN 11","ISBN23",30,"Tersedia");
            bukuDAO.createBuku(12,12,1,"Judul Buku 12","Pengarang 12","Penerbit 12","2003","Edisi 12","ISBN 12","ISBN24",30,"Tersedia");
            bukuDAO.createBuku(13,13,1,"Judul Buku 13","Pengarang 13","Penerbit 13","2004","Edisi 13","ISBN 13","ISBN25",30,"Tersedia");
            bukuDAO.createBuku(14,14,1,"Judul Buku 14","Pengarang 14","Penerbit 14","2005","Edisi 14","ISBN 14","ISBN26",30,"Tersedia");
            bukuDAO.createBuku(15,15,1,"Judul Buku 15","Pengarang 15","Penerbit 15","2006","Edisi 15","ISBN 15","ISBN27",30,"Tersedia");
            bukuDAO.createBuku(16,16,1,"Judul Buku 16","Pengarang 16","Penerbit 16","2007","Edisi 16","ISBN 16","ISBN28",30,"Tersedia");
            bukuDAO.createBuku(17,17,1,"Judul Buku 17","Pengarang 17","Penerbit 17","2008","Edisi 17","ISBN 17","ISBN29",30,"Tersedia");
            bukuDAO.createBuku(18,18,1,"Judul Buku 18","Pengarang 18","Penerbit 18","2009","Edisi 18","ISBN 18","ISBN30",30,"Tersedia");
            bukuDAO.createBuku(19,19,1,"Judul Buku 19","Pengarang 19","Penerbit 19","2010","Edisi 19","ISBN 19","ISBN31",30,"Tersedia");
            bukuDAO.createBuku(20,20,1,"Judul Buku 20","Pengarang 20","Penerbit 20","2011","Edisi 20","ISBN 20","ISBN32",30,"Tersedia");
            bukuDAO.createBuku(21,21,1,"Judul Buku 21","Pengarang 21","Penerbit 21","2012","Edisi 21","ISBN 21","ISBN33",30,"Tersedia");
            bukuDAO.createBuku(22,22,1,"Judul Buku 22","Pengarang 22","Penerbit 22","2013","Edisi 22","ISBN 22","ISBN34",30,"Tersedia");
            bukuDAO.createBuku(23,23,1,"Judul Buku 23","Pengarang 23","Penerbit 23","2014","Edisi 23","ISBN 23","ISBN35",30,"Tersedia");
            bukuDAO.createBuku(24,24,1,"Judul Buku 24","Pengarang 24","Penerbit 24","1999","Edisi 24","ISBN 24","ISBN36",30,"Tersedia");
            bukuDAO.createBuku(25,25,1,"Judul Buku 25","Pengarang 25","Penerbit 25","1999","Edisi 25","ISBN 25","ISBN37",30,"Tersedia");
            bukuDAO.createBuku(1,1,1,"Judul Buku 26","Pengarang 26","Penerbit 26","2002","Edisi 1","ISBN 1","ISBN13 ",30,"Tersedia");
            bukuDAO.createBuku(2,2,1,"Judul Buku 27","Pengarang 27","Penerbit 27","2003","Edisi 2","ISBN 2","ISBN14",30,"Tersedia");
            bukuDAO.createBuku(3,3,1,"Judul Buku 28","Pengarang 28","Penerbit 28","2004","Edisi 3","ISBN 3","ISBN15",30,"Tersedia");
            bukuDAO.createBuku(4,4,1,"Judul Buku 29","Pengarang 29","Penerbit 29","2005","Edisi 4","ISBN 4","ISBN16",30,"Tersedia");
            bukuDAO.createBuku(5,5,1,"Judul Buku 30","Pengarang 30","Penerbit 30","2006","Edisi 5","ISBN 5","ISBN17",30,"Tersedia");
            bukuDAO.createBuku(6,6,1,"Judul Buku 31","Pengarang 31","Penerbit 31","2007","Edisi 6","ISBN 6","ISBN18",30,"Tersedia");
            bukuDAO.createBuku(7,7,1,"Judul Buku 32","Pengarang 32","Penerbit 32","2008","Edisi 7","ISBN 7","ISBN19",30,"Tersedia");
            bukuDAO.createBuku(8,8,1,"Judul Buku 33","Pengarang 33","Penerbit 33","2009","Edisi 8","ISBN 8","ISBN20",30,"Tersedia");
            bukuDAO.createBuku(9,9,1,"Judul Buku 34","Pengarang 34","Penerbit 34","2000","Edisi 9","ISBN 9","ISBN21",30,"Tersedia");
            bukuDAO.createBuku(10,10,1,"Judul Buku 35","Pengarang 35","Penerbit 35","2001","Edisi 10","ISBN 10","ISBN22",30,"Tersedia");
            bukuDAO.createBuku(11,11,1,"Judul Buku 36","Pengarang 36","Penerbit 36","2002","Edisi 11","ISBN 11","ISBN23",30,"Tersedia");
            bukuDAO.createBuku(12,12,1,"Judul Buku 37","Pengarang 37","Penerbit 37","2003","Edisi 12","ISBN 12","ISBN24",30,"Tersedia");
            bukuDAO.createBuku(13,13,1,"Judul Buku 38","Pengarang 38","Penerbit 38","2004","Edisi 13","ISBN 13","ISBN25",30,"Tersedia");
            bukuDAO.createBuku(14,14,1,"Judul Buku 39","Pengarang 39","Penerbit 39","2005","Edisi 14","ISBN 14","ISBN26",30,"Tersedia");
            bukuDAO.createBuku(15,15,1,"Judul Buku 40","Pengarang 40","Penerbit 40","2006","Edisi 15","ISBN 15","ISBN27",30,"Tersedia");
            bukuDAO.createBuku(16,16,1,"Judul Buku 41","Pengarang 41","Penerbit 41","2007","Edisi 16","ISBN 16","ISBN28",30,"Tersedia");
            bukuDAO.createBuku(17,17,1,"Judul Buku 42","Pengarang 42","Penerbit 42","2008","Edisi 17","ISBN 17","ISBN29",30,"Tersedia");
            bukuDAO.createBuku(18,18,1,"Judul Buku 43","Pengarang 43","Penerbit 43","2009","Edisi 18","ISBN 18","ISBN30",30,"Tersedia");
            bukuDAO.createBuku(19,19,1,"Judul Buku 44","Pengarang 44","Penerbit 44","2010","Edisi 19","ISBN 19","ISBN31",30,"Tersedia");
            bukuDAO.createBuku(20,20,1,"Judul Buku 45","Pengarang 45","Penerbit 45","2011","Edisi 20","ISBN 20","ISBN32",30,"Tersedia");
            bukuDAO.createBuku(21,21,1,"Judul Buku 46","Pengarang 46","Penerbit 46","2012","Edisi 21","ISBN 21","ISBN33",30,"Tersedia");
            bukuDAO.createBuku(22,22,1,"Judul Buku 47","Pengarang 47","Penerbit 47","2013","Edisi 22","ISBN 22","ISBN34",30,"Tersedia");
            bukuDAO.createBuku(23,23,1,"Judul Buku 48","Pengarang 48","Penerbit 48","2014","Edisi 23","ISBN 23","ISBN35",30,"Tersedia");
            bukuDAO.createBuku(24,24,1,"Judul Buku 49","Pengarang 49","Penerbit 49","1999","Edisi 24","ISBN 24","ISBN36",30,"Tersedia");
            bukuDAO.createBuku(25,25,1,"Judul Buku 50","Pengarang 50","Penerbit 50","1999","Edisi 25","ISBN 25","ISBN37",30,"Tersedia");


            anggotaDAO.createAnggota(1, "Nama Lengkap1", "Nama1", "03/10/1994", "Jakarta", "Perempuan", "1234", "Sekolah1", "1");
            anggotaDAO.createAnggota(1, "Nama Lengkap2", "Nama2", "04/10/1994", "Tangerang", "Laki-laki", "1235", "Sekolah2", "2");
            anggotaDAO.createAnggota(1, "Nama Lengkap3", "Nama3", "05/10/1994", "Depok", "Perempuan", "1236", "Sekolah3", "1");
            anggotaDAO.createAnggota(1, "Nama Lengkap4", "Nama4", "06/10/1994", "Bekasi", "Laki-laki", "1237", "Sekolah4", "2");
            anggotaDAO.createAnggota(1, "Nama Lengkap5", "Nama5", "07/10/1994", "Serang", "Perempuan", "1238", "Sekolah5", "1");
            anggotaDAO.createAnggota(1, "Nama Lengkap6", "Nama6", "08/10/1994", "Cilegon", "Laki-laki", "1239", "Sekolah6", "2");
            anggotaDAO.createAnggota(1, "Nama Lengkap7", "Nama7", "09/10/1994", "Makasar", "Perempuan", "1240", "Sekolah7", "1");
            anggotaDAO.createAnggota(1, "Nama Lengkap8", "Nama8", "10/10/1994", "Medan", "Laki-laki", "1241", "Sekolah8", "2");
            anggotaDAO.createAnggota(1, "Nama Lengkap9", "Nama9", "11/10/1994", "Bali", "Perempuan", "1242", "Sekolah9", "1");
            anggotaDAO.createAnggota(1,"Nama Lengkap10","Nama10","12/10/1994","Jakarta","Laki-laki","1243","Sekolah10","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap11","Nama11","13/10/1994","Tangerang","Perempuan","1244","Sekolah11","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap12","Nama12","14/10/1994","Depok","Laki-laki","1245","Sekolah12","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap13","Nama13","15/10/1994","Bekasi","Perempuan","1246","Sekolah13","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap14","Nama14","16/10/1994","Serang","Laki-laki","1247","Sekolah14","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap15","Nama15","17/10/1994","Cilegon","Perempuan","1248","Sekolah15","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap16","Nama16","18/10/1994","Makasar","Laki-laki","1249","Sekolah16","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap17","Nama17","19/10/1994","Medan","Perempuan","1250","Sekolah17","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap18","Nama18","20/10/1994","Bali","Laki-laki","1251","Sekolah18","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap19","Nama19","21/10/1994","Jakarta","Perempuan","1252","Sekolah19","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap20","Nama20","22/10/1994","Tangerang","Laki-laki","1253","Sekolah20","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap21","Nama21","23/10/1994","Depok","Perempuan","1254","Sekolah21","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap22","Nama22","24/10/1994","Bekasi","Laki-laki","1255","Sekolah22","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap23","Nama23","25/10/1994","Serang","Perempuan","1256","Sekolah23","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap24","Nama24","26/10/1994","Cilegon","Laki-laki","1257","Sekolah24","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap25","Nama25","27/10/1994","Makasar","Perempuan","1258","Sekolah25","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap26","Nama26","28/10/1994","Medan","Laki-laki","1259","Sekolah26","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap27","Nama27","29/10/1994","Bali","Perempuan","1260","Sekolah27","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap28","Nama28","30/10/1994","Jakarta","Laki-laki","1261","Sekolah28","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap29","Nama29","31/10/1994","Tangerang","Perempuan","1262","Sekolah29","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap30","Nama30","01/11/1994","Depok","Laki-laki","1263","Sekolah30","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap31","Nama31","02/11/1994","Bekasi","Perempuan","1264","Sekolah31","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap32","Nama32","03/11/1994","Serang","Laki-laki","1265","Sekolah32","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap33","Nama33","04/11/1994","Cilegon","Perempuan","1266","Sekolah33","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap34","Nama34","05/11/1994","Makasar","Laki-laki","1267","Sekolah34","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap35","Nama35","06/11/1994","Medan","Perempuan","1268","Sekolah35","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap36","Nama36","07/11/1994","Bali","Laki-laki","1269","Sekolah36","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap37","Nama37","08/11/1994","Jakarta","Perempuan","1270","Sekolah37","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap38","Nama38","09/11/1994","Tangerang","Laki-laki","1271","Sekolah38","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap39","Nama39","10/11/1994","Depok","Perempuan","1272","Sekolah39","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap40","Nama40","11/11/1994","Bekasi","Laki-laki","1273","Sekolah40","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap41","Nama41","12/11/1994","Serang","Perempuan","1274","Sekolah41","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap42","Nama42","13/11/1994","Cilegon","Laki-laki","1275","Sekolah42","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap43","Nama43","14/11/1994","Makasar","Perempuan","1276","Sekolah43","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap44","Nama44","15/11/1994","Medan","Laki-laki","1277","Sekolah44","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap45","Nama45","16/11/1994","Bali","Perempuan","1278","Sekolah45","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap46","Nama46","17/11/1994","Jakarta","Laki-laki","1279","Sekolah46","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap47","Nama47","18/11/1994","Tangerang","Perempuan","1280","Sekolah47","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap48","Nama48","19/11/1994","Depok","Laki-laki","1281","Sekolah48","2");
            anggotaDAO.createAnggota(1,"Nama Lengkap49","Nama49","20/11/1994","Bekasi","Perempuan","1282","Sekolah49","1");
            anggotaDAO.createAnggota(1,"Nama Lengkap50","Nama50","21/11/1994","Serang","Laki-laki","1283","Sekolah50","2");

            tbSekitarDAO.createTbSekitar(1, "TBSekitar1", "Alamat1", "twitter1", "facebook1", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar2", "Alamat2", "twitter2", "facebook2", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar3", "Alamat3", "twitter3", "facebook3", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar4", "Alamat4", "twitter4", "facebook4", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar5", "Alamat5", "twitter5", "facebook5", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar6", "Alamat6", "twitter6", "facebook6", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar7", "Alamat7", "twitter7", "facebook7", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar8", "Alamat8", "twitter8", "facebook8", "12309013");
            tbSekitarDAO.createTbSekitar(1, "TBSekitar9", "Alamat9", "twitter9", "facebook9", "12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar10","Alamat10","twitter10","facebook10","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar11","Alamat11","twitter11","facebook11","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar12","Alamat12","twitter12","facebook12","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar13","Alamat13","twitter13","facebook13","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar14","Alamat14","twitter14","facebook14","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar15","Alamat15","twitter15","facebook15","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar16","Alamat16","twitter16","facebook16","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar17","Alamat17","twitter17","facebook17","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar18","Alamat18","twitter18","facebook18","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar19","Alamat19","twitter19","facebook19","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar20","Alamat20","twitter20","facebook20","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar21","Alamat21","twitter21","facebook21","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar22","Alamat22","twitter22","facebook22","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar23","Alamat23","twitter23","facebook23","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar24","Alamat24","twitter24","facebook24","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar25","Alamat25","twitter25","facebook25","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar26","Alamat26","twitter26","facebook26","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar27","Alamat27","twitter27","facebook27","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar28","Alamat28","twitter28","facebook28","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar29","Alamat29","twitter29","facebook29","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar30","Alamat30","twitter30","facebook30","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar31","Alamat31","twitter31","facebook31","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar32","Alamat32","twitter32","facebook32","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar33","Alamat33","twitter33","facebook33","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar34","Alamat34","twitter34","facebook34","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar35","Alamat35","twitter35","facebook35","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar36","Alamat36","twitter36","facebook36","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar37","Alamat37","twitter37","facebook37","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar38","Alamat38","twitter38","facebook38","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar39","Alamat39","twitter39","facebook39","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar40","Alamat40","twitter40","facebook40","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar41","Alamat41","twitter41","facebook41","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar42","Alamat42","twitter42","facebook42","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar43","Alamat43","twitter43","facebook43","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar44","Alamat44","twitter44","facebook44","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar45","Alamat45","twitter45","facebook45","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar46","Alamat46","twitter46","facebook46","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar47","Alamat47","twitter47","facebook47","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar48","Alamat48","twitter48","facebook48","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar49","Alamat49","twitter49","facebook49","12309013");
            tbSekitarDAO.createTbSekitar(1,"TBSekitar50","Alamat50","twitter50","facebook50","12309013");

            kegiatanDAO.createKegiatan(1, "Kegiatan 1", "07/01/2015", "Deskripsi 1");
            kegiatanDAO.createKegiatan(1, "Kegiatan 2", "08/01/2015", "Deskripsi 2");
            kegiatanDAO.createKegiatan(1, "Kegiatan 3", "09/01/2015", "Deskripsi 3");
            kegiatanDAO.createKegiatan(1, "Kegiatan 4", "10/01/2015", "Deskripsi 4");
            kegiatanDAO.createKegiatan(1, "Kegiatan 5", "11/01/2015", "Deskripsi 5");
            kegiatanDAO.createKegiatan(1, "Kegiatan 6", "12/01/2015", "Deskripsi 6");
            kegiatanDAO.createKegiatan(1, "Kegiatan 7", "13/01/2015", "Deskripsi 7");
            kegiatanDAO.createKegiatan(1, "Kegiatan 8", "14/01/2015", "Deskripsi 8");
            kegiatanDAO.createKegiatan(1, "Kegiatan 9", "15/01/2015", "Deskripsi 9");
            kegiatanDAO.createKegiatan(1,"Kegiatan 10","16/01/2015","Deskripsi 10");
            kegiatanDAO.createKegiatan(1,"Kegiatan 11","17/01/2015","Deskripsi 11");
            kegiatanDAO.createKegiatan(1,"Kegiatan 12","18/01/2015","Deskripsi 12");
            kegiatanDAO.createKegiatan(1,"Kegiatan 13","19/01/2015","Deskripsi 13");
            kegiatanDAO.createKegiatan(1,"Kegiatan 14","20/01/2015","Deskripsi 14");
            kegiatanDAO.createKegiatan(1,"Kegiatan 15","21/01/2015","Deskripsi 15");
            kegiatanDAO.createKegiatan(1,"Kegiatan 16","22/01/2015","Deskripsi 16");
            kegiatanDAO.createKegiatan(1,"Kegiatan 17","23/01/2015","Deskripsi 17");
            kegiatanDAO.createKegiatan(1,"Kegiatan 18","24/01/2015","Deskripsi 18");
            kegiatanDAO.createKegiatan(1,"Kegiatan 19","25/01/2015","Deskripsi 19");
            kegiatanDAO.createKegiatan(1,"Kegiatan 20","26/01/2015","Deskripsi 20");
            kegiatanDAO.createKegiatan(1,"Kegiatan 21","27/01/2015","Deskripsi 21");
            kegiatanDAO.createKegiatan(1,"Kegiatan 22","28/01/2015","Deskripsi 22");
            kegiatanDAO.createKegiatan(1,"Kegiatan 23","29/01/2015","Deskripsi 23");
            kegiatanDAO.createKegiatan(1,"Kegiatan 24","30/01/2015","Deskripsi 24");
            kegiatanDAO.createKegiatan(1,"Kegiatan 25","31/01/2015","Deskripsi 25");
            kegiatanDAO.createKegiatan(1,"Kegiatan 26","01/02/2015","Deskripsi 26");
            kegiatanDAO.createKegiatan(1,"Kegiatan 27","02/02/2015","Deskripsi 27");
            kegiatanDAO.createKegiatan(1,"Kegiatan 28","03/02/2015","Deskripsi 28");
            kegiatanDAO.createKegiatan(1,"Kegiatan 29","04/02/2015","Deskripsi 29");
            kegiatanDAO.createKegiatan(1,"Kegiatan 30","05/02/2015","Deskripsi 30");
            kegiatanDAO.createKegiatan(1,"Kegiatan 31","06/02/2015","Deskripsi 31");
            kegiatanDAO.createKegiatan(1,"Kegiatan 32","07/02/2015","Deskripsi 32");
            kegiatanDAO.createKegiatan(1,"Kegiatan 33","08/02/2015","Deskripsi 33");
            kegiatanDAO.createKegiatan(1,"Kegiatan 34","09/02/2015","Deskripsi 34");
            kegiatanDAO.createKegiatan(1,"Kegiatan 35","10/02/2015","Deskripsi 35");
            kegiatanDAO.createKegiatan(1,"Kegiatan 36","11/02/2015","Deskripsi 36");
            kegiatanDAO.createKegiatan(1,"Kegiatan 37","12/02/2015","Deskripsi 37");
            kegiatanDAO.createKegiatan(1,"Kegiatan 38","13/02/2015","Deskripsi 38");
            kegiatanDAO.createKegiatan(1,"Kegiatan 39","14/02/2015","Deskripsi 39");
            kegiatanDAO.createKegiatan(1,"Kegiatan 40","15/02/2015","Deskripsi 40");
            kegiatanDAO.createKegiatan(1,"Kegiatan 41","16/02/2015","Deskripsi 41");
            kegiatanDAO.createKegiatan(1,"Kegiatan 42","17/02/2015","Deskripsi 42");
            kegiatanDAO.createKegiatan(1,"Kegiatan 43","18/02/2015","Deskripsi 43");
            kegiatanDAO.createKegiatan(1,"Kegiatan 44","19/02/2015","Deskripsi 44");
            kegiatanDAO.createKegiatan(1,"Kegiatan 45","20/02/2015","Deskripsi 45");
            kegiatanDAO.createKegiatan(1,"Kegiatan 46","21/02/2015","Deskripsi 46");
            kegiatanDAO.createKegiatan(1,"Kegiatan 47","22/02/2015","Deskripsi 47");
            kegiatanDAO.createKegiatan(1,"Kegiatan 48","23/02/2015","Deskripsi 48");
            kegiatanDAO.createKegiatan(1,"Kegiatan 49","24/02/2015","Deskripsi 49");
            kegiatanDAO.createKegiatan(1,"Kegiatan 50","25/02/2015","Deskripsi 50");

            logHarianDAO.createLogHarian(1, "03/10/1994", 10, "10:00", "16:00");
            logHarianDAO.createLogHarian(1, "04/10/1994", 11, "10:01", "16:01");
            logHarianDAO.createLogHarian(1, "05/10/1994", 12, "10:02", "16:02");
            logHarianDAO.createLogHarian(1, "06/10/1994", 13, "10:03", "16:03");
            logHarianDAO.createLogHarian(1, "07/10/1994", 14, "10:04", "16:04");
            logHarianDAO.createLogHarian(1, "08/10/1994", 15, "10:05", "16:05");
            logHarianDAO.createLogHarian(1, "09/10/1994", 16, "10:06", "16:06");
            logHarianDAO.createLogHarian(1, "10/10/1994", 17, "10:07", "16:07");
            logHarianDAO.createLogHarian(1, "11/10/1994", 18, "10:08", "16:08");
            logHarianDAO.createLogHarian(1,"12/10/1994",19,"10:09","16:09");
            logHarianDAO.createLogHarian(1,"13/10/1994",20,"10:10","16:10");
            logHarianDAO.createLogHarian(1,"14/10/1994",21,"10:11","16:11");
            logHarianDAO.createLogHarian(1,"15/10/1994",22,"10:12","16:12");
            logHarianDAO.createLogHarian(1,"16/10/1994",23,"10:13","16:13");
            logHarianDAO.createLogHarian(1,"17/10/1994",24,"10:14","16:14");
            logHarianDAO.createLogHarian(1,"18/10/1994",25,"10:15","16:15");
            logHarianDAO.createLogHarian(1,"19/10/1994",26,"10:16","16:16");
            logHarianDAO.createLogHarian(1,"20/10/1994",27,"10:17","16:17");
            logHarianDAO.createLogHarian(1,"21/10/1994",28,"10:18","16:18");
            logHarianDAO.createLogHarian(1,"22/10/1994",29,"10:19","16:19");
            logHarianDAO.createLogHarian(1,"23/10/1994",30,"10:20","16:20");
            logHarianDAO.createLogHarian(1,"24/10/1994",31,"10:21","16:21");
            logHarianDAO.createLogHarian(1,"25/10/1994",32,"10:22","16:22");
            logHarianDAO.createLogHarian(1,"26/10/1994",33,"10:23","16:23");
            logHarianDAO.createLogHarian(1,"27/10/1994",34,"10:24","16:24");
            logHarianDAO.createLogHarian(1,"28/10/1994",35,"10:25","16:25");
            logHarianDAO.createLogHarian(1,"29/10/1994",36,"10:26","16:26");
            logHarianDAO.createLogHarian(1,"30/10/1994",37,"10:27","16:27");
            logHarianDAO.createLogHarian(1,"31/10/1994",38,"10:28","16:28");
            logHarianDAO.createLogHarian(1,"01/11/1994",39,"10:29","16:29");
            logHarianDAO.createLogHarian(1,"02/11/1994",40,"10:30","16:30");
            logHarianDAO.createLogHarian(1,"03/11/1994",41,"10:31","16:31");
            logHarianDAO.createLogHarian(1,"04/11/1994",42,"10:32","16:32");
            logHarianDAO.createLogHarian(1,"05/11/1994",43,"10:33","16:33");
            logHarianDAO.createLogHarian(1,"06/11/1994",44,"10:34","16:34");
            logHarianDAO.createLogHarian(1,"07/11/1994",45,"10:35","16:35");
            logHarianDAO.createLogHarian(1,"08/11/1994",46,"10:36","16:36");
            logHarianDAO.createLogHarian(1,"09/11/1994",47,"10:37","16:37");
            logHarianDAO.createLogHarian(1,"10/11/1994",48,"10:38","16:38");
            logHarianDAO.createLogHarian(1,"11/11/1994",49,"10:39","16:39");
            logHarianDAO.createLogHarian(1,"12/11/1994",50,"10:40","16:40");
            logHarianDAO.createLogHarian(1,"13/11/1994",51,"10:41","16:41");
            logHarianDAO.createLogHarian(1,"14/11/1994",52,"10:42","16:42");
            logHarianDAO.createLogHarian(1,"15/11/1994",53,"10:43","16:43");
            logHarianDAO.createLogHarian(1,"16/11/1994",54,"10:44","16:44");
            logHarianDAO.createLogHarian(1,"17/11/1994",55,"10:45","16:45");
            logHarianDAO.createLogHarian(1,"18/11/1994",56,"10:46","16:46");
            logHarianDAO.createLogHarian(1,"19/11/1994",57,"10:47","16:47");
            logHarianDAO.createLogHarian(1,"20/11/1994",58,"10:48","16:48");
            logHarianDAO.createLogHarian(1,"21/11/1994",59,"10:49","16:49");


            peminjamanDAO.createPeminjaman(2,2,"08/02/2013","09/03/2014",1);
            peminjamanDAO.createPeminjaman(3,3,"09/02/2013","10/03/2014",1);
            peminjamanDAO.createPeminjaman(4,4,"10/02/2013","11/03/2014",1);
            peminjamanDAO.createPeminjaman(5,5,"11/02/2013","12/03/2014",2);
            peminjamanDAO.createPeminjaman(6,6,"12/02/2013","13/03/2014",2);
            peminjamanDAO.createPeminjaman(7,7,"13/02/2013","14/03/2014",2);
            peminjamanDAO.createPeminjaman(8,8,"14/02/2013","15/03/2014",2);
            peminjamanDAO.createPeminjaman(9, 9, "15/02/2013","16/03/2014",3);
            peminjamanDAO.createPeminjaman(10,10,"16/02/2013","17/03/2014",3);
            peminjamanDAO.createPeminjaman(11,11,"17/02/2013","18/03/2014",3);
            peminjamanDAO.createPeminjaman(12,12,"18/02/2013","19/03/2014",3);
            peminjamanDAO.createPeminjaman(13,13,"19/02/2013","20/03/2014",3);
            peminjamanDAO.createPeminjaman(14,14,"20/02/2013","21/03/2014",4);
            peminjamanDAO.createPeminjaman(15,15,"21/02/2013","22/03/2014",4);
            peminjamanDAO.createPeminjaman(16,16,"22/02/2013","23/03/2014",4);
            peminjamanDAO.createPeminjaman(17,17,"23/02/2013","24/03/2014",4);
            peminjamanDAO.createPeminjaman(18,18,"24/02/2013","25/03/2014",4);
            peminjamanDAO.createPeminjaman(19,19,"25/02/2013","26/03/2014",5);
            peminjamanDAO.createPeminjaman(20,20,"26/02/2013","27/03/2014",5);
            peminjamanDAO.createPeminjaman(21,21,"27/02/2013","28/03/2014",5);
            peminjamanDAO.createPeminjaman(22,22,"28/02/2013","29/03/2014",5);
            peminjamanDAO.createPeminjaman(23,23,"01/03/2013","30/03/2014",5);
            peminjamanDAO.createPeminjaman(24,24,"02/03/2013","31/03/2014",4);
            peminjamanDAO.createPeminjaman(25,25,"03/03/2013","01/04/2014",4);


            pertukaranBukuDAO.createPertukaranBuku(26,26,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(27,27,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(28,28,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(29,29,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(30,30,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(31,31,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(32,32,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(33,33,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(34,34,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(35,35,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(36,36,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(37,37,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(38,38,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(39,39,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(40,40,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(41,41,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(42,42,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(43,43,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(44,44,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(45,45,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(46,46,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(47,47,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(48,48,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(49,49,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(50,50,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(26,26,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(27,27,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(28,28,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(29,29,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(30,30,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(31,31,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(32,32,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(33,33,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(34,34,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(35,35,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(36,36,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(37,37,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(38,38,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(39,39,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(40,40,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(41,41,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(42,42,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(43,43,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(44,44,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(45,45,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(46,46,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(47,47,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(48,48,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(49,49,"15/7/2012","18/7/2012");
            pertukaranBukuDAO.createPertukaranBuku(50,50,"15/7/2012","18/7/2012");

            donaturDAO.close();
            kategoriDAO.close();
            bukuDAO.close();
            anggotaDAO.close();
            peminjamanDAO.close();
            tbSekitarDAO.close();
            pertukaranBukuDAO.close();
            kegiatanDAO.close();
            logHarianDAO.close();

        }
    }
}
