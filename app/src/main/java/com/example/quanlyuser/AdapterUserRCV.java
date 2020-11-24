package com.example.quanlyuser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterUserRCV extends RecyclerView.Adapter<AdapterUserRCV.UserViewHolder> {
    private ArrayList<User> users;
    private Activity context;
    private UserDatabase userDatabase;

    public AdapterUserRCV( Activity context,ArrayList<User> users) {
        this.users = users;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterUserRCV.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcvnames,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserRCV.UserViewHolder holder, int position) {
        holder.tvName.setText(users.get(position).getName());
        userDatabase = UserDatabase.getInstance(context);
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext())
                .setTitle("Xóa User!")
               .setMessage("Bạn có muốn xóa "+users.get(position).getName()+" không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDatabase.userDAO().deleteUser(users.get(position));
                        users.clear();
                        users.addAll((ArrayList<User>)userDatabase.userDAO().getAllUser());
                        Toast.makeText(context, "Xóa thành công",Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }

                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_editname,null,false);
                AlertDialog alertDialog=builder.create();
                builder.setCancelable(false);
                alertDialog.setView(view);
                alertDialog.show();
                Button btnluu=view.findViewById(R.id.btnLuu);
                btnluu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User user = new User();
                        user = users.get(position);
                        TextView txteditName = view.findViewById(R.id.etpEditName);
                        user.setName(txteditName.getText().toString());
                        userDatabase.userDAO().updateUser(user);
                        alertDialog.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Sửa tên thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                Button btnThoat = view.findViewById(R.id.btnThoat);
                btnThoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private Button btnXoa,btnSua;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
