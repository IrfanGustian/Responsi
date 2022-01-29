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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterCardview adapter;
    private ArrayList<FileModel> fileModelArrayList;
    private FloatingActionButton fab_tambah, fab_hitung, fab, fab_logout;
    private DatabaseReference Database;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileModelArrayList = new ArrayList<>();

        Database = FirebaseDatabase.getInstance("https://responsi-756dc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        fab_tambah = findViewById(R.id.fab_tambah);
        fab_hitung = findViewById(R.id.fabhitungadmin);
        fab_logout = findViewById(R.id.fablgoout);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        Database.child("Files").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fileModelArrayList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    FileModel fileModel = data.getValue(FileModel.class);
                    fileModelArrayList.add(new FileModel(fileModel.getTitle(),fileModel.getDownload_link()));

                }
                adapter = new AdapterCardview(fileModelArrayList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Toast toast = Toast.makeText(MainActivity.this, "Berhasil memuat database", Toast.LENGTH_LONG);
                toast.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast toast = Toast.makeText(MainActivity.this, "Gagal memuat database", Toast.LENGTH_LONG);
                toast.show();
            }
        });


        fab_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChoseLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });

        fab_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HitungLuasTabung.class));
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
        fab_tambah.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab_hitung.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab_logout.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab_tambah.animate().translationY(0);
        fab_hitung.animate().translationY(0);
        fab_logout.animate().translationY(0);
    }

}