package com.example.e_troll.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_troll.GlobalPreference;
import com.example.e_troll.ModelClass.FineModelClass;
import com.example.e_troll.R;

import java.util.ArrayList;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.widget.Button;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.e_troll.PaymentActivity;



import java.util.HashMap;
import java.util.Map;



public class FineAdapter extends RecyclerView.Adapter<FineAdapter.MyViewHolder> {

    ArrayList<FineModelClass> list;
    Context context;
    String ip;
    String vehicle_no;
    String id;

    public FineAdapter(ArrayList<FineModelClass> list, Context context) {
        this.list = list;
        this.context = context;
        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
        vehicle_no = globalPreference.getvehicle_no();
        id = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_fine, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FineAdapter.MyViewHolder holder, int position) {
        FineModelClass finelist = list.get(position);

        holder.dateTV.setText(finelist.getDate());
        holder.fineTV.setText(finelist.getFine());
        holder.vehicleno.setText(vehicle_no);

        holder.fineBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String amount = finelist.getFine();
                String fid = finelist.getId();

                // Prepare the POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("uid", id);
                params.put("amount", amount);
                params.put("fid", fid);

                // Create a new StringRequest to make a POST request
                String url = "http://" + ip + "/E-Toll/api/payfine.php";
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Handle the response from the server
                                handlePaymentResponse(response, holder);

                                //Toast.makeText(context, "Errorfnnn: " + response, Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors if the request fails
                                // Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        return params;
                    }
                };

                // Add the request to the RequestQueue (usually you have a RequestQueue instance somewhere in your app)
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView fineTV;
        TextView vehicleno;
        TextView status;
        Button fineBT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.FdateTV);
            fineTV = itemView.findViewById(R.id.fineTV);
            vehicleno = itemView.findViewById(R.id.FvehicleNoTV);
            status = itemView.findViewById(R.id.TstatusTV);
            fineBT = itemView.findViewById(R.id.FfineBT);
        }
    }

    private void handlePaymentResponse(String response, FineAdapter.MyViewHolder holder) {
        if (response.equals("success")) {
            // Show an alert dialog asking the user to confirm payment
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Confirm Payment");
            alertDialogBuilder.setMessage("Are you sure you want to pay the fine?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // The user confirmed payment, update the UI (if needed)
                    // Show a success message, deduct the amount from the wallet, etc.
                    deductFineAmountFromWallet();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // The user canceled payment, update the UI (if needed)
                    // For example, go back to the fine activity
                }
            });
            alertDialogBuilder.show();
        } else if (response.equals("insufficient")) {
            // Show an alert dialog indicating insufficient wallet amount
            AlertDialog.Builder insufficientDialogBuilder = new AlertDialog.Builder(context);
            insufficientDialogBuilder.setTitle("Insufficient Amount");
            insufficientDialogBuilder.setMessage("No sufficient amount available in the wallet.");
            insufficientDialogBuilder.setPositiveButton("Add Amount", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // The user wants to add amount, handle the action (e.g., go to payment activity)
                    goToPaymentActivity();
                }
            });
            insufficientDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // The user canceled adding amount, update the UI (if needed)
                    // For example, go back to the fine activity
                }
            });
            insufficientDialogBuilder.show();
        } else {
            // Handle other cases or errors (e.g., "failed")
            // ...
        }
    }


    private void goToPaymentActivity() {
        Intent intent = new Intent(context, PaymentActivity.class);
        context.startActivity(intent);
    }

    private void deductFineAmountFromWallet() {
        // Implement the code to deduct the fine amount from the wallet
        // For example, make a POST request to the PHP server to deduct the amount from the wallet
        // ...
        Toast.makeText(context, "Paid Successfully", Toast.LENGTH_SHORT).show();
    }
}
