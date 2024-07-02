package com.replik.peksansevkiyat.DataClass.ModelDto.Label;

public class ZarfProducts {
    private String stokkodu;
    private String renkLogo;
    private String miktar;

    public ZarfProducts(String stokkodu, String renkLogo, String miktar) {
        this.stokkodu = stokkodu;
        this.renkLogo = renkLogo;
        this.miktar = miktar;
    }

    public String getStokkodu() {
        return stokkodu;
    }

    public void setStokkodu(String stokkodu) {
        this.stokkodu = stokkodu;
    }

    public String getRenkLogo() {
        return renkLogo;
    }

    public void setRenkLogo(String renkLogo) {
        this.renkLogo = renkLogo;
    }

    public String getMiktar() {
        return miktar;
    }

    public void setMiktar(String miktar) {
        this.miktar = miktar;
    }
}
