package com.example.licenta.smartdoctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.models.Doctor_User;
import com.example.licenta.smartdoctor.viewHolders.AlegereDoctorViewHolder;

import java.util.List;

public class AlegereDoctorAdapter<T> extends GenericAdapter<T> {

    public AlegereDoctorAdapter(Context context, List<T> list, View.OnClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.element_lista_alegere_doctor, parent, false);
        return new AlegereDoctorViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, T dataModel) {
        Doctor_User doctor = (Doctor_User) dataModel;
        AlegereDoctorViewHolder viewHolder = (AlegereDoctorViewHolder) holder;
        holder.itemView.setOnClickListener(mListener);

        viewHolder.getElementListaAlegereDoctorNumePrenume().setText("Dr. " + doctor.getNume() + " " + doctor.getPrenume());
        viewHolder.getElementListaAlegereDoctorEmail().setText(doctor.getEmail());
        viewHolder.getElementListaAlegereDoctorSpecializare().setText(doctor.getSpecializare());
    }
}
