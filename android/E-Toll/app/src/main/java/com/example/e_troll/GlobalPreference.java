package com.example.e_troll;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalPreference {

    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    private Context context;

    public GlobalPreference(Context context){
        sharedPreference = context.getSharedPreferences("sample",MODE_PRIVATE);
        editor = sharedPreference.edit();

    }

    public void saveIp(String ipaddress){
        editor.putString("ip",ipaddress);
        editor.apply();
    }

    public String getIp(){ return sharedPreference.getString("ip","");}


    public void saveID(String id){
        editor.putString("id",id);
        editor.apply();
    }

    public String getID(){ return sharedPreference.getString("id","");}

    public void savename(String name){
        editor.putString("name",name);
        editor.apply();
    }
    public String getname(){ return sharedPreference.getString("name","");}



    public void saveVehicle_no(String vehicle_no){
        editor.putString("vehicle_no",vehicle_no);
        editor.apply();
    }
    public String getvehicle_no(){ return sharedPreference.getString("vehicle_no","");}


    public void saveLatitude(String latitude){
        editor.putString("latitude",latitude);
        editor.apply();
    }
    public String getLatitude(){return sharedPreference.getString("latitude","");}

    public void saveLongitude(String longitude){
        editor.putString("longitude",longitude);
        editor.apply();
    }
    public String getLongitude(){return sharedPreference.getString("longitude","");}
}
