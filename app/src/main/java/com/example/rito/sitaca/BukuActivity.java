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
import android.support.v7.app.ActionBar;
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


public class BukuActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buku, menu);

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
                fragment.adapterBuku.filter(newText);
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
                        "Kesalahan : Belum ada buku yang dipilih",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah "+fragment.deleteList.size()+" buku yang anda pilih ingin dihapus?")
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

    /*public void slideDelete(){
        fragment.slideDeleteFragment();
    }*/

    public void showTambahBuku (View view) {
        Intent intent = new Intent(this, TambahBukuActivity.class);
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

        private ListView mListviewBuku;
        private ImageButton mBtnAddBuku;
        public BukuListAdapter adapterBuku;
        public CheckBox cb;
        public View rootView;
        public boolean isDelete = false, onDelete=true;
        public List<Buku> deleteList;
        private List<Buku> listBuku;
        private BukuDAO mBukuDao;

        private boolean bukuExists(Buku buku) {
            int id = buku.getId_buku();
            int bukuCount = listBuku.size();

            for (int i = 0; i < bukuCount; i++) {
                if (id == (int) listBuku.get(i).getId_buku())
                    return true;
            }
            return false;
        }

        public PlaceholderFragment() {
        }


        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    "Peringatan : " + e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        private class BukuListAdapter extends ArrayAdapter<Buku> {

            public ArrayList<Buku> arraylist;
            public List<Buku> mListbuku;

            public BukuListAdapter(List<Buku> mListBuku) {
                super ( getActivity(),
                        R.layout.list_item_buku,
                        // R.id.listItemBukuTextview,
                        mListBuku);
                this.mListbuku = mListBuku;
                this.arraylist = new ArrayList<Buku>();
                this.arraylist.addAll(mListBuku);
            }

            private class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView judul;
                TextView pengarang;
                TextView status;
                CheckBox cb;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                if(position < listBuku.size()) {
                    final Buku currentBuku = listBuku.get(position);
                    mBukuDao = new BukuDAO(getContext());

                    if (view == null) {//---> Kalo View belum ada buat holder baru
                        view = getActivity().getLayoutInflater().inflate(R.layout.list_item_buku, parent, false);
                        viewHolder = new ViewHolder();
                        viewHolder.judul = (TextView) view.findViewById(R.id.viewJudulBuku);
                        viewHolder.pengarang = (TextView) view.findViewById(R.id.viewPengarang);
                        viewHolder.status = (TextView) view.findViewById(R.id.viewStatus);
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
                            if(!deleteList.contains(currentBuku)) {
                                deleteList.add(currentBuku);
                            }
                        }
                        else {
                            deleteList.remove(currentBuku);
                        }
                        }
                    });

                    viewHolder.judul.setText(currentBuku.getJudul_buku());
                    viewHolder.pengarang.setText(currentBuku.getPengarang());
                    viewHolder.status.setText(currentBuku.getStatus());
                    ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
                    if(deleteList.contains(currentBuku))
                        viewHolder.cb.setChecked(true);
                    if(!deleteList.contains(currentBuku))
                        viewHolder.cb.setChecked(false);

                    view.setTag(viewHolder);
               }
                return view;
            }
            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListbuku.clear();
                if (charText.length() == 0) {
                    mListbuku.addAll(arraylist);
                }
                else
                {
                    for (Buku buku : arraylist)
                    {
                        if (mBukuDao.getBuku(buku.getId_buku()).getJudul_buku().toLowerCase(Locale.getDefault()).contains(charText))
                        {
                            mListbuku.add(buku);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void delete()
        { int cnt = 0;
           if(deleteList.size()!=0 && deleteList!=null)
           {
              for(int i =0; i<deleteList.size();i++)
              {
                  Buku buku = mBukuDao.getBuku(deleteList.get(i).getId_buku());
                  if(buku.getStatus().equalsIgnoreCase("Dipinjam")){
                      cnt++;
                  }
                  else{
                        mBukuDao.deleteBuku(deleteList.get(i));
                  }
              }
           }
            if(cnt>0)
            {
                Toast.makeText(
                        rootView.getContext(),
                        "Peringatan : "+cnt+ " buku tidak dapat dihapus karena sedang dipinjam.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            if(deleteList.size()>0) {
                if(deleteList.size() - cnt > 0){
                    Toast.makeText(
                            rootView.getContext(),
                            "" + (deleteList.size() - cnt) + " buku telah dihapus.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
            deleteList = new ArrayList<>();
            populateList();
        }
        public void showTambahBuku(View view) {
            Intent intent = new Intent(getActivity(), TambahBukuActivity.class);
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
            else {
                isDelete = false;
                onDelete = true;
                populateList();
            }
        }

        public void populateList()
        {
            mListviewBuku = (ListView) rootView.findViewById(R.id.listViewBuku);
            listBuku = mBukuDao.getAllBuku();
            listBuku.remove(0);
            Collections.reverse(listBuku);
            adapterBuku = new BukuListAdapter(listBuku);
            mListviewBuku.setAdapter(adapterBuku);
            mListviewBuku.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    if(listBuku.get(position).getStatus().equalsIgnoreCase("Dipinjam")){
                           errorToast("Peringatan : Buku tidak dapat diubah karena sedang dipinjam.");
                    }
                    else {
                        Intent intent = new Intent(rootView.getContext(), UbahBukuActivity.class);
                        intent.putExtra("id", listBuku.get(position).getId_buku());
                        mBukuDao.close();
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_buku, container, false);

            mBtnAddBuku = (ImageButton) rootView.findViewById(R.id.add_button);
            mBukuDao = new BukuDAO(rootView.getContext());
            deleteList = new ArrayList<Buku>();

            mBtnAddBuku.setOnClickListener(new View.OnClickListener() { //-------------------------------------------------------->>Baru
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), TambahBukuActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            populateList();
            return rootView;
        }
    }
}
