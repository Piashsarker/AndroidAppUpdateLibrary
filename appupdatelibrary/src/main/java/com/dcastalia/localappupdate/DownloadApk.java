package com.dcastalia.localappupdate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by piashsarker on 1/16/18.
 */

public class DownloadApk extends Activity{


    private static ProgressDialog bar;
    private static String TAG = "DownloadApk";
    private static Context context ;
    private static Activity activity ;
    private static String downloadUrl ;


    public DownloadApk(Context context){
        this.context = context ;
        this.activity = (Activity)context;
    }


    public   void startDownloadingApk(String url){
           downloadUrl = url ;
           if(downloadUrl!=null){
               new DownloadNewVersion().execute();
           }

    }




    private static  class DownloadNewVersion extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(context);
            bar.setCancelable(false);

            bar.setMessage("Downloading...");

            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            bar.setIndeterminate(false);
            bar.setMax(100);
            bar.setProgress(progress[0]);
            String msg = "";
            if(progress[0]>99){

                msg="Finishing... ";

            }else {

                msg="Downloading... "+progress[0]+"%";
            }
            bar.setMessage(msg);

        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            bar.dismiss();

            if(result){

                Toast.makeText(context,"Update Done",
                        Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(context,"Error: Try Again",
                        Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {
                URL url = new URL(downloadUrl);

                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                String PATH = Environment.getExternalStorageDirectory()+"/Download/";
                File file = new File(PATH);
                file.mkdirs();

                File outputFile = new File(file,"app-debug.apk");

                if(outputFile.exists()){
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = 1431692;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded=0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded +=len1;
                    per = (int) (downloaded * 100 / total_size);
                    publishProgress(per);
                }
                fos.close();
                is.close();

                OpenNewVersion(PATH);

                flag = true;
            } catch (Exception e) {
                Log.e(TAG, "Update Error: " + e.getMessage());
                flag = false;
            }
            return flag;

        }

    }

    private static void OpenNewVersion(String location) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(getUriFromFile(location),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
        activity.finish();

    }

    private static Uri getUriFromFile(String location) {

        if(Build.VERSION.SDK_INT<24){
            return   Uri.fromFile(new File(location + "app-debug.apk"));
        }
        else{
            return FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider",
                    new File(location + "app-debug.apk"));
        }
    }







}
