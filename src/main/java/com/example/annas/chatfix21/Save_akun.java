package com.example.annas.chatfix21;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Save_akun extends AppCompatActivity {
    private FirebaseFirestore Firedb;
    private TextInputLayout nNama;
    private TextInputLayout nNim;
    private TextInputLayout nNohp;
    private Button btSave;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_akun);
        mProgress = new ProgressDialog(this);

        Firedb = FirebaseFirestore.getInstance();

        nNama = (TextInputLayout) findViewById(R.id.edit_nama);
        nNim = (TextInputLayout) findViewById(R.id.edit_nim);
        nNohp = (TextInputLayout) findViewById(R.id.edit_nohp);
        btSave = (Button) findViewById(R.id.save_btn1);


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = new ProgressDialog(Save_akun.this);
                mProgress.setTitle("Simpan Perubahan");
                mProgress.setMessage("Silahkan Tunggu");
                mProgress.show();
                String display_nama = nNama.getEditText().getText().toString();
                String Nim = nNim.getEditText().getText().toString();
                String Nohp = nNohp.getEditText().getText().toString();

                Map<String, String> userMap = new HashMap<>();

                userMap.put("nama", display_nama);
                userMap.put("nim", Nim);
                userMap.put("nohp", Nohp);

                Firedb.collection("mahasiswa").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mProgress.dismiss();
                        Toast.makeText(Save_akun.this, "Ditambah ke fire Store", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        String error = e.getMessage();
                        Toast.makeText(Save_akun.this, "Error" + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
