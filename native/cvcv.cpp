#include <opencv2/opencv.hpp>
#include <vector>
#include <iostream>

using namespace std;
using namespace cv;

extern "C" void cvcv(vector<unsigned char>* imageSourceV) {
  // convert image binary to Mat
  cout << "function invoked start" << endl;
  Mat image = imdecode(*imageSourceV, CV_LOAD_IMAGE_COLOR);
  imageSourceV->clear();
  imencode(".bmp", image, *imageSourceV); // encode the Mat to bmp
  cout << "function invoked end" << endl;
}
