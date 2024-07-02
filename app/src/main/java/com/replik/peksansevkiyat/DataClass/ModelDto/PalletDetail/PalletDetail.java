package com.replik.peksansevkiyat.DataClass.ModelDto.PalletDetail;

import com.google.gson.annotations.SerializedName;

public class PalletDetail {
    @SerializedName("id")
    Integer id;
    @SerializedName("palletId")
    Integer palletId;
    @SerializedName("seriNo")
    String seriNo;
    @SerializedName("stokKodu")
    String stokKodu;
    @SerializedName("govde")
    String govde;
    @SerializedName("logo")
    String logo;
    @SerializedName("ust")
    String ust;
    @SerializedName("yapKod")
    String yapKod;
    @SerializedName("depoKodu")
    Integer depoKodu;
    @SerializedName("miktar")
    Number miktar;
    @SerializedName("miktar2")
    Number miktar2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPalletId() {
        return palletId;
    }

    public String getSeriNo() {
        return seriNo;
    }

    public String getStokKodu() {
        return stokKodu;
    }

    public String getGovde() {
        return govde;
    }

    public String getLogo() {
        return logo;
    }

    public String getUst() {
        return ust;
    }

    public String getYapKod() {
        return yapKod;
    }

    public Integer getDepoKodu() {
        return depoKodu;
    }

    public Number getMiktar() {
        return miktar;
    }

    public Number getMiktar2() {
        return miktar2;
    }
}
