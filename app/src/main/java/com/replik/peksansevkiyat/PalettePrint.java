package com.replik.peksansevkiyat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.replik.peksansevkiyat.DataClass.ListAdapter.ListAdapter_PalletPrint;
import com.replik.peksansevkiyat.DataClass.ModelDto.Dtos.getStandartLong;
import com.replik.peksansevkiyat.DataClass.ModelDto.Pallet.dtoPalletPrint;
import com.replik.peksansevkiyat.DataClass.ModelDto.Result;
import com.replik.peksansevkiyat.Interface.APIClient;
import com.replik.peksansevkiyat.Interface.APIInterface;
import com.replik.peksansevkiyat.Transection.Alert;
import com.replik.peksansevkiyat.Transection.Dialog;
import com.replik.peksansevkiyat.Transection.GlobalVariable;
import com.replik.peksansevkiyat.Transection.PrintBluetooth;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PalettePrint extends AppCompatActivity {
    APIInterface apiInterface;
    ProgressDialog nDialog;
    AlertDialog alert;
    PrintBluetooth printBT = new PrintBluetooth();
    Context context = PalettePrint.this;

    ImageButton imgLogo, imgSettings;
    TextView txtUserName;
    ListView lstData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_print);

        apiInterface = APIClient.getRetrofit().create(APIInterface.class);
        nDialog = Dialog.getDialog(context, getString(R.string.loading));

        lstData = findViewById(R.id.lstData);

        txtUserName = findViewById(R.id.txtUserName);
        txtUserName.setText(GlobalVariable.getUserName());

        imgLogo = findViewById(R.id.imgLogo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SettingsActivity.class);
                startActivity(i);
            }
        });

        fnGetPalletPrintList();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void fnGetPalletPrintList() {
        nDialog.show();
        apiInterface.getPalletPrint(GlobalVariable.getUserId()).enqueue(new Callback<dtoPalletPrint.PalletPrintList>() {
            @Override
            public void onResponse(Call<dtoPalletPrint.PalletPrintList> call, Response<dtoPalletPrint.PalletPrintList> response) {
                nDialog.hide();
                if (response.body().getSuccess()) {
                    if (response.body().getPalletPrints() != null) {
                        fnAddPalletDetail(response.body());
                    } else {
                        Toast.makeText(context, getString(R.string.empty_list), Toast.LENGTH_LONG).show();
                    }
                } else {
                    alert = Alert.getAlert(context, getString(R.string.danger), response.body().getMessage());
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<dtoPalletPrint.PalletPrintList> call, Throwable t) {
                nDialog.hide();
                alert = Alert.getAlert(context, getString(R.string.danger), t.getMessage());
                alert.show();
            }
        });
    }

    private void fnAddPalletDetail(dtoPalletPrint.PalletPrintList lstPalletPrints) {
        ArrayList lst = new ArrayList<dtoPalletPrint>();

        if (lstPalletPrints != null)
            for (dtoPalletPrint p : lstPalletPrints.getPalletPrints()) {
                lst.add(p);
            }

        ListAdapter_PalletPrint adapter = new ListAdapter_PalletPrint(context, R.layout.list_adapter_pallet_print_list, lst);
        lstData.setAdapter(adapter);

        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // emin misin sor!
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(getString(R.string.sure));
                builder.setMessage(getString(R.string.question_pallet_print));
                builder.setNegativeButton(getString(R.string.no), null);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        fnPrint(lstPalletPrints.getPalletPrints().get(i));
                    }
                });
                builder.show();
            }
        });

        nDialog.hide();
    }

    void fnPrint(dtoPalletPrint palet) {
        try {
            PrintBluetooth.printer_id = GlobalVariable.printerName;
            // YAZMA BAŞLAR
            printBT.findBT();
            printBT.openBT();
            printBT.printPalletLabel(palet.getBarkod());
            printBT.closeBT();
            // YAZMA BİTER

            nDialog.show();
            apiInterface.setPalletPrint(new getStandartLong(GlobalVariable.getUserId(), palet.getId())).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    nDialog.hide();

                    if (response.body().getSuccess()) {
                        alert = Alert.getAlert(context, getString(R.string.info), palet.getBarkod() + "\nYazdırıldı.");
                        alert.show();
                        fnGetPalletPrintList();
                    } else {
                        alert = Alert.getAlert(context, getString(R.string.error), response.body().getMessage());
                        alert.show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    nDialog.hide();
                    alert = Alert.getAlert(context, getString(R.string.error), t.getMessage());
                    alert.show();
                }
            });
        } catch (IOException e) {
            String error = e.getMessage();
            e.printStackTrace();
        }
    }
}