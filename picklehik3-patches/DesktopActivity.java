package com.termux.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.termux.x11.XServer;

public class DesktopActivity extends Activity {

    private XServer xServer;
    private DisplayManager displayManager;
    private DisplayManager.DisplayListener displayListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN |
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_FULLSCREEN |
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );

        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        displayListener = new DisplayManager.DisplayListener() {
            @Override
            public void onDisplayAdded(int displayId) {
            }

            @Override
            public void onDisplayRemoved(int displayId) {
                if (displayId == getDisplay().getDisplayId()) {
                    finish();
                }
            }

            @Override
            public void onDisplayChanged(int displayId) {
            }
        };
        displayManager.registerDisplayListener(displayListener, null);

        xServer = new XServer();
        xServer.start(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (displayListener != null) {
            displayManager.unregisterDisplayListener(displayListener);
        }
        if (xServer != null) {
            xServer.stop();
        }
    }
}
