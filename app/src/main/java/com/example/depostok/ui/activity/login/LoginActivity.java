package com.example.depostok.ui.activity.login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.depostok.R;
import com.example.depostok.db.UrlConnection;
import com.example.depostok.ui.activity.HomeActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOGIN_URL = UrlConnection.LOGIN_URL;
    public static final String DEVICE_URL = UrlConnection.DEVICE_URL;

    public static final String SHARED_PREF_NAME = "mypref";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_UNIQID = "uniqid";
    public static final String KEY_DATA = "null";
    public static final String CHECKED = "false";

    public CheckBox cremember;

    boolean connected = false;

    private EditText editTextUsername, editTextPassword;

    private String username, password, uniqid;

    public static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        cremember=findViewById(R.id.remember);
        editTextUsername = findViewById(R.id.userEmail);
        editTextPassword = findViewById(R.id.password);
        uniqid = getDeviceID();

        if(sharedPreferences.getBoolean(CHECKED, false))
        {
            queryUser(sharedPreferences.getString(KEY_USERNAME, null),
                    sharedPreferences.getString(KEY_PASSWORD, null),
                    uniqid);
        }

        Button loginBtn = findViewById(R.id.lgnBtn);
        loginBtn.setOnClickListener(this);
        setFonts();
        queryDevice();
    }

    @Override
    public void onClick(View v) {
        checklist(uniqid);
    }

    private void queryDevice() {
        TextView loginLbl=findViewById(R.id.loginlbl);
        TextView logindesc1=findViewById(R.id.loginDesc1);
        LinearLayout input1=findViewById(R.id.input1);
        LinearLayout input2=findViewById(R.id.input2);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            StringRequest queryDeviceString = new StringRequest(Request.Method.POST, DEVICE_URL,
                    response -> {
                        if(response.equals("success")){
                            loginLbl.setText(R.string.label_login);
                            logindesc1.setText(R.string.label_login_header_content);
                            input1.setVisibility(View.VISIBLE);
                            input2.setVisibility(View.VISIBLE);
                        }else if (response.equals("failure")){
                            loginLbl.setText("Kayıtsız Cihaz !");
                            logindesc1.setText("Cihazının Sistemde Kaydı Bulunamamıştır"+" "+uniqid);
                            input1.setVisibility(View.INVISIBLE);
                            input2.setVisibility(View.INVISIBLE);
                        }
                    },
                    error -> Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put(KEY_UNIQID,uniqid);
                    return map;
                }
            };
            RequestQueue queryDevice = Volley.newRequestQueue(LoginActivity.this);
            queryDevice.add(queryDeviceString);
        } else {
            connected = false;
            Toast.makeText(LoginActivity.this, "İnternet Bağlantınızı Kontrol Ediniz!", Toast.LENGTH_LONG).show();
        }

    }

    private void checklist(String uniqid) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();
            if (!username.equals("") && !password.equals("")) {
                queryUser(username, password, uniqid);
            } else if (username.equals("") && password.equals("")) {
                Toast.makeText(LoginActivity.this, R.string.label_error_empty_field, Toast.LENGTH_SHORT).show();
            } else if (username.equals("")) {
                Toast.makeText(LoginActivity.this, R.string.label_error_empty_email, Toast.LENGTH_SHORT).show();
            } else if (password.equals("")) {
                Toast.makeText(LoginActivity.this, R.string.label_error_empty_password, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, R.string.label_error, Toast.LENGTH_SHORT).show();
            }
        } else {
            connected = false;
            Toast.makeText(LoginActivity.this, "İnternet Bağlantınızı Kontrol Ediniz!", Toast.LENGTH_LONG).show();
        }
    }

    private void queryUser(String user, String pass, String uniqid) {
        StringRequest queryUserString = new StringRequest(Request.Method.POST, LOGIN_URL,
                response -> {
                    if(response.equals("failure")){
                        Toast.makeText(LoginActivity.this, "Kullanıcı Bilgileri Hatalı!", Toast.LENGTH_SHORT).show();
                    }else{
                        openApp(response);
                    }
                },
                error -> Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put(KEY_USERNAME,user);
                map.put(KEY_PASSWORD,pass);
                map.put(KEY_UNIQID,uniqid);
                return map;
            }
        };
        RequestQueue queryUser = Volley.newRequestQueue(LoginActivity.this);
        queryUser.add(queryUserString);

    }

    private void openApp(String data) {
        if (cremember.isChecked())
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME,editTextUsername.getText().toString());
            editor.putString(KEY_PASSWORD,editTextPassword.getText().toString());
            editor.putBoolean(CHECKED,cremember.isChecked());
            editor.apply();
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
        startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra(KEY_DATA,data));
        finish();

        /*Intent intent = new Intent(LoginActivity.this, ActivityUserProfile.class);
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);*/
    }

    public void fPass(View v) {
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
        finish();
    }

    private void setFonts() {
        Typeface open_sans_regular = Typeface.createFromAsset(getAssets(),"fonts/open_sans_regular.ttf");
        Typeface open_sans_bold = Typeface.createFromAsset(getAssets(),"fonts/open_sans_bold.ttf");
        Typeface open_sans_semibold = Typeface.createFromAsset(getAssets(),"fonts/open_sans_semibold.ttf");

        TextView loginLbl=findViewById(R.id.loginlbl);
        TextView loginDesc1=findViewById(R.id.loginDesc1);
        EditText userEmail=findViewById(R.id.userEmail);
        EditText password=findViewById(R.id.password);
        Button loginBtn=findViewById(R.id.lgnBtn);
        TextView forgetPass=findViewById(R.id.forgetPass);
        TextView copyright=findViewById(R.id.copyright);

        loginLbl.setTypeface(open_sans_regular);
        loginDesc1.setTypeface(open_sans_regular);
        userEmail.setTypeface(open_sans_semibold);
        password.setTypeface(open_sans_semibold);
        loginBtn.setTypeface(open_sans_bold);
        forgetPass.setTypeface(open_sans_bold);
        copyright.setTypeface(open_sans_bold);
    }

    private String getDeviceID() {
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uygulamadan Çıkış")
                .setMessage("Uygulamadan Çıkmak İstiyor Musunuz?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        System.exit(0);
                    }
                }).create().show();
    }
}
