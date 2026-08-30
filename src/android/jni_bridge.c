// Android JNI bridge for SRB2 KART
// Connects Java SurfaceView to native SDL2 game loop

#include <jni.h>
#include <android/log.h>
#include <android_native_app_glue.h>

#include "../doomdef.h"
#include "../d_main.h"
#include "../i_video.h"
#include "../command.h"

#define LOG_TAG "SRB2-JNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static struct android_app* g_app = NULL;
static int g_initialized = 0;

// Forward declaration
extern int srb2_main(void);

void I_InitGraphics(void);
void I_ShutdownGraphics(void);

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativeInit(JNIEnv* env, jobject thiz) {
    LOGI("nativeInit called");
    g_initialized = 1;
}

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativeQuit(JNIEnv* env, jobject thiz) {
    LOGI("nativeQuit called");
    g_initialized = 0;
}

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativeResize(JNIEnv* env, jobject thiz, jint width, jint height) {
    LOGI("nativeResize: %dx%d", width, height);
    // Update video dimensions
    vid.width = width;
    vid.height = height;
}

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativePushEvent(JNIEnv* env, jobject thiz, 
                                           jint type, jint x, jint y, jint data1, jint data2) {
    // Forward to SDL event queue
    // For now, just log
    LOGI("Event: type=%d x=%d y=%d d1=%d d2=%d", type, x, y, data1, data2);
}

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativeRunMain(JNIEnv* env, jobject thiz) {
    LOGI("nativeRunMain - starting SRB2KART");
    
    // Set up JNI env for callbacks
    (*env)->GetJavaVM(env, &g_app->activity->vm);
    g_app->activity->clazz = (*env)->NewGlobalRef(env, thiz);
    
    // Run the game
    srb2_main();
    
    LOGI("SRB2KART exited");
}

// Android entry point
void android_main(struct android_app* app) {
    LOGI("android_main called");
    g_app = app;
    
    // Initialize SDL-like subsystems
    // The actual SDL init happens inside srb2_main
    
    // Run the game
    srb2_main();
    
    LOGI("android_main exiting");
}
