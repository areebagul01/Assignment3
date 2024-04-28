package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.LoginViewHolder>{

    public ArrayList<Login> logins;
    AdapterCallback callback;
    Boolean isLoginList;

    public LoginAdapter(ArrayList<Login> list, Boolean isLoginList, AdapterCallback callback) {
        this.logins = list;
        this.isLoginList = isLoginList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public LoginViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.login_card, parent, false);
        return new LoginViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LoginViewHolder holder, int position) {
        holder.websiteUrl.setText(logins.get(position).getWebsiteUrl());
        holder.username.setText(logins.get(position).getUsername());
        holder.password.setText(logins.get(position).getPassword());
        if(isLoginList) {
            holder.menu.setImageResource(R.drawable.pencil);
        } else {
            holder.menu.setImageResource(R.drawable.restore);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext());
                alertDialog.setMessage("Do you really want to delete?");
                alertDialog.setTitle("Delete Notification");

                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Login login = logins.get(position);
                        callback.delete(login);
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();


                return false;
            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = logins.get(position);
                if(isLoginList) {
                    callback.info(login);
                } else  {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext());
                    alertDialog.setMessage("Do you really want to restore?");
                    alertDialog.setTitle("Restore Notification");

                    alertDialog.setPositiveButton("Restore", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callback.info(login);
                        }
                    });

                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return logins.size();
    }

    public static class LoginViewHolder extends RecyclerView.ViewHolder {
        TextView websiteUrl, username, password;
        ImageView menu;
        public LoginViewHolder(@NonNull View itemView) {
            super(itemView);
            websiteUrl =  itemView.findViewById(R.id.website_url);
            username =  itemView.findViewById(R.id.username);
            password =  itemView.findViewById(R.id.password);
            menu =  itemView.findViewById(R.id.menu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
