package cn.jpush.reactnativejvrification;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;


import java.util.ArrayList;
import java.util.List;

import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.PreLoginListener;
import cn.jiguang.verifysdk.api.PrivacyBean;
import cn.jiguang.verifysdk.api.RequestCallback;
import cn.jiguang.verifysdk.api.VerifyListener;
import cn.jiguang.verifysdk.api.JVerifyUIClickCallback;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;

public class JVerificationModule extends ReactContextBaseJavaModule  {

    private static String TAG = "JVerificationModule";


    private  Context context;

    public JVerificationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context=reactContext;
    }

    @Override
    public boolean canOverrideExistingModule() {
        return true;
    }

    @Override
    public String getName() {
        return "JVerificationModule";
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @ReactMethod
    public void initClient(String key,final Callback callback) {

        JVerificationInterface.init(this.context, new RequestCallback<String>() {
            @Override
            public void onResult(int code, String content) {
                doCallback(callback, code, content);
            }
        });

        JVerificationInterface.setDebugMode(true);
    }


    @ReactMethod
    public void setDebug(boolean enable) {
        JVerificationInterface.setDebugMode(enable);
    }

    @ReactMethod
    public void getToken(final Callback callback) {
        JVerificationInterface.getToken(getCurrentActivity(), new VerifyListener() {
            @Override
            public void onResult(int code, String content, String operato) {
                doCallback(callback, code, content);
            }
        });
    }

    @ReactMethod
    public void checkVerifyEnable(Callback callback){
        if(callback==null)return;
        callback.invoke(JVerificationInterface.checkVerifyEnable(this.context));
    }

    @ReactMethod
    public void preLogin(ReadableMap map,final  Callback callback){

        int time = map.getInt("timeout");
        JVerificationInterface.preLogin(this.context, time, new PreLoginListener() {
            @Override
            public void onResult(int code, String content) {
                if(callback==null)return;
                doCallback(callback, code, content);
            }
        });
    }


    @ReactMethod
    public void loginAuth(ReadableMap map, final Callback callback) {
        boolean isInstallWechat = map.getBoolean("isInstallWechat");
        ImageButton mBtn = new ImageButton(this.getCurrentActivity());
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        if (isInstallWechat) {
            mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mLayoutParams1.setMargins(200, 0, 0, 250);
        } else {
            mLayoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mLayoutParams1.setMargins(0, 0, 0, 250);
        }
        mBtn.setBackgroundResource(R.drawable.native_phone_number_login);
        mBtn.setLayoutParams(mLayoutParams1);

        ImageButton mBtn2 = null;
        if (isInstallWechat) {
            mBtn2 = new ImageButton(this.getCurrentActivity());
            RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            mLayoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mLayoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mLayoutParams2.setMargins(0, 0, 200, 250);
            mBtn2.setBackgroundResource(R.drawable.native_wechat_login);
            mBtn2.setLayoutParams(mLayoutParams2);
        }


        ViewGroup viewGroup = (ViewGroup) getCurrentActivity().getLayoutInflater().inflate(R.layout.line, null);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mLayoutParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mLayoutParams3.setMargins(0, 0, 0, 500);

        viewGroup.setLayoutParams(mLayoutParams3);

        List<PrivacyBean> privacyBeans = new ArrayList<PrivacyBean>() {{
            add(new PrivacyBean("《用户服务协议》", "https://wechat.letote.cn/agreement", "，"));
            add(new PrivacyBean("《隐私政策》", "https://wechat.letote.cn/privacy", "，"));
        }};

        JVerifyUIConfig uiConfig = new JVerifyUIConfig.Builder()
                .setNavColor(0xffffffff)
                .setNumberTextBold(true)
                .setNavReturnImgPath("native_close")
                .setNavReturnBtnOffsetX(20)
                .setLogoImgPath("native_login_icon")
                .setNavText("登录")
                .setNavTextColor(0xffffffff)
                .setLogoWidth(122)
                .setLogoHeight(45)
                .setLogoHidden(false)
                .setNumberColor(0xff333333)
                .setLogBtnText("一键登入")
                .setLogBtnTextColor(0xffffffff)
                .setLogBtnImgPath("native_login_bg")
                .setAppPrivacyColor(0xff666666, 0xff0085d0)
                .setCheckedImgPath("native_checked")
                .setUncheckedImgPath("native_unchecked")
                .setSloganTextColor(0xff999999)
                .setLogoOffsetY(50)
                .setNumFieldOffsetY(170)
                .setSloganOffsetY(215)
                .setLogBtnOffsetY(254)
                .setPrivacyMarginL(40)
                .setPrivacyMarginR(40)
                .setSloganTextSize(12)
                .setPrivacyCheckboxSize(30)
                .setPrivacyText("我已阅读并同意", "并使用本机号码登录")
                .setPrivacyNameAndUrlBeanList(privacyBeans)
                .enableHintToast(true,null)
                .addCustomView(mBtn, true, new JVerifyUIClickCallback() {
                    @Override
                    public void onClicked(Context context, View view) {
                        try {
                            doCallback(callback, 8000, "");
                        } catch (Exception e) {
                        }

                    }
                }).addCustomView(mBtn2, true, new JVerifyUIClickCallback() {
                    @Override
                    public void onClicked(Context context, View view) {
                        try {
                            doCallback(callback, 9000, "");
                        } catch (Exception e) {
                        }
                    }
                })
                .addCustomView(viewGroup, true, new JVerifyUIClickCallback() {
                    @Override
                    public void onClicked(Context context, View view) {

                    }
                })
                .setPrivacyOffsetY(30).build();
        JVerificationInterface.setCustomUIWithConfig(uiConfig);
        JVerificationInterface.loginAuth(this.context, new VerifyListener() {
            @Override
            public void onResult(final int code, final String content, final String operator) {
                try {
                    doCallback(callback, code, content, operator);
                } catch (Exception e) {
                }
            }
        });
    }




    private void doCallback(Callback callback, int code, String content, String operator) {
        WritableMap map = Arguments.createMap();
        map.putInt("code", code);
        map.putString("loginToken", content);
        map.putString("content", content);
        map.putString("operator", operator);
        callback.invoke(map);
    }

    private void doCallback(Callback callback, int code, String content) {
        doCallback(callback, code, content, null);
    }

}
