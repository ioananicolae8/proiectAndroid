package com.example.licenta.smartdoctor.models;

public class Pacient_User {
    private String email;
    private String username;
    private String avatarPath;
    private String cnp;
    private String gen;
    private int varsta;
    private String zi_de_nastere;
    private String nume;
    private String prenume;
    private String adresa;
    private String nr_telefon;
    private boolean hasCompletedQuiz;
    private boolean hasRequiredInformation;
    private String doctorId;
    private String doctorEmail;

    public Pacient_User() {
    }

    public Pacient_User(String email, String username, String avatarPath, String cnp, String gen, int varsta, String zi_de_nastere, String nume,
                        String prenume, String adresa, String nr_telefon, boolean hasCompletedQuiz, boolean hasRequiredInformation, String doctorId, String doctorEmail) {
        this.email = email;
        this.username = username;
        this.avatarPath = avatarPath;
        this.cnp=cnp;
        this.gen=gen;
        this.varsta = varsta;
        this.zi_de_nastere=zi_de_nastere;
        this.nume=nume;
        this.prenume=prenume;
        this.adresa=adresa;
        this.nr_telefon=nr_telefon;
        this.hasCompletedQuiz = hasCompletedQuiz;
        this.hasRequiredInformation = hasRequiredInformation;
        this.doctorId = doctorId;
        this.doctorEmail = doctorEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getZi_de_nastere() {
        return zi_de_nastere;
    }

    public void setZi_de_nastere(String zi_de_nastere) {
        this.zi_de_nastere = zi_de_nastere;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getNr_telefon() {
        return nr_telefon;
    }

    public void setNr_telefon(String nr_telefon) {
        this.nr_telefon = nr_telefon;
    }

    public boolean isHasCompletedQuiz() {
        return hasCompletedQuiz;
    }

    public void setHasCompletedQuiz(boolean hasCompletedQuiz) {
        this.hasCompletedQuiz = hasCompletedQuiz;
    }

    public boolean isHasRequiredInformation() {
        return hasRequiredInformation;
    }

    public void setHasRequiredInformation(boolean hasRequiredInformation) {
        this.hasRequiredInformation = hasRequiredInformation;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }
}

