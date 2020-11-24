package com.example.quanlyuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserDatabase userDatabase;
    private ArrayList<User> users;
    private AdapterUserRCV adapterUserRCV;
    private Button btnThem,btnCancel;
    private TextView tvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcvNames);

        userDatabase = UserDatabase.getInstance(this);
        users = new ArrayList<>();
        users = (ArrayList<User>) userDatabase.userDAO().getAllUser();
        users.add(new User("Ty"));
        adapterUserRCV = new AdapterUserRCV(this,users);

        recyclerView.setAdapter(adapterUserRCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnThem = findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName = findViewById(R.id.etpName);
                String nameUser = tvName.getText().toString();
                userDatabase.userDAO().addUser(new User(nameUser));
                users.clear();
                users.addAll(userDatabase.userDAO().getAllUser());
                adapterUserRCV.notifyDataSetChanged();

                tvName.setText("");
                Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName = findViewById(R.id.etpName);
                tvName.setText("");
            }
        });
    }

}