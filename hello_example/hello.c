#include<stdio.h>
#include<jni.h>
#include "hello.h" 

JNIEXPORT void JNICALL Java_hello_sayHello
  (JNIEnv *env, jobject object, jint len) {
  printf ( "\nLength is %d", len ); }