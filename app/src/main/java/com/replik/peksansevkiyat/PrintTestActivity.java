package com.replik.peksansevkiyat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.replik.peksansevkiyat.Transection.PrintBluetooth;
import com.replik.peksansevkiyat.Transection.Voids;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PrintTestActivity extends AppCompatActivity {

    PrintBluetooth printBT = new PrintBluetooth();

    EditText txtBarcode, txtPrinterName;
    Button btnCreateQr, btnSetPrinter, btnPrint, btnNoti;
    ImageView imgQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_test);

        txtBarcode = findViewById(R.id.txtSearch);
        btnCreateQr = findViewById(R.id.btnCreateQr);
        imgQr = findViewById(R.id.imgQr);

        txtPrinterName = findViewById(R.id.txtPrinterName);
        btnSetPrinter = findViewById(R.id.btnSetPrinter);
        btnPrint = findViewById(R.id.btnPrint);
        btnNoti = findViewById(R.id.btnNoti);

        btnCreateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgQr.setImageBitmap(Voids.generateQr(txtBarcode.getText().toString(), 150, 150));
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintBluetooth.printer_id = txtPrinterName.getText().toString();
                Bitmap bitmap = Voids.generateQr(txtBarcode.getText().toString(), 550, 550);
                try {
                    printBT.findBT();
                    printBT.openBT();
                    printBT.printText(txtBarcode.getText().toString());
                    printBT.printQrCode(bitmap);
                    printBT.closeBT();
                } catch (IOException e) {
                    String error = e.getMessage();
                    e.printStackTrace();
                }
            }
        });

        btnSetPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintBluetooth.printer_id = txtPrinterName.getText().toString();
/*
                Bitmap bitmap1 = Voids.textAsBitmap("231214082849425PLT", 35);
                Bitmap bitmap2 = Voids.generateQr("231214082849425PLT", 500, 500);

                Bitmap bitmap = Voids.finalBitmap(bitmap1, 0, 100, bitmap2, bitmap1.getHeight(), 50);
                txtBarcode.setText(bitmap.getWidth() + " " + bitmap.getHeight());
*/

                //imgQr.setImageBitmap(bitmap);

                try {

                    printBT.findBT();
                    printBT.openBT();
                    //printBT.printQrCode(bitmap);

                    //PrintPic printPic1 = PrintPic.getInstance();
                    //printPic1.init(bitmap);
                    //byte[] bitmapdata2 = printPic1.printDraw();
                    //printBT.write(bitmapdata2);


                    //palet
                    //String printData = "SIZE 57 mm,60 mm\nGAP 0 mm,0 mm\nCLS\nTEXT 60 mm,30 mm,\"2\",0,1.5 mm,1.5 mm,\"231214082849425PLT\"\nQRCODE 45 mm,80 mm,\"1\",17,1,0,1,1,\"231214082849425PLT\"\nPRINT 1\nEND\n";
                    //sipariş
                    String printData = "SIZE 57 mm,60 mm\nGAP 0 mm,0 mm\nCLS" +
                            "\nTEXT 20 mm,35 mm,\"2\",0,1.5 mm,1.5 mm,\"000000000000027\"" +
                            "\nTEXT 20 mm,90 mm,\"0\",0,1.5 mm,1.4 mm,\"HALIL TICARET ANONIM\"" +
                            "\nTEXT 20 mm,130 mm,\"2\",0,1.5 mm,1.4 mm,\"SIRKETI BLA BLA\"" +
                            "\nTEXT 20 mm,360 mm,\"2\",0,1.8 mm,1.7 mm,\"YURTICI KARGO\"" +
                            "\nTEXT 20 mm,410 mm,\"2\",0,1.8 mm,1.7 mm,\"MANISA\"" +
                            "\nPRINT 1\nEND\n";
                    /*String printData = "SIZE 57 mm,60 mm\nGAP 0 mm,0 mm\nCLS" +
                            "\nBITMAP 57 mm,60 mm, 1, 1,\""+new String(bitmapdata2)+"\"" +
                            "\nPRINT 1\nEND\n";*/
                    printBT.write(printData.getBytes(StandardCharsets.UTF_8));
                    printBT.closeBT();
                } catch (IOException e) {
                    String error = e.getMessage();
                    e.printStackTrace();
                }

            }
        });

        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    if (ContextCompat.checkSelfPermission(PrintTestActivity.this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PrintTestActivity.this,
                                new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, 101);
                    }
                }

                String chanelID = "CHANNEL_ID_NOTIFICATION";
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getApplicationContext(), chanelID);

                builder.setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Notification Title")
                        .setContentText("Some text for notification here")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", "Some value to be passed here");

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = notificationManager.getNotificationChannel(chanelID);

                    if (notificationChannel == null) {
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        notificationChannel = new NotificationChannel(chanelID, "Some description", importance);
                        notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.enableVibration(true);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                }

                notificationManager.notify(0, builder.build());
            }
        });
    }

    public static byte[] convertToBMW(Bitmap bmp, int concentration) {
        if (concentration <= 0 || concentration >= 255) {
            concentration = 128;
        }
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        byte[] bytes = new byte[(width) / 8 * height];
        int[] p = new int[8];
        int n = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width / 8; j++) {
                for (int z = 0; z < 8; z++) {
                    int grey = bmp.getPixel(j * 8 + z, i);
                    int red = ((grey & 0x00FF0000) >> 16);
                    int green = ((grey & 0x0000FF00) >> 8);
                    int blue = (grey & 0x000000FF);
                    int gray = (int) (0.29900 * red + 0.58700 * green + 0.11400 * blue); // 灰度转化公式
                    if (gray <= concentration) {
                        gray = 0;
                    } else {
                        gray = 1;
                    }
                    p[z] = gray;
                }
                byte value = (byte) (p[0] * 128 + p[1] * 64 + p[2] * 32 + p[3] * 16 + p[4] * 8 + p[5] * 4 + p[6] * 2 + p[7]);
                bytes[width / 8 * i + j] = value;
            }
        }
        return bytes;
    }
}