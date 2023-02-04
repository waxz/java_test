# 1. add project library
https://www.jetbrains.com/help/idea/library.html#define-a-project-library

for example:
- add https://github.com/sh0nk/matplotlib4j
- find maven information

    <dependency>
        <groupId>com.github.sh0nk</groupId>
        <artifactId>matplotlib4j</artifactId>
        <version>0.5.0</version>
    </dependency>
  
- in idea Project Setting, Libraries, Add from Maven, select Download Path and Transitive Dependencies

# 2. add source 
for example:
- add https://github.com/yuriy-g/simple-java-plot
- extract source file to lib/yuriy-g-simple-java-plot-5c502c2/src/main/java/com/github/plot/Plot.java
- in idea Project Setting, Modules, Sources, mark `lib/yuriy-g-simple-java-plot-5c502c2/src/main/java/com` as Source

# 3. jni generate tool
program
```shell
$JDKPath$/bin/javac
```
arguments
```
-h jni -d /tmp/$FileClass$ $FilePath$

```

working directory
```shell
$ProjectFileDir$
```

# 4. edit java.library.path
copy `libHelloJNI.so` to IntelliJIDEA's ProjectDirectory/lib 

in Run, Edit Configuration, More options, Add VM options
add `-Djava.library.path=$ProjectFileDir$/lib` to  `VM options`.
