package com.example.licenta.smartdoctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.models.ElementListaDetalii;
import com.example.licenta.smartdoctor.viewHolders.ListaDetaliiViewHolder;

import java.util.List;

public class ListaDetaliiAdapter<T> extends GenericAdapter<T> {

    public ListaDetaliiAdapter(Context context, List<T> list, View.OnClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.element_lista_detalii, parent, false);
        return new ListaDetaliiViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, T dataModel) {
        ElementListaDetalii elementListaDetalii = (ElementListaDetalii) dataModel;
        holder.itemView.setOnClickListener(mListener);

        ListaDetaliiViewHolder viewHolder = (ListaDetaliiViewHolder) holder;
        viewHolder.getElementListaDetaliiImageView().setImageResource(elementListaDetalii.getImagine());
        viewHolder.getElementListaDetaliiTextView().setText(elementListaDetalii.getTitlu());
    }
}
