/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class HelloJNI */

#ifndef _Included_HelloJNI
#define _Included_HelloJNI
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     HelloJNI
 * Method:    sayHello
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_HelloJNI_sayHello
  (JNIEnv *, jobject);

/*
 * Class:     HelloJNI
 * Method:    isPrime
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_HelloJNI_isPrime(JNIEnv *, jobject, jint);

/*
 * Class:     HelloJNI
 * Method:    forEachElement
 * Signature: ([FFLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_HelloJNI_forEachElement
  (JNIEnv *, jobject, jfloatArray, jfloat, jstring);

#ifdef __cplusplus
}
#endif
#endif
