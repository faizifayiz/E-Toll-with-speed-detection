package com.example.e_troll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_troll.Adapter.FineAdapter;
import com.example.e_troll.Adapter.TollAdapter;
import com.example.e_troll.ModelClass.FineModelClass;
import com.example.e_troll.ModelClass.TollModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TollActivity extends AppCompatActivity {

    private static String TAG ="TollActivity";

    RecyclerView tollRV;

    ArrayList<TollModelClass> list;

    private GlobalPreference globalPreference;
    private String ip;
    private String id;

    private ImageView backImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toll);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        id = globalPreference.getID();

        // Toast.makeText(this, "qwertyui"+id, Toast.LENGTH_SHORT).show();
        backImageButton = findViewById(R.id.TBackImageButton);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(TollActivity.this,HomeActivity.class);
                startActivity(bIntent);
            }
        });

        tollRV = findViewById(R.id.TolllRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tollRV.setLayoutManager(layoutManager);


        getToll();
    }

    private void getToll() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/E-Toll/api/getToll.php?uid="+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);
                 // Toast.makeText(TollActivity.this, "gfdsw", Toast.LENGTH_SHORT).show();

                if (response.equals("failed")){
                    Toast.makeText(TollActivity.this, "No Toll Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{

                       //  Toast.makeText(TollActivity.this, "sdfghjk", Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String tid = object.getString("tid");
                            String date = object.getString("date");
                            String time = object.getString("time");
                            String boothname = object.getString("toll_booth");
                            String tollamount = object.getString("amount");
                            String status = object.getString("status");



                            list.add(new TollModelClass(id,date,tid,time,boothname,tollamount,status));

                            // Toast.makeText(CircularActivity.this, ""+description, Toast.LENGTH_SHORT).show();

                        }

                        TollAdapter adapter = new TollAdapter(list,TollActivity.this);
                        tollRV.setAdapter(adapter);

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}