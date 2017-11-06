package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


public class KelolaPertukaranBukuActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener  {

    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pertukaran_buku);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kelola_pertukaran_buku, menu);

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


        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                fragment.adapterPertukaranBuku.filter(newText);
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

        if (id == R.id.search) { //search
            return true;
        }

        if(id == R.id.delete)
        {
            if(fragment.deleteList!= null && fragment.deleteList.size() == 0)
            {
                Toast.makeText(
                        getApplicationContext(),
                        "Kesalahan : Belum ada pertukaran buku yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah "+fragment.deleteList.size()+" pertukaran buku yang anda pilih telah dikembalikan?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        fragment.deleteList.size()+" pertukaran buku telah dikembalikan.",
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

    public void showTambahPertukaranBuku(View view) {
        Intent intent = new Intent(this, TambahPertukaranBukuActivity.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public ListView mListviewPertukaranBuku;
        public ImageButton mBtnAddPertukaranBuku;
        public View rootView;
        public PertukaranBukuListAdapter adapterPertukaranBuku;
        public List<PertukaranBuku> listPertukaranBuku;
        public PertukaranBukuDAO mPertukaranBukuDao;
        public BukuDAO mBukuDao;
        public TbSekitarDAO mTBSekitarDao;
        public List<PertukaranBuku> deleteList;

        private boolean pertukaranBukuExists(PertukaranBuku pertukaranBuku) {
            int id = pertukaranBuku.getId_pertukaranBuku();
            int pertukaranBukuCount = listPertukaranBuku.size();

            for (int i = 0; i < pertukaranBukuCount; i++) {
                if (id == (int) listPertukaranBuku.get(i).getId_pertukaranBuku())
                    return true;
            }
            return false;
        }

        public PlaceholderFragment() {
        }

        private class PertukaranBukuListAdapter extends ArrayAdapter<PertukaranBuku> {

            public ArrayList<PertukaranBuku> arraylist;
            public List<PertukaranBuku> mListPertukaranBuku;

            public PertukaranBukuListAdapter(List<PertukaranBuku> mListPertukaranBuku) {
                super ( getActivity(),
                        R.layout.list_item_kelola_pertukaran_buku,
                        mListPertukaranBuku);
                this.mListPertukaranBuku = mListPertukaranBuku;
                this.arraylist = new ArrayList<PertukaranBuku>();
                this.arraylist.addAll(mListPertukaranBuku);
            }

            private class ViewHolder {
                TextView buku;
                TextView TBSekitar;
                TextView tglPinjam;
                TextView tglKembali;
                CheckBox cb;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;

                final PertukaranBuku  currentPertukaranBuku = mListPertukaranBuku.get(position);
                mTBSekitarDao = new TbSekitarDAO(getContext());
                mBukuDao = new BukuDAO(getContext());
                Log.d("id tb sekitar",""+currentPertukaranBuku.getId_TBSekitar());
                String namaTB = mTBSekitarDao.getTbSekitar(currentPertukaranBuku.getId_TBSekitar()).getNama();
                Log.d("nama TB", namaTB);
                String namaBuku = mBukuDao.getBuku(currentPertukaranBuku.getId_buku()).getJudul_buku();

                if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_kelola_pertukaran_buku, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.buku = (TextView) view.findViewById(R.id.viewBuku);
                    viewHolder.TBSekitar = (TextView) view.findViewById(R.id.viewTBSekitar);
                    viewHolder.tglPinjam = (TextView) view.findViewById(R.id.viewTanggalPinjam);
                    viewHolder.tglKembali = (TextView) view.findViewById(R.id.viewTanggalKembali);
                    viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);
                }
                else{
                    viewHolder = (ViewHolder) view.getTag();
                }

                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (!deleteList.contains(currentPertukaranBuku))
                                deleteList.add(currentPertukaranBuku);
                        } else {
                            deleteList.remove(currentPertukaranBuku);
                        }
                    }
                });
                //Simpan Datanya ke Holder
                viewHolder.buku.setText(namaBuku);
                viewHolder.TBSekitar.setText(namaTB);
                viewHolder.tglPinjam.setText(currentPertukaranBuku.getTanggal_pinjam());
                viewHolder.tglKembali.setText(currentPertukaranBuku.getTanggal_kembali());

                if(deleteList.contains(currentPertukaranBuku))
                    viewHolder.cb.setChecked(true);
                if(!deleteList.contains(currentPertukaranBuku))
                    viewHolder.cb.setChecked(false);

                view.setTag(viewHolder);
                return view;

        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            //Pake list yang di passing ke adapter dari fragment
            mListPertukaranBuku.clear();
            if (charText.length() == 0) {
                mListPertukaranBuku.addAll(arraylist);
            }
            else
            {
                for (PertukaranBuku pertukaranBuku : arraylist)
                {
                    if (mBukuDao.getBuku(pertukaranBuku.getId_buku()).getJudul_buku().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        mListPertukaranBuku.add(pertukaranBuku);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

        public void delete()
        {
            if(deleteList.size()!=0 && deleteList!=null)
            {
                for(int i =0; i<deleteList.size();i++)
                {
                    Buku buku = mBukuDao.getBuku(deleteList.get(i).getId_buku());
                    buku.setStatus("Tersedia");
                    mBukuDao.updateBuku(buku);
                    PertukaranBuku pertukaranBuku = deleteList.get(i);
                    pertukaranBuku.setStatus(0);
                    mPertukaranBukuDao.updatePertukaranBuku(pertukaranBuku);
                }
            }
            deleteList = new ArrayList<>();
            populateList();
        }

        //Animasi saat tekan tombol delete
        /*public void slideDeleteFragment()
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
        }*/

        public void populateList()
        {
            mListviewPertukaranBuku = (ListView) rootView.findViewById(R.id.listViewKelolaPertukaranBuku);
            listPertukaranBuku = mPertukaranBukuDao.getAllPertukaranBuku();
            Collections.reverse(listPertukaranBuku);
            adapterPertukaranBuku = new PertukaranBukuListAdapter(listPertukaranBuku);
            mListviewPertukaranBuku.setAdapter(adapterPertukaranBuku);
            mListviewPertukaranBuku.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent = new Intent(rootView.getContext(), UbahPertukaranBukuActivity.class);
                    intent.putExtra("id", listPertukaranBuku.get(position).getId_pertukaranBuku());
                    mTBSekitarDao.close();
                    mBukuDao.close();
                    mPertukaranBukuDao.close();
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_kelola_pertukaran_buku, container, false);


            mBtnAddPertukaranBuku = (ImageButton) rootView.findViewById(R.id.add_button);
            mPertukaranBukuDao = new PertukaranBukuDAO(rootView.getContext());
            mTBSekitarDao = new TbSekitarDAO(rootView.getContext());
            mBukuDao = new BukuDAO(rootView.getContext());
            deleteList = new ArrayList<PertukaranBuku>();

            mBtnAddPertukaranBuku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), TambahPertukaranBukuActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            populateList();
            return rootView;
        }
    }
}