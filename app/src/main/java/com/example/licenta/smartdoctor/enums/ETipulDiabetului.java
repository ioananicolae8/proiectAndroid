package com.example.licenta.smartdoctor.enums;

public enum ETipulDiabetului {
    TIP1{
        @Override
        public String getName() {
            return "Tip 1";
        }
    },
    TIP2 {
        @Override
        public String getName() {
            return "Tip 2";
        }
    },
    LADA{
        @Override
        public String getName() {
            return "LADA";
        }
    },
    PREDIABET {
        @Override
        public String getName() {
            return "Prediabet";
        }
    },
    MODY {
        @Override
        public String getName() {
            return "MODY";
        }
    };

    public abstract String getName();
}
