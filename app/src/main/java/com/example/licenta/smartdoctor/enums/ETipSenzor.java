package com.example.licenta.smartdoctor.enums;

public enum ETipSenzor {
    FREESTYLE_LIBRE{
        @Override
        public String getName() {
            return "Freestyle Libre";
        }
    },
    FREESTYLE_NAVIGATOR_II {
        @Override
        public String getName() {
            return "Freestyle Navigator II";
        }
    },
    G4 {
        @Override
        public String getName() {
            return "G4";
        }
    },
    G5 {
        @Override
        public String getName() {
            return "G5";
        }
    },
    ENLITE_SENZOR {
        @Override
        public String getName() {
            return "Enlite Senzor";
        }
    },
    GUARDIAN_SENSOR {
        @Override
        public String getName() {
            return "Guardian Sensor";
        }
    },
    NU_FOLOSESC {
        @Override
        public String getName() {
            return "Nu folosesc senzor";
        }
    },
    ALTCEVA {
        @Override
        public String getName() {
            return "Alt aparat";
        }
    };

    public abstract String getName();
}
