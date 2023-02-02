package com.example.get_phone_data_app;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "BatteryLevel";

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        //some code
        MethodChannel myMethod = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);
        myMethod.setMethodCallHandler(
                (call, result) -> {
                    if (call.method.equals("getBatteryLevel")) {
                        int batteryLevel = getBatteryLevel();
                        result.success(batteryLevel);
                    }
                    if (call.method.equals("getIsCharged")) {
                        result.success(getBatteryLevel() > 20);
                    }
                }
        );
        super.configureFlutterEngine(flutterEngine);
    }

    private int getBatteryLevel() {
        int batteryLevel;
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(getApplicationContext()).
                    registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }
        return batteryLevel;
    }
}
