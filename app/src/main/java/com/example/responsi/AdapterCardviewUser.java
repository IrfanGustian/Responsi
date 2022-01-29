package com.example.responsi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterCardviewUser extends RecyclerView.Adapter<AdapterCardviewUser.FileViewHolder> {
    private DatabaseReference Database;
    Context con;

    private ArrayList<FileModel> dataList;

    public AdapterCardviewUser(ArrayList<FileModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_layout_user, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        holder.txtTitle.setText(dataList.get(pos).getTitle());

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadfile(dataList.get(pos).getDownload_link(), view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder{
        private AppCompatButton btnDownload;
        private TextView txtTitle, txtDownload_link;

        public FileViewHolder(View itemView) {
            super(itemView);
            btnDownload = (AppCompatButton)itemView.findViewById(R.id.btnDownloaduser);
            txtTitle = (TextView) itemView.findViewById(R.id.titleuser);

        }
    }

    private void downloadfile(String link_download, View v) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://responsi-756dc.appspot.com");
        storageRef.child(link_download + "." + "pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }
}
