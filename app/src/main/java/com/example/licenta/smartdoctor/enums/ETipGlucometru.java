package com.example.licenta.smartdoctor.enums;

public enum ETipGlucometru {
    FREESTYLE_FREEDOM_LITE {
        @Override
        public String getName() {
            return "Freestyle Freedom Lite";
        }
    },
    FREESTYLE_INSULINX {
        @Override
        public String getName() {
            return "Freestyle Insulinx";
        }
    },
    FREESTYLE_Optium {
        @Override
        public String getName() {
            return "Freestyle Optium";
        }
    },
    GLUCOCHECK_EXCELLENT{
        @Override
        public String getName() {
            return "GlucoCheck Excellent";
        }
    },
    ONETOUCH_SELECT_PLUS {
        @Override
        public String getName() {
            return "OneTouch Select Plus";
        }
    },
    BRIO {
        @Override
        public String getName() {
            return "Brio";
        }
    },
    NEXUS {
        @Override
        public String getName() {
            return "Nexus";
        }
    },
    ESPRIT {
        @Override
        public String getName() {
            return "Esprit";
        }
    },
    ACCU_CHEK {
        @Override
        public String getName() {
            return "Accu chek";
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
