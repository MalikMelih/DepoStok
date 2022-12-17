package com.example.depostok.ui.activity.stock.stock_out;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.depostok.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;

public class Stock_Out_Scanner extends Activity implements DecoratedBarcodeView.TorchListener {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    Boolean torchStatus=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_out);
        start(savedInstanceState);

        Button btnListOut = findViewById(R.id.btnListOut);
        btnListOut.setOnClickListener(v -> {
            startActivity(new Intent(Stock_Out_Scanner.this, Stock_Out.class));
            finishAndRemoveTask();
        });
    }

    public void start(Bundle savedInstanceState) {
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);

        ImageView switchFlashlightButton = findViewById(R.id.ivFlash);

        ViewfinderView viewfinderView = findViewById(R.id.zxing_viewfinder_view);

        if (!hasFlash()) {
            switchFlashlightButton.setVisibility(View.GONE);
        }else{
            switchFlashlightButton.setOnClickListener(v -> {
                if(torchStatus) {
                    barcodeScannerView.setTorchOff();
                    torchStatus = false;
                }else{
                    barcodeScannerView.setTorchOn();
                    torchStatus = true;
                }
            });
        }

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.setShowMissingCameraPermissionDialog(false);
        capture.decode();

        viewfinderView.setMaskColor(Color.argb(100, 0, 0, 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    public void onBackPressed(View view) {
        finishAndRemoveTask();
    }
}
