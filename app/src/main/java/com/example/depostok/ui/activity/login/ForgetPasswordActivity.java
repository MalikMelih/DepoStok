package com.example.depostok.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.depostok.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextNick;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setFonts();

        editTextEmail= findViewById(R.id.etEmail);
        editTextNick= findViewById(R.id.etNick);
        send= findViewById(R.id.btnSend);

        send.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "DepoStok Uygulaması Giriş Bilgileri Sıfırlama");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Kayıtlı E-Mail Adresi "+editTextEmail.getText().toString()+" ve Kullanıcı Adı "+editTextNick.getText().toString()+" olan hesabın giriş bilgilerinin sıfırlanmasını talep ediyorum. '"+getDeviceID()+"'");
            startActivity(Intent.createChooser(emailIntent, "E-mail Göndermek için Seçiniz:"));
            String[] aEmailList = { "support@malikmelih.com" };
            emailIntent.putExtra(Intent.EXTRA_EMAIL, aEmailList);
            startActivity(emailIntent);
        });
    }

    public void bHome(View v) {
        startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
        finish();
    }

    private void setFonts() {
        Typeface open_sans_regular = Typeface.createFromAsset(getAssets(),"fonts/open_sans_regular.ttf");
        Typeface open_sans_bold = Typeface.createFromAsset(getAssets(),"fonts/open_sans_bold.ttf");

        TextView tvHeader= findViewById(R.id.tvHeader);
        TextView tvSubHeader1= findViewById(R.id.tvSubHeader1);
        TextView tvSubHeader2= findViewById(R.id.tvSubHeader2);
        Button btnSend= findViewById(R.id.btnSend);
        TextView tvBackLogin= findViewById(R.id.tvBackLogin);

        tvHeader.setTypeface(open_sans_regular);
        tvSubHeader1.setTypeface(open_sans_regular);
        tvSubHeader2.setTypeface(open_sans_regular);
        btnSend.setTypeface(open_sans_bold);
        tvBackLogin.setTypeface(open_sans_bold);
    }

    private String getDeviceID() {
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }

}
