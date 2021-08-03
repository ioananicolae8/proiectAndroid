package com.example.licenta.smartdoctor.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;

public class ProgramareViewHolder extends RecyclerView.ViewHolder {

    private TextView elementListaProgramareNume;
    private TextView elementListaProgramarePrenume;
    private TextView elementListaProgramareTelefon;
    private TextView elementListaProgramareEmail;
    private TextView elementListaProgramareDataOra;
    private TextView elementListaProgramareDetalii;

    public ProgramareViewHolder(View itemView) {
        super(itemView);

        elementListaProgramareNume = itemView.findViewById(R.id.elementListaProgramareNume);
        elementListaProgramarePrenume = itemView.findViewById(R.id.elementListaProgramarePrenume);
        elementListaProgramareTelefon = itemView.findViewById(R.id.elementListaProgramareTelefon);
        elementListaProgramareEmail = itemView.findViewById(R.id.elementListaProgramareEmail);
        elementListaProgramareDataOra = itemView.findViewById(R.id.elementListaProgramareDataOra);
        elementListaProgramareDetalii = itemView.findViewById(R.id.elementListaProgramareDetalii);
    }

    public TextView getElementListaProgramareNume() {
        return elementListaProgramareNume;
    }

    public TextView getElementListaProgramarePrenume() {
        return elementListaProgramarePrenume;
    }

    public TextView getElementListaProgramareTelefon() {
        return elementListaProgramareTelefon;
    }

    public TextView getElementListaProgramareEmail() {
        return elementListaProgramareEmail;
    }

    public TextView getElementListaProgramareDataOra() {
        return elementListaProgramareDataOra;
    }

    public TextView getElementListaProgramareDetalii() {
        return elementListaProgramareDetalii;
    }
}
