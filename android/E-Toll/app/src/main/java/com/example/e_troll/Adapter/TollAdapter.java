package com.example.e_troll.Adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_troll.GlobalPreference;
import com.example.e_troll.ModelClass.TollModelClass;
import com.example.e_troll.PaymentActivity;
import com.example.e_troll.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TollAdapter extends RecyclerView.Adapter<TollAdapter.MyViewHolder>{

    ArrayList<TollModelClass> list;
    Context context;
    String ip;
    String id;

    public TollAdapter(ArrayList<TollModelClass> list, Context context) {
        this.list = list;
        this.context = context;
        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
        id = globalPreference.getID();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_toll, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TollModelClass tolllist = list.get(position);

        holder.dateTV.setText(tolllist.getDate());
        holder.timeTV.setText(tolllist.getTime());
        holder.boothname.setText(tolllist.getBoothname());
        holder.tollamount.setText(tolllist.getTollamount());
        holder.status.setText(tolllist.getStatus());

        //Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();

        holder.tollpayBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = tolllist.getTollamount();
                String tid= tolllist.getId();

                // Prepare the POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("uid", id);
                params.put("tid", tid);
                params.put("amount", amount);

                // Create a new StringRequest to make a POST request
                String url = "http://" + ip + "/E-Toll/api/paytoll.php";
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Handle the response from the server
                                handlePaymentResponse(response, holder);
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
        TextView timeTV;
        TextView boothname;
        TextView tollamount;
        TextView status;
        Button tollpayBT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.TdateTV);
            timeTV = itemView.findViewById(R.id.TtimeTV);
            boothname = itemView.findViewById(R.id.TboothTV);
            tollamount = itemView.findViewById(R.id.TamountTV);
            status = itemView.findViewById(R.id.TstatusTV);
            tollpayBT = itemView.findViewById(R.id.TpayBT);
        }
    }

    private void handlePaymentResponse(String response, MyViewHolder holder) {
        if (response.equals("success")) {
            // Show an alert dialog asking the user to confirm payment
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Confirm Payment");
            alertDialogBuilder.setMessage("Are you sure you want to pay the toll?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // The user confirmed payment, update the UI (if needed)
                    // Show a success message, deduct the amount from the wallet, etc.
                    deductTollAmountFromWallet();

                    // Hide the tollpayBT button
                    holder.tollpayBT.setVisibility(View.GONE);
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // The user canceled payment, update the UI (if needed)
                    // For example, go back to the toll activity
                }
            });
            alertDialogBuilder.show();
        } else if (response.equals("insufficient")) {
            // Show an alert dialog indicating insufficient wallet amount
            AlertDialog.Builder insufficientDialogBuilder = new AlertDialog.Builder(context);
            insufficientDialogBuilder.setTitle("Insufficient Amount");
            insufficientDialogBuilder.setMessage("No sufficient amount available in wallet.");
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
                    // For example, go back to the toll activity
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

    private void deductTollAmountFromWallet() {
        // Implement the code to deduct the toll amount from the wallet
        // For example, make a POST request to the PHP server to deduct the amount from the wallet
        // ...
        Toast.makeText(context, "Paid Successfully", Toast.LENGTH_SHORT).show();


    }
}
