package com.replik.peksansevkiyat.DataClass.ModelDto.Label;

import androidx.annotation.Nullable;

import java.util.List;

public class ZarfLabel {
    private String teslimAdi;
    private String teslimAdresi;
    private String nakliyeTipi;
    @Nullable
    private String sevkNo;
    private String not;
    private List<ZarfProducts> products;

    public ZarfLabel(String teslimAdi, String teslimAdresi, String nakliyeTipi, String not, String sevkNo, List<ZarfProducts> products) {
        this.teslimAdi = teslimAdi;
        this.teslimAdresi = teslimAdresi;
        this.nakliyeTipi = nakliyeTipi;
        this.products = products;
        this.not = not;
        this.sevkNo = sevkNo;
    }

    public String getTeslimAdi() {
        return teslimAdi;
    }

    public void setTeslimAdi(String teslimAdi) {
        this.teslimAdi = teslimAdi;
    }

    public String getTeslimAdresi() {
        return teslimAdresi;
    }

    public void setTeslimAdresi(String teslimAdresi) {
        this.teslimAdresi = teslimAdresi;
    }

    public String getNakliyeTipi() {
        return nakliyeTipi;
    }

    public void setNakliyeTipi(String nakliyeTipi) {
        this.nakliyeTipi = nakliyeTipi;
    }

    public List<ZarfProducts> getProducts() {
        return products;
    }

    public void setProducts(List<ZarfProducts> products) {
        this.products = products;
    }

    public String getSevkNo() {
        return sevkNo;
    }

    public void setSevkNo(String sevkNo) {
        this.sevkNo = sevkNo;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }
}
