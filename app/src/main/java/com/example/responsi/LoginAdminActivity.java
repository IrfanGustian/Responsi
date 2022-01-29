package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdminActivity extends AppCompatActivity {
    private EditText Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        Username = (EditText)findViewById(R.id.usernameAdmin);
        Password = (EditText) findViewById(R.id.passAdmin);
        Button button = (Button) findViewById(R.id.btnadminlogin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = Username.getText().toString();
                String pw = Password.getText().toString();

                if( un.equalsIgnoreCase("admin") && pw.equalsIgnoreCase("admin")){
                    Toast.makeText(LoginAdminActivity.this, "Anda Berhasil Login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginAdminActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginAdminActivity.this, "Username atau password salah", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}