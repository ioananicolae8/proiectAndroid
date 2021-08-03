package com.example.licenta.smartdoctor.enums;

public enum ETerapieDeDiabet {
    STILOU_INJECTOR_INSULINA {
        @Override
        public String getName() {
            return "Stilou injector insulina";
        }
    },
    POMPA_CU_INSULINA {
        @Override
        public String getName() {
            return "Pompa cu insulina";
        }
    },
    FARA_INSULINA {
        @Override
        public String getName() {
            return "Fara insulina";
        }
    };
    public abstract String getName();
}
