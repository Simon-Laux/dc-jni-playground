#!/bin/sh

export CLASSPATH="src"

./build.sh

CLASSPATH="src" java -Djava.library.path=src/chat/delta/native DCTest