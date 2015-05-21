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
        if (id == R.id.action_settings) {
            return true;
        }

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

                        donaturDAO.createDonatur("Donatur Tidak Terdaftar","","Dummy Donatur", null, "0");
                        kategoriDAO.createKategori("Tidak Ada Kategori","Buku tersebut tidak termasuk kategori apapun");
                        anggotaDAO.createAnggota(1,"Anggota Tidak Ada","DummyAnggota","ddmmyyyy","alamat","jk","no_telp","sekolah","lagi");
                        tbSekitarDAO.createTbSekitar(1,"TB Tidak Ada","dummy","dummy","dummy","dummy");
                        bukuDAO.createBuku(1,1,1,"Buku Tidak Ada","dummy","dummy","dummy","dummy","dummy","dummy",0,"dummy");

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
    }
}
