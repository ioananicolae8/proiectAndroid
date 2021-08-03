package com.example.licenta.smartdoctor.models;

import java.io.Serializable;

public class Doctor_Programari implements Serializable {
    private String idDoctor;
    private String nume;
    private String prenume;
    private String nrTelefon;
    private String email;
    private String dataOra;
    private String detalii;

    public Doctor_Programari() {}

    public Doctor_Programari(
            String idDoctor,
            String nume,
            String prenume,
            String nrTelefon,
            String email,
            String dataOra,
            String detalii
    ) {
        this.idDoctor = idDoctor;
        this.nume = nume;
        this.prenume = prenume;
        this.nrTelefon = nrTelefon;
        this.email = email;
        this.dataOra = dataOra;
        this.detalii = detalii;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataOra() {
        return dataOra;
    }

    public void setDataOra(String dataOra) {
        this.dataOra = dataOra;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }
}
