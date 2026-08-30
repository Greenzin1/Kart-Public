package org.srb2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.PowerManager;

/**
 * SRB2 KART Android Activity
 * Uses SDL2 for rendering and input
 */
public class SRB2Activity extends Activity {
    private static final String TAG = "SRB2KART";
    private SDLGameThread gameThread;
    private SurfaceDisplay surfaceDisplay;
    private PowerManager.WakeLock wakeLock;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN |
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_FULLSCREEN |
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        
        // Keep screen on
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "SRB2KART::GameLock");
        wakeLock.acquire();
        
        // Create surface view
        surfaceDisplay = new SurfaceDisplay(this);
        setContentView(surfaceDisplay);
        
        // Hide system UI
        hideSystemUI();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        surfaceDisplay.resume();
        if (gameThread != null) {
            gameThread.resume();
        }
        hideSystemUI();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        surfaceDisplay.pause();
        if (gameThread != null) {
            gameThread.pause();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameThread != null) {
            gameThread.stop();
        }
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
    
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }
    
    // Load native library
    static {
        System.loadLibrary("srb2kart");
    }
    
    // Native methods
    public static native void nativeInit();
    public static native void nativeQuit();
    public static native void nativeResize(int width, int height);
    public static native void nativePushEvent(int type, int x, int y, int data1, int data2);
    public static native void nativeRunMain();
}
