cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c99 -Wall")
set(CMAKE_CXX_STANDARD 11)

add_library(
        native_demo SHARED
        NativeDemo.h
        NativeDemo.cpp
)

find_library(log-lib log)

target_link_libraries(
        native_demo
        ${log-lib}
)