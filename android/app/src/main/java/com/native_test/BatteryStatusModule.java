package com.native_test; // replace your-app-name with your app’s name


import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.util.Log;
import android.app.Activity;
import com.facebook.react.bridge.Promise;



public class BatteryStatusModule extends ReactContextBaseJavaModule  {
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";

  private Promise mPickerPromise;

     public BatteryStatusModule(ReactApplicationContext reactContext) {

         super(reactContext);
     }

     @Override
     public String getName() {
    return "BatteryStatusModule";
}

    @ReactMethod
    public void getBatteryStatus(Promise mPromise) {

        Activity currentActivity = getCurrentActivity();
        
        if (currentActivity == null) {
          mPickerPromise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
          return;
        }

        try {
            Intent batteryIntent = getCurrentActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if(level == -1 || scale == -1) {
                level = 0;
            }
            float findBattery =  ((float)level / (float)scale) * 100.0f;
            mPromise.resolve(findBattery);
          } catch (Exception e) {
            mPromise = null;
          }

        
        //System.out.print("battery level");
        //System.out.print(level);
        // successCallback.invoke(null ,((float)level / (float)scale) * 100.0f);
    }

    @ReactMethod
    public void isBatteryCharging(Promise mPromise) {
      try {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = contextMember.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean bCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||status == BatteryManager.BATTERY_STATUS_FULL;
        mPromise.resolve(bCharging);
      } catch (Exception e) {
        mPromise.reject("null");
      }
    }

}