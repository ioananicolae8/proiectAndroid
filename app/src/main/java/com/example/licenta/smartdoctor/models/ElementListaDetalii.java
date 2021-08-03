package com.example.licenta.smartdoctor.models;

public class ElementListaDetalii {

    private int imagine;
    private String titlu;

    public ElementListaDetalii(int imagine, String titlu) {
        this.imagine = imagine;
        this.titlu = titlu;
    }

    public int getImagine() {
        return imagine;
    }

    public void setImagine(int imagine) {
        this.imagine = imagine;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }
}
