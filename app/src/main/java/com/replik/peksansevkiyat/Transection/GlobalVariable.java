package com.replik.peksansevkiyat.Transection;

import android.app.Application;

import com.replik.peksansevkiyat.DataClass.ModelDto.Order.Order;

public class GlobalVariable extends Application {
    public static String FileName = "/Replik.txt";

    public static String apiUrl;
    public static String getApiUrl() { return apiUrl; /*apiUrl == null ? "http://192.168.2.251:7076/" : apiUrl; */ }
    public static void setApiUrl(String url) { apiUrl = url; }

    public static String apiVersion = "1.0.19";

    public static String printerName;
    public static void setPrinter(String name) { printerName = name; }

    private static Integer userId;
    public static Integer getUserId() { return userId; }
    public static void setUserId(Integer value) {
        userId = value;
    }

    private static String userName = "Kullanıcı Girişi Yapılmamış";
    public static String getUserName() { return userName; }
    public static void setUserName(String value) {
        userName = value;
    }

    private static Order selectedOrder;
    public static Order getSelectedOrder() { return selectedOrder; }
    public static void setSelectedOrder(Order order) { selectedOrder = order; }

    private static Double manuelQuantityVal;
    public static Double getManuelQuantityVal() { return manuelQuantityVal; }
    public static void setManuelQuantityVal(Double order) { manuelQuantityVal = order; }
}
