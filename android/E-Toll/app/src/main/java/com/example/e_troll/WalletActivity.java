package com.example.e_troll;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WalletActivity extends AppCompatActivity {

    GlobalPreference globalPreference;

    TextView walletamountET;
    Button addmoneyBT;

    private String id;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        globalPreference = new GlobalPreference(this);
        id = globalPreference.getID();

        ip = globalPreference.getIp();
       // Toast.makeText(this, "hellooooooo"+id, Toast.LENGTH_SHORT).show();

        walletamountET = findViewById(R.id.walletamountTV);
        addmoneyBT = findViewById(R.id.addmoneyBT);

        addmoneyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, PaymentActivity.class);
                startActivity(intent);


            }
        });

                Mywallet();
            }


            private void Mywallet() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/E-Toll/api/wallet.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       //  Toast.makeText(WalletActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        if (!response.equals("")) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject data = jsonArray.getJSONObject(0);


                                String wallet = data.getString("amount");

                                walletamountET.setText("₹"+wallet);

                               //Toast.makeText(WalletActivity.this, ""+wallet+id, Toast.LENGTH_SHORT).show();
                                // TODO: Process the wallet data here
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Toast.makeText(WalletActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    @Nullable
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("uid",id);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);
                requestQueue.add(stringRequest);

    }
}