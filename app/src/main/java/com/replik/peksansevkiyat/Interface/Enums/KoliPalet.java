package com.replik.peksansevkiyat.Interface.Enums;

import androidx.annotation.NonNull;

public enum KoliPalet {
    Koli, Palet, Dokme;

    @NonNull
    @Override
    public String toString() {
        switch (this) {
            case Koli:
                return "Koli";
            case Palet:
                return "Palet";
            case Dokme:
                return "Dökme";
        }

        return "";
    }
}
