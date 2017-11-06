package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class AnggotaActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anggota, menu);

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
        //  searchView.setAnimation(new TranslateAnimation(0,100,0,0));
        //searchView.startAnimation();
        searchView.setIconifiedByDefault(true);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        // Getting the 'search_plate' LinearLayout.
        LinearLayout searchPlate = (LinearLayout) searchView.findViewById(searchPlateId);
        // Setting background of 'search_plate' to earlier defined drawable.
        searchPlate.setBackgroundResource(R.drawable.search);
        searchPlate.setLayoutTransition(new LayoutTransition());


        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                fragment.adapterAnggota.filter(newText);
                System.out.println("on text chnge text: "+newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
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
        if(id == R.id.delete)
        {
            if(fragment.deleteList!= null && fragment.deleteList.size() == 0)
            {
                Toast.makeText(
                        getApplicationContext(),
                        "Kesalahan : Belum ada anggota yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah "+fragment.deleteList.size()+" anggota yang anda pilih ingin dihapus?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                fragment.delete();
                            }
                        })
                        .setNegativeButton("Tidak", null).show();
                //slideDelete();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void showTambahAnggota(View view) {
        Intent intent = new Intent(this, BuatAnggotaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    protected void onDestroy() {
        super.onDestroy();
        //mAnggotaDao.close();
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ListView mListviewAnggota;
        private ImageButton mBtnAddAnggota;
        public View rootView;
        public AnggotaListAdapter adapterAnggota;
        public AnggotaDAO mAnggotaDao;
        public PeminjamanDAO peminjamanDAO;
        public boolean isDelete = false, onDelete=true;
        private List<Anggota> listAnggota;
        public List<Anggota> deleteList;
        //public CheckBox cb;

        private boolean anggotaExists(Anggota anggota) {
            int id = anggota.getId_anggota();
            int anggotaCount = listAnggota.size();

            for (int i = 0; i < anggotaCount; i++) {
                if (id == (int) listAnggota.get(i).getId_anggota())
                    return true;
            }
            return false;
        }



        public ArrayAdapter<Anggota> anggotaAdapter;

        public PlaceholderFragment() {
        }

        private class AnggotaListAdapter extends ArrayAdapter<Anggota> {

            ////////////////////////////ubah search// ada yang dihilangkan di sini!!!!!!!!!!!!!!!!!!!!!!!

            public ArrayList<Anggota> arraylist;
            public List<Anggota> mListanggota;


            public AnggotaListAdapter(List<Anggota> mListAnggota) {
                super ( getActivity(),
                        R.layout.list_item_anggota,
                        // R.id.listItemPeminjamanTextview,
                        mListAnggota);
                this.mListanggota = mListAnggota;
                this.arraylist = new ArrayList<Anggota>();
                this.arraylist.addAll(mListAnggota);
            }

            private class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView nama;
                TextView status;
                CheckBox cb;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final Anggota currentAnggota = listAnggota.get(position);
               // mAnggotaDao = new AnggotaDAO(getContext());
               // mTamanBacaDao = new TamanBacaDAO(getContext());


                String namaAnggota = mAnggotaDao.getAnggota(currentAnggota.getId_anggota()).getNama_lengkap();
                String status = mAnggotaDao.getAnggota(currentAnggota.getId_anggota()).getStatus();

                if (view == null) {//---> Kalo View belum ada buat holder baru
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_anggota, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nama = (TextView) view.findViewById(R.id.viewNamaAnggota);
                    viewHolder.status = (TextView) view.findViewById(R.id.viewStatus);
                    viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);
                }
                else{ //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }

                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(!deleteList.contains(currentAnggota))
                                deleteList.add(currentAnggota);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                        else {
                            deleteList.remove(currentAnggota);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                });

                //Simpan Datanya ke Holder
                viewHolder.nama.setText(namaAnggota);
                viewHolder.status.setText(status);
                ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
                if(deleteList.contains(currentAnggota))
                    viewHolder.cb.setChecked(true);
                if(!deleteList.contains(currentAnggota))
                    viewHolder.cb.setChecked(false);

                view.setTag(viewHolder);
                return view;
            }
            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListanggota.clear();
                if (charText.length() == 0) {
                    mListanggota.addAll(arraylist);
                }
                else
                {
                    for (Anggota anggota : arraylist)
                    {
                        if (mAnggotaDao.getAnggota(anggota.getId_anggota()).getNama_lengkap().toLowerCase(Locale.getDefault()).contains(charText))
                        {
                            mListanggota.add(anggota);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void delete()
        {
            int cnt = 0;
            if(deleteList.size()!=0)
            {

                for(int i =0; i<deleteList.size();i++)
                {
                    Anggota anggota = deleteList.get(i);
                    if(peminjamanDAO.isMeminjam(anggota.getId_anggota())){
                        cnt++;
                    }
                    else
                        mAnggotaDao.deleteAnggota(deleteList.get(i));
                }
            }
            if(cnt>0)
            {
                Toast.makeText(
                        rootView.getContext(),
                        "Peringatan : "+cnt+ " Anggota tidak dapat dihapus karena sedang meminjam buku.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            if(deleteList.size()>0) {
                if(deleteList.size() - cnt > 0) {
                    Toast.makeText(
                            rootView.getContext(),
                            "" + (deleteList.size() - cnt) + " Anggota telah dihapus.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
            deleteList = new ArrayList<>();
            populateList();
        }

        //Animasi saat tekan tombol delete
        public void slideDeleteFragment()
        {
            if(!isDelete) {
                isDelete = true;
                onDelete = false;
                populateList();
            }
            else{
                isDelete = false;
                onDelete = true;
                populateList();
            }
        }

        public void populateList()
        {
            mListviewAnggota = (ListView) rootView.findViewById(R.id.listViewAnggota);
            listAnggota = mAnggotaDao.getAllAnggota();
            listAnggota.remove(0);
            Collections.reverse(listAnggota);
            adapterAnggota = new AnggotaListAdapter(listAnggota);
            mListviewAnggota.setAdapter(adapterAnggota);
            mListviewAnggota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent = new Intent(rootView.getContext(), UbahAnggotaActivity.class);
                    intent.putExtra("id", listAnggota.get(position).getId_anggota());
                    mAnggotaDao.close();
                    //mTamanBacaDao.close();
                    startActivity(intent);
                   getActivity().finish();
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_anggota, container, false);

            mBtnAddAnggota = (ImageButton) rootView.findViewById(R.id.add_button);
            mAnggotaDao = new AnggotaDAO(rootView.getContext());
            peminjamanDAO = new PeminjamanDAO(rootView.getContext());
            deleteList = new ArrayList<Anggota>();

            mBtnAddAnggota.setOnClickListener(new View.OnClickListener() { //-------------------------------------------------------->>Baru
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), BuatAnggotaActivity.class);
                    startActivity(intent);
                   getActivity().finish();
                }
            });

            populateList();
            return rootView;
        }
    }
}
