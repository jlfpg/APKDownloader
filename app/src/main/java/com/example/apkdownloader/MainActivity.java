package com.example.apkdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import java.io.File;


public class MainActivity extends AppCompatActivity {

    //Variables globales
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0;
    public static final int progress_bar_type = 0;
    private static String file_url_insta = "http://jlfpg.github.io/images/insta.apk";
    private static String file_url_netflix = "http://jlfpg.github.io/images/netflix.apk";
    private static String file_url_pure = "http://jlfpg.github.io/images/pure.apk";
    private static String file_url_spotify = "http://jlfpg.github.io/images/spotify.apk";
    Button btnInsta, btnNetflix, btnSpoty, btnPure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Pantalla completa
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Comprobar los permisos
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        AndroidNetworking.initialize(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!getPackageManager().canRequestPackageInstalls()) {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse(String.format("package:%s", getPackageName()))), 1234);
            } else {
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        //Instagram
        btnInsta = (Button) findViewById(R.id.btnInsta);
        btnInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName ="Instagram.apk";
                String dirPath=Environment.getExternalStorageDirectory().toString() + "/Download/APKDownloader/";
                AndroidNetworking.download(file_url_insta,dirPath,fileName)
                        .setTag("Descargando")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                                // do anything with progress
                            }
                        })
                        .startDownload(new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                int duration = 5;
                                Toast.makeText(MainActivity.this, "Descargando APK",
                                        Toast.LENGTH_LONG).show();
                                instalarAPK("Instagram");
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(MainActivity.this, "Fallo al descargar el APK",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }


    public void instalarAPK(String nombre){
        String PATH = Environment.getExternalStorageDirectory() + "/Download/APKDownloader/" + nombre+".apk";
        File file = new File(PATH);
        if(file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uriFromFile(getApplicationContext(), new File(PATH)), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                getApplicationContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Instalando APK",Toast.LENGTH_LONG).show();
        }
    }
    Uri uriFromFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }
}