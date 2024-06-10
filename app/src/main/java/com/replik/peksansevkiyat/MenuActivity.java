package com.replik.peksansevkiyat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.replik.peksansevkiyat.Transection.GlobalVariable;
import com.replik.peksansevkiyat.Transection.PrintBluetooth;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {
    Button btnMenuPalette, btnMenuOrder, btnShipment;
    ImageButton imgLogo, imgSettings;
    TextView txtUserName;
    Context context = MenuActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserName.setText(GlobalVariable.getUserName());

        imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SettingsActivity.class);
                startActivity(i);
            }
        });
        btnMenuPalette = (Button) findViewById(R.id.btnPaletteAdd);
        btnMenuPalette.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, PaletteActivity.class);
                startActivity(i);
            }
        });

        btnMenuOrder = (Button) findViewById(R.id.btnPaletteEdit);
        btnMenuOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, OrderActivity.class);
                startActivity(i);
            }
        });

        btnShipment = (Button) findViewById(R.id.btnShipment);
        btnShipment.setOnClickListener(v -> {
            /*Intent intent = new Intent(context, ShipmentCustomerListActivity.class);
            startActivity(intent);*/

            try {
                PrintBluetooth printBluetooth = new PrintBluetooth();
                PrintBluetooth.printer_id = GlobalVariable.printerName;

                printBluetooth.findBT();
                printBluetooth.openBT();
                printBluetooth.printTestTable();
                printBluetooth.closeBT();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        imgLogo = (ImageButton) findViewById(R.id.imgLogo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}