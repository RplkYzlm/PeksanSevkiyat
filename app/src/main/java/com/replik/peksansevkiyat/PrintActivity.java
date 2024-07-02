package com.replik.peksansevkiyat;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PrintActivity extends AppCompatActivity {
    WebView webView;
    Button btnPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        webView = findViewById(R.id.webView);
        btnPrint = findViewById(R.id.btnPrint);

        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        // WebView loading handling
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //if WebView load successfully then VISIBLE fab Button
                btnPrint.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl("http://192.168.1.100:7056/html/PXX2024000000001.html");

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code Here ==========
                createWebPrintJob(webView);
            }
        });
    }

    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = getString(R.string.app_name) + " Print Test";
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }
}