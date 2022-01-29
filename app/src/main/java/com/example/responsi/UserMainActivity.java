package com.example.responsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterCardviewUser adapter;
    private ArrayList<FileModel> fileModelArrayList;
    private DatabaseReference Database;
    private FloatingActionButton fab_hitung, fab_logout, fab;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        fileModelArrayList = new ArrayList<>();

        Database = FirebaseDatabase.getInstance("https://responsi-756dc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_user);

        fab_hitung = findViewById(R.id.fab_hitunguser);
        fab_logout = findViewById(R.id.fablgooutuser);
        fab = (FloatingActionButton) findViewById(R.id.fabuser);

        Database.child("Files").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fileModelArrayList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    FileModel fileModel = data.getValue(FileModel.class);
                    fileModelArrayList.add(new FileModel(fileModel.getTitle(),fileModel.getDownload_link()));

                }
                adapter = new AdapterCardviewUser(fileModelArrayList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(UserMainActivity.this, 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Toast toast = Toast.makeText(UserMainActivity.this, "Berhasil memuat database", Toast.LENGTH_LONG);
                toast.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast toast = Toast.makeText(UserMainActivity.this, "Gagal memuat database", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        fab_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainActivity.this, ChoseLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        fab_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMainActivity.this, HitungLuasTabung.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

    }

    private void showFABMenu(){
        isFABOpen=true;
        fab_hitung.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab_logout.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab_hitung.animate().translationY(0);
        fab_logout.animate().translationY(0);
    }
}