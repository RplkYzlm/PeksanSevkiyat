package com.replik.peksansevkiyat.DataClass.ModelDto.Label;

public class ShippingPrintLabelDto {
    public String shippingNo;
    public String deliveryName;
    public String deliveryAddress;
    public ShippingPrintLabelDto(String shippingNo, String deliveryName, String deliveryAddress) {
        this.shippingNo = shippingNo;
        this.deliveryName = deliveryName;
        this.deliveryAddress = deliveryAddress;
    }
}
