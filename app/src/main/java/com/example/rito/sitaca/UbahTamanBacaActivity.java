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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


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

                        if (new Connection().checkConnection(getActivity()) && pref.getInt("kirim_tb", -1) == 1) {
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("aksi", "update_user"));
                            //Log.d("idcek",""+id);
                            params.add(new BasicNameValuePair("id_tb", "" + pref.getInt("id_tb", -1)));
                            params.add(new BasicNameValuePair("id_user", "" + pref.getInt("id_user", -1)));
                            params.add(new BasicNameValuePair("nama", String.valueOf(nama.getText())));
                            params.add(new BasicNameValuePair("alamat", String.valueOf(alamat.getText())));
                            params.add(new BasicNameValuePair("facebook", String.valueOf(facebook.getText())));
                            params.add(new BasicNameValuePair("twitter", String.valueOf(twitter.getText())));

                            RequestData requestData = new RequestData(
                                    "tbdao.php",
                                    params,
                                    rootView.getContext(),
                                    "Mengubah TB") {
                                @Override
                                protected void onPostExecute(JSONArray data) {
                                    pDialog.dismiss();
                                    try {
                                        Toast.makeText(
                                                getActivity(),
                                                data.get(0).toString(),
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        if (!data.get(0).toString().contains("Kesalahan")) {
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
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            };
                            requestData.execute();
                        }
                        else {
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
                            Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        tamanBacaDAO.close();
                    }}
            });
            return rootView;
        }
    }
}
