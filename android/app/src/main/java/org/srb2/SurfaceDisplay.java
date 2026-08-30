package org.srb2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * SurfaceView that handles touch input and rendering
 */
public class SurfaceDisplay extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private boolean isRunning = false;
    
    public SurfaceDisplay(Context context) {
        super(context);
        init();
    }
    
    public SurfaceDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        setKeepScreenOn(true);
    }
    
    public void resume() {
        isRunning = true;
    }
    
    public void pause() {
        isRunning = false;
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        SRB2Activity.nativeResize(width, height);
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        
        int x = (int) event.getX(pointerIndex);
        int y = (int) event.getY(pointerIndex);
        
        // SDL-style event types
        int eventType;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                eventType = 5; // SDL_FINGERDOWN
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                eventType = 6; // SDL_FINGERUP
                break;
            case MotionEvent.ACTION_MOVE:
                eventType = 7; // SDL_FINGERMOTION
                break;
            default:
                return true;
        }
        
        SRB2Activity.nativePushEvent(eventType, x, y, pointerId, 0);
        return true;
    }
}
