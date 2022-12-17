package com.example.depostok.ui.activity.stock.stock_in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.depostok.R;
import com.example.depostok.services.Stock_In.StockScannerIn;

public class Stock_In_Form extends AppCompatActivity {
    String Barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_stock_in);
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }

        Intent intent = getIntent();
        Barcode = intent.getStringExtra(StockScannerIn.KEY_BARCODE);

        TextView bNumber=findViewById(R.id.tvCode);
        bNumber.setText(Barcode);
    }

    public void onBackPressed(View view) {finishAndRemoveTask();}
}
