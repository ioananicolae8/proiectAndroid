package com.example.licenta.smartdoctor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;

public class DespreDiabetActivity extends AppCompatActivity {

    private TextView despreDiabetTitlu;
    private ImageView despreDiabetImage;
    private TextView despreDiabetContent;

    private String titluSelectat;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despre_diabet);

        despreDiabetTitlu = findViewById(R.id.despreDiabetTitlu);
        despreDiabetImage= findViewById(R.id.despreDiabetImage);
        despreDiabetContent = findViewById(R.id.despreDiabetContent);


        titluSelectat = getIntent().getStringExtra("titluDetaliiSelectat");
        populeazaDetalii();
    }

    private void populeazaDetalii() {
        switch (titluSelectat) {
            case "Tipuri de diabet":
                despreDiabetTitlu.setText("Tipuri de diabet");
                despreDiabetImage.setImageResource(R.drawable.icon_diabet1);
                despreDiabetContent.setText(R.string.text1);
                break;
            case "Cauzele și simptomele diabetului":
                despreDiabetTitlu.setText("Cauzele și simptomele diabetului");
                despreDiabetImage.setImageResource(R.drawable.icon_diabet2);
                despreDiabetContent.setText(R.string.text2);
                break;
            case "Tratament și măsuri de prevenire":
                despreDiabetTitlu.setText("Tratament și măsuri de prevenire");
                despreDiabetImage.setImageResource(R.drawable.icon_diabet3);
                despreDiabetContent.setText(R.string.text3);
                break;
            case "Statistici în România":
                despreDiabetTitlu.setText("Statistici în România");
                despreDiabetImage.setImageResource(R.drawable.icon_diabet4);
                despreDiabetContent.setText(R.string.text4);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(this);
    }
}