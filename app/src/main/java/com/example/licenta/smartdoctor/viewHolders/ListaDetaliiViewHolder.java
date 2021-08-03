package com.example.licenta.smartdoctor.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;

public class ListaDetaliiViewHolder extends RecyclerView.ViewHolder {

    private ImageView elementListaDetaliiImageView;
    private TextView elementListaDetaliiTextView;

    public ListaDetaliiViewHolder(@NonNull View itemView) {
        super(itemView);

        elementListaDetaliiImageView = itemView.findViewById(R.id.elementListaDetaliiImageView);
        elementListaDetaliiTextView = itemView.findViewById(R.id.elementListaDetaliiTextView);
    }

    public ImageView getElementListaDetaliiImageView() {
        return elementListaDetaliiImageView;
    }

    public TextView getElementListaDetaliiTextView() {
        return elementListaDetaliiTextView;
    }
}
