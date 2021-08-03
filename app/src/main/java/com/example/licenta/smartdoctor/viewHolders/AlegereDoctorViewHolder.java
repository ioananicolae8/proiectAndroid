package com.example.licenta.smartdoctor.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;

public class AlegereDoctorViewHolder extends RecyclerView.ViewHolder {

    private TextView elementListaAlegereDoctorNumePrenume;
    private TextView elementListaAlegereDoctorSpecializare;
    private TextView elementListaAlegereDoctorEmail;

    public AlegereDoctorViewHolder(View itemView) {
        super(itemView);

        elementListaAlegereDoctorNumePrenume = itemView.findViewById(R.id.elementListaAlegereDoctorNumePrenume);
        elementListaAlegereDoctorSpecializare = itemView.findViewById(R.id.elementListaAlegereDoctorSpecializare);
        elementListaAlegereDoctorEmail = itemView.findViewById(R.id.elementListaAlegereDoctorEmail);
    }

    public TextView getElementListaAlegereDoctorNumePrenume() {
        return elementListaAlegereDoctorNumePrenume;
    }

    public TextView getElementListaAlegereDoctorSpecializare() {
        return elementListaAlegereDoctorSpecializare;
    }

    public TextView getElementListaAlegereDoctorEmail() {
        return elementListaAlegereDoctorEmail;
    }
}
