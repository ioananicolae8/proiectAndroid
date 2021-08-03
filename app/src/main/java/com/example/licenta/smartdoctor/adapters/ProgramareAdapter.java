package com.example.licenta.smartdoctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.models.Doctor_Programari;
import com.example.licenta.smartdoctor.viewHolders.ProgramareViewHolder;

import java.util.List;

public class ProgramareAdapter<T> extends GenericAdapter<T> {

    public ProgramareAdapter(Context context, List<T> list, View.OnClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.element_lista_programare, parent, false);
        return new ProgramareViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, T dataModel) {
        Doctor_Programari programare = (Doctor_Programari) dataModel;
        ProgramareViewHolder viewHolder = (ProgramareViewHolder) holder;

        viewHolder.getElementListaProgramareNume().setText(programare.getNume());
        viewHolder.getElementListaProgramarePrenume().setText(programare.getPrenume());
        viewHolder.getElementListaProgramareTelefon().setText(programare.getNrTelefon());
        viewHolder.getElementListaProgramareEmail().setText(programare.getEmail());
        viewHolder.getElementListaProgramareDataOra().setText(programare.getDataOra());
        viewHolder.getElementListaProgramareDetalii().setText(programare.getDetalii());
    }
}
