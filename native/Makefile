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
SEARCH_LIB=-lopencv_core -lopencv_highgui -lopencv_features2d -lopencv_contrib\
					 -lopencv_calib3d -lopencv_flann -lopencv_gpu -lopencv_imgproc
INCLUDE=-I/usr/local/include -I$(JAVA_INCLUDE) -I./include
LIBRARY=-L/usr/local/lib
FLAGS= -m64 -dynamiclib -fPIC
FLAGS2= -m64 -shared -fPIC
OUT=libcvjni.jnilib
SRC=cvjni.cpp

mac: clean cvjni_byte_in test_cv

cvjni_byte_in:
	$(CC) -m64 -c -fPIC src/cvjni_byte_in.cpp -o src/cvjni_byte_in.o $(INCLUDE)
	$(CC) -dynamiclib src/cvjni_byte_in.o -o src/libcvjni_byte_in.jnilib

test_cv:
	$(CC) -m64 -c -fPIC test_code/cvcv.cpp -o test_code/cvcv.o $(INCLUDE)
	$(CC) -shared test_code/cvcv.o -o test_code/cvcv.so -L/usr/local/lib $(SEARCH_LIB)
	$(CC) -m64 -c -fPIC test_code/cv_histogram.cpp -o test_code/cv_histogram.o $(INCLUDE)
	$(CC) -shared test_code/cv_histogram.o -o test_code/cv_histogram.so -L/usr/local/lib $(SEARCH_LIB)

clean:
	rm -rf **/*.so
	rm -rf **/*.jnilib
	rm -rf **/*.o
