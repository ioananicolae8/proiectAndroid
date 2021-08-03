package com.example.licenta.smartdoctor.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.dao.Doctor_ProgramariDao;
import com.example.licenta.smartdoctor.models.Doctor_Programari;
import com.example.licenta.smartdoctor.utils.common.AuthenticationHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Doctor_HomeFragment extends Fragment {

    private EditText etNume;
    private EditText etPrenume;
    private EditText etTelefon;
    private EditText etEmail;
    private TimePicker tpOra;
    private DatePicker dpData;
    private EditText etDetalii;
    private Button btSalveaza;

    private String idUtilizatorLogat;
    private Doctor_ProgramariDao doctor_programariDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doctor_programariDao = new Doctor_ProgramariDao(FirebaseFirestore.getInstance());
        idUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor__home, container, false);

        etNume = view.findViewById(R.id.etNume);
        etPrenume = view.findViewById(R.id.etPrenume);
        etTelefon = view.findViewById(R.id.etTelefon);
        etEmail = view.findViewById(R.id.etEmail);
        tpOra = view.findViewById(R.id.tpOra);
        dpData = view.findViewById(R.id.dpData);
        etDetalii = view.findViewById(R.id.etDetalii);
        btSalveaza = view.findViewById(R.id.btSalveaza);

        btSalveaza.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btSalveaza && validare()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(
                            dpData.getYear(),
                            dpData.getMonth(),
                            dpData.getDayOfMonth(),
                            tpOra.getHour(),
                            tpOra.getMinute()
                    );

                    String dataFormatata = dateFormat.format(calendar.getTime());

                    Doctor_Programari programare = new Doctor_Programari(
                            idUtilizatorLogat,
                            etNume.getText().toString(),
                            etPrenume.getText().toString(),
                            etTelefon.getText().toString(),
                            etEmail.getText().toString(),
                            dataFormatata,
                            etDetalii.getText().toString()
                    );

                    incarcareProgramare(programare);
                }
            }
        });

        return view;
    }

    private boolean validare() {
        boolean isValid = true;

        if (etNume.getText().toString().isEmpty()) {
            etNume.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (!AuthenticationHelper.isUsernameValid(etNume.getText().toString())) {
            etNume.setError(getString(R.string.error_invalid_nume));
            isValid = false;
        }

        if (etPrenume.getText().toString().isEmpty()) {
            etPrenume.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (!AuthenticationHelper.isUsernameValid(etPrenume.getText().toString())) {
            etPrenume.setError(getString(R.string.error_invalid_prenume));
            isValid = false;
        }

        if (etTelefon.getText().toString().isEmpty()) {
            etTelefon.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (!AuthenticationHelper.isPhoneValid(etTelefon.getText().toString())) {
            etTelefon.setError(getString(R.string.error_invalid_phone));
            isValid = false;
        }

        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (!AuthenticationHelper.isEmailValid(etEmail.getText().toString())) {
            etEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        return isValid;
    }

    private void incarcareProgramare(Doctor_Programari programare){
        doctor_programariDao.addProgramare(programare)
               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       Toast.makeText(
                               Doctor_HomeFragment.this.getContext(),
                               "Programare adăugată cu succes! ",
                               Toast.LENGTH_SHORT
                       ).show();

                       etNume.setText("");
                       etPrenume.setText("");
                       etEmail.setText("");
                       etTelefon.setText("");
                       etDetalii.setText("");
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(Exception e) {
                       e.printStackTrace();
                       Toast.makeText(
                               Doctor_HomeFragment.this.getContext(),
                               "Programarea nu a putut fi adaugată.",
                               Toast.LENGTH_SHORT
                       ).show();
                   }
               }) ;
    }
}
