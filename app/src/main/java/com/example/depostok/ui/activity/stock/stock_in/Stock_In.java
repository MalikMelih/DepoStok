package com.example.depostok.ui.activity.stock.stock_in;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depostok.R;
import com.example.depostok.obj.StockInData;
import com.example.depostok.ui.adapter.StockInAdapter;

import java.util.ArrayList;

public class Stock_In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {getSupportActionBar().hide();}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in);
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

        ArrayList<StockInData> mlistStockIn = new ArrayList<>();
        mlistStockIn.add(new StockInData("activity_stock_in",
                "DELL Inspiron 7577",
                "Dizüstü Laptop",
                R.drawable.ic_laptop));

        RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        StockInAdapter mAdapter = new StockInAdapter(mlistStockIn);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
