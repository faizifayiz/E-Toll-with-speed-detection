package com.example.e_troll;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class IpActivity extends AppCompatActivity {

    GlobalPreference globalPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);


        globalPreference = new GlobalPreference(this);


        getIP();
    }

    private void getIP() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View mview = layoutInflater.inflate(R.layout.raw_ip,null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mview);
        final EditText userInputDialogEditText = mview.findViewById(R.id.ipEditText);
        userInputDialogEditText.setText(globalPreference.getIp());

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogbox, int id) {

                        globalPreference.saveIp(userInputDialogEditText.getText().toString());
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        userInputDialogEditText.setText(userInputDialogEditText.getText().toString());
                        Intent mintent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(mintent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogbox, int id) {
                        dialogbox.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
    }
}