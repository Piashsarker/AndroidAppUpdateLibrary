package com.dcastalia.localapkupdatelibrary;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dcastalia.localappupdate.DownloadApk;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1001;
    private TextView versionText ;
    private String version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
             version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionText=  findViewById(R.id.txt_version);
        versionText.setText("Current Version "+version);
    }

    /** TODO: Must need to check the External Storage Permission Because we are storing the
     *  ApK in the External Or Internal Storage.
     */
    private void checkWriteExternalStoragePermission() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // If we have permission than we can Start the Download the task
            downloadTask();
        } else {
            //  If we don't have permission than requesting  the permission
            requestWriteExternalStoragePermission();
        }
    }

    private void requestWriteExternalStoragePermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,  new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadTask();
        } else {
            Toast.makeText(MainActivity.this, "Permission Not Granted.", Toast.LENGTH_SHORT).show();
        }
    }



    public void download(View view) {
        // First check the external storage permission
      checkWriteExternalStoragePermission();
    }

    private void downloadTask() {
        // This @DownloadApk class is provided by our library
        // Pass the Context when creating object of DownloadApk

        DownloadApk downloadApk = new DownloadApk(MainActivity.this);

        // For starting download call the method startDownloadingApk() by passing the URL and the optional filename
        downloadApk.startDownloadingApk("https://github.com/Piashsarker/AndroidAppUpdateLibrary/raw/master/app-debug.apk", "Update 2.0");
    }
}
