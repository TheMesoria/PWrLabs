# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.10

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/black/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/181.4668.70/bin/cmake/bin/cmake

# The command to remove a file.
RM = /home/black/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/181.4668.70/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/black/Work/lab/Lab11Task1

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/black/Work/lab/Lab11Task1/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Lab11Task1.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Lab11Task1.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Lab11Task1.dir/flags.make

CMakeFiles/Lab11Task1.dir/library.cpp.o: CMakeFiles/Lab11Task1.dir/flags.make
CMakeFiles/Lab11Task1.dir/library.cpp.o: ../library.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/black/Work/lab/Lab11Task1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Lab11Task1.dir/library.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Lab11Task1.dir/library.cpp.o -c /home/black/Work/lab/Lab11Task1/library.cpp

CMakeFiles/Lab11Task1.dir/library.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Lab11Task1.dir/library.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/black/Work/lab/Lab11Task1/library.cpp > CMakeFiles/Lab11Task1.dir/library.cpp.i

CMakeFiles/Lab11Task1.dir/library.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Lab11Task1.dir/library.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/black/Work/lab/Lab11Task1/library.cpp -o CMakeFiles/Lab11Task1.dir/library.cpp.s

CMakeFiles/Lab11Task1.dir/library.cpp.o.requires:

.PHONY : CMakeFiles/Lab11Task1.dir/library.cpp.o.requires

CMakeFiles/Lab11Task1.dir/library.cpp.o.provides: CMakeFiles/Lab11Task1.dir/library.cpp.o.requires
	$(MAKE) -f CMakeFiles/Lab11Task1.dir/build.make CMakeFiles/Lab11Task1.dir/library.cpp.o.provides.build
.PHONY : CMakeFiles/Lab11Task1.dir/library.cpp.o.provides

CMakeFiles/Lab11Task1.dir/library.cpp.o.provides.build: CMakeFiles/Lab11Task1.dir/library.cpp.o


# Object files for target Lab11Task1
Lab11Task1_OBJECTS = \
"CMakeFiles/Lab11Task1.dir/library.cpp.o"

# External object files for target Lab11Task1
Lab11Task1_EXTERNAL_OBJECTS =

libLab11Task1.so: CMakeFiles/Lab11Task1.dir/library.cpp.o
libLab11Task1.so: CMakeFiles/Lab11Task1.dir/build.make
libLab11Task1.so: CMakeFiles/Lab11Task1.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/black/Work/lab/Lab11Task1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX shared library libLab11Task1.so"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Lab11Task1.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Lab11Task1.dir/build: libLab11Task1.so

.PHONY : CMakeFiles/Lab11Task1.dir/build

CMakeFiles/Lab11Task1.dir/requires: CMakeFiles/Lab11Task1.dir/library.cpp.o.requires

.PHONY : CMakeFiles/Lab11Task1.dir/requires

CMakeFiles/Lab11Task1.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Lab11Task1.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Lab11Task1.dir/clean

CMakeFiles/Lab11Task1.dir/depend:
	cd /home/black/Work/lab/Lab11Task1/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/black/Work/lab/Lab11Task1 /home/black/Work/lab/Lab11Task1 /home/black/Work/lab/Lab11Task1/cmake-build-debug /home/black/Work/lab/Lab11Task1/cmake-build-debug /home/black/Work/lab/Lab11Task1/cmake-build-debug/CMakeFiles/Lab11Task1.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Lab11Task1.dir/depend

