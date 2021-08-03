package com.example.licenta.smartdoctor.models;

import com.example.licenta.smartdoctor.enums.EMomentulZilei;
import com.example.licenta.smartdoctor.enums.EStareGenerala;

import java.io.Serializable;

public class Pacient_Formular2 implements Serializable {
    private String momentulZilei;
    private String activitate;
    private int carbohidrati;
    private String data_ora;
    private int diastola;
    private int glicemie;
    private int greutate;
    private String id_pacient;
    private String masa;
    private String medicamente;
    private String notite;
    private int sistola;
    private String stare_generala;
    private String vocal;

    public Pacient_Formular2(){

    }

    public Pacient_Formular2(String momentulZilei, String activitate, int carbohidrati, String data_ora, int diastola, int glicemie, int greutate,
                             String id_pacient, String masa, String medicamente, String notite, int sistola,
                             String stare_generala, String vocal) {
        this.momentulZilei=momentulZilei;
        this.activitate = activitate;
        this.carbohidrati = carbohidrati;
        this.data_ora = data_ora;
        this.diastola = diastola;
        this.glicemie = glicemie;
        this.greutate = greutate;
        this.id_pacient = id_pacient;
        this.masa = masa;
        this.medicamente = medicamente;
        this.notite = notite;
        this.sistola = sistola;
        this.stare_generala = stare_generala;
        this.vocal = vocal;
    }

    public String getMomentulZilei(){return momentulZilei;}

    public void setMomentulZilei(String momentulZilei) {this.momentulZilei=momentulZilei;}

    public EMomentulZilei geteMomentulZilei() {
        return EMomentulZilei.valueOf(momentulZilei);
    }

    public void seteMomentulZilei(EMomentulZilei eMomentulZilei) {
        this.stare_generala = eMomentulZilei.toString();
    }

    public String getActivitate() {
        return activitate;
    }

    public void setActivitate(String activitate) {
        this.activitate = activitate;
    }

    public int getCarbohidrati() {
        return carbohidrati;
    }

    public void setCarbohidrati(int carbohidrati) {
        this.carbohidrati = carbohidrati;
    }

    public String getData_ora() {
        return data_ora;
    }

    public void setData_ora(String data_ora) {
        this.data_ora = data_ora;
    }

    public int getDiastola() {
        return diastola;
    }

    public void setDiastola(int diastola) {
        this.diastola = diastola;
    }

    public int getGlicemie() {
        return glicemie;
    }

    public void setGlicemie(int glicemie) {
        this.glicemie = glicemie;
    }

    public int getGreutate() {
        return greutate;
    }

    public void setGreutate(int greutate) {
        this.greutate = greutate;
    }

    public String getId_pacient() {
        return id_pacient;
    }

    public void setId_pacient(String id_pacient) {
        this.id_pacient = id_pacient;
    }

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public String getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(String medicamente) {
        this.medicamente = medicamente;
    }

    public String getNotite() {
        return notite;
    }

    public void setNotite(String notite) {
        this.notite = notite;
    }

    public int getSistola() {
        return sistola;
    }

    public void setSistola(int sistola) {
        this.sistola = sistola;
    }

    public String getStare_generala() {
        return stare_generala;
    }

    public void setStare_generala(String stare_generala) {
        this.stare_generala = stare_generala;
    }

    public EStareGenerala geteStareGenerala() {
        return EStareGenerala.valueOf(stare_generala);
    }

    public void seteStareGenerala(EStareGenerala eStareGenerala) {
        this.stare_generala = eStareGenerala.toString();
    }

    public String getVocal() {
        return vocal;
    }

    public void setVocal(String vocal) {
        this.vocal = vocal;
    }
}
