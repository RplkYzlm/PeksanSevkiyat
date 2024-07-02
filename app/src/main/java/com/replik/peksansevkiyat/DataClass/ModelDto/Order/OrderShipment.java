package com.replik.peksansevkiyat.DataClass.ModelDto.Order;

import com.replik.peksansevkiyat.DataClass.ModelDto.OrderShipping.OrderShipping;

public class OrderShipment {
    private String sipno;
    private OrderShipping orderShipping;

    public OrderShipment(String sipno, OrderShipping orderShipping) {
        this.sipno = sipno;
        this.orderShipping = orderShipping;
    }

    public String getSipno() {
        return sipno;
    }

    public void setSipno(String sipno) {
        this.sipno = sipno;
    }

    public OrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
