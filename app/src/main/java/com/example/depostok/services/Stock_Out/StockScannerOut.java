package com.example.depostok.services.Stock_Out;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.depostok.ui.activity.stock.stock_out.Stock_Out_Form;
import com.example.depostok.ui.activity.stock.stock_out.Stock_Out_Scanner;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class StockScannerOut extends AppCompatActivity {
    public static String KEY_BARCODE = "Barcode";
    String barcode=null;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("StockScannerOut", "Cancelled scan");
                        finishAndRemoveTask();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("StockScannerOut", "Cancelled scan due to missing camera permission");
                        Toast.makeText(StockScannerOut.this, "Kamera kullanım izni verdiğinizden emin olun", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("StockScannerOut", "Scanned");
                    OpenForm(result.getContents());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scanCustomScanner();
    }

    public void OpenForm(String text) {
        Intent intent = new Intent(StockScannerOut.this, Stock_Out_Form.class);
        intent.putExtra(KEY_BARCODE, text);
        startActivity(intent);
        finishAndRemoveTask();
    }

    public void scanCustomScanner() {
        ScanOptions options = new ScanOptions().setOrientationLocked(false).setCaptureActivity(Stock_Out_Scanner.class);
        barcodeLauncher.launch(options);
    }
}
