package com.example.licenta.smartdoctor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.dao.Pacient_Formular2Dao;
import com.example.licenta.smartdoctor.enums.EStareGenerala;
import com.example.licenta.smartdoctor.models.Pacient_Formular2;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pacient_Formular2Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText etGlicemie;
    private EditText etCarbohidrati;
    private EditText etSistola;
    private EditText etDiastola;
    private EditText etGreutate;
    private EditText etActivitate;
    private EditText etMedicamente;
    private EditText etMasa;
    private Spinner spStare;
    private EditText etNotite;
    private ImageView imageVocal;
    private Chronometer chTimp;
    private Button btnFormular2;

    private Pacient_Formular2Dao formular2Dao;

    private String numeInregistrare;
    private MediaRecorder mediaRecorder;
    private String momentulZileiSelectat;

    private boolean inregistrarePornita = false;
    private boolean existaInregistrare = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular2_pacient);

        etGlicemie = findViewById(R.id.etGlicemie);
        etCarbohidrati = findViewById(R.id.etCarbohidrati);
        etSistola = findViewById(R.id.etSistola);
        etDiastola = findViewById(R.id.etDiastola);
        etGreutate = findViewById(R.id.etGreutate);
        etActivitate = findViewById(R.id.etActivitate);
        etMedicamente = findViewById(R.id.etMedicamente);
        etMasa = findViewById(R.id.etMasa);

        spStare = findViewById(R.id.spStare);
        List<String> stari = new ArrayList<String>();
        for (EStareGenerala stare : EStareGenerala.values()) {
            stari.add(stare.name());
        }
        ArrayAdapter<String> adapterStari = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stari);
        adapterStari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStare.setAdapter(adapterStari);

        etNotite = findViewById(R.id.etNotite);
        imageVocal = findViewById(R.id.imageVocal);
        chTimp = findViewById(R.id.chTimp);
        btnFormular2 = findViewById(R.id.btnFormular2);

        momentulZileiSelectat = getIntent().getStringExtra("momentulZileiSelectat");

        imageVocal.setOnClickListener(this);
        btnFormular2.setOnClickListener(this);

        formular2Dao = new Pacient_Formular2Dao(FirebaseFirestore.getInstance());
    }

    private boolean validare() {
        boolean isValid = true;

        if (etGlicemie.getText().toString().isEmpty()) {
            etGlicemie.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (etCarbohidrati.getText().toString().isEmpty()) {
            etCarbohidrati.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (etSistola.getText().toString().isEmpty()) {
            etSistola.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (etDiastola.getText().toString().isEmpty()) {
            etDiastola.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (etGreutate.getText().toString().isEmpty()) {
            etGreutate.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (etActivitate.getText().toString().isEmpty()) {
            etActivitate.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (etMedicamente.getText().toString().isEmpty()) {
            etMedicamente.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (etMasa.getText().toString().isEmpty()) {
            etMasa.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFormular2 && validare()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String data = dateFormat.format(new Date());

            Pacient_Formular2 formular2 = new Pacient_Formular2(
                    momentulZileiSelectat,
                    etActivitate.getText().toString(),
                    Integer.parseInt(etCarbohidrati.getText().toString()),
                    data,
                    Integer.parseInt(etDiastola.getText().toString()),
                    Integer.parseInt(etGlicemie.getText().toString()),
                    Integer.parseInt(etGreutate.getText().toString()),
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    etMasa.getText().toString(),
                    etMedicamente.getText().toString(),
                    etNotite.getText().toString(),
                    Integer.parseInt(etSistola.getText().toString()),
                    spStare.getSelectedItem().toString(),
                    ""
            );

            if (existaInregistrare) {
                incarcareVocal(formular2);
            } else {
                incarcareFormular2(formular2);
            }
        }

        if (v.getId() == R.id.imageVocal) {
            if (!inregistrarePornita) {
                inregistrarePornita = true;
                imageVocal.setImageResource(R.drawable.icon_microfon_rosu);
                chTimp.setBase(SystemClock.elapsedRealtime());
                chTimp.start();
                startInregistrare();
            } else {
                inregistrarePornita = false;
                imageVocal.setImageResource(R.drawable.icon_microfon);
                chTimp.stop();
                stopInregistrare();
                existaInregistrare = true;
            }
        }
    }

    private void incarcareVocal(final Pacient_Formular2 formular2) {
        String caleInregistrare = "incarcariVocal/" + numeInregistrare;
        Uri fisierInregistrare = Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC) +
                "/" + numeInregistrare));

        final StorageReference reference = FirebaseStorage.getInstance().getReference().child(
                caleInregistrare
        );

        reference.putFile(fisierInregistrare)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    formular2.setVocal(uri.toString());
                                    incarcareFormular2(formular2);
                                }
                            });
                        } else {
                            Toast.makeText(Pacient_Formular2Activity.this, "Inregistrarea nu a putut fi adaugata.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void incarcareFormular2(Pacient_Formular2 formular2) {
        formular2Dao.addFormular2(formular2)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Pacient_Formular2Activity.this, "Activitate înregistrată!", Toast.LENGTH_SHORT).show();
//                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(Pacient_Formular2Activity.this, "Activitatea nu s-a putut înregistra!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startInregistrare() {
        numeInregistrare = FirebaseAuth.getInstance().getCurrentUser().getUid() +
                "_" + (new Date()).toString().replace(" ", "_") + ".m4a";
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/" + numeInregistrare);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Toast.makeText(this, "Eroare la inregistrare.", Toast.LENGTH_SHORT).show();
        }

        mediaRecorder.start();
    }

    private void stopInregistrare() {
        mediaRecorder.stop();
        mediaRecorder.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(this);
    }
}