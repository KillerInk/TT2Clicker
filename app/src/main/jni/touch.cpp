#include <jni.h>
#include <stdlib.h>
#include <android/log.h>
#include <fcntl.h>
#include "MyInput.h"

#define  LOG_TAG    "clickerbot.NativeTouch"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

extern "C"
{



JNIEXPORT jobject JNICALL Java_clickerbot_com_troop_clickerbot_NativeTouch_init(JNIEnv *env, jobject thiz)
{
    MyInput * touch = new MyInput();
    return env->NewDirectByteBuffer(touch, 0);
}

JNIEXPORT void JNICALL Java_clickerbot_com_troop_clickerbot_NativeTouch_open(JNIEnv *env, jobject thiz, jobject handel, jstring path)
{
    MyInput * touch =  (MyInput*)env->GetDirectBufferAddress(handel);
    char * outfile  = (char*) env->GetStringUTFChars(path,NULL);
    touch->openFile(outfile);
    env->ReleaseStringUTFChars(path,outfile);
}
JNIEXPORT void JNICALL Java_clickerbot_com_troop_clickerbot_NativeTouch_close(JNIEnv *env, jobject thiz, jobject handel)
{
    MyInput * touch =  (MyInput*)env->GetDirectBufferAddress(handel);
    touch->closeFile();
    delete touch;
}

JNIEXPORT void JNICALL Java_clickerbot_com_troop_clickerbot_NativeTouch_sendEvent(JNIEnv *env, jobject thiz, jobject handel, jint type,jint code, jint value)
{
    MyInput * touch =  (MyInput*)env->GetDirectBufferAddress(handel);
    touch->sendEvent(type,code,value);
}

}
