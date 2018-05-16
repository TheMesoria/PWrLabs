#include <jni.h>
#include <stdio.h>
#include <iostream>
#include "HelloJNI.h"
#include <cmath>

JNIEXPORT void JNICALL Java_HelloJNI_sayHello(JNIEnv *, jobject)
{
        printf("\nHello world\n\n");
        return;
}

JNIEXPORT jboolean JNICALL Java_HelloJNI_isPrime(JNIEnv *kek, jobject, jint val)
{
        float x = sqrt(val)+1;
    for(auto i=2;i<val;i++)
        if(val % i == 0)
            return false;


    return true;
}

std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}

JNIEXPORT jfloatArray JNICALL Java_HelloJNI_forEachElement(JNIEnv *env, jobject obj, jfloatArray arr, jfloat val, jstring str)
{
            std::string string = jstring2string(env, str);
            float *array = (float*)env->GetFloatArrayElements(arr,0);
            auto size = 3;
            for(auto i=0;i<size;i++)
            {
                    if(string=="add")
                            array[i]+=val;
                    else if(string=="sub")
                            array[i]-=val;
                    else if(string=="div")
                            array[i]/=val;
                    else if(string=="mul")
                            array[i]*=val;
            }
            std::cout << array[0] <<"\n";
            std::cout << array[1] <<"\n";
            std::cout << array[2] <<"\n";
            env->SetFloatArrayRegion(arr,0,3,array);
            return arr;
}