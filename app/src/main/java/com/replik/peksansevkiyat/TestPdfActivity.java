package com.replik.peksansevkiyat;

import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestPdfActivity extends AppCompatActivity {

    Button backButton, printButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_test_pdf);

        backButton = findViewById(R.id.back_button);
        printButton = findViewById(R.id.print_button);

        backButton.setOnClickListener(v -> {
            finish();
        });

        printButton.setOnClickListener(v -> {

            PdfDocument pdfDocument = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(283, 283, 1
            ).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

        });
    }
}
