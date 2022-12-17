package com.example.depostok.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depostok.R;
import com.example.depostok.obj.DataHome;
import com.example.depostok.services.Stock_In.StockScannerIn;
import com.example.depostok.services.Stock_Out.StockScannerOut;
import com.example.depostok.ui.activity.login.LoginActivity;
import com.example.depostok.ui.activity.profile.ActivityUserProfile;
import com.example.depostok.ui.adapter.HomeAdapter;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public static final String KEY_NICK = null;
    private ArrayList<DataHome> mlistDataHome;
    String udata, NameSurname, Email, nick;

    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {getSupportActionBar().hide();}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser();
        setFonts();
        addItem();

        TextView showComp = findViewById(R.id.tvTitleToolbar);
        showComp.setOnClickListener(v -> { showComp(); });
    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

    public void gProfile(View v) {
        startActivity(new Intent(HomeActivity.this, ActivityUserProfile.class).putExtra(KEY_NICK,nick));
    }

    private void getItem(int position) {
        String activity = mlistDataHome.get(position).getActivity();
        openPage(activity);
    }

    private void addItem() {

        mlistDataHome = new ArrayList<>();
        mlistDataHome.add(new DataHome("activity_stock_in",
                getString(R.string.label_menu_title_menu_asset_masuk),
                getString(R.string.label_menu_desc_1_menu_asset_masuk),
                getString(R.string.label_menu_desc_2_menu_asset_masuk),
                R.drawable.ic_barang_masuk));

        mlistDataHome.add(new DataHome("activity_stock_out",
                getString(R.string.label_menu_title_menu_asset_keluar),
                getString(R.string.label_menu_desc_1_menu_asset_keluar),
                getString(R.string.label_menu_desc_2_menu_asset_keluar),
                R.drawable.ic_barang_keluar));

        mlistDataHome.add(new DataHome("activity",
                getString(R.string.label_menu_title_menu_stock_opname),
                getString(R.string.label_menu_desc_1_menu_stock_opname),
                getString(R.string.label_menu_desc_2_menu_stock_opname),
                R.drawable.ic_stock_opname));

        mlistDataHome.add(new DataHome("activity",
                getString(R.string.label_menu_title_menu_barang_assets),
                getString(R.string.label_menu_desc_1_menu_barang_assets),
                getString(R.string.label_menu_desc_2_menu_barang_assets),
                R.drawable.ic_product_assets));

        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HomeAdapter(mlistDataHome);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(position -> getItem(position));

    }

    public void showComp() {
        /*
        BottomSheetDialog dialog = new BottomSheetDialog(HomeActivity.this, R.style.ThemeOverlay_MaterialComponents_DayNight_BottomSheetDialog);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_top_home,findViewById(R.id.hometop));
        dialogView.findViewById(R.id.allComp).setOnClickListener(v -> {
            TextView comp = findViewById(R.id.tvTitleToolbar);
            comp.setText("Tüm Şirketler");
            dialog.cancel();
        });
        dialogView.findViewById(R.id.ahl).setOnClickListener(v -> {
            TextView comp = findViewById(R.id.tvTitleToolbar);
            comp.setText("Ahlatcı Holding");
            dialog.cancel();
        });
        dialogView.findViewById(R.id.cg).setOnClickListener(v -> {
            TextView comp = findViewById(R.id.tvTitleToolbar);
            comp.setText("ÇorumGaz");
            dialog.cancel();
        });
        dialogView.findViewById(R.id.tvTitleToolbarDialog).setOnClickListener(v -> dialog.cancel());
        dialog.setContentView(dialogView);
        dialog.show();
        */
        Dialog dialog = new Dialog(HomeActivity.this, R.style.Base_V21_ThemeOverlay_MaterialComponents_BottomSheetDialog);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_top_home,findViewById(R.id.hometop));
        dialogView.findViewById(R.id.allComp).setOnClickListener(v -> {
            TextView comp = findViewById(R.id.tvTitleToolbar);
            comp.setText("Tüm Şirketler");
            dialog.cancel();
        });
        dialogView.findViewById(R.id.ahl).setOnClickListener(v -> {
            TextView comp = findViewById(R.id.tvTitleToolbar);
            comp.setText("Ahlatcı Holding");
            dialog.cancel();
        });
        dialogView.findViewById(R.id.cg).setOnClickListener(v -> {
            TextView comp = findViewById(R.id.tvTitleToolbar);
            comp.setText("ÇorumGaz");
            dialog.cancel();
        });
        dialogView.findViewById(R.id.tvTitleToolbarDialog).setOnClickListener(v -> dialog.cancel());
        dialog.setContentView(dialogView);
        dialog.show();

    }

    public void openPage(String name){
        if (name.equals("activity_stock_in")) {
            startActivity(new Intent(HomeActivity.this, StockScannerIn.class));
        }else if (name.equals("activity_stock_out")){
            startActivity(new Intent(HomeActivity.this, StockScannerOut.class));
        }else{
            Toast.makeText(HomeActivity.this,"Hata !",Toast.LENGTH_LONG).show();
        }
    }

    private void setFonts() {
        Typeface open_sans_semibold = Typeface.createFromAsset(getAssets(),"fonts/open_sans_semibold.ttf");

        TextView username=findViewById(R.id.username);
        TextView umail=findViewById(R.id.umail);

        username.setTypeface(open_sans_semibold);
        umail.setTypeface(open_sans_semibold);
    }

    private void getUser () {
        Intent intent = getIntent();
        udata=intent.getStringExtra(LoginActivity.KEY_DATA);
        String datas[] =udata.split(",",4);

        nick=datas[0];
        NameSurname=datas[1];
        Email=datas[2];
        TextView data1 = findViewById(R.id.username);
        data1.setText(NameSurname);
        TextView data2 = findViewById(R.id.umail);
        data2.setText(Email);

        //Profil Fotoğrafı Çekme
        final ImageView profile_photo = findViewById(R.id.ivPhotoProfile);
        final String imgURL  = "https://malikmelih.com/inventory/img/"+datas[3];
        if(!NameSurname.equals("Malik Melih HORAN")) {
            new DownLoadImageTask(profile_photo).execute(imgURL);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uygulamadan Çıkış")
                .setMessage("Uygulamadan Çıkmak İstiyor Musunuz?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> System.exit(0)).create().show();
    }
}

