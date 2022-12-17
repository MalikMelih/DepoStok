package com.example.depostok.ui.activity.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.depostok.MainActivity;
import com.example.depostok.R;
import com.example.depostok.db.UrlConnection;
import com.example.depostok.helper.AppController;
import com.example.depostok.ui.activity.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityUserProfile extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView apikey;
    private String nick;
    Button btnLogout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        btnLogout=findViewById(R.id.btnLogout);
        EditText Email=findViewById(R.id.userEmail);

        Intent intent = getIntent();
        String udata=intent.getStringExtra(HomeActivity.KEY_NICK);
        String datas[] =udata.split(",",3);

        nick=datas[0];
        name=findViewById(R.id.tvName);
        email=findViewById(R.id.tvEmail);
        apikey=findViewById(R.id.tvAlamat);

        executeGetMethod();
    }

    public void executeGetMethod() {
        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.POST, UrlConnection.LOGIN_URL+ "/data.php" + "?nick=" + nick,
                response -> {
                    Log.i("log", response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONObject jsonBody = obj.getJSONObject("uye-bilgileri");
                        name.setText(jsonBody.getString("adSoyad"));
                        email.setText(jsonBody.getString("posta"));
                        apikey.setText(jsonBody.getString("API_TOKEN"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        JSONObject jsonObject = null;
                        String errorMessage = null;

                        if (response.statusCode == 400) {
                            errorMessage = new String(response.data);

                            try {

                                jsonObject = new JSONObject(errorMessage);
                                String serverResponseMessage = (String) jsonObject.get("hataMesaj");
                                Toast.makeText(getApplicationContext(), "" + serverResponseMessage, Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }) {

            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return new HashMap<>();
            }


        };

        jsonForGetRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonForGetRequest);

    }

    public void profileLogout(View V) {
        SharedPreferences preferences = getSharedPreferences("rcheckbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();

        startActivity(new Intent(ActivityUserProfile.this, MainActivity.class));
        finishAndRemoveTask();
    }

    public void onBackPressed(View view) {finishAndRemoveTask();}
}
