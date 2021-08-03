package com.example.licenta.smartdoctor.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.activities.DespreDiabetActivity;
import com.example.licenta.smartdoctor.adapters.GenericAdapter;
import com.example.licenta.smartdoctor.adapters.ListaDetaliiAdapter;
import com.example.licenta.smartdoctor.models.ElementListaDetalii;

import java.util.ArrayList;
import java.util.List;

public class Pacient_Detalii_Fragment extends Fragment {

    private RecyclerView listaDetaliiRecyclerView;
    private List<ElementListaDetalii> elementeListaDetalii;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elementeListaDetalii = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pacient__detalii_, container, false);
        listaDetaliiRecyclerView = view.findViewById(R.id.listaDetaliiRecyclerView);
        populeazaLista();

        return view;
    }

    private void populeazaLista() {
        elementeListaDetalii.add(new ElementListaDetalii(R.drawable.icon_diabet1, "Tipuri de diabet"));
        elementeListaDetalii.add(new ElementListaDetalii(R.drawable.icon_diabet2, "Cauzele și simptomele diabetului"));
        elementeListaDetalii.add(new ElementListaDetalii(R.drawable.icon_diabet3, "Tratament și măsuri de prevenire"));
        elementeListaDetalii.add(new ElementListaDetalii(R.drawable.icon_diabet4, "Statistici în România"));

        GenericAdapter adapter = new ListaDetaliiAdapter(this.getContext(), elementeListaDetalii, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pozitie = listaDetaliiRecyclerView.getChildLayoutPosition(v);
                Intent intent = new Intent(Pacient_Detalii_Fragment.this.getContext(), DespreDiabetActivity.class);
                intent.putExtra("titluDetaliiSelectat", elementeListaDetalii.get(pozitie).getTitlu());
                startActivity(intent);
            }
        });

        listaDetaliiRecyclerView.setAdapter(adapter);
        listaDetaliiRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}