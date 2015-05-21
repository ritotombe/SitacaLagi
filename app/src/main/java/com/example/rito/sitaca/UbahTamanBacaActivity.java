package com.example.rito.sitaca;

import android.content.Intent;
import android.content.SharedPreferences;
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


public class UbahTamanBacaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_taman_baca);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_taman_baca, menu);
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
        private UserDAO userDAO;
        private TamanBacaDAO tamanBacaDAO;
        private EditText nama, alamat, twitter, facebook;
        private View rootView;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_ubah_taman_baca, container, false);
            final SharedPreferences pref;
            nama = (EditText) rootView.findViewById(R.id.buatTamanBacaNama);
            alamat = (EditText) rootView.findViewById(R.id.buatTamanBacaAlamat);
            twitter = (EditText) rootView.findViewById(R.id.buatTamanBacaTwitter);
            facebook =(EditText) rootView.findViewById(R.id.buatTamanBacaFacebook);

            userDAO = new UserDAO(rootView.getContext());
            tamanBacaDAO = new TamanBacaDAO(rootView.getContext());
            pref = getActivity().getSharedPreferences("kirim", 0);
            //Bundle intent = getActivity().getIntent().getExtras();
            int id = 0;

            final TamanBaca tamanBaca = tamanBacaDAO.getAllTamanBaca().get(0);

            nama.setText(tamanBaca.getNama());
            alamat.setText(tamanBaca.getAlamat());
            twitter.setText(tamanBaca.getTwitter());
            facebook.setText(tamanBaca.getFacebook());

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Lihat model dan Db handlernya ada method-methodnya untuk di sini

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
                    if(!(new Connection().checkConnection(getActivity())) &&  pref.getInt("kirim_user", -1) == 1) {
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Peringatan : Tidak ada koneksi internet.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    if(cek){
                    tamanBaca.setNama(String.valueOf(nama.getText()));
                    tamanBaca.setAlamat(String.valueOf(alamat.getText()));
                    tamanBaca.setFacebook(String.valueOf(facebook.getText()));
                    tamanBaca.setTwitter(String.valueOf(twitter.getText()));

                    tamanBacaDAO.updateTamanBaca(tamanBaca);
                    Toast.makeText(
                            rootView.getContext(),
                            "Taman baca telah diubah.",
                            Toast.LENGTH_SHORT
                    ).show();
                    tamanBacaDAO.close();

                    Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }}
            });
            return rootView;
        }
    }
}
