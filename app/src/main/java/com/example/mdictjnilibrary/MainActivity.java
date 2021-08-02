package com.example.mdictjnilibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;



public class MainActivity extends AppCompatActivity {
    String path = "test.mdx";
    String word = "sein";

    WebView webView;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 1;
    // Used to load the 'jni-layer' library on application startup.
    static {
        System.loadLibrary("jni-layer");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);

        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.myWebView);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        File dict = new File(getExternalFilesDir(null), path);
//        if (dict.exists()) {
//            Log.i("file", "the absolute path of the file is " + dict.getAbsolutePath());
//        } else {
//            Log.i("file", "the file cannot be found");
//        }


        // Example of a call to a native method
        String queryReturnedValue = entryPoint(dict.getAbsolutePath(), word);
        webView.loadData(queryReturnedValue, "text/html; charset=utf-8", "UTF-8");

//        isExternalStorageReadable();
//        System.out.println(queryReturnedValue.length());
    }

    private boolean isExternalStorageReadable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            Log.i("State", "Yes, it is readable!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String entryPoint(String argument1, String argument2);
}