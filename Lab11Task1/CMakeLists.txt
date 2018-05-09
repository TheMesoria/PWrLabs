cmake_minimum_required(VERSION 3.10)
project(Lab11Task1)

set(CMAKE_CXX_STANDARD 11)

add_executable(Lab11Task2 main.cpp library.cpp)
add_library(Lab11Task1 SHARED library.cpp library.hpp)