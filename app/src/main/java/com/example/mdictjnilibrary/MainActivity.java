package com.example.mdictjnilibrary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;



public class MainActivity extends AppCompatActivity {
    String path = "German Conjugation(185 verbs).mdx";
    String word = "sein";

    WebView webView;
    // Used to load the 'jni-layer' library on application startup.
    static {
        System.loadLibrary("jni-layer");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.myWebView);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        File dict = new File(getExternalFilesDir(null), path);

        // Example of a call to a native method
        String queryReturnedValue = entryPoint(dict.getAbsolutePath(), word);
        webView.loadData(queryReturnedValue, "text/html; charset=utf-8", "UTF-8");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String entryPoint(String argument1, String argument2);
}