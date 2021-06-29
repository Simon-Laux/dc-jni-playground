
run `./build.sh` to build

run `./run.sh` to build and run


for trying out the package version:

copy DCTest.java into the out folder
`cd` into the `out` folder

```sh
# compile
javac DCTest.java -cp deltachat.jar
# run
java -cp deltachat.jar:. -Djava.library.path=. DCTest
```

> **WARNING**: the build script clears `out` folder so don't put original files into it.