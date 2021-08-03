package com.example.licenta.smartdoctor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.models.Pacient_Formular2;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;

import java.io.IOException;

public class Pacient_VizualizareIstoric extends AppCompatActivity implements View.OnClickListener {

    private TextView tvGlicemie;
    private TextView tvCarbohidrati;
    private TextView tvSistola;
    private TextView tvDiastola;
    private TextView tvGreutate;
    private TextView tvActivitate;
    private TextView tvMedicamente;
    private TextView tvMasa;
    private TextView tvStare;
    private TextView tvNotite;
    private ImageView imageVizualizareIstoric;
    private Chronometer chVizualizareIstoric;
    private LinearLayout layoutInregistrareVizualizareIstoric;

    private Pacient_Formular2 formular2;

    private MediaPlayer mediaPlayer;
    private boolean seRedaInregistrarea = false;
    private long timpScursCronometru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient_vizualizare_istoric);

        tvGlicemie = findViewById(R.id.tvGlicemie);
        tvCarbohidrati = findViewById(R.id.tvCarbohidrati);
        tvSistola = findViewById(R.id.tvSistola);
        tvDiastola = findViewById(R.id.tvDiastola);
        tvGreutate = findViewById(R.id.tvGreutate);
        tvActivitate = findViewById(R.id.tvActivitate);
        tvMedicamente = findViewById(R.id.tvMedicamente);
        tvMasa = findViewById(R.id.tvMasa);
        tvStare = findViewById(R.id.tvStare);
        tvNotite = findViewById(R.id.tvNotite);
        imageVizualizareIstoric = findViewById(R.id.imageVizualizareIstoric);
        chVizualizareIstoric = findViewById(R.id.chVizualizareIstoric);
        layoutInregistrareVizualizareIstoric = findViewById(R.id.layoutInregistrareVizualizareIstoric);

        formular2 = (Pacient_Formular2) getIntent().getSerializableExtra("formular_selectat");
        mediaPlayer = new MediaPlayer();

        imageVizualizareIstoric.setOnClickListener(this);
        chVizualizareIstoric.setBase(SystemClock.elapsedRealtime());

        afisareDate();
    }

    private void afisareDate() {
        tvGlicemie.setText(String.valueOf(formular2.getGlicemie()));
        tvCarbohidrati.setText(String.valueOf(formular2.getCarbohidrati()));
        tvSistola.setText(String.valueOf(formular2.getSistola()));
        tvDiastola.setText(String.valueOf(formular2.getDiastola()));
        tvGreutate.setText(String.valueOf(formular2.getGreutate()));
        tvActivitate.setText(String.valueOf(formular2.getActivitate()));
        tvMedicamente.setText(formular2.getMedicamente());
        tvMasa.setText(formular2.getMasa());
        tvStare.setText(formular2.getStare_generala());
        tvNotite.setText(formular2.getNotite());

        if (!formular2.getVocal().equals("")) {
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(formular2.getVocal());
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        layoutInregistrareVizualizareIstoric.setVisibility(View.VISIBLE);
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        seRedaInregistrarea = false;
                        resetareCronometru();
                        imageVizualizareIstoric.setImageResource(R.drawable.icon_play);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageVizualizareIstoric && !seRedaInregistrarea) {
            seRedaInregistrarea = true;
            imageVizualizareIstoric.setImageResource(R.drawable.icon_pause);
            startCronometru();
            mediaPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void startCronometru() {
        chVizualizareIstoric.setBase(SystemClock.elapsedRealtime() - timpScursCronometru);
        chVizualizareIstoric.start();
    }

    public void resetareCronometru() {
        chVizualizareIstoric.stop();
        chVizualizareIstoric.setBase(SystemClock.elapsedRealtime());
        timpScursCronometru = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(this);
    }
}