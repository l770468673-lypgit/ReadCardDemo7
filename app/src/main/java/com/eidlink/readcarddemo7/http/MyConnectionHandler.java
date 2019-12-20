package com.eidlink.readcarddemo7.http;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyConnectionHandler extends Handler {
	  MyHttpConnectionCallBack callBack;
	  String callbackMethodName;

	  public MyConnectionHandler(MyHttpConnectionCallBack _callBack, String _callbackMethodName) {
	    callBack = _callBack;
	    callbackMethodName = _callbackMethodName;
	  }

	  @Override
	  public void handleMessage(Message msg) {
	    if (msg.what == -1) {
	    	Toast.makeText((Activity)callBack, "请求服务器失败", Toast.LENGTH_LONG).show();
	    } else {
	      Bundle bundle = msg.getData();
	      byte[] bytes = bundle.getByteArray("result");
	      try {
	        callBack.httpConnectionCallBack(callbackMethodName, bytes);
	      } catch (Exception e) {
	        callBack.httpConnectionCallBack("PARSE_DATA_FAILED", "请求服务器失败出现异常".getBytes());
	      }
	    }
	  }
}

