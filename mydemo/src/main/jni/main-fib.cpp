//
// Created by oupxgd on 2019/2/24.
//
#include "pxgd_hyena_com_mydemo_NDK.h"

JNIEXPORT jstring JNICALL Java_pxgd_hyena_com_mydemo_NDK_stringFromJNI
  (JNIEnv *, jobject){
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
  }
