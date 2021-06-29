#!/bin/sh

export CLASSPATH="src"

javac src/*.java
javac src/chat/delta/java/*.java

javah chat.delta.java.hello

gcc \
-I /usr/lib/jvm/java-8-openjdk/include -I /usr/lib/jvm/java-8-openjdk/include/linux/ \
-o libhello.so -shared chat_delta_java_hello.c