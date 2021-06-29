#include<stdio.h>
#include<jni.h>
#include "chat_delta_java_hello.h" 

JNIEXPORT void JNICALL Java_chat_delta_java_hello_sayHello
  (JNIEnv *env, jobject object, jint len) {
  printf ( "\nLength is %d", len ); }