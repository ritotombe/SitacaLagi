package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class PengumumanActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengumuman);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pengumuman, menu);
        ////////////////////////////ubah search!!!!!!!!!!!!!!!!!!!!!!!
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        int searchIconId = searchView.getContext().getResources().
                getIdentifier("android:id/search_button", null, null);
        ImageView searchIcon = (ImageView) searchView.findViewById(searchIconId);
        searchIcon.setImageResource(R.mipmap.ic_search);
        searchIcon.setScaleX(0.7f);
        searchIcon.setScaleY(0.7f);

        searchView.setIconifiedByDefault(true);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        // Getting the 'search_plate' LinearLayout.
        LinearLayout searchPlate = (LinearLayout) searchView.findViewById(searchPlateId);
        // Setting background of 'search_plate' to earlier defined drawable.
        searchPlate.setBackgroundResource(R.drawable.search);
        searchPlate.setLayoutTransition(new LayoutTransition());


        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                fragment.adapterPengumuman.filter(newText);
                //System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.search)
        {
            ////////////////////////////ubah search/ada yang dihapus disini!!!!!!!!!!!!!!!!!!!!!!!
            return true;
        }
               return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public View rootView;
        public ListView mListViewPengumuman;
        public PengumumanListAdapter adapterPengumuman;
        public List<Pengumuman> listPengumuman;


        public PlaceholderFragment() {
        }

        public class PengumumanListAdapter extends ArrayAdapter<Pengumuman> {

            ////////////////////////////ubah search// ada yang dihilangkan di sini!!!!!!!!!!!!!!!!!!!!!!!

            public ArrayList<Pengumuman> arraylist;
            public List<Pengumuman> mListPengumuman;

            public PengumumanListAdapter(List<Pengumuman> mListPengumuman) {
                super(getActivity(),
                        R.layout.list_item_pengumuman,
                        // R.id.listItemPeminjamanTextview,
                        mListPengumuman);
                this.mListPengumuman = mListPengumuman;
                this.arraylist = new ArrayList<Pengumuman>();
                this.arraylist.addAll(mListPengumuman);
            }

            public class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView nama;
                TextView judul;
                TextView waktu;
            }

            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final Pengumuman currentPengumuman = mListPengumuman.get(position);

                if (view == null) {//---> Kalo View belum ada buat holder baru
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_pengumuman, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nama = (TextView) view.findViewById(R.id.viewNama);
                    viewHolder.judul = (TextView) view.findViewById(R.id.viewJudul);
                    viewHolder.waktu = (TextView) view.findViewById(R.id.viewWaktu);
                    view.setTag(viewHolder);
                } else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }

                //Simpan Datanya ke Holder
                viewHolder.nama.setText(currentPengumuman.getNamaAdmin());
                viewHolder.judul.setText(currentPengumuman.getJudul());
                viewHolder.waktu.setText(currentPengumuman.getWaktu());

                view.setTag(viewHolder);
                return view;
            }

            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListPengumuman.clear();
                if (charText.length() == 0) {
                    mListPengumuman.addAll(arraylist);
                } else {
                    for (Pengumuman pengumuman : arraylist) {
                        if (pengumuman.getJudul().toLowerCase(Locale.getDefault()).contains(charText)) {
                            mListPengumuman.add(pengumuman);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }


        public void populateList() throws ExecutionException, InterruptedException {
            mListViewPengumuman = (ListView) rootView.findViewById(R.id.listViewPengumuman);
            listPengumuman = new ArrayList<>();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "pengumuman"));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Pengumuman")
            {
                @Override
                protected void onPostExecute(JSONArray data) {

                    pDialog.dismiss();
                    if (data == null) {

                        Toast.makeText(
                                getActivity(),
                                "Pengumuman tidak ada",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    else {
                        JSONArray jsonArray = data;
                        Log.d("cekid", ""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject o = jsonArray.getJSONObject(i);
                                //String temp = o.getJSONObject("waktu").toString();
                                //Log.d("cekxx", ""+temp);
                                Pengumuman pengumuman = new Pengumuman(
                                        o.getInt("id"),
                                        o.getInt("id_admin"),
                                        o.getString("nama"),
                                        o.getString("judul"),
                                        null,
                                        o.getString("waktu")
                                );
                                listPengumuman.add(pengumuman);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Collections.reverse(listPengumuman);
                    adapterPengumuman = new PengumumanListAdapter(listPengumuman);
                    mListViewPengumuman.setAdapter(adapterPengumuman);
                    mListViewPengumuman.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            Intent intent = new Intent(rootView.getContext(), DetilPengumumanActivity.class);
                            intent.putExtra("id", listPengumuman.get(position).getId_pengumuman());
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                }
            };

            requestData.execute();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_pengumuman, container, false);
            try {
                populateList();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rootView;
        }
    }
}
