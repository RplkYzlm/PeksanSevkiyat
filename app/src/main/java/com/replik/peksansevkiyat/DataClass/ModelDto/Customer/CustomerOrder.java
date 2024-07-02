package com.replik.peksansevkiyat.DataClass.ModelDto.Customer;

import com.replik.peksansevkiyat.Interface.Enums.KoliPalet;

import java.io.Serializable;

public class CustomerOrder implements Serializable {
    private int id;
    private String sevkNo;
    private String nakliyeTipi;
    private int nakliyeId;
    private String cariIsim;
    private String cariKod;
    private String teslimAdi;
    private String teslimAdresi;
    private String koliAdet;
    private String createdAt;
    private String shipmentDate;
    private int koliPalet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSevkNo() {
        return sevkNo;
    }

    public void setSevkNo(String sevkNo) {
        this.sevkNo = sevkNo;
    }

    public String getNakliyeTipi() {
        return nakliyeTipi;
    }

    public void setNakliyeTipi(String nakliyeTipi) {
        this.nakliyeTipi = nakliyeTipi;
    }

    public String getCariIsim() {
        return cariIsim;
    }

    public void setCariIsim(String cariIsim) {
        this.cariIsim = cariIsim;
    }

    public String getCariKod() {
        return cariKod;
    }

    public void setCariKod(String cariKod) {
        this.cariKod = cariKod;
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

    public String getKoliAdet() {
        return koliAdet;
    }

    public void setKoliAdet(String koliAdet) {
        this.koliAdet = koliAdet;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public int getNakliyeId() {
        return nakliyeId;
    }

    public void setNakliyeId(int nakliyeId) {
        this.nakliyeId = nakliyeId;
    }

    public String getKoliPalet() {
        switch (this.koliPalet) {
            case 0:
                return "Koli";
            case 1:
                return "Palet";
            case 3:
                return "Dökme";
        }

        return "";
    }

    public void setKoliPalet(int koliPalet) {
        this.koliPalet = koliPalet;
    }
}
