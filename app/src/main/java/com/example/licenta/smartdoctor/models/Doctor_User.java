package com.example.licenta.smartdoctor.models;

public class Doctor_User {

    private String email;
    private String username;
    private String avatarPath;
    private String gen;
    private int varsta;
    private String nume;
    private String prenume;
    private String nr_telefon;
    private String specializare;
    private String experienta;


    public Doctor_User() {

    }

    public Doctor_User(String email, String username, String avatarPath, String gen, int varsta, String nume,
                       String prenume, String nr_telefon, String specializare, String experienta) {
        this.email = email;
        this.username = username;
        this.avatarPath = avatarPath;
        this.gen=gen;
        this.varsta = varsta;
        this.nume=nume;
        this.prenume=prenume;
        this.nr_telefon=nr_telefon;
        this.specializare = specializare;
        this.experienta = experienta;
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

    public String getNr_telefon() {
        return nr_telefon;
    }

    public void setNr_telefon(String nr_telefon) {
        this.nr_telefon = nr_telefon;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public String getExperienta() {
        return experienta;
    }

    public void setExperienta(String experienta) {
        this.experienta = experienta;
    }
}
