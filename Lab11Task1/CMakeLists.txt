cmake_minimum_required(VERSION 3.10)
project(Lab11Task1)

set(CMAKE_CXX_STANDARD 11)

find_package(JNI)

if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif()


add_executable(Lab11Task2 main.cpp library.cpp)
add_library(Lab11Task1 SHARED library.cpp library.hpp)

target_link_libraries(Lab11Task1 ${JNI_LIBRARIES})
