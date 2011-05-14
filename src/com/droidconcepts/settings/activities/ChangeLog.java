package com.droidconcepts.settings.activities;

import com.droidconcepts.settings.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Config;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

public class ChangeLog extends AlertActivity {

    private static final String CHANGELOG_PATH = "/system/etc/DC-CHANGELOG.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputStreamReader inputReader = null;
        StringBuilder data = null;
        try {
            data = new StringBuilder(2048);
            char tmp[] = new char[2048];
            int numRead;
            inputReader = new FileReader(CHANGELOG_PATH);
            while ((numRead = inputReader.read(tmp)) >= 0) {
                data.append(tmp, 0, numRead);
            }
        } catch (IOException e) {
            showErrorAndFinish();
            return;
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
            }
        }

        if (TextUtils.isEmpty(data)) {
            showErrorAndFinish();
            return;
        }

        WebView webView = new WebView(this);

        // Begin the loading.  This will be done in a separate thread in WebView.
        webView.loadDataWithBaseURL(null, data.toString(), "text/plain", "utf-8", null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Change from 'Loading...' to the real title
                mAlert.setTitle(getString(R.string.changelog_dialog));
            }
        });

        final AlertController.AlertParams p = mAlertParams;
        p.mTitle = getString(R.string.changelog_loading);
        p.mView = webView;
        p.mForceInverseBackground = true;
        setupAlert();
    }

    private void showErrorAndFinish() {
        Toast.makeText(this, R.string.changelog_error, Toast.LENGTH_LONG)
                .show();
        finish();
    }

}
