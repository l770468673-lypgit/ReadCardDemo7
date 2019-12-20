package com.eidlink.readcarddemo7.http;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpConnection extends Thread {
  Handler handler = null;
  String urlPath = "";
  String postType = "";
  String json = null;

  public MyHttpConnection(String _urlPath, String _postType, String _json, Handler _handler) {
    urlPath = _urlPath;
    handler = _handler;
    json = _json;
    postType = _postType;
  }

  @Override
  public void run() {
    sync();
  }

  public void sync() {
    byte[] result = null;
    try {
      int len = -1;
      URL url = new URL(urlPath);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      if (postType.toUpperCase().equals("POST")) {
        // 发�? json数据
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(20000); 
        conn.setReadTimeout(20000); 
        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.flush();
        os.close();
      }
      conn.connect();
      // 服务器返回的data结果
      InputStream inStream = conn.getInputStream();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] data = new byte[4 * 1024];
      while ((len = inStream.read(data)) != -1) {
        baos.write(data, 0, len);
      }
      result = baos.toByteArray();
      String res = new String(result);
      inStream.close();
      baos.close();
    } catch (Exception e) { 
      if (handler != null)
        handler.sendEmptyMessage(-1);
      e.printStackTrace();
    }
    if (handler != null) {
      Message message = new Message();
      Bundle bundle = new Bundle();
      bundle.putByteArray("result", result);
      message.setData(bundle);
      handler.sendMessage(message);
    }
  }
}

