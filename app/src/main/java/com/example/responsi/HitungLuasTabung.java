package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HitungLuasTabung extends AppCompatActivity {
    private EditText edittxtjari_jari, edittxtTinggi;
    private TextView hasil, hasilvol;
    private AppCompatButton btnHitung, btnBatal;
    private Double lp, volume, r, t;
    private String jari_jari, tinggi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_luas_tabung);

        edittxtjari_jari = findViewById(R.id.Jari_jari);
        edittxtTinggi = findViewById(R.id.tinggi);
        hasil = findViewById(R.id.textviewhasil);
        hasilvol = findViewById(R.id.textviewhasilvol);
        btnBatal = findViewById(R.id.btnBatalHitungtabung);
        btnHitung = findViewById(R.id.btnHitungTabung);



        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jari_jari = edittxtjari_jari.getText().toString();
                r = Double.parseDouble(jari_jari);

                tinggi = edittxtTinggi.getText().toString();
                t = Double.parseDouble(tinggi);

                System.out.println(jari_jari + tinggi);


                lp = 2 * Math.PI * r * (r + t);
                volume = Math.PI * (r*r) * t;
                hasil.setText(lp.toString() + " cm2");
                hasilvol.setText(volume.toString() + " cm3");
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}