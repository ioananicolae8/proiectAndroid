package com.example.licenta.smartdoctor.models;

public class MesajChat {

    public static final String FORMAT_DATA = "dd/MM/yyyy HH:mm:ss";

    private String pacientId;
    private String pacientEmail;
    private String doctorId;
    private String doctorEmail;
    private String mesaj;
    private String dataOraTrimitere;
    private String expeditorEmail;

    public MesajChat() {}

    public MesajChat(
            String pacientId,
            String pacientEmail,
            String doctorId,
            String doctorEmail,
            String mesaj,
            String dataOraTrimitere,
            String expeditorEmail
    ) {
        this.pacientId = pacientId;
        this.pacientEmail = pacientEmail;
        this.doctorId = doctorId;
        this.doctorEmail = doctorEmail;
        this.mesaj = mesaj;
        this.dataOraTrimitere = dataOraTrimitere;
        this.expeditorEmail = expeditorEmail;
    }

    public boolean esteMesajulUseruluiLogat(String emailUserLogat) {
        return expeditorEmail.equals(emailUserLogat);
    }

    public String getPacientId() {
        return pacientId;
    }

    public void setPacientId(String pacientId) {
        this.pacientId = pacientId;
    }

    public String getPacientEmail() {
        return pacientEmail;
    }

    public void setPacientEmail(String pacientEmail) {
        this.pacientEmail = pacientEmail;
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

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getDataOraTrimitere() {
        return dataOraTrimitere;
    }

    public void setDataOraTrimitere(String dataOraTrimitere) {
        this.dataOraTrimitere = dataOraTrimitere;
    }

    public String getExpeditorEmail() {
        return expeditorEmail;
    }

    public void setExpeditorEmail(String expeditorEmail) {
        this.expeditorEmail = expeditorEmail;
    }
}
