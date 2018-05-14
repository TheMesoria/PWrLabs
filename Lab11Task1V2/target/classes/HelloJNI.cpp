#include <jni.h>
#include <stdio.h>
#include "HelloJNI.h"

JNIEXPORT void JNICALL Java_HelloJNI_isPrime(JNIEnv *, jobject, jint)
{
    printf("Hello world");
    return;
}