#include <iostream>
#include "library.hpp"

//
// Created by black on 09.05.18.
//
int main(){
	if(isPrime(7))
		std::cout << "YES" << std::endl;
	else
		std::cout << "NO" << std::endl;
	float arr[]={1.0,2.0,3.0,4.0,5.0};
	forEachElement(arr,3,"add");
//	for(auto i=0;i<5;i++)
//		std::cout << arr[i] << std::endl;

	return 0;
}
