#include <jni.h>
#include <android/log.h>
#include <SDL.h>

#define LOG_TAG "SRB2-JNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern int SDL_main(int argc, char *argv[]);

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativeInit(JNIEnv* env, jobject thiz) {
    LOGI("nativeInit");
}

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativeRunMain(JNIEnv* env, jobject thiz) {
    LOGI("nativeRunMain - starting SRB2KART");
    SDL_main(0, NULL);
    LOGI("SRB2KART exited");
}

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativeResize(JNIEnv* env, jobject thiz, jint width, jint height) {
    LOGI("nativeResize: %dx%d", width, height);
}

JNIEXPORT void JNICALL
Java_org_srb2_SRB2Activity_nativePushEvent(JNIEnv* env, jobject thiz,
                                           jint type, jint x, jint y, jint data1, jint data2) {
    LOGI("Event: type=%d x=%d y=%d d1=%d d2=%d", type, x, y, data1, data2);
}
