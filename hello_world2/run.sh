#!/bin/sh

export CLASSPATH="src"

./build.sh

CLASSPATH="src" java -Djava.library.path=. hello3