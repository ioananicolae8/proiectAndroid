package com.example.licenta.smartdoctor.utils.common;

import com.example.licenta.smartdoctor.enums.ETerapieDeDiabet;
import com.example.licenta.smartdoctor.enums.ETipGlucometru;
import com.example.licenta.smartdoctor.enums.ETipSenzor;
import com.example.licenta.smartdoctor.enums.ETipulDiabetului;

public class EnumHelper {

    public static ETipSenzor getEnumTipSenzor(String value) {
        switch (value) {
            case "Freestyle Libre":
                return ETipSenzor.FREESTYLE_LIBRE;

            case "Freestyle Navigator II":
                return ETipSenzor.FREESTYLE_NAVIGATOR_II;

            case "G4":
                return ETipSenzor.G4;

            case "G5":
                return ETipSenzor.G5;

            case "Enlite Senzor":
                return ETipSenzor.ENLITE_SENZOR;

            case "Guardian Sensor":
                return ETipSenzor.GUARDIAN_SENSOR;

            case "Nu folosesc senzor":
                return ETipSenzor.NU_FOLOSESC;

            case "Alt aparat":
                return ETipSenzor.ALTCEVA;

        }

        return ETipSenzor.G5;
    }

    public static ETipGlucometru getEnumTipGlucometru(String value) {
        switch (value) {
            case "Freestyle Freedom Lite":
                return ETipGlucometru.FREESTYLE_FREEDOM_LITE;

            case "Freestyle Insulinx":
                return ETipGlucometru.FREESTYLE_INSULINX;

            case "Freestyle Optium":
                return ETipGlucometru.FREESTYLE_Optium;

            case "GlucoCheck Excellent":
                return ETipGlucometru.GLUCOCHECK_EXCELLENT;

            case "OneTouch Select Plus":
                return ETipGlucometru.ONETOUCH_SELECT_PLUS;

            case "Brio":
                return ETipGlucometru.BRIO;

            case "Nexus":
                return ETipGlucometru.NEXUS;

            case "Esprit":
                return ETipGlucometru.ESPRIT;

            case "Accu chek":
                return ETipGlucometru.ACCU_CHEK;

            case "Alt aparat":
                return ETipGlucometru.ALTCEVA;
        }

        return ETipGlucometru.FREESTYLE_FREEDOM_LITE;
    }

    public static ETerapieDeDiabet getEnumTerapieDeDiabet(String value) {
        switch(value) {
            case "Stilou injector insulina":
                return ETerapieDeDiabet.STILOU_INJECTOR_INSULINA;

            case "Pompa cu insulina":
                return ETerapieDeDiabet.POMPA_CU_INSULINA;

            case "Fara insulina":
                return ETerapieDeDiabet.FARA_INSULINA;
        }
        return ETerapieDeDiabet.FARA_INSULINA;
    }

    public static ETipulDiabetului getEnumTipDiabet(String value) {
        switch(value) {
            case "Tip 1":
                return ETipulDiabetului.TIP1;

            case "Tip 2":
                return ETipulDiabetului.TIP2;

            case "LADA":
                return ETipulDiabetului.LADA;

            case "Prediabet":
                return ETipulDiabetului.PREDIABET;

            case "MODY":
                return ETipulDiabetului.MODY;
        }
        return ETipulDiabetului.PREDIABET;
    }


}
