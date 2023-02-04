cmake_minimum_required(VERSION 3.17)
project(hello)

set(CMAKE_CXX_STANDARD 14)

set(JAVA_HOME "/home/waxz/.jdks/openjdk-19.0.2")

find_package(Java COMPONENTS Development)

# We are only interested in finding jni.h: we do not care about extended JVM
# functionality or the AWT library.
set(JAVA_AWT_LIBRARY NotNeeded)
set(JAVA_JVM_LIBRARY NotNeeded)
set(JAVA_INCLUDE_PATH2 NotNeeded)
set(JAVA_AWT_INCLUDE_PATH NotNeeded)
find_package(JNI REQUIRED)


add_library(HelloJNI SHARED ./HelloJNI.cpp)

target_include_directories(HelloJNI PUBLIC
        ${JNI_INCLUDE_DIRS}
        ${JAVA_HOME}/include/linux/
        )
set(CMAKE_INSTALL_PREFIX  ${CMAKE_SOURCE_DIR}/install)
install(TARGETS HelloJNI
        ARCHIVE DESTINATION ${CMAKE_INSTALL_PREFIX}/lib
        LIBRARY DESTINATION ${CMAKE_INSTALL_PREFIX}/lib
        RUNTIME DESTINATION ${CMAKE_INSTALL_PREFIX}/bin
        )