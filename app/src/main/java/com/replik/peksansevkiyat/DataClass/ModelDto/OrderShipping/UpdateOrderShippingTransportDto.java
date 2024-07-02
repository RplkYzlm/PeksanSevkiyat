package com.replik.peksansevkiyat.DataClass.ModelDto.OrderShipping;

public class UpdateOrderShippingTransportDto {
    private String sevkNo;
    private int transportId;

    public UpdateOrderShippingTransportDto(String sevkNo, int transportId) {
        this.sevkNo = sevkNo;
        this.transportId = transportId;
    }
}
