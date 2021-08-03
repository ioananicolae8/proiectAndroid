package com.example.licenta.smartdoctor.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.models.MesajChat;
import com.example.licenta.smartdoctor.viewHolders.MesajChatViewHolder;

import java.util.List;

public class MesajChatAdapter<T> extends GenericAdapter<T> {

    private String emailUserLogat;

    public MesajChatAdapter(Context context, List<T> list, View.OnClickListener listener, String emailUserLogat) {
        super(context, list, listener);
        this.emailUserLogat = emailUserLogat;
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.element_lista_mesaj_chat, parent, false);
        return new MesajChatViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, T dataModel) {
        MesajChat mesajChat = (MesajChat) dataModel;
        MesajChatViewHolder viewHolder = (MesajChatViewHolder) holder;

        if (mesajChat.esteMesajulUseruluiLogat(emailUserLogat)) {
            viewHolder.getElementListaMesajChatLayoutMare().setGravity(Gravity.END);
            viewHolder.getElementListaMesajChatLayoutMic().setGravity(Gravity.END);
        } else {
            viewHolder.getElementListaMesajChatLayoutMare().setGravity(Gravity.START);
            viewHolder.getElementListaMesajChatLayoutMic().setGravity(Gravity.START);
        }

        viewHolder.getElementListaMesajDataOra().setText(mesajChat.getDataOraTrimitere());
        viewHolder.getElementListaMesajExpeditorEmail().setText(mesajChat.getExpeditorEmail());
        viewHolder.getElementListaMesajMesaj().setText(mesajChat.getMesaj());
    }
}
