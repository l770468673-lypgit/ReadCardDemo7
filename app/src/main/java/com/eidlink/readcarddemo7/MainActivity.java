package com.eidlink.readcarddemo7;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eidlink.idocr.sdk.EidLinkReadCardCallBack;
import com.eidlink.idocr.sdk.EidLinkSE;
import com.eidlink.idocr.sdk.EidLinkSEFactory;
import com.eidlink.idocr.sdk.IDOCRCardType;
import com.eidlink.idocr.util.DelayUtil;
import com.eidlink.readcarddemo7.apis.HttpCallback;
import com.eidlink.readcarddemo7.apis.HttpManager;
import com.eidlink.readcarddemo7.apis.LUtils;
import com.eidlink.readcarddemo7.apis.bean_person;
import com.guoguang.jni.JniCall;

import java.io.IOException;

import retrofit2.Call;


/*
 *
 **demo中NFC读取身份证不需要点击读卡，读卡针对第三方设备适配。
 *
 * NFC标准api贴卡，目前读取不了电子证照！！！！！
 *
 * 如未做权限申请，使用前在设置→应用设置→权限→允许所有权限
 * ！！！！！！！修改demo中mcid！！！！！！！！！！
 *  ip和port使用demo中默认ip及端口
 * 详情见文档！！！！！！！！！！！
 * 错误码见文档！！！！！！！！！！
 * NFC读取身份证，手机NFC区域对准身份证感应区,不要移动，不同手机NFC感应区不同
 * 时延测试为测试网络时延情况
 * 贴卡演示获取当前机型动态gif图，展示如何贴卡，接口只是返回动态图，与读卡无关
 * */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private PendingIntent pi;
    NfcAdapter nfcAdapter;
    private TextView tv;

    public final static int READ_CARD_START = 10000001;
    public final static int READ_CARD_SUCCESS = 30000003;
    public final static int READ_CARD_FAILED = 90000009;
    public final static int READ_CARD_DELAY = 40000004;
    private Button delay, tkbtn, dkbtn;
    //    private EditText edtype;
    private long starttime, endtime;
    private EidLinkSE eid;

    private String mcid = "1320D00";//!!!!!!!!!!!!!!CID替换为分配的CID！！！！！！！！！！！！
    private String ip = "testnidocr.eidlink.com";//云解码服务地址
    private int port = 9989;//云解码端口
    private IsoDep mIsodep;

    private TextView main_tv_name, main_tv_age, main_tv_sex, main_tv_useridcard, main_tv_idcard, main_tv_address, main_tv_nation, main_tv_classify, main_tv_starttime, main_tv_endtime, main_tv_product;
    private ImageView main_im_userpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestCameraPerm();
        initNfc();
        initEid();
        initView();
        Log.i(TAG, "getIntent===: " + getIntent());
    }

    /*eid读卡初始化,读卡必须初始化,具体参数见文档*/
    private void initEid() {
        try {
            eid = EidLinkSEFactory.getEidLinkSEForNfc(mHandler, this, mcid, ip, port, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        tv = this.findViewById(R.id.text);

        delay = this.findViewById(R.id.delay);
        tkbtn = this.findViewById(R.id.tkbtn);
        dkbtn = this.findViewById(R.id.dkbtn);

        /*NFC读卡不需要输入类型，NFC读卡目前只支持 身份证*/
        //        edtype = this.findViewById(R.id.edtype);


        main_tv_name = this.findViewById(R.id.main_tv_name);
        main_tv_age = this.findViewById(R.id.main_tv_age);
        main_tv_sex = this.findViewById(R.id.main_tv_sex);
        main_tv_useridcard = this.findViewById(R.id.main_tv_useridcard);
        main_tv_product = this.findViewById(R.id.main_tv_product);
        main_tv_idcard = this.findViewById(R.id.main_tv_idcard);
        main_tv_address = this.findViewById(R.id.main_tv_address);
        main_tv_nation = this.findViewById(R.id.main_tv_nation);
        main_tv_classify = this.findViewById(R.id.main_tv_classify);
        main_tv_starttime = this.findViewById(R.id.main_tv_starttime);
        main_tv_endtime = this.findViewById(R.id.main_tv_endtime);
        main_im_userpic = this.findViewById(R.id.main_im_userpic);


        /*时延按钮*/
        delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*时延测试，测试当前网络环境与云解码服务之间的延时，单位ms*/
                DelayUtil.getDelayTime(mHandler, 5);
                delay.setEnabled(false);
            }
        });
        /*贴卡演示按钮，与贴卡无关*/
        tkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*贴卡演示,与！！贴卡无关！！,只是请求接口返回gif图，接口result返回N，为无当前型号的GIT图，返回为默认GIF图*/
                Intent intent = new Intent(MainActivity.this, GifActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /*读卡按钮，适配callBack方法，再点击读卡，！！！ 标准NFC直接贴卡，不需要点击按钮！！！
         * 点击按钮前实现，callBack中方法
         * */
        dkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*读卡按钮，针对第三方设备适配读卡方法
                 * eid.ReadCard(int,callBack);
                 * 参数 int ：读卡类型(0为通用类型（包含身份证和电子证照,
                 * 使用0前提必须实现callBack中的TypeA和TypeB中apdu方法），1 ：身份证，2 ：电子证照)
                 * 建议明确读取卡片类型(1和2)
                 * */
                Intent intent = getIntent();

                intent.setAction("android.nfc.action.TAG_DISCOVERED");
                if (NfcAdapter.ACTION_TAG_DISCOVERED.equals("android.nfc.action.TAG_DISCOVERED")) {
                    eid.NFCreadCard(intent);
                }
            }
        });
    }

    EidLinkReadCardCallBack callBack = new EidLinkReadCardCallBack() {
        @Override
        public byte[] transceiveTypeB(byte[] bytes) {
            //TypeB的Apdu实现，读取身份证
            return bytes;
        }

        @Override
        public byte[] transceiveTypeA(byte[] bytes) {
            //TypeA的Apdu实现，读取电子证照
            byte[] outData = new byte[bytes.length];
            Log.i(TAG, "transceiveTypeA: " + bytesToHexString(bytes, bytes.length));
            //Arrays.fill(outData, (byte)0x00);
            try {
                outData = mIsodep.transceive(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return outData;
        }
    };

    public static String bytesToHexString(byte[] bArray, int length) {
        StringBuffer sb = new StringBuffer(length);
        String sTemp;
        for (int i = 0; i < length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            //            sb.append(" ");
        }
        return sb.toString();
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case READ_CARD_START:
                    tv.setText("开始读卡");
                    starttime = System.currentTimeMillis();
                    break;
                case READ_CARD_FAILED:
                    int s = msg.arg1;
                    tv.setText("读卡失败 " + s);
                    try {
                        if (mIsodep != null) {
                            mIsodep.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    break;
                case READ_CARD_SUCCESS:
                    endtime = System.currentTimeMillis() - starttime;
                    String reqid = (String) msg.obj;
                    Log.e(TAG, "   读卡成功    salt    " + reqid);
                    tv.setText("读卡成功   " + reqid + "  时间 " + endtime);
                    try {
                        if (mIsodep != null) {

                            mIsodep.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    queryIdCards(reqid);
                    break;
                case READ_CARD_DELAY:
                    int ss = msg.arg1;
                    Log.e(TAG, "ss    " + ss);
                    tv.setText("延迟  ss   " + ss);
                    break;
                default:
                    break;
            }
        }
    };

    private void queryIdCards(String reqid) {
        Call<bean_person> bean_personCall = HttpManager.getInstance().getHttpClient().QueryIdCard("101", reqid);
        bean_personCall.enqueue(new HttpCallback<bean_person>() {
            @Override
            protected boolean processData(bean_person bo) {
                if (bo.getErrorCode() == 0) {
                    bean_person.ExtraBean extra = bo.getExtra();
                    bean_person.ExtraBean.ResBean status = extra.getRes();
                    String idType = status.getIdType();
                    if ("01".equals(idType)) {
                        main_tv_idcard.setText(idType);
                    }

                    String address = status.getAddress();
                    main_tv_address.setText(address);

                    String beginTime = status.getBeginTime();
                    main_tv_starttime.setText(beginTime);

                    String birthDate = status.getBirthDate();
                    main_tv_age.setText(birthDate);

                    String classify = status.getClassify();
                    if (classify.equals("1")) {
                        main_tv_classify.setText(classify + "身份证");
                    } else if (classify.equals("2")) {
                        main_tv_classify.setText(classify + "电子证照");
                    }


                    String endTime = status.getEndTime();
                    main_tv_endtime.setText(endTime);

                    String idnum = status.getIdnum();
                    main_tv_useridcard.setText(idnum);

                    String name = status.getName();
                    main_tv_name.setText(name);

                    String nation = status.getNation();
                    main_tv_nation.setText(nation);

                    String sex = status.getSex();
                    main_tv_sex.setText(sex);

                    String signingOrganization = status.getSigningOrganization();
                    main_tv_product.setText(signingOrganization);

                    byte[] bytes = hexStr2byte(extra.getPicture());
                    Bitmap bitmap = readByteMap(bytes);
                    main_im_userpic.setImageBitmap(bitmap);
                    LUtils.d(TAG, "extra.getPicture()===" + extra.getPicture());
                } else {
                    LUtils.d(TAG, "bo.getErrorCode()===" + bo.getErrorCode());
                }
                return true;
            }
        });

    }

    private Bitmap readByteMap(byte[] pucPHMsg) {
        Bitmap bitmap = null;
        try {
            byte[] bmp = new byte[14 + 40 + 308 * 126];
            int ret1 = JniCall.Huaxu_Wlt2Bmp(pucPHMsg, bmp, 0);
            bitmap = BitmapFactory.decodeByteArray(bmp, 0, bmp.length);
            Log.e(TAG, "ret==" + ret1);
            Log.e(TAG, "bitmap==" + bitmap.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static boolean isHexStr(String num) {
        if (num == null || "".equals(num)) {
            return false;
        }
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            if ((c < '0' || c > '9') && (c < 'A' || c > 'F') && (c < 'a' || c > 'f')) {
                return false;
            }
        }
        return true;
    }

    
    public static byte[] hexStr2byte(String hexStr) {
        System.out.println("hexStr2byte");
        if (!isHexStr(hexStr) || hexStr.length() % 2 != 0) {
            System.out.println("不符合十六进制数据");
            return null;
        }
        hexStr = hexStr.toUpperCase();
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();

        byte[] bytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int m = str.indexOf(hexs[2 * i]) << 4;
            int n = str.indexOf(hexs[2 * i + 1]);
            if (m == -1 || n == -1) {//非16进制字符串
                return null;
            }
            bytes[i] = (byte) (m | n);
        }
        return bytes;
    }

    /*Android 标准初始化*/
    private void initNfc() {
        Log.e(TAG, "initNfc  1 ");
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            tv.setText("设备不支持NFC");
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            tv.setText("请在系统设置中先启用NFC功能");
            return;
        }
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initNfc();
        initEid();
        try {
            Log.e(TAG, "onResume nfcAdapter  " + nfcAdapter);
            if (null != nfcAdapter) {
                nfcAdapter.enableForegroundDispatch(this, pi, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != nfcAdapter) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e(TAG, "onNewIntent  " + intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            try {
                Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                mIsodep = IsoDep.get(tagFromIntent);
                mIsodep.connect();
                if (mIsodep.isConnected()) {
                    eid.ReadCard(IDOCRCardType.ECCARD, callBack);
                }
                //读卡完成调用 isodep.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, " end  onNewIntent  ");
        //          if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
        //            eid.NFCreadCard(intent);
        //        }
    }

    private void requestCameraPerm() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.BLUETOOTH
                                , Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        81);
            } else {
            }
        } else {

        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 81) {
             if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {// Permission Granted
                //                finish();
                Toast.makeText(this, "无所需权限,请在设置中添加权限", Toast.LENGTH_LONG).show();
            } else {
            }
        } else {

        }
    }


}

