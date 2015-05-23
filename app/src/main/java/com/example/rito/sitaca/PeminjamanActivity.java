package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import static android.widget.AdapterView.*;


public class PeminjamanActivity extends ActionBarActivity implements OnItemLongClickListener, OnItemClickListener, View.OnClickListener {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_peminjaman, menu);

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


        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                fragment.adapterPeminjaman.filter(newText);
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
        //Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement ------------------------------------------ tambahandelete

        if (id == R.id.search) {
            ////////////////////////////ubah search/ada yang dihapus disini!!!!!!!!!!!!!!!!!!!!!!!
            return true;
        }
        if (id == R.id.delete) {
            if (fragment.deleteList != null && fragment.deleteList.size() == 0) {
                Toast.makeText(
                        getApplicationContext(),
                        "Kesalahan : Belum ada peminjaman yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah " + fragment.deleteList.size() + " peminjaman yang anda pilih telah dikembalikan?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        fragment.deleteList.size() + " peminjaman telah dikembalikan.",
                                        Toast.LENGTH_SHORT
                                ).show();
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


    /*public void slideDelete(){
        fragment.slideDeleteFragment();
        }*/

    public void showTambahPeminjaman(View view) {
        Intent intent = new Intent(this, TambahPeminjamanActivity.class);
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

    //tambah
    protected  void onResume(){
        super.onResume();
        fragment.populateList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mPeminjamanDao.close();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public ListView mListviewPeminjaman;
        public ImageButton mBtnAddPeminjaman;
        public View rootView;
        public PeminjamanListAdapter adapterPeminjaman;
        public List<Peminjaman> listPeminjaman;
        public PeminjamanDAO mPeminjamanDao;
        public BukuDAO mBukuDao;
        public AnggotaDAO mAnggotaDao;
        public List<Peminjaman> deleteList;

        private boolean peminjamanExists(Peminjaman peminjaman) {
            int id = peminjaman.getId_peminjaman();
            int peminjamanCount = listPeminjaman.size();

            for (int i = 0; i < peminjamanCount; i++) {
                if (id == (int) listPeminjaman.get(i).getId_peminjaman())
                    return true;
            }
            return false;
        }

        public PlaceholderFragment() {
        }

        public class PeminjamanListAdapter extends ArrayAdapter<Peminjaman> {

            ////////////////////////////ubah search// ada yang dihilangkan di sini!!!!!!!!!!!!!!!!!!!!!!!

            public ArrayList<Peminjaman> arraylist;
            public List<Peminjaman> mListpeminjaman;

            public PeminjamanListAdapter(List<Peminjaman> mListPeminjaman) {
                super(getActivity(),
                        R.layout.list_item_peminjaman,
                        // R.id.listItemPeminjamanTextview,
                        mListPeminjaman);
                this.mListpeminjaman = mListPeminjaman;
                this.arraylist = new ArrayList<Peminjaman>();
                this.arraylist.addAll(mListPeminjaman);
            }

            public class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView buku;
                TextView peminjam;
                TextView tglPinjam;
                TextView tglKembali;
                CheckBox cb;
            }


            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final Peminjaman currentPeminjaman = mListpeminjaman.get(position);
                mAnggotaDao = new AnggotaDAO(getContext());
                mBukuDao = new BukuDAO(getContext());


                String namaPeminjam = mAnggotaDao.getAnggota(currentPeminjaman.getId_anggota()).getNama_lengkap();
                String namaBuku = mBukuDao.getBuku(currentPeminjaman.getId_buku()).getJudul_buku();

                if (view == null) {//---> Kalo View belum ada buat holder baru
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_peminjaman, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.buku = (TextView) view.findViewById(R.id.viewBuku);
                    viewHolder.peminjam = (TextView) view.findViewById(R.id.viewPeminjam);
                    viewHolder.tglPinjam = (TextView) view.findViewById(R.id.viewTanggalPinjam);
                    viewHolder.tglKembali = (TextView) view.findViewById(R.id.viewTanggalKembali);
                    viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);
                } else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }


                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (!deleteList.contains(currentPeminjaman))
                                deleteList.add(currentPeminjaman);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        } else {
                            deleteList.remove(currentPeminjaman);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                });

                //Simpan Datanya ke Holder
                viewHolder.buku.setText(namaBuku);
                viewHolder.peminjam.setText(namaPeminjam);
                viewHolder.tglPinjam.setText(currentPeminjaman.getTanggal_pinjam());
                viewHolder.tglKembali.setText(currentPeminjaman.getTanggal_kembali());
                ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
                if (deleteList.contains(currentPeminjaman))
                    viewHolder.cb.setChecked(true);
                if (!deleteList.contains(currentPeminjaman))
                    viewHolder.cb.setChecked(false);

                view.setTag(viewHolder);
                return view;
            }

            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListpeminjaman.clear();
                if (charText.length() == 0) {
                    mListpeminjaman.addAll(arraylist);
                } else {
                    for (Peminjaman peminjaman : arraylist) {
                        if (mBukuDao.getBuku(peminjaman.getId_buku()).getJudul_buku().toLowerCase(Locale.getDefault()).contains(charText)) {
                            mListpeminjaman.add(peminjaman);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void delete() {
            if (deleteList.size() != 0 && deleteList != null) {
                for (int i = 0; i < deleteList.size(); i++) {
                    Buku buku = mBukuDao.getBuku(deleteList.get(i).getId_buku());
                    buku.setStatus("Tersedia");
                    Anggota anggota = mAnggotaDao.getAnggota(deleteList.get(i).getId_anggota());
                    int poin = anggota.getJumlahPoin() + buku.getPoin();
                    anggota.setJumlahPoin(poin);
                    mAnggotaDao.updateAnggota(anggota);
                    mBukuDao.updateBuku(buku);
                    Peminjaman peminjaman = deleteList.get(i);
                    peminjaman.setStatus(0);
                    mPeminjamanDao.updatePeminjaman(peminjaman);
                }
            }
            deleteList = new ArrayList<>();
            populateList();
        }

        public void populateList() {
            mListviewPeminjaman = (ListView) rootView.findViewById(R.id.listViewPeminjaman);
            listPeminjaman = mPeminjamanDao.getAllPeminjaman();
            Collections.reverse(listPeminjaman);
            adapterPeminjaman = new PeminjamanListAdapter(listPeminjaman);
            mListviewPeminjaman.setAdapter(adapterPeminjaman);
            mListviewPeminjaman.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent = new Intent(rootView.getContext(), UbahPeminjamanActivity.class);
                    intent.putExtra("id", listPeminjaman.get(position).getId_peminjaman());
                    mAnggotaDao.close();
                    mBukuDao.close();
                    mPeminjamanDao.close();
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_peminjaman, container, false);


            mBtnAddPeminjaman = (ImageButton) rootView.findViewById(R.id.add_button);
            mPeminjamanDao = new PeminjamanDAO(rootView.getContext());
            mAnggotaDao = new AnggotaDAO(rootView.getContext());
            mBukuDao = new BukuDAO(rootView.getContext());
            deleteList = new ArrayList<Peminjaman>();


            mBtnAddPeminjaman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), TambahPeminjamanActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            populateList();
            return rootView;
        }
    }
}
