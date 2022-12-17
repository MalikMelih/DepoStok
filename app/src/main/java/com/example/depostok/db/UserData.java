package com.example.depostok.db;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.depostok.R;
import com.example.depostok.helper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserData extends AppCompatActivity {
    private String nick;
    String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_loading_dialog);
    }

    public void executeGetMethod() {
        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.GET, UrlConnection.LOGIN_URL+ "/data.php" + "?nick=" + nick,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("log", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject jsonBody = obj.getJSONObject("uye-bilgileri");
                            name =(jsonBody.getString("adSoyad"));
                            email=(jsonBody.getString("posta"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    JSONObject jsonObject = null;
                    String errorMessage = null;

                    switch (response.statusCode) {
                        case 400:
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
            }


        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> param = new HashMap<String, String>();

                return param;
            }


        };

        jsonForGetRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonForGetRequest);

    }
}
