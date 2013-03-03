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
INCLUDE=-I/usr/local/include -I$(JAVA_INCLUDE)
LIBRARY=-L/usr/local/lib
FLAGS= -m64 -dynamiclib -fPIC
OUT=libcvjni.jnilib
SRC=cvjni.cpp

mac:
	$(CC) $(FLAGS) $(LIBRARY) $(SEARCH_LIB) $(INCLUDE) $(SRC) -o $(OUT)
