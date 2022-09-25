//
// Created by 李杰 on 8/13/22.
//
#include "NativeDemo.h"


int add(JNIEnv *env, jclass clazz, jint x, jint y) {
    return (x + y) * 10;
}

static JNINativeMethod nativeMethods[] = {
        /* name, signature, funcPtr */
        {"add", "(II)I", (void *) add},
};


JNIEXPORT jint
JNI_OnLoad(JavaVM *vm, void * /* reserved */) {
    JNIEnv *env = nullptr;
    jint result = -1;
    jclass clazz;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "ERROR: GetEnv failed\n");
        return result;
    }
    if (env == nullptr) {
        return result;
    }
    clazz = env->FindClass(MATH_JNI_CLASS);
    if (clazz == nullptr) {
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "Can't find %s", MATH_JNI_CLASS);
        return result;
    };
    if (env->RegisterNatives(clazz, nativeMethods, NELEM(nativeMethods)) < 0) {
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "RegisterNatives failed");
        return result;
    }

    return JNI_VERSION_1_4;
}


