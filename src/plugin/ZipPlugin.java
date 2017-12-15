package com.inspur.plugin;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.inspur.plugin.bean.MailData;
import com.inspur.plugin.bean.MultiMailsender;
import com.inspur.utils.FileUtils;
import com.inspur.utils.ZipFileUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.wq on 2017/12/15.
 */

public class ZipPlugin  extends CordovaPlugin {

        private static final String TAG = "ZipPlugin";

        @Override
        public void initialize(final CordovaInterface cordova, final CordovaWebView webView) {
            super.initialize(cordova, webView);
            Log.d(TAG, "initialize"  );
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onResume(boolean multitasking) {
            super.onResume(multitasking);
        }

        @Override
        public void onStop() {

            super.onStop();
        }


        @Override
        public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
            boolean cmdProcessed = true;
            if ("unzip".equals(action)) {
                unzip(args,callbackContext);
             } else if ("zip".equals(action)) {
             } else{
                cmdProcessed = false;
            }

            return cmdProcessed;
        }
    public void unzip(CordovaArgs args,CallbackContext callbackContext) {
        String res="";
     try {
         res = ZipFileUtils.unZip(args.optJSONObject(0).getJSONObject("unZipFileName").toString());
            if(res==null){
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK,"解压成功"));
            }
     }catch (JSONException e){
         res=e.getMessage();
     }
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR,"解压失败"));

    }

        @Override
        public void onDestroy() {
            // TODO Auto-generated method stub

        }
    }

