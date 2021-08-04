package com.example.mdictjnilibrary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    String dict = "German Conjugation(185 verbs).mdx";
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
        webView.getSettings().setAllowFileAccess(true);

        File dictionary = new File(getExternalFilesDir(null), dict);

        if (dictionary.exists()) {
            Log.i("FileInfo", "the absolute path of the parent directory is " + getExternalFilesDir(null).getAbsolutePath());
            Log.i("FileInfo", "the absolute path of the file is " + dictionary.getAbsolutePath());
        } else {
            Log.i("FileInfo", "the file cannot be found");
        }

        // Example of a call to a native method
        String queryReturnedValue = entryPoint(dictionary.getAbsolutePath(), word);
        writeFile(queryReturnedValue);

        File dictDirectory = createDictDirectory(dict);
        File html = createHtmlPage(dictDirectory);
        webView.loadUrl(html.getAbsolutePath());

        Log.i("length", String.valueOf(queryReturnedValue.length()));
    }

    private boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("StateInfo", "Yes, it is writable!");
            return true;
        } else {
            return false;
        }
    }

    private File createDictDirectory(String dict) {
        return new File(getExternalFilesDir(null).getAbsolutePath() + "/" + dict.substring(0, dict.length() - 4));
    }

    private File createHtmlPage(File dictDirectory) {
        return new File(dictDirectory, word + ".html");
    }

    private void writeFile (String definition) {
        if (isExternalStorageWritable()) {
            File dictDirectory = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + dict.substring(0, dict.length() - 4));
            if (!dictDirectory.exists()) {
                boolean wasSuccessful = dictDirectory.mkdir();;
                if (!wasSuccessful) {
                    Log.i("FileInfo", "directory creation is not successful");
                }
            }
            File htmlPage = new File(dictDirectory, word + ".html");
            if (!htmlPage.exists()) {
                Log.i("FileInfo", "file exist");
                try {
                    FileOutputStream fos = new FileOutputStream(htmlPage);
                    fos.write(definition.getBytes());
                    fos.close();

                    Toast.makeText(this, "saved to" + htmlPage.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this, "cannot write to external storage", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String entryPoint(String argument1, String argument2);
}