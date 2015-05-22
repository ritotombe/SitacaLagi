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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class KategoriActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kategori, menu);

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
                fragment.adapterKategori.filter(newText);
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
        if (id == R.id.action_settings) {
            return true;
        }
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
                        "Kesalahan : Belum ada kategori yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah "+fragment.deleteList.size()+" kategori yang anda pilih ingin dihapus?")
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

    public void slideDelete(){
        fragment.slideDeleteFragment();
    }

    public void showTambahKategori (View view) {
        Intent intent = new Intent(this, TambahKategoriActivity.class);
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
        //mBukuDao.close();
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ListView mListviewKategori;
        private ImageButton mBtnAddKategori;
        public KategoriListAdapter adapterKategori;
        public CheckBox cb;
        public View rootView;
        public boolean isDelete = false, onDelete=true;
        public List<Kategori> deleteList;
        private List<Kategori> listKategori;
        private KategoriDAO mKategoriDao;

        private boolean kategoriExists(Kategori kategori) {
            int id = kategori.getId_kategori();
            int kategoriCount = listKategori.size();

            for (int i = 0; i < kategoriCount; i++) {
                if (id == (int) listKategori.get(i).getId_kategori())
                    return true;
            }
            return false;
        }

        public PlaceholderFragment() {
        }

        private class KategoriListAdapter extends ArrayAdapter<Kategori> {

            public ArrayList<Kategori> arraylist;
            public List<Kategori> mListkategori;

            public KategoriListAdapter(List<Kategori> mListKategori) {
                super ( getActivity(),
                        R.layout.list_item_kategori,
                        // R.id.listItemBukuTextview,
                        mListKategori);
                this.mListkategori = mListKategori;
                this.arraylist = new ArrayList<Kategori>();
                this.arraylist.addAll(mListKategori);
            }

            private class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView nama;
                CheckBox cb;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {
                ViewHolder viewHolder = null;// Tambahin Holder

                    final Kategori currentKategori = listKategori.get(position);
                    mKategoriDao = new KategoriDAO(getContext());

                    if (view == null) {//---> Kalo View belum ada buat holder baru
                        view = getActivity().getLayoutInflater().inflate(R.layout.list_item_kategori, parent, false);
                        viewHolder = new ViewHolder();
                        viewHolder.nama = (TextView) view.findViewById(R.id.viewNamaKategori);
                        viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                        view.setTag(viewHolder);
                    }
                    else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                        viewHolder = (ViewHolder) view.getTag();
                    }

                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(!deleteList.contains(currentKategori))
                                deleteList.add(currentKategori);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                        else {
                            deleteList.remove(currentKategori);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                });

                viewHolder.nama.setText(currentKategori.getNama());
                if(deleteList.contains(currentKategori))
                    viewHolder.cb.setChecked(true);
                if(!deleteList.contains(currentKategori))
                    viewHolder.cb.setChecked(false);

                view.setTag(viewHolder);
                return view;
            }
            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListkategori.clear();
                if (charText.length() == 0) {
                    mListkategori.addAll(arraylist);
                }
                else
                {
                    for (Kategori kategori : arraylist)
                    {
                        if (mKategoriDao.getKategori(kategori.getId_kategori()).getNama().toLowerCase(Locale.getDefault()).contains(charText))
                        {
                            mListkategori.add(kategori);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void delete()
        {
            if(deleteList.size()!=0)
            {
                for(int i =0; i<deleteList.size();i++)
                {
                    mKategoriDao.deleteKategori(deleteList.get(i));
                }
            }
            Toast.makeText(
                    rootView.getContext(),
                    deleteList.size()+ " kategori telah dihapus.",
                    Toast.LENGTH_SHORT
            ).show();
            deleteList = new ArrayList<>();
            populateList();
        }
        public void showTambahKategori(View view) {
            Intent intent = new Intent(getActivity(), TambahKategoriActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
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
            mListviewKategori = (ListView) rootView.findViewById(R.id.listViewKategori);
            listKategori = mKategoriDao.getAllKategori();
            listKategori.remove(0);
            Collections.reverse(listKategori);
            adapterKategori = new KategoriListAdapter(listKategori);
            mListviewKategori.setAdapter(adapterKategori);
            mListviewKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent = new Intent(rootView.getContext(), UbahKategoriActivity.class);
                    intent.putExtra("id", listKategori.get(position).getId_kategori());
                    mKategoriDao.close();
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_kategori, container, false);

            mBtnAddKategori = (ImageButton) rootView.findViewById(R.id.add_button);
            mKategoriDao = new KategoriDAO(rootView.getContext());
            deleteList = new ArrayList<Kategori>();

            mBtnAddKategori.setOnClickListener(new View.OnClickListener() { //-------------------------------------------------------->>Baru
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), TambahKategoriActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            populateList();
            return rootView;
        }
    }
}
