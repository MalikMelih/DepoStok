package com.example.depostok.ui.activity.stock.stock_out;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depostok.R;
import com.example.depostok.obj.StockOutData;
import com.example.depostok.ui.adapter.StockOutAdapter;

import java.util.ArrayList;

public class Stock_Out extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {getSupportActionBar().hide();}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
        setFonts();
        addItem();
    }

    private void setFonts() {
        Typeface open_sans_semibold = Typeface.createFromAsset(getAssets(),"fonts/open_sans_semibold.ttf");

        TextView tvTitleToolbar=findViewById(R.id.tvTitleToolbar);

        tvTitleToolbar.setTypeface(open_sans_semibold);
    }

    public void onBackPressed(View view) {
        finishAndRemoveTask();
    }

    private void addItem() {

        ArrayList<StockOutData> mlistStockOut = new ArrayList<>();
        mlistStockOut.add(new StockOutData("activity_stock_out",
                "DELL Inspiron 7577",
                "Dizüstü Laptop",
                R.drawable.ic_laptop));

        RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        StockOutAdapter mAdapter = new StockOutAdapter(mlistStockOut);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}

