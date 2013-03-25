#CC=g++
#CV_PATH=/usr/local/lib
#SEARCH_LIB=$(CV_PATH)/libopencv_core.so $(CV_PATH)/libopencv_highgui.so
#INCLUDE=-I/usr/local/include -I$(JAVA_INCLUDE) -I$(JAVA_INCLUDE)/linux
#LIBRARY=-L/usr/local/lib
#
#FLAGS= -m64 -shared -fPIC
#all:
	#$(CC) $(FLAGS) cvjni.cpp $(SEARCH_LIB) -o libcvjni.so $(INCLUDE) 

CC=g++
SEARCH_LIB=-lopencv_core -lopencv_highgui
INCLUDE=-I/usr/local/include -I$(JAVA_INCLUDE) -I./include
LIBRARY=-L/usr/local/lib
FLAGS= -m64 -dynamiclib -fPIC
FLAGS2= -m64 -shared -fPIC
OUT=libcvjni.jnilib
SRC=cvjni.cpp

mac: clean cvjni_byte_string cvjni_byte_byte 

cvjni_byte_string:
	$(CC) -m64 -c -fPIC src/cvjni_byte_string.cpp -o src/cvjni_byte_string.o $(INCLUDE)
	$(CC) -dynamiclib src/cvjni_byte_string.o -o src/libcvjni_byte_string.jnilib

cvjni_byte_byte:
	$(CC) -m64 -c -fPIC src/cvjni_byte_byte.cpp -o src/cvjni_byte_byte.o $(INCLUDE)
	$(CC) -dynamiclib src/cvjni_byte_byte.o -o src/libcvjni_byte_byte.jnilib

#$(CC) $(FLAGS2) cvcv.cpp -o cvcv.so $(SEARCH_LIB) $(INCLUDE)

clean:
	rm -rf **/*.so
	rm -rf **/*.jnilib
	rm -rf **/*.o
