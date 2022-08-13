//
// Created by 李杰 on 8/13/22.
//

#include <jni.h>
#include <android/log.h>

int add(JNIEnv *env, jclass clazz, jint x, jint y);

# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
# define MATH_JNI_CLASS "hua/lee/skills/jni/MathJni"
# define TAG "LiJie"
