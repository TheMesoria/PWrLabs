#include "library.hpp"

#include <iostream>

void hello()
{
	std::cout << "Hello, World!" << std::endl;
}

bool isPrime(int const& val)
{
	for(auto i=2;i<val;i++)
		if(val % i == 0)
			return false;
}

void forEachElement(float array[], float const& val, std::string const& string)
{
	auto size = 5;
	for(auto i=0;i<size;i++)
	{
		if(string=="add")
			array[i]+=val;
		else if(string=="sub")
			array[i]-=val;
		else if(string=="div")
			array[i]/=val;
		else if(string=="mul")
			array[i]*=val;
	}
}
