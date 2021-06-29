
gcc -I /usr/lib/jvm/java-8-openjdk/include -I /usr/lib/jvm/java-8-openjdk/include/linux/ -o libhello.so -shared hello.c

java -Djava.library.path=. hello