package com.example.responsi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TambahActivity extends AppCompatActivity {
    private DatabaseReference Database;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private AppCompatButton btnTambah, btnBatal;
    private EditText edit1, edit2;
    Uri fileuri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        btnTambah = (AppCompatButton) findViewById(R.id.btnTambahdata);
        btnBatal = (AppCompatButton)findViewById(R.id.btnBataldata);
        edit1 = (EditText) findViewById(R.id.edit_text1);
        //edit2 = (EditText) findViewById(R.id.edit_text2);

        Database = FirebaseDatabase.getInstance("https://responsi-756dc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        storageRef = FirebaseStorage.getInstance("gs://responsi-756dc.appspot.com").getReference();
        

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadintent = new Intent();
                uploadintent.setAction(Intent.ACTION_GET_CONTENT);

                uploadintent.setType("application/pdf");
                startActivityForResult(uploadintent, 1);

//                String titlefile = edit1.getText().toString();
//                String link_download = edit2.getText().toString();
//                if (!titlefile.matches("") && !link_download.matches("")) {
//                    tambahDataFile(titlefile, link_download);
//                    //finish();
//                }
//                Toast toast = Toast.makeText(TambahActivity.this, "Isi kedua form dengan benar", Toast.LENGTH_LONG);

            }
        });
        
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            fileuri = data.getData();

            final String timestamp = "" + System.currentTimeMillis();
            final String titlefile = edit1.getText().toString();
            final String link_download = timestamp;

            final StorageReference filepath = storageRef.child(link_download + "." + "pdf");

            if (!titlefile.matches("") && !link_download.matches("")) {
                tambahDataFile(titlefile, link_download);
            }
            Toast toast = Toast.makeText(TambahActivity.this, "Berhasil Upload", Toast.LENGTH_SHORT);

            filepath.putFile(fileuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        Toast.makeText(TambahActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(TambahActivity.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void tambahDataFile(String title, String link_download){
        FileModel data = new FileModel(title, link_download);
        Database.child("Files").child(link_download).setValue(data);

    }

}