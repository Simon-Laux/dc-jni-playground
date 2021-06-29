#!/bin/sh
set -e

# prepare directory structure
mkdir -p src/chat/delta/java || true 
mkdir -p src/chat/delta/native || true

# clean up
rm src/chat/delta/java/* || true
rm src/chat/delta/native/* || true

# copy in
cp ../delta-chat-android/src/com/b44t/messenger/*.java src/chat/delta/java/
cp ../delta-chat-android/jni/dc_wrapper.c src/chat/delta/native/dc_wrapper.c
cp ../delta-chat-android/jni/deltachat-core-rust/deltachat-ffi/deltachat.h src/chat/delta/native/deltachat.h
# cp ../delta-chat-android/jni/deltachat-core-rust/target/release/libdeltachat.so src/chat/delta/native/libdeltachat.so
cp ../delta-chat-android/jni/deltachat-core-rust/target/release/libdeltachat.a src/chat/delta/native/libdeltachat.a

# change packagename - java package name
sed -i s/com.b44t.messenger/chat.delta.java/ src/chat/delta/java/*.java

# change packagename - dc_wrapper.c
sed -i s/com_b44t_messenger/chat_delta_java/ src/chat/delta/native/dc_wrapper.c


# make the header file import simpler
sed -i s+deltachat-core-rust/deltachat-ffi/deltachat.h+deltachat.h+ src/chat/delta/native/dc_wrapper.c