package com.replik.peksansevkiyat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.replik.peksansevkiyat.DataClass.ListAdapter.ListAdapter_PalletDetail;
import com.replik.peksansevkiyat.DataClass.ModelDto.Dtos.getStandartLong;
import com.replik.peksansevkiyat.DataClass.ModelDto.Pallet.PalletSingle;
import com.replik.peksansevkiyat.DataClass.ModelDto.PalletDetail.PalletDetail;
import com.replik.peksansevkiyat.DataClass.ModelDto.PalletDetail.PalletDetailDtos;
import com.replik.peksansevkiyat.DataClass.ModelDto.PalletDetail.PalletDetailList;
import com.replik.peksansevkiyat.DataClass.ModelDto.Result;
import com.replik.peksansevkiyat.DataClass.ModelDto.Seritra.spSeritra;
import com.replik.peksansevkiyat.DataClass.ModelDto.Seritra.spSeritraSingle;
import com.replik.peksansevkiyat.Interface.APIClient;
import com.replik.peksansevkiyat.Interface.APIInterface;
import com.replik.peksansevkiyat.Transection.Alert;
import com.replik.peksansevkiyat.Transection.Dialog;
import com.replik.peksansevkiyat.Transection.GlobalVariable;
import com.replik.peksansevkiyat.Transection.PrintBluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaletteAdd extends AppCompatActivity {
    APIInterface apiInterface;
    ProgressDialog nDialog;
    AlertDialog alert;

    PrintBluetooth printBT = new PrintBluetooth();

    Context context = PaletteAdd.this;

    ImageButton imgLogo, imgSettings;
    TextView txtUserName, txtBarcode, lblBoxCount;
    ListView lstData;
    Button btnPalletPrint;

    Integer palletId = -1;
    String palletBarcode = "";
    String palletAciklama = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_add);

        apiInterface = APIClient.getRetrofit().create(APIInterface.class);
        nDialog = Dialog.getDialog(context, getString(R.string.loading));

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserName.setText(GlobalVariable.getUserName());

        lblBoxCount = (TextView) findViewById(R.id.lblBoxCount);

        imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SettingsActivity.class);
                startActivity(i);
            }
        });

        imgLogo = (ImageButton) findViewById(R.id.imgLogo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lstData = findViewById(R.id.lstData);

        txtBarcode = findViewById(R.id.txtSearch);
        //txtBarcode.setShowSoftInputOnFocus(false);
        txtBarcode.setInputType(InputType.TYPE_NULL);
        txtBarcode.requestFocus();
        txtBarcode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    /*if (event.getAction() == KeyEvent.KEYCODE_ENTER) {
                        Toast.makeText(context, "The text is: " + txtBarcode.getText() , Toast.LENGTH_LONG).show();
                        fnSeriControl(txtBarcode.getText().toString());
                        return true;
                    }*/

                    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (txtBarcode.getText().toString().contains("=")) {
                            String[] value = txtBarcode.getText().toString().split("=");

                            fnSeriControl(value[1].toUpperCase());

                        } else
                            fnSeriControl(txtBarcode.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });

        btnPalletPrint = findViewById(R.id.btnPalletPrint);
        btnPalletPrint.setVisibility(View.GONE);
        btnPalletPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnPrintLabel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        fnBackControl();
    }

    private void fnBackControl() {
        if (btnPalletPrint.getVisibility() == View.VISIBLE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.sure));
            builder.setMessage(getString(R.string.question_pallet_not_print));
            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fnPrintLabel();
                    finish();
                }
            });
            builder.show();
        } else
            finish();
    }

    private void fnPrintLabel() {
        try {
            PrintBluetooth.printer_id = GlobalVariable.printerName;

            // YAZMA BAŞLAR
            printBT.findBT();
            printBT.openBT();
            printBT.printPalletLabel(palletBarcode);
            printBT.closeBT();
            // YAZMA BİTER

            nDialog.show();
            apiInterface.setPalletPrint(new getStandartLong(GlobalVariable.getUserId(), palletId)).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    nDialog.hide();

                    if (response.body().getSuccess()) {
                        alert = Alert.getAlert(context, getString(R.string.info), palletBarcode + "\nYazdırıldı.");
                        alert.show();
                        btnPalletPrint.setVisibility(View.GONE);
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

    void fnSeriControl(String barcode) {
        lblBoxCount.setText("0");
        txtBarcode.setText("");
        nDialog.show();
        if (barcode.endsWith("PLT"))
            fnGetPallet(barcode);
        else
            fnGetSeriControl(barcode);
    }

    void fnGetPallet(String barcode) {
        apiInterface.getPalletControl(GlobalVariable.getUserId(), barcode).enqueue(new Callback<PalletSingle>() {
            @Override
            public void onResponse(Call<PalletSingle> call, Response<PalletSingle> response) {
                if (response.body().getSuccess()) {
                    palletId = response.body().getPallet().getId();
                    palletBarcode = response.body().getPallet().getBarkod();
                    btnPalletPrint.setVisibility(View.VISIBLE);
                    fnGetPalletDetailList();
                } else {
                    nDialog.hide();
                    alert = Alert.getAlert(context, getString(R.string.error), response.body().getMessage());
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<PalletSingle> call, Throwable t) {
                nDialog.hide();
                alert = Alert.getAlert(context, getString(R.string.error), t.getMessage());
                alert.show();
            }
        });
    }

    void fnGetPalletDetailList() {
        apiInterface.getPalletDetail(GlobalVariable.getUserId(), palletId).enqueue(new Callback<PalletDetailList>() {
            @Override
            public void onResponse(Call<PalletDetailList> call, Response<PalletDetailList> response) {
                if (response.body().getSuccess())
                    setPalletDetailAdapter(response.body());
                else {
                    nDialog.hide();
                    alert = Alert.getAlert(context, getString(R.string.error), response.body().getMessage());
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<PalletDetailList> call, Throwable t) {
                nDialog.hide();
                alert = Alert.getAlert(context, getString(R.string.error), t.getMessage());
                alert.show();
            }
        });
    }

    void fnGetSeriControl(String barcode) {
        apiInterface.getSeriControl(GlobalVariable.getUserId(), barcode).enqueue(new Callback<spSeritraSingle>() {
            @Override
            public void onResponse(Call<spSeritraSingle> call, Response<spSeritraSingle> response) {
                if (response.body().getSuccess() && response.body().getSpSeritra() != null)
                    fnSetPalletDetail(response.body().getSpSeritra());
                else {
                    nDialog.hide();
                    alert = Alert.getAlert(context, getString(R.string.error), response.body().getMessage().toString().equals("") ? "SERİ TANIMLI DEĞİLDİR." : response.body().getMessage());
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<spSeritraSingle> call, Throwable t) {
                nDialog.hide();
                alert = Alert.getAlert(context, getString(R.string.error), t.getMessage());
                alert.show();
            }
        });
    }

    void fnSetPalletDetail(spSeritra seritra) {
        PalletDetailDtos.setPalletDetailColumn data = new PalletDetailDtos.setPalletDetailColumn(palletId, palletAciklama, GlobalVariable.getUserId(), seritra.getSeriNo(), seritra.getStokKodu(), seritra.getMiktar(), seritra.getMiktar2());
        apiInterface.setPalletDetail(data).enqueue(new Callback<PalletDetailList>() {
            @Override
            public void onResponse(Call<PalletDetailList> call, Response<PalletDetailList> response) {
                if (response.body().getSuccess())
                    setPalletDetailAdapter(response.body());
                else {
                    nDialog.hide();
                    alert = Alert.getAlert(context, getString(R.string.error), response.body().getMessage());
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<PalletDetailList> call, Throwable t) {
                nDialog.hide();
                alert = Alert.getAlert(context, getString(R.string.error), t.getMessage());
                alert.show();
            }
        });
    }

    void setPalletDetailAdapter(PalletDetailList lstPalletDetail) {
        ArrayList lst = new ArrayList<PalletDetail>();
        for (PalletDetail p : lstPalletDetail.getPalletDetails()) {
            palletId = p.getPalletId();
            btnPalletPrint.setVisibility(View.VISIBLE);
            lst.add(p);
        }

        lblBoxCount.setText(String.valueOf(lst.size()));

        //paletin barkodunu bul
        apiInterface.getPalletControl(GlobalVariable.getUserId(), palletId).enqueue(new Callback<PalletSingle>() {
            @Override
            public void onResponse(Call<PalletSingle> call, Response<PalletSingle> response) {
                palletBarcode = response.body().getPallet().getBarkod();
            }

            @Override
            public void onFailure(Call<PalletSingle> call, Throwable t) {

            }
        });

        ListAdapter_PalletDetail adapter = new ListAdapter_PalletDetail(context, R.layout.list_adapter_pallet_detail, lst);
        lstData.setAdapter(adapter);

        nDialog.hide();
    }
}