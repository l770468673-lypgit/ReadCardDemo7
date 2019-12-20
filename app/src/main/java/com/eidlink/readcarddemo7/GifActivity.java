package com.eidlink.readcarddemo7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.eidlink.readcarddemo7.http.MyConnectionHandler;
import com.eidlink.readcarddemo7.http.MyHttpConnection;
import com.eidlink.readcarddemo7.http.MyHttpConnectionCallBack;

import org.json.JSONObject;

public class GifActivity extends Activity implements MyHttpConnectionCallBack {
    private static final String TAG = GifActivity.class.getSimpleName();
    private static MyConnectionHandler connectionHandler = null;
    private ImageView ivgif;
    private Button back;
    protected ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        connectionHandler = new MyConnectionHandler(this, TAG);
        back = this.findViewById(R.id.back);
        ivgif = this.findViewById(R.id.ivgif);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(MainActivity.class);
            }
        });
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        go(MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        hideProgressDlg();
        super.onDestroy();

    }

    private void go(Class c){
        Intent intent = new Intent(GifActivity.this,c);
        startActivity(intent);
        finish();
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showProgressDlg("提示", "正在读卡...");
                    doID("");
                    break;
                case 2:
                    String str = (String)msg.obj;
                    byte[] b = Base64.decode(str,Base64.NO_WRAP);
                    Glide.with(GifActivity.this).load(b).into(ivgif);
                    break;
                default:
                    break;
            }
        }
    };

   /* private static void isExist(String path)
    {
        File file = new File(path);

        if (!file.exists())
        {
            file.mkdir();
        }
    }*/
  /*  public static void saveBitmapAsPng(byte[] bmp) {

        try {
            isExist("/sdcard/eidlink");
            File file = new File("/sdcard/eidlink/g.gif");
            FileOutputStream out = new FileOutputStream(file);
            out.write(bmp);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    protected void showProgressDlg(String tip, String message) {

        if (null != progressDialog && progressDialog.isShowing()) {

            progressDialog.hide();
            progressDialog.dismiss();
            progressDialog = null;

        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(tip);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    protected void hideProgressDlg() {

        if (null != progressDialog) {
            progressDialog.hide();
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

    //--------------查询
    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    private static String getSystemModel() {
        return android.os.Build.MODEL;
    }
    private void doID(String str){
        String data = "{phoneModel:'"+getSystemModel()+"'}";
        Log.e("TAG","data "+data);
        MyThread myThread = new MyThread(data);
        myThread.start();
    }
    class MyThread extends Thread {

        String str;

        public MyThread(String strr){
            str = strr;
        }


        @Override
        public void run() {
            super.run();
            try {
                 new MyHttpConnection("http://testnidocr.eidlink.com:8081/webapi/phoneVideo/sticker/gif.do", "POST", str, connectionHandler).sync();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void httpConnectionCallBack(String methodTag, byte[] bytes) {
        try {
            String result = new String(bytes);
            JSONObject json = new JSONObject(result);
            Log.e("TAG", result);
            hideProgressDlg();
            if(json.get("result").equals("Y")){
                //根据状态改变 显示内容
                Message msg = new Message();
                msg.what = 2;
                msg.obj = json.get("data").toString();
                mHandler.sendMessage(msg);
            }else{
                Message msg = new Message();
                msg.what = 2;
                msg.obj = json.get("data").toString();
                mHandler.sendMessage(msg);
//                Toast.makeText(this, json.getString("请求失败"), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap stringtoBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray= Base64.decode(string, Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
