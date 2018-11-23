package com.example.annas.chatfix21;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db;
    private TextInputLayout nNama;
    private TextInputLayout nNim;
    private TextInputLayout nNohp;
    private Mahasiswa mahasiswa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mahasiswa = (Mahasiswa) getIntent().getSerializableExtra("mahasiswa");//
        db = FirebaseFirestore.getInstance();

        nNama = (TextInputLayout) findViewById(R.id.update_nama);
        nNim = (TextInputLayout) findViewById(R.id.update_nim);
        nNohp = (TextInputLayout) findViewById(R.id.update_nohp);

        nNama.getEditText().setText(mahasiswa.getNama());
        nNim.getEditText().setText(mahasiswa.getNpm());
        nNohp.getEditText().setText(mahasiswa.getNohp());

      findViewById(R.id.btn_update).setOnClickListener(this);
    }
    private boolean hasValidationErrors(String nama, String npm, String nohp) {
        if (nama.isEmpty()) {
            nNama.setError("Name required");
            nNama.requestFocus();
            return true;
        }

        if (npm.isEmpty()) {
            nNim.setError("Nim required");
            nNim.requestFocus();
            return true;
        }

        if (nohp.isEmpty()) {
            nNohp.setError("NoHp required");
            nNohp.requestFocus();
            return true;
        }

        return false;
    }
    private void updateMahasiswa() {
        String nama = nNama.getEditText().getText().toString().trim();
        String npm = nNim.getEditText().getText().toString().trim();
        String nohp = nNohp.getEditText().getText().toString().trim();

        if (!hasValidationErrors(nama, npm, nohp)) {

            Mahasiswa p = new Mahasiswa(
                    nama, npm, nohp

            );


            db.collection("mahasiswa").document(mahasiswa.getId())
                    .set(p)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateActivity.this, "Mahasiswa Updated", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                updateMahasiswa();
                break;

        }

    }
    }

