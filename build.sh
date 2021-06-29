#!/bin/sh

export CLASSPATH="src"

javac src/*.java
javac src/chat/delta/java/*.java


cd src/chat/delta/native

gcc \
dc_wrapper.c \
-I /usr/lib/jvm/java-8-openjdk/include -I /usr/lib/jvm/java-8-openjdk/include/linux/ \
-L libdeltachat.a -l:./libdeltachat.a \
-o libdeltajni.so -shared

cd ../../../..

# package
mkdir -p out || true
rm out/* || true

cd src
jar cf ../out/deltachat.jar chat/delta/java/*.class
cd ..
cp src/chat/delta/native/libdeltajni.so out/libdeltajni.so