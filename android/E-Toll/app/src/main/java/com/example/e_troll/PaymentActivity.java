package com.example.e_troll;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    EditText amountET;
    EditText cardNameET;
    EditText cardNumberET;
    EditText cvvET;
    Button makePaymentBT;
    RadioGroup cardTypeRG;
    String cardType;

    private GlobalPreference globalPreference;
    private String ip,uid;

    List<String> month = new ArrayList<>();

    List<String> year = new ArrayList<>();
    private Spinner MMspin;
    private Spinner YYspin;

    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        iniit();

        month.add("MM");
        for (int i = 1 ; i <= 31 ; i++)
        {
            month.add(String.valueOf(i));
        }

        year.add("YY");
        for(int i = 2020 ; i<=2025 ; i++ )
            year.add(String.valueOf(i));


        // Getting value from Spinners


        MMspin = (Spinner) findViewById(R.id.mmspinner);
        ArrayAdapter MM = new ArrayAdapter(this,android.R.layout.simple_spinner_item,month);
        MM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MMspin.setAdapter(MM);



        YYspin = (Spinner) findViewById(R.id.yyspinner);
        ArrayAdapter YY = new ArrayAdapter(this,android.R.layout.simple_spinner_item,year);
        YY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YYspin.setAdapter(YY);

        // Getting value from Radio Button

        cardTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
                cardType = radioButton.getText().toString();

            }
        });



        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent =  new Intent(PaymentActivity.this,WalletActivity.class);
                startActivity(bIntent);
            }
        });

        makePaymentBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(PaymentActivity.this, "hy"+ip, Toast.LENGTH_SHORT).show();

                payNow();
            }
        });

    }

    private void payNow() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/E-Toll/api/payment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Intent bIntent =  new Intent(PaymentActivity.this,HomeActivity.class);
                    startActivity(bIntent);
                    Toast.makeText(PaymentActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();
                    makePaymentBT.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(PaymentActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            @Nullable
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("bookId",bookID);
                params.put("userid",uid);
                params.put("amount",amountET.getText().toString());
                params.put("cardname",cardNameET.getText().toString());
                params.put("cardnumber",cardNumberET.getText().toString());
                params.put("cardtype",cardType);
                params.put("cardmonth",MMspin.getSelectedItem().toString());
                params.put("cardyear",YYspin.getSelectedItem().toString());
                params.put("cvv",cvvET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);
    }


    private void iniit() {
        amountET = findViewById(R.id.addamountEditText);
        cardNameET = findViewById(R.id.cardNameEditText);
        cardNumberET = findViewById(R.id.cardNumberEditText);
        cvvET = findViewById(R.id.cvvEditText);
        makePaymentBT = findViewById(R.id.makePaymentButton);
        cardTypeRG = findViewById(R.id.cardTypeRG);
        backIV = findViewById(R.id.BackImageButton);
    }
}