package com.example.licenta.smartdoctor.models;

import com.example.licenta.smartdoctor.enums.ETerapieDeDiabet;
import com.example.licenta.smartdoctor.enums.ETipGlucometru;
import com.example.licenta.smartdoctor.enums.ETipSenzor;
import com.example.licenta.smartdoctor.enums.ETipulDiabetului;
import com.example.licenta.smartdoctor.utils.common.EnumHelper;

public class Pacient_Formular1 {
    private String alergii;
    private String medicamente;
    private String terapie_de_diabet;
    private String tipul_diabetului;
    private String tip_glucometru;
    private String tip_senzor;

    public Pacient_Formular1(){
    }

    public Pacient_Formular1(String alergii, String medicamente, String terapie_de_diabet,
                             String tipul_diabetului, String tip_glucometru,
                             String tip_senzor) {
        this.alergii = alergii;
        this.medicamente = medicamente;
        this.terapie_de_diabet = terapie_de_diabet;
        this.tipul_diabetului = tipul_diabetului;
        this.tip_glucometru = tip_glucometru;
        this.tip_senzor = tip_senzor;
    }

    public String getAlergii() {
        return alergii;
    }

    public void setAlergii(String alergii) {
        this.alergii = alergii;
    }

    public String getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(String medicamente) {
        this.medicamente = medicamente;
    }

    public String getTerapie_de_diabet() {
        return terapie_de_diabet;
    }

    public void setTerapie_de_diabet(String terapie_de_diabet) {
        this.terapie_de_diabet = terapie_de_diabet;
    }

    public ETerapieDeDiabet geteTerapieDeDiabet() {
        return EnumHelper.getEnumTerapieDeDiabet(this.terapie_de_diabet);
    }

    public void seteTerapieDeDiabet(ETerapieDeDiabet eTerapieDeDiabet) {
        this.terapie_de_diabet = eTerapieDeDiabet.getName();
    }

    public String getTipul_diabetului() {
        return tipul_diabetului;
    }

    public void setTipul_diabetului(String tipul_diabetului) {
        this.tipul_diabetului = tipul_diabetului;
    }

    public ETipulDiabetului geteTipulDiabetului() {
        return EnumHelper.getEnumTipDiabet(tipul_diabetului);
    }

    public void seteTipulDiabetului(ETipulDiabetului eTipulDiabetului) {
        this.tipul_diabetului = eTipulDiabetului.getName();
    }

    public String getTip_glucometru() {
        return tip_glucometru;
    }

    public void setTip_glucometru(String tip_glucometru) {
        this.tip_glucometru = tip_glucometru;
    }

    public ETipGlucometru geteTipGlucometru() {
        return EnumHelper.getEnumTipGlucometru(tip_glucometru);
    }

    public void seteTipGlucometru(ETipGlucometru eTipGlucometru) {
        this.tip_glucometru = eTipGlucometru.getName();
    }

    public String getTip_senzor() {
        return tip_senzor;
    }

    public void setTip_senzor(String tip_senzor) {
        this.tip_senzor = tip_senzor;
    }

    public ETipSenzor geteTipSenzor() {
        return EnumHelper.getEnumTipSenzor(tip_senzor);
    }

    public void seteTipSenzor(ETipSenzor eTipSenzor) {
        this.tip_senzor = eTipSenzor.getName();
    }
}
