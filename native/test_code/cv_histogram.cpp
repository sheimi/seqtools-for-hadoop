#include <opencv2/opencv.hpp>
#include <vector>
#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <sstream>

using namespace std;
using namespace cv;

extern "C" void histogram(vector<unsigned char> *input, string *output) {
    // convert image binary to Mat
    cout << "function invoked start" << endl;
    Mat src = imdecode(*input, CV_LOAD_IMAGE_COLOR);
    Mat hsv;
    cvtColor(src, hsv, CV_BGR2HSV);

    int hbins = 30;
    int sbins = 32;
    int histSize[] = { hbins, sbins };
    float hranges[] = { 0, 180 };
    float sranges[] = { 0, 256 };
    const float *ranges[] = { hranges, sranges };
    MatND hist;

    cout << "function invoked internal 1" << endl;

    int channels[] = { 0, 1 };
    calcHist(&hsv, 1, channels, Mat(), hist, 2, histSize, ranges, true, false);

    double maxVal = 0;
    minMaxLoc(hist, 0, &maxVal, 0, 0);

    cout << "function invoked internal 2" << endl;

    int scale = 10;
    Mat histImg = Mat::zeros(sbins * scale, hbins * 10, CV_8UC3);

    stringstream ss;

    for (int h = 0; h < hbins; h++) {
        for (int s = 0; s < sbins; s++) {
            float binVal = hist.at<float>(h, s);
            int intensity = cvRound(binVal * 255 / maxVal);
            ss << intensity << " ";
        }
    }
    output->append(ss.str());
    cout << "function invoked end" << endl;
}
