package com.example.apkdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
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

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // Permission has already been granted
        }

        //Instagram
        btnInsta = (Button) findViewById(R.id.btnInsta);
        btnInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Descargando APP",
                        Toast.LENGTH_LONG).show();
                String fileName ="Instagram.apk";
                String dirPath=Environment.getExternalStorageDirectory().toString() + "/Download/APKVault/";
                AndroidNetworking.download(file_url_insta,dirPath,fileName)
                        .setTag("Descargando")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                            }
                        })
                        .startDownload(new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                int duration = 5;
                                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                                i.putExtra("app", "Instagram");
                                startActivity(i);
                                finish();
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(MainActivity.this, "Fallo al descargar el APK",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        //Netflix
        btnNetflix = (Button) findViewById(R.id.btnNetflix);
        btnNetflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Descargando APP",
                        Toast.LENGTH_LONG).show();
                String fileName ="Netflix.apk";
                String dirPath=Environment.getExternalStorageDirectory().toString() + "/Download/APKVault/";
                AndroidNetworking.download(file_url_netflix,dirPath,fileName)
                        .setTag("Descargando")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                            }
                        })
                        .startDownload(new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                int duration = 5;
                                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                                i.putExtra("app", "Netflix");
                                startActivity(i);
                                finish();
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(MainActivity.this, "Fallo al descargar el APK",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        //Spotify
        btnSpoty = (Button) findViewById(R.id.btnSpoty);
        btnSpoty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Descargando APP",
                        Toast.LENGTH_LONG).show();
                String fileName ="Spotify.apk";
                String dirPath=Environment.getExternalStorageDirectory().toString() + "/Download/APKVault/";
                AndroidNetworking.download(file_url_spotify,dirPath,fileName)
                        .setTag("Descargando")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                            }
                        })
                        .startDownload(new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                int duration = 5;
                                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                                i.putExtra("app", "Spotify");
                                startActivity(i);
                                finish();
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(MainActivity.this, "Fallo al descargar el APK",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        //APKPure
        btnPure = (Button) findViewById(R.id.btnPure);
        btnPure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Descargando APP",
                        Toast.LENGTH_LONG).show();
                String fileName ="APKPure.apk";
                String dirPath=Environment.getExternalStorageDirectory().toString() + "/Download/APKVault/";
                AndroidNetworking.download(file_url_pure,dirPath,fileName)
                        .setTag("Descargando")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                            }
                        })
                        .startDownload(new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                int duration = 5;
                                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                                i.putExtra("app", "APKPure");
                                startActivity(i);
                                finish();
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

}