#include <opencv2/opencv.hpp>
#include <vector>
#include <iostream>

using namespace std;
using namespace cv;

extern "C" void cvcv(vector<unsigned char> *input,
        vector<unsigned char> *output) {
    // convert image binary to Mat
    cout << "function invoked start" << endl;
    Mat image = imdecode(*input, CV_LOAD_IMAGE_COLOR);
    input->clear();
    imencode(".bmp", image, *output); // encode the Mat to bmp
    cout << "function invoked end" << endl;
}
