package com.example.e_troll;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {


    EditText feedbackET;
    EditText reportET;
    Button submitBT;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private String encodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        reportET=findViewById(R.id.reportET);
        feedbackET=findViewById(R.id.feedbackBT);
        submitBT=findViewById(R.id.continueButton);



        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(FeedbackActivity.this, "", Toast.LENGTH_SHORT).show();
                uploadUserDetails();
            }


        });
    }

    private void uploadUserDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/E-Toll/api/feedback.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                    Toast.makeText(FeedbackActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(FeedbackActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FeedbackActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("report",reportET.getText().toString());
                params.put("feedback",feedbackET.getText().toString());


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FeedbackActivity.this);
        requestQueue.add(stringRequest);
    }
}