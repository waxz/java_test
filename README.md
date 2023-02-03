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
