package com.example.licenta.smartdoctor.viewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;

public class MesajChatViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout elementListaMesajChatLayoutMare;
    private LinearLayout elementListaMesajChatLayoutMic;
    private TextView elementListaMesajDataOra;
    private TextView elementListaMesajExpeditorEmail;
    private TextView elementListaMesajMesaj;


    public MesajChatViewHolder(View itemView) {
        super(itemView);

        elementListaMesajChatLayoutMare = itemView.findViewById(R.id.elementListaMesajChatLayoutMare);
        elementListaMesajChatLayoutMic = itemView.findViewById(R.id.elementListaMesajChatLayoutMic);
        elementListaMesajDataOra = itemView.findViewById(R.id.elementListaMesajDataOra);
        elementListaMesajExpeditorEmail = itemView.findViewById(R.id.elementListaMesajExpeditorEmail);
        elementListaMesajMesaj = itemView.findViewById(R.id.elementListaMesajMesaj);
    }

    public LinearLayout getElementListaMesajChatLayoutMare() {
        return elementListaMesajChatLayoutMare;
    }

    public LinearLayout getElementListaMesajChatLayoutMic() {
        return elementListaMesajChatLayoutMic;
    }

    public TextView getElementListaMesajDataOra() {
        return elementListaMesajDataOra;
    }

    public TextView getElementListaMesajExpeditorEmail() {
        return elementListaMesajExpeditorEmail;
    }

    public TextView getElementListaMesajMesaj() {
        return elementListaMesajMesaj;
    }
}
