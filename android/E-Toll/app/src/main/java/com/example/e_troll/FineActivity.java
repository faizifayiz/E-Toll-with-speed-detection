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
import com.example.e_troll.ModelClass.FineModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FineActivity extends AppCompatActivity {

    private static String TAG ="FineActivity";

    RecyclerView fineRV;

    ArrayList<FineModelClass> list;

    private GlobalPreference globalPreference;
    private String ip;
    private String id;

    private ImageView backImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        id = globalPreference.getID();

         //Toast.makeText(this, "qwertyui"+id, Toast.LENGTH_SHORT).show();
        backImageButton = findViewById(R.id.EBackImageButton);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(FineActivity.this,HomeActivity.class);
                startActivity(bIntent);
            }
        });

        fineRV = findViewById(R.id.FineRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        fineRV.setLayoutManager(layoutManager);


        getEventsImg();
    }

    private void getEventsImg() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/E-Toll/api/fine.php?uid="+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);
               //   Toast.makeText(FineActivity.this, "gfdsw", Toast.LENGTH_SHORT).show();

                if (response.equals("failed")){
                    Toast.makeText(FineActivity.this, "No Fine Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{

                        // Toast.makeText(FineActivity.this, "sdfghjk", Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String date = object.getString("date");
                            String fine = object.getString("fine_amount");
                            String status = object.getString("status");




                            list.add(new FineModelClass(id,date,fine,status));

                             //Toast.makeText(FineActivity.this, ""+fine, Toast.LENGTH_SHORT).show();

                        }

                         FineAdapter adapter = new FineAdapter(list,FineActivity.this);
                        fineRV.setAdapter(adapter);

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