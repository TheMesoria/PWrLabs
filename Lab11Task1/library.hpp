#ifndef LAB11TASK1_LIBRARY_H
#define LAB11TASK1_LIBRARY_H
#include <string>
#include "jni.h"

void hello();
bool isPrime(int const& val);
void forEachElement(float array[], float const& val, std::string const& string);

#endif