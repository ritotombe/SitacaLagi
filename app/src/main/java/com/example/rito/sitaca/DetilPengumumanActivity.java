package com.example.rito.sitaca;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class DetilPengumumanActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_pengumuman);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detil_pengumuman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private EditText judul, isi,pengirim;
        private TextView waktu;
        private int id_pengumuman;
        private View rootView;

        public PlaceholderFragment() {
        }

        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : " + e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_detil_pengumuman, container, false);
            judul = (EditText) rootView.findViewById(R.id.viewJudul);
            id_pengumuman = -1;
            isi = (EditText) rootView.findViewById(R.id.viewIsi);
            waktu = (TextView) rootView.findViewById(R.id.viewWaktu);
            pengirim = (EditText) rootView.findViewById(R.id.viewPengirim);
            /*alamatTB = (EditText) rootView.findViewById(R.id.alamatTB);
            twitter = (EditText) rootView.findViewById(R.id.twitter);
            facebook = (EditText) rootView.findViewById(R.id.facebook);*/
            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "lihat"));
            params.add(new BasicNameValuePair("id", ""+id));
            RequestData requestData = new RequestData(
                    "pengumumandao.php",
                    params,
                    getActivity(),
                    "Memuat Pengumuman")
            {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    JSONArray jsonArray = data;
                    Log.d("ceklala", "" + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("cekid", " " + i);
                        try {
                            JSONObject o = jsonArray.getJSONObject(i);
                            Pengumuman pengumuman = new Pengumuman(
                                    o.getInt("id"),
                                    o.getInt("id_admin"),
                                    o.getString("nama"),
                                    o.getString("judul"),
                                    o.getString("isi"),
                                    o.getString("waktu")
                            );

                            id_pengumuman = pengumuman.getId_pengumuman();

                            judul.setText(pengumuman.getJudul());
                            isi.setText(pengumuman.getIsi());
                            pengirim.setText(pengumuman.getNamaAdmin());
                            waktu.setText("Waktu: "+pengumuman.getWaktu());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            requestData.execute();
            return rootView;
        }
    }
}
